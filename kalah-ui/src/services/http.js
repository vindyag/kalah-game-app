const BASE_URL = 'http://localhost:8080/api/v1/games';

import axios from 'axios';

export const createNewGame = () => {
    return axios.post(BASE_URL);
};

export const fetchGameData = (gameId) => {
    return axios.get(`${BASE_URL}/${gameId}`);
};

export const makeMove = (gameId, pitIndex) => {
    return axios.patch(`${BASE_URL}/${gameId}/pits/${pitIndex}`);
};