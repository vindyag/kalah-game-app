package org.game.kalahcore.service.helper;

import org.game.kalahcore.constants.GameStatus;
import org.game.kalahcore.model.Game;
import org.game.kalahcore.model.Pit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.game.kalahcore.constants.AppConstants.*;

@Service
public class GameGenerationHelper {

    public static Game generateGame() {
        Game game = new Game();
        game.setGameStatus(GameStatus.NOT_STARTED);
        game.setNextPlayerId(PLAYER_ONE);
        game.setCurrentPlayerId(null);
        game.setWinnerPlayerId(null);
        List<Pit> pits = new ArrayList<>(14);
        for (int i = 0; i < NO_OF_PITS; i++) {
            pits.add(generatePit(i));
        }
        game.setPits(pits);
        return game;
    }

    private static Pit generatePit(Integer index) {
        Pit pit = new Pit();
        pit.setPitIndex(index);
        pit.setStore(index == 6 || index == 13);
        if(Boolean.TRUE.equals(pit.isStore())) {
            pit.setSeedCount(0);
        } else {
            pit.setSeedCount(NO_OF_SEEDS_PER_PIT);
        }
        pit.setNextPitIndex((index + 1) % 14);
        if (index <= 6) {
            pit.setPlayerId(PLAYER_ONE);
        } else {
            pit.setPlayerId(PLAYER_TWO);
        }
        return pit;
    }
}
