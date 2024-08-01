import { Button, Form, Input, message, Row } from "antd";
import axios from "axios";
import { memo, useState } from "react";
import { Link, useNavigate } from "react-router-dom"

export const ResetPassword = memo(() => {
    const [loading, setLoading] = useState(false);//Set loading to avoid send many request at the same time
    const navigate = useNavigate();

    const onFinish = async (values) => {
        try {
            setLoading(true);

            const response = await axios.post('http://localhost:8080/auth/reset-password', values,
                {
                    params: {
                        email: values.email,
                    },
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                
            // Kiểm tra trạng thái thành công từ server
            if (response.status === 200) {
                // Chuyển hướng đến trang update-password khi thành công
                navigate('/auth/update-password');
            } else {
                // Xử lý khi có lỗi từ server, ví dụ hiển thị thông báo lỗi
                message.error('Reset password failed. Please try again.');
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
            <h1>Forgot password?</h1>
            <Form name="reset-password" initialValues={{ remember: true }} onFinish={onFinish}>
                <Form.Item
                    name="email"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your username!',
                        },
                    ]}
                >
                    <Input className="input-box" placeholder="Email" />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Send request
                    </Button>
                </Form.Item>
                <Row className="justify-content-end">
                    <Link style={{ color: "#fff" }} to={"/auth/login"}>Cancel</Link>
                </Row>
            </Form>
        </div>
    )
})