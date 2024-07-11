import { memo, useState } from "react";
import { Button, Form, Input, Row, message } from "antd"
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios'

export const UpdatePassword = memo(() => {
    const [loading, setLoading] = useState(false);//Set loading to avoid send many request at the same time
    const navigate = useNavigate();

    const onFinish = async (values) => {
        try {
            setLoading(true);

            const response = await axios.post('http://localhost:8080/auth/update-password', values, {
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
                message.error('Update password failed. Please try again.');
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
            <h1>New password</h1>
            <Form name="updatePassword" initialValues={{ remember: true }} onFinish={onFinish}>
                <Form.Item
                    name="confirmCode"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your confirm code!',
                        },
                    ]}
                >
                    <Input className="input-box" placeholder="Confirm code" />
                </Form.Item>

                <Form.Item
                    name="newPassword"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your new password!',
                        },
                    ]}
                >
                    <Input.Password className="input-box" placeholder="New password" />
                </Form.Item>

                <Form.Item
                    name="confirmPassword"
                    rules={[
                        {
                            required: true,
                            message: 'Please re-enter your new password!',
                        },
                    ]}
                >
                    <Input.Password className="input-box" placeholder="Confirm password" />
                </Form.Item>

                <Form.Item>
                    <Button type="primary" htmlType="submit" loading={loading}>
                        Submit
                    </Button>
                </Form.Item>
                <Row className="justify-content-end">
                    <Link style={{ color: "#fff" }} to={"/auth/login"}>Cancel</Link>
                </Row>
            </Form>
        </div>
    )
})