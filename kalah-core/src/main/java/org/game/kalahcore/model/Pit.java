package org.game.kalahcore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pitId;
    private Integer pitIndex;
    private Integer seedCount;
    private Integer playerId;
    private Boolean isStore;
    private Integer nextPitIndex;

    public int getPitId() {
        return pitIndex;
    }

    public int getPitIndex() {
        return pitIndex;
    }

    public void setPitIndex(Integer pitIndex) {
        this.pitIndex = pitIndex;
    }

    public Integer getSeedCount() {
        return seedCount;
    }

    public void setSeedCount(Integer seedCount) {
        this.seedCount = seedCount;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Boolean isStore() {
        return isStore;
    }

    public void setStore(Boolean store) {
        isStore = store;
    }

    public Integer getNextPitIndex() {
        return nextPitIndex;
    }

    public void setNextPitIndex(Integer nextPitIndex) {
        this.nextPitIndex = nextPitIndex;
    }
}
