package org.game.kalahcore.service.rule;

import org.game.kalahcore.model.Game;
import org.game.kalahcore.model.Pit;

public class ExtraTurnRule implements GameRule {

    /*
    If the last sown seed lands in the player's store, the player gets an additional move.
     */
    @Override
    public boolean isApplicable(Game game, Integer pitIndex) {
        Pit pit = game.getPits().get(pitIndex);
        return pit.isStore() && pit.getPlayerId().equals(game.getCurrentPlayerId());
    }

    @Override
    public Integer apply(Game game, Integer pitIndex) {
        Integer currentPitIndex = pitIndex;
        do {
            currentPitIndex = new SowSeedsRule().apply(game, currentPitIndex);
        } while (this.isApplicable(game, currentPitIndex));
        return currentPitIndex;
    }
}
