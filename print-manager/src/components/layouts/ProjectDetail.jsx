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
                        <div className="p-3 text-white">
                            <h3>Project</h3>
                            <ul className="rounded p-3" style={{listStyle:'none', fontSize:'20px'}}>
                                <li>Project: {project.projectName}</li>
                                <li>Requset: {project.requestDescriptionFromCustomer}</li>
                                <li>Start Date: {formatDate(project.startDate)}</li>
                                <li>Expected Date: {formatDate(project.expectedEndDate)}</li>
                                <li>Status: {project.projectStatus}</li>
                            </ul>
                        </div>
                        </Row>
                    </Col>
                    <Col lg={4} sm={12}>
                        <div className="border border-1 border-primary-subtle p-3 text-white rounded">
                            <h3>Information</h3>
                            <ul className="rounded p-3" style={{listStyle:'none', backgroundColor:'#3A4156'}}>
                                <li>Leader: {project.employee.fullName}</li>
                                <li>Email: {project.employee.email}</li>
                                <li>Contact: {project.employee.phoneNumber}</li>
                            </ul>
                            <ul className="rounded p-3" style={{listStyle:'none', backgroundColor:'#3A4156'}}>
                                <li>Customer: {project.customer.fullName}</li>
                                <li>Email: {project.customer.email}</li>
                                <li>Contact: {project.customer.phoneNumber}</li>
                                <li>Address: {project.customer.address}</li>
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