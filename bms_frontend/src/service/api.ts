import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:2411',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default api;
