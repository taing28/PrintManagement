import { memo, useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useUser } from "../config/UserContext";
import axiosInstance from "../config/AxiosConfig";
import { Button, Card, Col, DatePicker, Form, Input, message, Modal, Popconfirm, Row, Select, Spin } from "antd";
import Search from "antd/es/transfer/search";

export const Project = memo(() => {
    const { Option } = Select;
    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [submiting, setSubmitting] = useState(false);
    const [userList, setUserList] = useState([]);
    const [customerList, setCustomerList] = useState([])

    // Fetch project
    const fetchProjects = useCallback(async () => {
        try {
            const response = await axiosInstance.get('/projects');
            console.log('Projects', response.data);
            setProjects(response.data);
        } catch (err) {
            message.error(err.response?.data || 'Error fetching project');
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchProjects();
    }, [fetchProjects]);

    //Check role
    const navigate = useNavigate();
    const { user } = useUser();
    if (user.team.name !== 'Prints' && (user.authorities?.some((value) => {
        return value === 'ROLE_ADMIN' || value === 'ROLE_MANAGER' || value === 'ROLE_LEADER' || value === 'ROLE_SALE' || value === 'ROLE_DESIGNER';
    }) ? false : true)) {
        navigate('/auth/login');
    }

    //Fetch Users, Customers
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axiosInstance.get('/users');
                console.log('Users', response.data);
                setUserList(response.data);
            } catch (err) {
                message.error(err.response?.data || 'Error fetching users');
            }
        };

        const fetchCustomers = async () => {
            try {
                const response = await axiosInstance.get('/customers');
                console.log('Customers', response.data);
                setCustomerList(response.data);
            } catch (err) {
                message.error(err.response?.data || 'Error fetching customers');
            }
        };

        fetchUsers();
        fetchCustomers();
    }, []);

    //Load page to wait data to fetch
    if (loading) {
        return (
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
                <Spin tip="Loading..." />
            </div>
        );
    }

    //Limit text length
    function truncateText(text, maxLength) {
        if (text && text.length > maxLength) {
            return text.substring(0, maxLength) + '...';
        }
        return text;
    }
    //format dateTime to date
    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }

    //create Project
    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const handleCreate = async (values) => {
        setSubmitting(true);
        try {
            const response = await axiosInstance.post('/projects', values);
            fetchProjects();
            setIsModalVisible(false);
        } catch (error) {
            console.error('Error creating project:', error);
            message.error(error.response.data)
        } finally {
            setSubmitting(false);
        }
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
                <button className="btn btn-success" onClick={showModal} hidden={!(user.team.name === 'Sales')}>Create</button>
            </Row>
            <Row className="pt-4" gutter={16}>
                {
                    projects.map((project, index) => {
                        return (
                            <Col key={index} style={{ padding: '8px' }} span={8}>
                                <Card className="card-item" title={project.projectName} bordered={true}>
                                    <div>Description: {truncateText(project.requestDescriptionFromCustomer, 30)}</div>
                                    <div>Start date: {formatDate(project.startDate)}</div>
                                    <div>End date: {formatDate(project.expectedEndDate)}</div>
                                    <div className="pt-2 d-flex justify-content-center">
                                        <Link to={`/projects/${project.id}`}>
                                            <button className="btn btn-primary card-button">View</button>
                                        </Link>
                                        <Popconfirm
                                            title="Delete the project"
                                            description="Are you sure to delete this project?"
                                            onConfirm
                                            onCancel
                                            okText="Yes"
                                            cancelText="No"
                                        >
                                            <button className="btn btn-danger card-button" hidden={!user.authorities?.some((value) => {
                                                return value === 'ROLE_SALE';
                                            })}>Delete</button>
                                        </Popconfirm>
                                    </div>
                                </Card>
                            </Col>
                        )
                    })
                }
            </Row>

            {/* Modal */}
            <Modal title="Create Project" open={isModalVisible} onCancel={handleCancel} footer={null}>
                <Form onFinish={handleCreate}>
                    <Form.Item name="projectName" label="Project Name" rules={[{ required: true, message: 'Please enter project name' }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="requestDescriptionFromCustomer" label="Request Description" rules={[{ required: true, message: 'Please enter request description' }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="expectedEndDate" label="Expected End Date" rules={[{ required: true, message: 'Please select expected end date' }]}>
                        <DatePicker />
                    </Form.Item>
                    <Form.Item name="employeeId" label="Employee" rules={[{ required: true, message: 'Please select employee' }]}>
                        <Select>
                            {userList.map((userValue, index) => {
                                return <Option value={userValue.id} key={index}>{userValue.name}</Option>
                            })}
                        </Select>
                    </Form.Item>
                    <Form.Item name="customerId" label="Customer" rules={[{ required: true, message: 'Please select customer' }]}>
                        <Select>
                            {customerList.map((customerValue, index) => {
                                return <Option value={customerValue.id} key={index}>{customerValue.name}</Option>
                            })}
                        </Select>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit" loading={submiting}>Create</Button>
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
})