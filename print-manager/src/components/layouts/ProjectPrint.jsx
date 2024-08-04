import { memo, useCallback, useContext, useEffect, useState } from "react";
import { Col, Container, Form, Row, Table } from "react-bootstrap";
import { Link, useParams } from "react-router-dom";
import { ProjectContext } from "../config/ProjectContext";
import { useUser } from "../config/UserContext";
import axiosInstance from "../config/AxiosConfig";
import { message } from "antd";

export const ProjectPrint = memo(() => {
    const { user } = useUser();
    const { projectId } = useParams();
    const { project, loading } = useContext(ProjectContext);
    const [resourceDetails, setResourceDetails] = useState([]);
    const [bill, setBill] = useState(null)

    //Fetching ResourceDetail

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

    //Fetching Bill
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

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <Container fluid className="bg-dark p-4 rounded mt-3 border border-1 border-primary-subtle">

                <Row className="content">
                    <Col lg={8} sm={12}>
                        <Row>
                            <div style={{ maxHeight: '450px', overflowY: 'scroll', }}>
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
                                                        <Form.Group controlId={`quantity-${detail.id}`}>
                                                            <Form.Control
                                                                type="text"
                                                                placeholder="Quantity"
                                                                required
                                                                defaultValue={0}
                                                                disabled={bill && bill.totalMoney > 0}
                                                            />
                                                        </Form.Group>
                                                    </td>
                                                </tr>
                                            )
                                        })}
                                    </tbody>
                                </Table>
                            </div>
                            <button className="btn btn-warning w-100 shadow fw-bolder mt-2" hidden={user.team.name !== 'Prints'} disabled={bill && bill.totalMoney > 0} >Confirm resource</button>
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
                                <li>Total money: {bill ? bill.totalMoney : 0}</li>
                            </ul>
                            <div className="text-center p-2">
                                <button className="btn btn-danger w-100 shadow fw-bolder" hidden={user.team.name !== 'Prints'} disabled={bill}>Make bill</button>
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
                        <Link to={`/projects/${projectId}/bill`} hidden={!bill}>
                            <button className="btn btn-primary">Bill</button>
                        </Link>
                    </Col>
                </Row>
            </Container>

        </div>
    )
})