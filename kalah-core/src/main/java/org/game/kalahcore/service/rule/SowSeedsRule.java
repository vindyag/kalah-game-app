package org.game.kalahcore.service.rule;

import org.game.kalahcore.model.Game;
import org.game.kalahcore.model.Pit;

import java.util.List;

public class SowSeedsRule implements GameRule {

    @Override
    public boolean isApplicable(Game game, Integer pitIndex) {
        Pit pit = game.getPits().get(pitIndex);
        return pit.getSeedCount() > 0;
    }

    @Override
    public Integer apply(Game game, Integer pitIndex) {
        List<Pit> pits = game.getPits();
        Pit sowingPit = pits.get(pitIndex);
        int seedCountToMove = sowingPit.getSeedCount();
        sowingPit.setSeedCount(0);

        while (seedCountToMove > 0) {
            sowingPit = pits.get(sowingPit.getNextPitIndex());
            if(Boolean.TRUE.equals(sowingPit.isStore()) && !sowingPit.getPlayerId().equals(game.getNextPlayerId())) {
                continue; // do not add seeds to other players store
            }
            sowingPit.setSeedCount(sowingPit.getSeedCount() + 1);
            seedCountToMove--;
        }
        return sowingPit != null ? sowingPit.getPitIndex() : -1;
    }
}
