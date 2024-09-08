package org.game.kalahcore.service.validation;

import org.game.kalahcore.exception.InvalidInputException;
import org.game.kalahcore.model.Game;
import org.game.kalahcore.model.Pit;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.game.kalahcore.constants.AppConstants.NO_OF_PITS;

@Service
public class GameValidator {

    public void validateMove(Game game, Integer pitIndex) throws InvalidInputException {
        if (pitIndex < 0 || pitIndex > NO_OF_PITS) {
            throw new InvalidInputException("Selected pit index is invalid");
        }

        Pit pit = game.getPits().get(pitIndex);
        if (Boolean.TRUE.equals(pit.isStore())) {
            throw new InvalidInputException("Selected Pit is a store");
        }

        if (!Objects.equals(game.getNextPlayerId(), pit.getPlayerId())) { //check
            throw new InvalidInputException("Selected Pit does not belong to the current player");
        }
    }

}
