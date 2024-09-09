package org.game.kalahcore.service.rule;

import org.game.kalahcore.constants.GameStatus;
import org.game.kalahcore.model.Game;
import org.game.kalahcore.model.Pit;

import java.util.Optional;

public class EndGameRule implements GameRule {

    /*When one player no longer has any seeds in any of their houses, the game ends. */
    @Override
    public boolean isApplicable(Game game, Integer pitIndex) {
        Integer currentPlayerId = game.getCurrentPlayerId();
        Optional<Pit> pitsWithNonEmptySeedsOfPlayer =  game.getPits().stream().filter(
                pit -> pit.getPlayerId().equals(currentPlayerId) && pit.getSeedCount() > 0
        ).findAny();

        return pitsWithNonEmptySeedsOfPlayer.isEmpty();
    }

    @Override
    public Integer apply(Game game, Integer pitIndex) {
        game.setGameStatus(GameStatus.FINISHED);
        game.setWinnerPlayerId(game.getCurrentPlayerId());
        game.setCurrentPlayerId(null);
        game.setNextPlayerId(null);
        return -1;
    }
}
