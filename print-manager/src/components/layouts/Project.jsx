import { memo, useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useUser } from "../config/UserContext";
import axiosInstance from "../config/AxiosConfig";
import { Card, Col, message, Popconfirm, Row, Spin } from "antd";
import Search from "antd/es/transfer/search";

export const Project = memo(() => {
    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true);

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
    if (user.authorities?.some((value) => {
        return value.authority === 'ROLE_ADMIN' || value.authority === 'ROLE_MANAGER' || value.authority === 'ROLE_LEADER' || value.authority === 'ROLE_SALE' || value.authority === 'ROLE_DESIGNER';
    }) ? false : true) {
        navigate('/auth/login');
    }

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
                <button className="btn btn-success">Create</button>
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
                                    <div>Process: %</div>
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
                                                return value.authority === 'ROLE_SALE';
                                            })}>Delete</button>
                                        </Popconfirm>
                                    </div>
                                </Card>
                            </Col>
                        )
                    })
                }
            </Row>
        </div>
    )
})