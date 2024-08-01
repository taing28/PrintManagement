import { Form, Input, message, Modal, Popconfirm, Row, Select } from "antd";
import { Table } from "react-bootstrap"
import Search from "antd/es/transfer/search";
import { memo, useEffect, useState } from "react";
import axiosInstance from "../config/AxiosConfig";
import { useNavigate } from "react-router-dom";
import { useUser } from "../config/UserContext";

export const User = memo(() => {
    const [userList, setUserList] = useState([]);
    const [teamList, setTeamList] = useState([]);
    const [submitStatus, setSubmitStatus] = useState(false);
    const [formTeam] = Form.useForm();
    //Check role
    const navigate = useNavigate();
    const { user } = useUser();
    if (user.authorities?.some((value) => {
        return value === 'ROLE_ADMIN';
    }) ? false : true) {
        navigate('/auth/login');
    }

    // Fetch teams
    useEffect(() => {
        const fetchTeams = async () => {
            try {
                const response = await axiosInstance.get('/teams');
                setTeamList(response.data)
            } catch (err) {
                message.error(err.response?.data || 'Error fetching teams');
            }
        }

        fetchTeams();
    }, [])

    //Fetch users
    const fetchUsers = async () => {
        try {
            const response = await axiosInstance.get('/users');
            setUserList(response.data)
        } catch (err) {
            message.error(err.response?.data || 'Error fetching users');
        }
    }
    //re-render
    useState(() => {
        fetchUsers();
    }, [])

    //Handle Delete
    const confirm = async (userId) => {
        try {
            await axiosInstance.put(`/users/${userId}`);
            message.success('User status change successfully');
            // Gọi lại hàm fetchTeams để cập nhật danh sách các đội
            fetchUsers();
        } catch (error) {
            console.error('Error change status user:', error);
            message.error('Failed to change status user');
        }
    };

    const cancel = (e) => {
        console.log(e);
        message.error('Click on No');
    };

    //Handle Change Team
    const [isModalTeamOpen, setIsModalTeamOpen] = useState(false);
    const showModalTeam = (user) => {
        formTeam.setFieldsValue({
            userId: user.id,
            teamId: user.team.id,
        });
        setIsModalTeamOpen(true);
    };

    const submitTeam = async (values) => {
        try {
            setSubmitStatus(true);

            const response = await axiosInstance.put('/users/change-team', values, {
                params: {
                    userId: values.userId,
                    teamId: values.teamId,
                },
            });

            // Kiểm tra trạng thái thành công từ server
            if (response.status === 200) {
                fetchUsers();
                setIsModalTeamOpen(false);
            } else {
                // Xử lý khi có lỗi từ server, ví dụ hiển thị thông báo lỗi
                message.error('Change failed. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error); // Handle error
            message.error(error.response.data);
        } finally {
            setSubmitStatus(false); // Kết thúc trạng thái loading sau khi hoàn thành yêu cầu
        }
    }

    const handleTeamCancel = () => {
        setIsModalTeamOpen(false);
    };

    return (
        <div>
            <Row className="mt-2 justify-content-between">
                <div className="search-box">
                    <Search
                        placeholder="input search text"
                        allowClear
                        onSearch={() => console.log('Building search method')}
                        style={{
                            width: 200,
                        }}
                    />
                </div>
                <button className="btn btn-success" >Future function</button>
            </Row>
            <Table className="mt-3" striped bordered hover variant="dark">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Full name</th>
                        <th>DOB</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Team</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {userList.map((user, index) => {
                        return (
                            <tr key={index} hidden={!user.isActive}>
                                <td>{user.username}</td>
                                <td>{user.name}</td>
                                <td>{user.dateOfBirth}</td>
                                <td>{user.email}</td>
                                <td>{user.phoneNumber}</td>
                                <td>{user.team.name}</td>
                                <td>
                                    <div className="d-flex justify-content-evenly" hidden={user.username === 'admin'}>
                                        <button className="btn btn-warning ">Role</button>
                                        <button className="btn btn-success" onClick={() => showModalTeam(user)}>Team</button>
                                        <Popconfirm
                                            title="Disable the user"
                                            description="Are you sure to ban this user?"
                                            onConfirm={() => confirm(user.id)}
                                            onCancel={cancel}
                                            okText="Yes"
                                            cancelText="No"
                                        >
                                            <button className="btn btn-danger">Ban</button>
                                        </Popconfirm>
                                    </div>
                                </td>
                            </tr>
                        )
                    })}
                </tbody>
            </Table>
            <h3 style={{ color: '#fff' }}>Disabled users:</h3>
            <Table className="mt-3" striped bordered hover variant="dark">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Full name</th>
                        <th>DOB</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Team</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {userList.map((user, index) => {
                        return (
                            <tr key={index} hidden={user.isActive}>
                                <td>{user.username}</td>
                                <td>{user.name}</td>
                                <td>{user.dateOfBirth}</td>
                                <td>{user.email}</td>
                                <td>{user.phoneNumber}</td>
                                <td>{user.team.name}</td>
                                <td>
                                    <div className="d-flex justify-content-evenly">
                                        <Popconfirm
                                            title="Activate the user"
                                            description="Are you sure to active this user?"
                                            onConfirm={() => confirm(user.id)}
                                            onCancel={cancel}
                                            okText="Yes"
                                            cancelText="No"
                                        >
                                            <button className="btn btn-secondary">Activate</button>
                                        </Popconfirm>
                                    </div>
                                </td>
                            </tr>
                        )
                    })}
                </tbody>
            </Table>

            <Modal title="Change Team" open={isModalTeamOpen} onCancel={handleTeamCancel} footer={null}>
                <Form name="team-form"
                    labelCol={{
                        span: 6,
                    }}
                    wrapperCol={{
                        span: 14,
                    }}
                    layout="horizontal"

                    form={formTeam}
                    onFinish={submitTeam}
                >
                    <Form.Item
                        label="Id"
                        name="userId"
                        hidden
                    >
                        <Input disabled />
                    </Form.Item>

                    <Form.Item label="Team"
                        name="teamId"
                        rules={[
                            {
                                required: true,
                                message: 'Please choose your team!',
                            },
                        ]}
                    >
                        <Select>
                            {
                                teamList.map((team, indexTeam) => {
                                    return (
                                        <Select.Option key={indexTeam} value={team.id}>{team.name}</Select.Option>
                                    )
                                })
                            }
                        </Select>
                    </Form.Item>

                    <Row className="justify-content-center">
                        <button type="submit" className="btn btn-primary card-button" disabled={submitStatus}>
                            Submit
                        </button>
                        <button type="button" className="btn btn-secondary card-button" onClick={handleTeamCancel} disabled={submitStatus}>
                            Cancel
                        </button>
                    </Row>
                </Form>
            </Modal>
        </div>
    )
})