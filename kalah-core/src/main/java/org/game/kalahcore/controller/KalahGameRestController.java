package org.game.kalahcore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.game.kalahcore.dto.ApiResponse;
import org.game.kalahcore.dto.GameDTO;
import org.game.kalahcore.service.KalahGameService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Kalah Game API")
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/games")
public class KalahGameRestController {

    private final KalahGameService kalahGameService;

    public KalahGameRestController(final KalahGameService kalahGameService) {
        this.kalahGameService = kalahGameService;
    }

    @Operation(summary = "Start a new Game")
    @PostMapping
    public ResponseEntity<EntityModel<ApiResponse<Long>>> startGame() {

        ApiResponse<Long> response = kalahGameService.startNewGame();

        EntityModel<ApiResponse<Long>> resource = EntityModel.of(response);
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(KalahGameRestController.class).loadGame(response.data())).withRel("loadGame"));

        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @Operation(summary = "Retrieve exiting Game by ID")
    @GetMapping("/{gameId}")
    public ResponseEntity<EntityModel<ApiResponse<GameDTO>>> loadGame(@PathVariable Long gameId) {

        ApiResponse<GameDTO> response = kalahGameService.loadGame(gameId);

        EntityModel<ApiResponse<GameDTO>> resource = EntityModel.of(response);
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(KalahGameRestController.class).loadGame(gameId)).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(KalahGameRestController.class).chosePit(gameId, null)).withRel("chosePit"));

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Move seeds from a Pit in a Game")
    @PatchMapping("/{gameId}/pits/{pitIndex}")
    public ResponseEntity<EntityModel<ApiResponse<GameDTO>>> chosePit(@PathVariable Long gameId, @PathVariable Integer pitIndex) {

        ApiResponse<GameDTO> apiResponse = kalahGameService.sowSeedsInPit(gameId,pitIndex);

        EntityModel<ApiResponse<GameDTO>> resource = EntityModel.of(apiResponse);
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(KalahGameRestController.class).loadGame(gameId)).withRel("loadGame"));
        resource.add(WebMvcLinkBuilder.linkTo(methodOn(KalahGameRestController.class).chosePit(gameId, pitIndex)).withSelfRel());

        return ResponseEntity.ok(resource);
    }
}
