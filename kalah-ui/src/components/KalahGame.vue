<template>
  <div id="game">
    <h1>Kalah Game</h1>

    <button class="start-button" @click="startGame">Start New Game</button>

    <div v-if="gameData" class="game-board">
      <h3>Game Board</h3>

      <div class="player-row">
        <div class="store" v-if="player1Store">
          <div>P1 Store</div>
          <div>{{ player1Store.noOfSeeds }}</div>
        </div>
        <div class="pit" v-for="(pit, index) in player1PitsReversed" :key="index" @click="makeMove(pit.pitIndex)">
          <div>P1 {{ pit.pitIndex }}</div>
          <div>{{ pit.noOfSeeds }}</div>
        </div>
      </div>
      <div class="player-row">
        <div class="pit" v-for="(pit, index) in player2Pits" :key="index" @click="makeMove(pit.pitIndex)">
          <div>P2 {{ pit.pitIndex }}</div>
          <div>{{ pit.noOfSeeds }}</div>
        </div>
        <div class="store" v-if="player2Store">
          <div>P2 Store</div>
          <div>{{ player2Store.noOfSeeds }}</div>
        </div>
      </div>
    </div>

    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

  </div>
</template>

<script>
import {createNewGame, fetchGameData, makeMove} from '@/services/http';

export default {
  data() {
    return {
      gameId: null,
      gameData: null,
      errorMessage: null,
    };
  },
  computed: {
    player1Pits() {
      return this.gameData ? this.gameData.pits.filter(pit => !pit.isStore && pit.playerId === 1) : [];
    },
    player2Pits() {
      return this.gameData ? this.gameData.pits.filter(pit => !pit.isStore && pit.playerId === 2) : [];
    },
    player1PitsReversed() {
      return this.player1Pits.slice().reverse();
    },
    player1Store() {
      return this.gameData ? this.gameData.pits.find(pit => pit.isStore && pit.playerId === 1) : null;
    },
    player2Store() {
      return this.gameData ? this.gameData.pits.find(pit => pit.isStore && pit.playerId === 2) : null;
    }
  },
  methods: {
    async startGame() {

      const [data, error] = await createNewGame();

      if (error) {
        console.log(error)
        this.errorMessage = 'Error creating game';
      }
      if (data) {
        console.log(data.data);
        this.gameId = data.data;
      }

      await this.getGameData();

    },
    async getGameData() {
      if (this.gameId) {
        const [data, error] = await fetchGameData(this.gameId);
        if (error) {
          console.log(error)
          this.errorMessage = 'Error fetching game data';
        }
        if (data) {
          console.log(data.data);
          this.gameData = data.data;
          this.errorMessage = undefined;
        }
      }
    },
    async makeMove(pitIndex) {
      if (this.gameId) {
        const [data, error] = await makeMove(this.gameId, pitIndex);

        if (error) {
          console.log(error)
          this.errorMessage = 'Error making move';
        }
        if (data) {
          console.log(data.data);
          this.gameData = data.data;
          this.errorMessage = undefined;
        }
      }
    }
  },
};
</script>

<style scoped>
#game {
  font-family: Arial, sans-serif;
  text-align: center;
  margin-top: 50px;
}

.start-button {
  padding: 20px 25px;
  background-color: silver;
  border: 0.5px solid #000;
  font-size: 15px;
}

.error-message {
  color: red;
  margin-top: 20px;
}

.game-board {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

.player-row {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.pit, .store {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100px;
  height: 100px;
  padding: 10px;
  margin: 5px;
  text-align: center;
  background-color: #f0f0f0;
  border: 1px solid #000;
  box-sizing: border-box;
}

.store {
  font-weight: bold;
}
</style>