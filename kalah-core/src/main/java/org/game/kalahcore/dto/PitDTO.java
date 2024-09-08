package org.game.kalahcore.dto;

public record PitDTO(
        Integer pitIndex,
        Integer noOfSeeds,
        Integer playerId,
        Boolean isStore,
        Integer nextPitIndex
) {


}
