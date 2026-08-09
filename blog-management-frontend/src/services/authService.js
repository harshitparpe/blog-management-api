import api from '../api/axiosConfig';

export const registerUser = (data) =>
    api.post('/api/auth/register', data).then((response) => response.data);

export const loginUser = (data) =>
    api.post('/api/auth/login', data).then((response) => response.data);
