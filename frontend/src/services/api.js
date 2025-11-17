import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080",
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem("jwt");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});
// api.interceptors.response.use(
//   (response) => response,
//   (error) => {
//     if (error.response && error.response.status === 401) {
//       alert('Your session has expired. Please log in again.');
//       localStorage.removeItem('token');
//       localStorage.removeItem('email');
//       window.location.href = '/';
//     }
//     return Promise.reject(error);
//   }
// );

export default api;

