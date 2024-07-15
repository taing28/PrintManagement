import axios from 'axios';
import Cookies from 'js-cookie';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080', // Thay đổi URL API của bạn ở đây
    headers: {
        'Content-Type': 'application/json',
    },
});

// Thêm interceptor để thêm token vào header của tất cả các yêu cầu
axiosInstance.interceptors.request.use(
    (config) => {
        const token = Cookies.get('authToken');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default axiosInstance;