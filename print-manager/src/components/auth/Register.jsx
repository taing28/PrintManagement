import { memo, useState } from "react";
import { Button, DatePicker, Form, Input, Row, message } from "antd"
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios'

export const Register = memo(() => {
    const [loading, setLoading] = useState(false);//Set loading to avoid send many request at the same time
    const navigate = useNavigate();

    const onFinish = async (values) => {
        try {
            setLoading(true);

            const response = await axios.post('http://localhost:8080/auth/register', values, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            // Kiểm tra trạng thái thành công từ server
            if (response.status === 200) {
                // Chuyển hướng đến trang update-password khi thành công
                navigate('/auth/login');
            } else {
                // Xử lý khi có lỗi từ server, ví dụ hiển thị thông báo lỗi
                message.error('Sign up failed. Please try again.');
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
            <h1>Sign up</h1>
            <Form name="login" onFinish={onFinish}>
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

                <Form.Item
                    name="cfPassword"
                    rules={[
                        {
                            required: true,
                            message: 'Please re enter your password!',
                        },
                    ]}
                >
                    <Input.Password className="input-box" placeholder="Confirm password" />
                </Form.Item>

                <Form.Item
                    name="fullname"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your fullname!',
                        },
                    ]}
                >
                    <Input className="input-box" placeholder="Fullname" />
                </Form.Item>

                <Form.Item
                    name="dateOfBirth"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your dob!',
                        },
                    ]}
                >
                    <DatePicker className="input-box" />
                </Form.Item>

                <Form.Item
                    name="email"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your email!',
                        },
                        {
                            type: 'email',
                            message: 'The input is not valid E-mail!',
                        },
                    ]}
                >
                    <Input className="input-box" placeholder="Email" />
                </Form.Item>

                <Form.Item
                    name="phoneNumber"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your phone!',
                        },
                        {
                            pattern: /^0\d{9}$/, // Pattern ensures the number starts with 0 followed by 9 digits
                            message: 'The input is not a valid phone number!',
                        },
                    ]}
                >
                    <Input className="input-box" placeholder="Phone number" />
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Sign up
                    </Button>
                </Form.Item>
                <Row className="justify-content-end">
                    <Link style={{ color: "#fff" }} to={"/auth/login"}>Cancel</Link>
                </Row>
            </Form>
        </div>
    )
})