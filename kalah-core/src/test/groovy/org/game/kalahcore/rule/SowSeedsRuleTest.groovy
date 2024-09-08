package org.game.kalahcore.rule

import org.game.kalahcore.service.helper.GameGenerationHelper
import org.game.kalahcore.service.rule.SowSeedsRule
import spock.lang.Specification

class SowSeedsRuleTest extends Specification{

    def "Player 1 Sow seeds on a fresh game"(){
        given:
            def sowSeedsRule = new SowSeedsRule()
            def game = GameGenerationHelper.generateGame()

        when: "Player 1 Sow seeds using his first Pit"
            def result = sowSeedsRule.apply(game, 1)

        then:
            assert result == 5

    }

}
