package org.game.kalahcore.controller;

import org.game.kalahcore.dto.ApiResponse;
import org.game.kalahcore.dto.GameDTO;
import org.game.kalahcore.service.KalahGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.game.kalahcore.constants.AppConstants.URI_PREFIX;

@CrossOrigin("*")
@RestController
@RequestMapping(URI_PREFIX)
public class KalahGameRestController {

    private final KalahGameService kalahGameService;

    public KalahGameRestController(final KalahGameService kalahGameService) {
        this.kalahGameService = kalahGameService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> startGame() throws URISyntaxException {
        ApiResponse<Long> response = kalahGameService.startNewGame();
        URI location = new URI(URI_PREFIX + response.data());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDTO> loadgame(@PathVariable Long gameId) {
        GameDTO game = kalahGameService.loadGame(gameId);
        return ResponseEntity.ok(game);
    }

    @PatchMapping("/{gameId}/pits/{pitIndex}")
    public ResponseEntity<ApiResponse<GameDTO>> chosePit(@PathVariable Long gameId, @PathVariable Integer pitIndex) {
        ApiResponse<GameDTO> apiResponse = kalahGameService.sowSeedsInPit(gameId,pitIndex);
        return ResponseEntity.ok(apiResponse);
    }
}
