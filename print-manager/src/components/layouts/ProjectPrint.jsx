import { memo, useCallback, useContext, useEffect, useState } from "react";
import { Col, Container, Form, InputGroup, Row, Table } from "react-bootstrap";
import { Link, useParams } from "react-router-dom";
import { ProjectContext } from "../config/ProjectContext";
import { useUser } from "../config/UserContext";
import axiosInstance from "../config/AxiosConfig";
import { message } from "antd";

export const ProjectPrint = memo(() => {
    const { user } = useUser();
    const { projectId } = useParams();
    const { project, loading } = useContext(ProjectContext);
    //Fetching ResourceDetail
    const [resourceDetails, setResourceDetails] = useState([]);

    const fetchResourceDtail = useCallback(async () => {
        try {
            const response = await axiosInstance.get('/property-detail');
            console.log('ResourceDetail', response.data);
            setResourceDetails(response.data);
        } catch (err) {
            message.error(err.response?.data || 'Error fetching resource detail');
        }
    }, [projectId])

    useEffect(() => {
        fetchResourceDtail();
    }, [fetchResourceDtail]);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <Container fluid className="bg-dark p-4 rounded mt-3 border border-1 border-primary-subtle">

                <Row className="content">
                    <Col lg={8} sm={12}>
                        <Row>
                            <div style={{ maxHeight: '450px', overflowY: 'scroll' }}>
                                <Table striped hover variant="dark">
                                    <thead>
                                        <tr>
                                            <th>
                                                Image
                                            </th>
                                            <th>
                                                Name
                                            </th>
                                            <th>
                                                Quantity
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {resourceDetails.map((detail, index) => {
                                            return (
                                                <tr key={index}>
                                                    <td>
                                                        <div style={{ maxWidth: '100px' }}>
                                                            <img className="w-100" src={detail.image} alt="Resource" />
                                                        </div>
                                                    </td>
                                                    <td>{detail.propertyDetailName}</td>
                                                    <td>
                                                        <Form.Group controlId="quantity">
                                                            <Form.Control type="text" placeholder="Quantity" required />
                                                        </Form.Group>
                                                    </td>
                                                </tr>
                                            )
                                        })}
                                    </tbody>
                                </Table>
                            </div>
                        </Row>
                    </Col>
                    <Col lg={4} sm={12}>
                        <div className="border border-1 border-primary-subtle p-3 text-white">
                            <h3>Information</h3>
                            <div className="p-2" style={{ maxWidth: '300px' }}>
                                <img className="w-100" src={project.designList.length > 0 ? project.designList[0].filePath : ""} alt="Design" />
                            </div>
                            <ul className="rounded p-3" style={{ listStyle: 'none', backgroundColor: '#3A4156' }}>
                                <li>Project: {project.projectName}</li>
                                <li>Status: {project.projectStatus}</li>
                            </ul>
                            <div className="text-center p-2">
                                <button className="btn btn-warning w-100 shadow fw-bolder" hidden={user.team.name !== 'Prints'}>Confirm resource</button>
                            </div>
                        </div>
                    </Col>
                </Row>
                <Row
                    className="p-2 justify-content-between"
                >
                    <Col>
                        <Link to={`/projects/${projectId}/designs`}>
                            <button className="btn btn-secondary">Back</button>
                        </Link>
                    </Col>
                    <Col style={{ textAlign: 'end' }}>
                        <Link to={`/projects/${projectId}/bill`}>
                            <button className="btn btn-primary">Bill</button>
                        </Link>
                    </Col>
                </Row>
            </Container>

        </div>
    )
})