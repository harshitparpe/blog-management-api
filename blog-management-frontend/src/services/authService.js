import api from '../api/axiosConfig'; export const registerUser=d=>api.post('/auth/register',d).then(r=>r.data);export const loginUser=d=>api.post('/auth/login',d).then(r=>r.data);
