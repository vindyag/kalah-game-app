package org.game.kalahcore.service.rule;

import org.game.kalahcore.model.Game;

public interface GameRule {

    boolean isApplicable(Game game, Integer pitIndex);

    Integer apply(Game game, Integer pitIndex);
}
