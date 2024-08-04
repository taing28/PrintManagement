import { memo, useContext, useEffect, useState } from "react";
import { Col, Container, Row, Table } from "react-bootstrap";
import { Link, useParams } from "react-router-dom";
import { useUser } from "../config/UserContext";
import { ProjectContext } from "../config/ProjectContext";
import axiosInstance from "../config/AxiosConfig";
import { Form, message, Select } from "antd";

export const ProjectBill = memo(() => {
    const { Option } = Select;
    const { user } = useUser();
    const { projectId } = useParams();
    const { project, loading } = useContext(ProjectContext);
    const [bill, setBill] = useState(null);
    const [delivers, setDelivers] = useState([])

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

    useEffect(() => {
        const fetchDeliver = async () => {
            try {
                const response = await axiosInstance.get('/users/delivers');
                setDelivers(response.data);
            } catch (err) {
                message.error(err.response?.data || 'Error fetching deliver')
            }
        }

        fetchDeliver();
    }, [])

    if (loading) {
        return <div>Loading...</div>;
    }

    const handleDeliver = async (values) => {
        try {
            const deliveryData = {
                id: 0,
                customerId: project.customer.id,
                deliverId: values.deliverId,
                deliveryAddress: project.customer.address,
                projectId: projectId,
                shippingMethodId: values.shippingMethodId,
            }
            const response = await axiosInstance.post('/delivery', deliveryData);
            console.log(response.data);
            
        } catch (err) {
            message.error(err.response?.data || 'Error set deliver')
        }
    }

    return (
        <div>
            {bill ? (<Container fluid className="bg-dark p-4 rounded mt-3 border border-1 border-primary-subtle">
                <Row className="content">
                    <Col lg={8} sm={12}>
                        <Row>
                            <Table striped hover variant="dark">
                                <thead>
                                    <tr>
                                        <th>Bill</th>
                                        <th>Customer</th>
                                        <th>Address</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>{bill.billName}</td>
                                        <td>{project.customer.fullName}</td>
                                        <td>{project.customer.address}</td>
                                        <td>
                                            {project.deliveryList.size < 1 ? (<Form
                                            onFinish={handleDeliver}
                                            >
                                                <Form.Item name="deliverId" rules={[{ required: true, message: 'Please choose deliver' }]}>
                                                    <Select>
                                                        {delivers.map(deliver => {
                                                            return <Option key={deliver.id} value={deliver.id}>{deliver.fullName}</Option>
                                                        })}
                                                    </Select>
                                                </Form.Item>
                                                <Form.Item name="shippingMethodId" rules={[{ required: true, message: 'Please choose deliver' }]}>
                                                    <Select>
                                                         <Option value={1}>Fast Shipping</Option>
                                                         <Option value={2}>Save Shipping</Option>
                                                         <Option value={3}>Normal Shipping</Option>
                                                    </Select>
                                                </Form.Item>
                                                <button type="submit" className="btn btn-info">Set deliver</button>
                                            </Form>): ''}
                                        </td>
                                    </tr>
                                </tbody>
                            </Table>
                        </Row>
                    </Col>
                    <Col lg={4} sm={12}>
                        <div className="border border-1 border-primary-subtle p-3 text-white">
                            <h3>Information</h3>
                            <ul className="rounded p-3" style={{ listStyle: 'none', backgroundColor: '#3A4156' }}>
                                <li>Trading Code: {bill.billName}</li>
                                <li>Bill Name: {project.projectName}</li>
                            </ul>
                            <ul className="rounded p-3" style={{ listStyle: 'none', backgroundColor: '#3A4156' }}>
                                <li>Customer: {project.customer.fullName}</li>
                                <li>Phone: {project.customer.phoneNumber}</li>
                                <li>Address: {project.customer.address}</li>
                                <li>Total Money: {bill.totalMoney}</li>
                            </ul>
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
        ) : 'Loading'}
        </div>
    )
})