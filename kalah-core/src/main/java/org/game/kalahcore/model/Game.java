package org.game.kalahcore.model;

import jakarta.persistence.*;
import org.game.kalahcore.constants.GameStatus;

import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;
    private GameStatus gameStatus;
    private Integer currentPlayerId;
    private Integer nextPlayerId;
    private Integer winnerPlayerId;
/*
In JPA, arrays are not directly supported as attribute types for entity fields.
Instead, you should use a List or another collection type.
This is because JPA needs to manage the relationship between the entity and
the collection elements,
which is not straightforward with arrays.
 */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pit> pits ;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Integer getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(Integer currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }


    public Integer getNextPlayerId() {
        return nextPlayerId;
    }

    public void setNextPlayerId(Integer nextPlayerId) {
        this.nextPlayerId = nextPlayerId;
    }

    public Integer getWinnerPlayerId() {
        return winnerPlayerId;
    }

    public void setWinnerPlayerId(Integer currentPlayerId) {
        this.winnerPlayerId = currentPlayerId;
    }

    public List<Pit> getPits() {
        return pits;
    }

    public void setPits(List<Pit> pits) {
        this.pits = pits;
    }
}
