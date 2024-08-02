import { memo, useCallback, useEffect, useState } from "react";
import { Col, message, Row } from "antd";
import Search from "antd/es/transfer/search";
import axiosInstance from "../config/AxiosConfig";
import { Card } from "react-bootstrap";

export const Stock = memo(() => {
    const [resources, setResources] = useState([]);
    const [resourceProperties, setResourcesProperties] = useState([]);
    const [propertyDetails, setPropertyDetails] = useState([]);
    const [loading, setLoading] = useState(true);

    //Fetch Resources
    const fetchResources = useCallback(async () => {
        try {
            const response = await axiosInstance.get('/resources');
            console.log('Resources', response.data);
            setResources(response.data);
        } catch (err) {
            message.error(err.response?.data || 'Error fetching resource');
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchResources();
    }, [fetchResources]);

    //Loading
    if (loading) {
        return (
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
                Loading...
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
                {resources.map((resource, index) => {
                    return (
                        <Col key={index} style={{ padding: '8px' }} span={8}>
                            <Card style={{ width: '18rem' }}>
                                <Card.Img variant="top" src={resource.image} />
                                <Card.Body>
                                    <Card.Title>{resource.resourceName}</Card.Title>
                                    <Card.Text>
                                        <div>Type: {resource.resourceType}</div>
                                        <div>Quantity: {resource.availableQuantity}</div>
                                        <div>Status: {resource.resourceStatus}</div>
                                        <div>
                                            <button className="btn btn-success">Add</button>
                                            <button className="btn btn-primary">View</button>
                                            <button className="btn btn-danger">Delete</button>
                                        </div>
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    )
                })}
            </Row>
        </div>
    )
})