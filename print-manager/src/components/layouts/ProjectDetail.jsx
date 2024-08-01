import { memo, useContext } from "react";
import { Col, Container, Row } from "react-bootstrap";
import { Link, useParams } from "react-router-dom";
import { ProjectContext } from "../config/ProjectContext";

export const ProjectDetail = memo(() => {
    const { projectId } = useParams();
    const { project, loading } = useContext(ProjectContext);

    if (loading) {
        return <div>Loading...</div>;
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
            <Container fluid className="bg-dark p-4 rounded mt-3 border border-1 border-primary-subtle">

                <Row className="content">
                    <Col lg={8} sm={12}>
                        <Row>
                            Detail
                        </Row>
                    </Col>
                    <Col lg={4} sm={12}>
                        <div className="border border-1 border-primary-subtle p-2 text-white">
                            <ul>
                                <li>Name: {project.projectName}</li>
                                <li>End Date: {formatDate(project.expectedEndDate)}</li>
                                <li>Leader: {project.employeeName}</li>
                                <li>Customer: {project.customerName}</li>
                                <li>Request: {project.requestDescriptionFromCustomer}</li>
                                <li>Status: {project.projectStatus}</li>
                            </ul>
                        </div>
                    </Col>
                </Row>
                <Row
                    className="p-2 justify-content-between"
                >
                    <Col>
                        <Link to={`/projects`}>
                            <button className="btn btn-secondary">Back</button>
                        </Link>
                    </Col>
                    <Col style={{ textAlign: 'end' }}>
                        <Link to={`/projects/${projectId}/designs`}>
                            <button className="btn btn-warning">Design</button>
                        </Link>
                    </Col>
                </Row>
            </Container>

        </div>
    )
})