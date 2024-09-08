package org.game.kalahcore.validation

import org.game.kalahcore.exception.InvalidInputException
import org.game.kalahcore.service.helper.GameGenerationHelper
import org.game.kalahcore.service.validation.GameValidator
import spock.lang.Specification

class GameValidatorTest extends Specification {

    def gameValidator = new GameValidator()

    def "Validate Pit Index is a valid index on Move "() {
        given: "A game where the current player is 1"
            def game = GameGenerationHelper.generateGame()
        when: "Player tries to use an invalid pit index"
            def pitIndex = 15
            gameValidator.validateMove(game, pitIndex)
        then:
            final InvalidInputException ex = thrown(InvalidInputException.class)
            ex.getMessage() == "Selected pit index is invalid"
    }

    def "Validate Pit Index is not a store on Move "() {
        given: "A game where the current player is 1"
        def game = GameGenerationHelper.generateGame()
        when: "Player tries to sow using his Store"
        def pitIndex = 0 // Store of player 1
        gameValidator.validateMove(game, pitIndex)
        then:
        final InvalidInputException ex = thrown(InvalidInputException.class)
        ex.getMessage() == "Selected Pit is a store"
    }

    def "Validate Pit Index belongs to the player on Move "() {
        given: "A game where the current player is 1"
            def game = GameGenerationHelper.generateGame()
        when: "Player tries to move from a pit that does not belong to him"
            def pitIndex = 10 // belong to player 2
            gameValidator.validateMove(game, pitIndex)
        then:
            final InvalidInputException ex = thrown(InvalidInputException.class)
            ex.getMessage() == "Selected Pit does not belong to the current player"
    }

}
