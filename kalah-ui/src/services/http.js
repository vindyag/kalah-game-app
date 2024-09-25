const BASE_URL = `${process.env.VUE_APP_CORE_BASE_URL}/api/v1/games`

import axios from 'axios';

export const createNewGame = () => {
    return axios.post(BASE_URL)
        .then(response => [response.data, undefined])
        .catch(error => [undefined, error])
};

export const fetchGameData = (gameId) => {
    return axios.get(`${BASE_URL}/${gameId}`)
        .then(response => [response.data, undefined])
        .catch(error => [undefined, error])
};

export const makeMove = (gameId, pitIndex) => {
    return axios.patch(`${BASE_URL}/${gameId}/pits/${pitIndex}`)
        .then(response => [response.data, undefined])
        .catch(error => [undefined, error])
};