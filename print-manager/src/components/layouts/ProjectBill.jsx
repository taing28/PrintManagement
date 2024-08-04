import { memo, useContext, useEffect, useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import { Link, useParams } from "react-router-dom";
import { useUser } from "../config/UserContext";
import { ProjectContext } from "../config/ProjectContext";
import axiosInstance from "../config/AxiosConfig";
import { message } from "antd";

export const ProjectBill = memo(() => {
    const { user } = useUser();
    const { projectId } = useParams();
    const { project, loading } = useContext(ProjectContext);
    const [bill, setBill] = useState(null)

    useEffect(() => {
        const fetchBill = async () => {
            try {
                const response = await axiosInstance.get(`/bills/${projectId}`);
                console.log('Bill', response.data);
                setBill(response.data);
            } catch (err) {
                message.error(err.response?.data || 'Error fetching bill');
            }
        }

        fetchBill();
    }, [])

    return (
        <div>
            <Container fluid className="bg-dark p-4 rounded mt-3 border border-1 border-primary-subtle">
                <Row className="content">
                <Col lg={8} sm={12}>
                        <Row>
                            
                        </Row>
                    </Col>
                    <Col lg={4} sm={12}>
                        <div className="border border-1 border-primary-subtle p-3 text-white">
                            
                        </div>
                    </Col>
                </Row>
                <Row
                    className="p-2 justify-content-between"
                >
                    <Col>
                        <Link to={`/projects/${projectId}/prints`}>
                            <button className="btn btn-secondary">Back</button>
                        </Link>
                    </Col>
                    <Col style={{ textAlign: 'end' }}>
                    </Col>
                </Row>
            </Container>
        </div>
    )
})