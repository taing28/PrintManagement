import { memo, useEffect } from "react";
import { useNavigate } from "react-router-dom"
import axiosInstance from "../config/AxiosConfig";

export const Home = memo(() => {
    const navigate = useNavigate();

    useEffect(() => {
      const checkToken = async () => {
        try {
          const response = await axiosInstance.get('/'); // Endpoint để kiểm tra token
          if (response.status !== 200) {
            throw new Error('Token không hợp lệ');
          } 
        } catch (error) {
          localStorage.removeItem('authToken'); // Xóa token nếu không hợp lệ
          navigate('/auth/login'); // Chuyển hướng đến trang đăng nhập
        }
      };
  
      checkToken();
    }, [navigate]);

    return (
        <>
        Home
        </>
    )
})