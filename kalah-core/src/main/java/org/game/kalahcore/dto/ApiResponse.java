package org.game.kalahcore.dto;


import org.game.kalahcore.constants.ResponseStatus;

public record ApiResponse<T>(
        ResponseStatus status,
        T data,
        String message
        ){
}
