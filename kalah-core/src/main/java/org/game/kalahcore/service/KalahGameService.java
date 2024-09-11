package org.game.kalahcore.service;

import org.game.kalahcore.constants.GameStatus;
import org.game.kalahcore.constants.ResponseStatus;
import org.game.kalahcore.dto.ApiResponse;
import org.game.kalahcore.dto.GameDTO;
import org.game.kalahcore.dto.PitDTO;
import org.game.kalahcore.exception.InvalidInputException;
import org.game.kalahcore.model.Game;
import org.game.kalahcore.model.Pit;
import org.game.kalahcore.repository.GameRepository;
import org.game.kalahcore.service.validation.GameValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.game.kalahcore.constants.AppConstants.PLAYER_ONE;
import static org.game.kalahcore.constants.AppConstants.PLAYER_TWO;
import static org.game.kalahcore.service.helper.GameGenerationHelper.generateGame;

@Service
public class KalahGameService {

    private final GameRepository gameRepository;
    private final GameValidator gameValidator;
    private final RuleEngine ruleEngine;

    public KalahGameService(final GameValidator gameValidator, final GameRepository gameRepository, final RuleEngine ruleEngine) {
        this.gameValidator = gameValidator;
        this.gameRepository = gameRepository;
        this.ruleEngine = ruleEngine;
    }

    public ApiResponse<Long> startNewGame() {
        Game savedGame = gameRepository.save(generateGame());
        return new ApiResponse<>(ResponseStatus.SUCCESS, savedGame.getGameId(), null);
    }


    public ApiResponse<GameDTO> loadGame(Long gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            return new ApiResponse<>(ResponseStatus.SUCCESS, getGameGTO(gameOptional.get()), null);
        }
        else {
            throw new InvalidInputException("Game not found");
        }
    }

    public ApiResponse<GameDTO> sowSeedsInPit(Long gameId, Integer pitIndex) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();

            if (GameStatus.NOT_STARTED.equals(game.getGameStatus())) {
                game.setGameStatus(GameStatus.IN_PROGRESS);
            }
            gameValidator.validateMove(game, pitIndex);

            game.setCurrentPlayerId(game.getNextPlayerId());
            game.setNextPlayerId(getNextPlayerId(game.getCurrentPlayerId()));

            ruleEngine.applyRules(game, pitIndex);

            game.setCurrentPlayerId(null);

            Game savedGame = gameRepository.save(game);
            GameDTO gameDTO = getGameGTO(savedGame);
            return new ApiResponse<>(ResponseStatus.SUCCESS, gameDTO, null);
        } else {
            throw new InvalidInputException("Game not found");
        }
    }

    private GameDTO getGameGTO(Game game) {
        List<PitDTO> pits = getGamePitDTOs(game.getPits());
        return new GameDTO(
                game.getGameId(),
                game.getGameStatus(),
                game.getNextPlayerId(),
                game.getWinnerPlayerId(),
                pits
        );
    }

    private List<PitDTO> getGamePitDTOs(List<Pit> pits) {
        return pits.stream()
                .map(pit -> new PitDTO(
                        pit.getPitIndex(),
                        pit.getSeedCount(),
                        pit.getPlayerId(),
                        pit.isStore(),
                        pit.getNextPitIndex()))
                .collect(Collectors.toList());
    }

    public Integer getNextPlayerId(Integer currentPlayerId) {
        return currentPlayerId.equals(PLAYER_ONE) ? PLAYER_TWO : PLAYER_ONE;
    }
}
