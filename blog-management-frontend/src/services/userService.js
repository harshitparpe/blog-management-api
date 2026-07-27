import api from '../api/axiosConfig'; export const getProfile=()=>api.get('/users/profile').then(r=>r.data);export const updateProfile=d=>api.put('/users/profile',d).then(r=>r.data);
