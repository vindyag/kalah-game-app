package org.game.kalahcore.dto;

import org.game.kalahcore.constants.GameStatus;

import java.util.List;

public record GameDTO(Long gameId,
                      GameStatus gameStatus,
                      Integer nextPlayerId,
                      Integer winnerPlayerId,
                      List<PitDTO> pits) {

}