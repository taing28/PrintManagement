import { memo, useCallback, useEffect, useState } from "react";
import { Card, Col, message, Popconfirm, Row, Spin } from "antd";
import axiosInstance from "../config/AxiosConfig";
import Search from "antd/es/transfer/search";
import { Link } from "react-router-dom"

export const Team = memo(() => {
    const [teamList, setTeamList] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchTeams = useCallback(async () => {
        try {
            const response = await axiosInstance.get('http://localhost:8080/teams');
            console.log('Teams:', response);
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

    const confirm = async (teamId) => {
        try {
            await axiosInstance.delete(`http://localhost:8080/teams/${teamId}`);
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
                <button className="btn btn-success">Create</button>
            </Row>
            <Row className="pt-4" gutter={16}>
                {Array.isArray(teamList) && teamList.length > 0 ? (
                    teamList.map((team, index) => (
                        <Col style={{padding: '8px'}} span={8} key={index}>
                            <Card className="card-item" title={team.name} bordered={true}>
                                <div>Description: {team.description}</div>
                                <div>Members: {team.members.length}</div>
                                <div className="pt-2 d-flex justify-content-center">
                                    <button className="btn btn-primary card-button">Edit</button>
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
                    ))
                ) : (
                    <p>No teams found</p>
                )}
            </Row>
        </div>
    )
})