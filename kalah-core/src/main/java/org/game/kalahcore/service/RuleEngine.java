package org.game.kalahcore.service;

import org.game.kalahcore.model.Game;
import org.game.kalahcore.service.rule.EndGameRule;
import org.game.kalahcore.service.rule.ExtraTurnRule;
import org.game.kalahcore.service.rule.GameRule;
import org.game.kalahcore.service.rule.SowSeedsRule;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RuleEngine {

    private final List<GameRule> gameRules;

    public RuleEngine() {
        this.gameRules = Arrays.asList(
                new SowSeedsRule(),
                new ExtraTurnRule(),
                new EndGameRule()
        );
    }

    public void applyRules(Game game, Integer pitIndex) {
        for (GameRule gameRule : gameRules) {
            if (gameRule.isApplicable(game, pitIndex)) {
                pitIndex = gameRule.apply(game, pitIndex);
            }
        }
    }
}
