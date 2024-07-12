import axios from 'axios';

// Tạo instance của axios
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080', // Thay đổi base URL cho phù hợp
});

// Thêm interceptor để thêm token vào header của mỗi request
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken'); // Giả sử bạn lưu token trong localStorage
    console.log(token);
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