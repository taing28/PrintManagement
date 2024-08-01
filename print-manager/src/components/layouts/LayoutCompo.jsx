import { memo, useEffect } from "react";
import { HeaderCompo } from "./HeaderCompo";
import axiosInstance from "../config/AxiosConfig";
import Cookies from 'js-cookie'
import { Link, Outlet, useLocation, useNavigate } from "react-router-dom";
import { useUser } from "../config/UserContext";
import { Layout } from "antd";
const { Header, Content, Sider } = Layout;

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
                const response = await axiosInstance.get('/', {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                });
                console.log('User', response.data);
                setUser(response.data)
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
            <Header className="p-0 fixed-top">
                <HeaderCompo />
            </Header>
            <Sider
                className="fixed-sider"
                width={200}
                style={{ padding: '12px' }}
            >
                <Link to={'/'} className="list-sider-items">
                    <div className="btn btn-success w-100 mt-2">
                        Home
                    </div>
                </Link>
                <Link to={'/teams'} className="list-sider-items"
                    hidden={!user.authorities?.some((value) => {
                        return value === 'ROLE_ADMIN' || value === 'ROLE_MANAGER';
                    })}
                >
                    <div className="btn btn-success w-100 mt-2">
                        Manage Team
                    </div>
                </Link>
                <Link to={'/users'} className="list-sider-items"
                    hidden={!user.authorities?.some((value) => {
                        return value === 'ROLE_ADMIN';
                    })}
                >
                    <div className="btn btn-success w-100 mt-2">
                        Manage User
                    </div>
                </Link>
                <Link to={'/projects'} className="list-sider-items">
                    <div className="btn btn-success w-100 mt-2">
                        Manage Project
                    </div>
                </Link>
            </Sider>
            <Layout
                className="content-body"
            >
                <Content
                    style={{
                        padding: '0 24px',
                        minHeight: '100vh',
                    }}
                >
                    <Outlet />
                </Content>
            </Layout>
        </div>
    )
})