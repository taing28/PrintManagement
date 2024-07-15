import { memo, useEffect } from "react";
import { HeaderCompo } from "./HeaderCompo";
import axiosInstance from "../config/AxiosConfig";
import Cookies from 'js-cookie'
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { useUser } from "../config/UserContext";

export const LayoutCompo = memo(() => {
    const location = useLocation(); // Lấy thông tin về location (path)
    const navigate = useNavigate();
    const { user, setUser } = useUser();

    useEffect(() => {
        const checkToken = async () => {
            const token = Cookies.get('authToken'); // Lấy token từ cookie

            if (!token) {
                navigate('/auth/login'); // Chuyển hướng đến trang đăng nhập nếu không có token
                return;
            }

            try {
                // Thêm token vào header của yêu cầu API
                const response = await axiosInstance.get(location.pathname, {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                });

                console.log(response.data);
                setUser(response.data)
                console.log('user:',user);
                if (response.status !== 200) {
                    throw new Error('Token không hợp lệ');
                }
            } catch (error) {
                Cookies.remove('authToken'); // Xóa token nếu không hợp lệ
                navigate('/auth/login'); // Chuyển hướng đến trang đăng nhập
            }
        };

        checkToken();
    }, [navigate, location.pathname]);

    return (
        <div>
            <HeaderCompo />
            <Outlet />
        </div>
    )
})