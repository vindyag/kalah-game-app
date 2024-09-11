package org.game.kalahcore.controller;

import org.game.kalahcore.constants.ResponseStatus;
import org.game.kalahcore.dto.ApiResponse;
import org.game.kalahcore.dto.GameDTO;
import org.game.kalahcore.service.KalahGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse> startGame() {
        ApiResponse<Long> response = kalahGameService.startNewGame();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<ApiResponse> loadGame(@PathVariable Long gameId) {
        GameDTO game = kalahGameService.loadGame(gameId);
        ApiResponse<GameDTO> apiResponse = new ApiResponse<>(ResponseStatus.SUCCESS, game, null);
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{gameId}/pits/{pitIndex}")
    public ResponseEntity<ApiResponse<GameDTO>> chosePit(@PathVariable Long gameId, @PathVariable Integer pitIndex) {
        ApiResponse<GameDTO> apiResponse = kalahGameService.sowSeedsInPit(gameId,pitIndex);
        return ResponseEntity.ok(apiResponse);
    }
}
