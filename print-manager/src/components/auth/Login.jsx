import { memo, useState } from "react";
import { Button, Form, Input, Row, message } from "antd"
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios'

export const Login = memo(() => {
    const [loading, setLoading] = useState(false);//Set loading to avoid send many request at the same time
    const navigate = useNavigate();

    const onFinish = async (values) => {
        try {
            setLoading(true);

            const response = await axios.post('http://localhost:8080/auth/login', values, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            // Kiểm tra trạng thái thành công từ server
            if (response.status === 200) {
                console.log(response.data);
                // Chuyển hướng đến trang update-password khi thành công
                navigate('/');
            } else {
                // Xử lý khi có lỗi từ server, ví dụ hiển thị thông báo lỗi
                message.error('Login failed. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error); // Handle error
            message.error(error.response.data);
        } finally {
            setLoading(false); // Kết thúc trạng thái loading sau khi hoàn thành yêu cầu
        }
    };

    return (
        <div>
            <h1>Login</h1>
            <Form name="login" initialValues={{ remember: true }} onFinish={onFinish}>
                <Form.Item
                    name="username"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your username!',
                        },
                    ]}
                >
                    <Input className="input-box" placeholder="Username" />
                </Form.Item>
                <Form.Item
                    name="password"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your password!',
                        },
                    ]}
                >
                    <Input.Password className="input-box" placeholder="Password" />
                </Form.Item>
                <Row className="register-forgot">
                    <Link to={"/auth/register"}>Register</Link>
                    <Link to={"/auth/reset-password"}>Forgot password</Link>
                </Row>
                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Login
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )
})