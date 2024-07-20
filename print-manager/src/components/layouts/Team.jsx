import { memo, useCallback, useEffect, useState } from "react";
import { Card, Col, Form, Input, message, Modal, Popconfirm, Row, Select, Spin } from "antd";
import axiosInstance from "../config/AxiosConfig";
import Search from "antd/es/transfer/search";
import { useForm } from "antd/es/form/Form";

export const Team = memo(() => {
    const [teamList, setTeamList] = useState([]);
    const [loading, setLoading] = useState(true);
    const [userList, setUserList] = useState([]);
    const [submitStatus, setSubmitStatus] = useState(false);
    const [form] = Form.useForm();
    const [formEdit] = Form.useForm();
    // Fetch users
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axiosInstance.get('/users');
                console.log('Users', response.data);
                setUserList(response.data)
            } catch (err) {
                message.error(err.response?.data || 'Error fetching users');
            }
        }

        fetchUsers();
    }, [])

    // Fetch teams
    const fetchTeams = useCallback(async () => {
        try {
            const response = await axiosInstance.get('/teams');
            console.log('Teams', response);
            console.log('Team members', response.data[0].members);
            setTeamList(response.data);
        } catch (err) {
            message.error(err.response?.data || 'Error fetching teams');
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchTeams();
    }, [fetchTeams]);

    //Handle delete
    const confirm = async (teamId) => {
        try {
            await axiosInstance.delete(`/teams/${teamId}`);
            message.success('Team deleted successfully');
            // Gọi lại hàm fetchTeams để cập nhật danh sách các đội
            fetchTeams();
        } catch (error) {
            console.error('Error deleting team:', error);
            message.error('Failed to delete team');
        }
    };

    const cancel = (e) => {
        console.log(e);
        message.error('Click on No');
    };

    //Handle create
    const [isModalCreateOpen, setIsModalCreateOpen] = useState(false);
    const showModalCreate = () => {
        setIsModalCreateOpen(true);
    };

    const submitCreate = async (values) => {
        try {
            setSubmitStatus(true);

            const response = await axiosInstance.post('/teams', values);

            // Kiểm tra trạng thái thành công từ server
            if (response.status === 200) {
                fetchTeams();
                setIsModalCreateOpen(false);
                form.resetFields();
            } else {
                // Xử lý khi có lỗi từ server, ví dụ hiển thị thông báo lỗi
                message.error('Create failed. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error); // Handle error
            message.error(error.response.data);
        } finally {
            setSubmitStatus(false); // Kết thúc trạng thái loading sau khi hoàn thành yêu cầu
        }
    }

    const handleCancelCreate = () => {
        setIsModalCreateOpen(false);
    };

    //Handle edit
    const [isModalUpdateOpen, setIsModalUpdateOpen] = useState(false);
    const [selectedTeam, setSelectedTeam] = useState(null);
    const showModalUpdate = (team) => {
        setSelectedTeam(team);
        formEdit.setFieldsValue({
            id: team.id,
            name: team.name,
            description: team.description,
            managerId: team.managerId,
        });
        setIsModalUpdateOpen(true);
    };

    const submitUpdate = async (values) => {
        try {
            setSubmitStatus(true);

            const response = await axiosInstance.put('/teams', values);

            // Kiểm tra trạng thái thành công từ server
            if (response.status === 200) {
                fetchTeams();
                setIsModalUpdateOpen(false);
                form.resetFields();
            } else {
                // Xử lý khi có lỗi từ server, ví dụ hiển thị thông báo lỗi
                message.error('Update failed. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error); // Handle error
            message.error(error.response.data);
        } finally {
            setSubmitStatus(false); // Kết thúc trạng thái loading sau khi hoàn thành yêu cầu
        }
    }

    const handleCancelUpdate = () => {
        setIsModalUpdateOpen(false);
    };
    //Load page to wait data to fetch
    if (loading) {
        return (
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
                <Spin tip="Loading..." />
            </div>
        );
    }

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
                <button className="btn btn-success" onClick={showModalCreate}>Create</button>
            </Row>
            <Row className="pt-4" gutter={16}>
                {
                    teamList.map((team, index) => {
                        const manager = userList.find((user) => user.id === team.managerId);
                        console.log('Manager', manager);
                        return (<>
                            <Col style={{ padding: '8px' }} span={8} key={index}>
                                <Card className="card-item" title={team.name} bordered={true}>
                                    <div>Manager: {manager ? manager.fullName : "None"}</div>
                                    <div>Description: {team.description}</div>
                                    <div>Members: {team.members.length}</div>
                                    <div className="pt-2 d-flex justify-content-center">
                                        <button className="btn btn-primary card-button" onClick={() => showModalUpdate(team)}>Edit</button>
                                        <Popconfirm
                                            title="Delete the team"
                                            description="Are you sure to delete this team?"
                                            onConfirm={() => confirm(team.id)}
                                            onCancel={cancel}
                                            okText="Yes"
                                            cancelText="No"
                                        >
                                            <button className="btn btn-danger card-button">Delete</button>
                                        </Popconfirm>
                                    </div>
                                </Card>
                            </Col>
                        </>
                        )
                    }
                    )
                }
            </Row>

            {/* Modal */}

            <Modal title="Create Team" open={isModalCreateOpen} onCancel={handleCancelCreate} footer={null}>
                <Form name="create-form"
                    labelCol={{
                        span: 6,
                    }}
                    wrapperCol={{
                        span: 14,
                    }}
                    layout="horizontal"

                    form={form}
                    onFinish={submitCreate}
                >
                    <Form.Item
                        label="Name"
                        name="name"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your team name!',
                            },
                        ]}
                    >
                        <Input placeholder="Team name" />
                    </Form.Item>

                    <Form.Item
                        label="Description"
                        name="description"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your description!',
                            },
                        ]}
                    >
                        <Input placeholder="Team description" />
                    </Form.Item>

                    <Form.Item label="Manager"
                        name="managerId"
                        rules={[
                            {
                                required: true,
                                message: 'Please choose your manager!',
                            },
                        ]}
                    >
                        <Select>
                            {
                                userList.map((user, index) => {
                                    return (
                                        <Select.Option key={index} value={user.id}>{user.fullName}</Select.Option>
                                    )
                                })
                            }
                        </Select>
                    </Form.Item>

                    <Row className="justify-content-center">
                        <button type="submit" className="btn btn-primary card-button" disabled={submitStatus}>
                            Submit
                        </button>
                        <button type="button" className="btn btn-secondary card-button" onClick={handleCancelCreate} disabled={submitStatus}>
                            Cancel
                        </button>
                    </Row>
                </Form>
            </Modal>

            <Modal title="Update Team" open={isModalUpdateOpen} onCancel={handleCancelUpdate} footer={null}>
                                <Form name="update-form"
                                    labelCol={{
                                        span: 6,
                                    }}
                                    wrapperCol={{
                                        span: 14,
                                    }}
                                    layout="horizontal"

                                    form={formEdit}
                                    onFinish={submitUpdate}
                                >
                                    <Form.Item
                                        label="Id"
                                        name="id"
                                        hidden
                                    >
                                        <Input disabled />
                                    </Form.Item>

                                    <Form.Item
                                        label="Name"
                                        name="name"
                                        rules={[
                                            {
                                                required: true,
                                                message: 'Please input your team name!',
                                            },
                                        ]}
                                    >
                                        <Input placeholder="Team name" />
                                    </Form.Item>

                                    <Form.Item
                                        label="Description"
                                        name="description"
                                        rules={[
                                            {
                                                required: true,
                                                message: 'Please input your description!',
                                            },
                                        ]}
                                    >
                                        <Input placeholder="Team description" />
                                    </Form.Item>

                                    <Form.Item label="Manager"
                                        name="managerId"
                                        rules={[
                                            {
                                                required: true,
                                                message: 'Please choose your manager!',
                                            },
                                        ]}
                                    >
                                        <Select>
                                            {
                                                userList.map((user, indexUsr) => {
                                                    return (
                                                        <Select.Option key={indexUsr} value={user.id}>{user.fullName}</Select.Option>
                                                    )
                                                })
                                            }
                                        </Select>
                                    </Form.Item>

                                    <Row className="justify-content-center">
                                        <button type="submit" className="btn btn-primary card-button" disabled={submitStatus}>
                                            Submit
                                        </button>
                                        <button type="button" className="btn btn-secondary card-button" onClick={handleCancelUpdate} disabled={submitStatus}>
                                            Cancel
                                        </button>
                                    </Row>
                                </Form>
                            </Modal>
        </div>
    )
})