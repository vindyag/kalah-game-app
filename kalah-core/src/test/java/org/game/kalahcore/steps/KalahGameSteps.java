package org.game.kalahcore.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.game.kalahcore.constants.GameStatus;
import org.game.kalahcore.constants.ResponseStatus;
import org.game.kalahcore.dto.ApiResponse;
import org.game.kalahcore.dto.GameDTO;
import org.game.kalahcore.dto.PitDTO;
import org.game.kalahcore.service.KalahGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = {KalahGameService.class}, loader = AnnotationConfigContextLoader.class)
public class KalahGameSteps {

    private Long gameId;
    private Integer sowingPitIndex;

    @Autowired
    private KalahGameService gameService;

    @Given("A New Game is started")
    public void a_new_game_is_started() {
        ApiResponse<Long> response = gameService.startNewGame();
        gameId = response.data();
        assertNotNull(gameId);
        assertEquals(ResponseStatus.SUCCESS, response.status());
    }

    /*
     *  Kalah New Game Board
     *  ----------------
     *  | 6 | 5 | 4 |  3 |  2 |  1 |  0 |
     *  |(0)|(4)|(4)| (4)| (4)| (4)| (4)|
     *  |---|---|---|----|----|----|----|
     *  | 7 | 8 | 9 | 10 | 11 | 12 | 13 |
     *  |(4)|(4)|(4)| (4)| (4)| (4)| (0)|
     *  ----------------
     *  Player 1's pits: 0 to 6 - store 6th
     *  Player 2's pits: 7 to 13 - store 13th
     */
    @Then("Game should reflect the initial game state")
    public void game_should_reflect_the_initial_game_state() {
        ApiResponse<GameDTO> response = gameService.loadGame(gameId);
        GameDTO game = response.data();
        assertNotNull(game);
        assertNotNull(game.gameId());
        assertEquals(GameStatus.NOT_STARTED, game.gameStatus());
        assertEquals(14, game.pits().size());
        List<PitDTO> pits = game.pits();
        pits.forEach(pit -> {
            if (pit.isStore()) {
                assertEquals(0, (int) pit.noOfSeeds());
            } else {
                assertEquals(4, (int) pit.noOfSeeds());
            }
        });
    }

    @When("Player makes a move by sowing seeds in Pit {int}")
    public void player_makes_a_move_by_sowing_seeds_in_pit(int pitIndex) {
        sowingPitIndex = pitIndex;
        ApiResponse<GameDTO> response = gameService.sowSeedsInPit(gameId, pitIndex);
        assertEquals(ResponseStatus.SUCCESS, response.status());
    }

    /*
     *  Kalah New Game Board
     *  ----------------
     *  | 6 | 5 | 4 |  3 |  2 |  1 |  0 |
     *  |(0)|(5)|(5)| (5)| (5)| (0)| (4)|
     *  |---|---|---|----|----|----|----|
     *  | 7 | 8 | 9 | 10 | 11 | 12 | 13 |
     *  |(4)|(4)|(4)| (4)| (4)| (4)| (0)|
     *  ----------------
     *  Player 1's pits: 0 to 6 - store 6th
     *  Player 2's pits: 7 to 13 - store 13th
     */
    @Then("Game should reflect the move")
    public void game_should_reflect_the_move() {
        ApiResponse<GameDTO> response = gameService.loadGame(gameId);
        GameDTO game = response.data();
        assertNotNull(game);
        assertNotNull(game.gameId());
        assertEquals(GameStatus.IN_PROGRESS, game.gameStatus());
        List<PitDTO> pits = game.pits();
        assertEquals(0, (int) pits.get(sowingPitIndex).noOfSeeds());
        assertEquals(5, (int) pits.get(sowingPitIndex+1).noOfSeeds());
        assertEquals(5, (int) pits.get(sowingPitIndex+2).noOfSeeds());
        assertEquals(5, (int) pits.get(sowingPitIndex+3).noOfSeeds());
        assertEquals(5, (int) pits.get(sowingPitIndex+4).noOfSeeds());
        assertEquals(0, (int) pits.get(sowingPitIndex+5).noOfSeeds());
    }

}
