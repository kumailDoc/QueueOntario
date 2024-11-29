import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080", // backend base URL
  withCredentials: true,
});

// Request interceptor
apiClient.interceptors.request.use(
  (config) => {
    const token = JSON.parse(localStorage.getItem("userInfo"))?.accessToken;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default apiClient;
