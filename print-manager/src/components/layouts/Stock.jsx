import { memo, useCallback, useEffect, useState } from "react";
import { Button, Col, Form, Input, message, Modal, Row, Select, Upload } from "antd";
import Search from "antd/es/transfer/search";
import axiosInstance from "../config/AxiosConfig";
import { Card, Table } from "react-bootstrap";
import { UploadOutlined } from "@ant-design/icons";

export const Stock = memo(() => {
    const { Option } = Select;
    const [currentResource, setCurrentResource] = useState();
    const [resources, setResources] = useState([]);
    const [resourceProperties, setResourcesProperties] = useState([]);
    const [loading, setLoading] = useState(true);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [formData, setFormData] = useState({
        multipartFile: null,
        name: '',
        resourceType: 'renewable',
        availableQuantity: '',
        resourceStatus: 'available',
    });
    const [isAddModalVisible, setIsAddModalVisible] = useState(false);
    const [isViewModalVisible, setIsViewModalVisible] = useState(false);

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


    //Add Modal


    const showAddModal = (resource) => {
        setCurrentResource(resource);
        console.log('Current', resource);

        setIsAddModalVisible(true);
    };

    const handleAddModalCancel = () => {
        setIsAddModalVisible(false);
    };

    const handleAddSubmit = async (values) => {
        console.log(values);

        try {
            // const response = await axiosInstance.post(`/resource-property`, values);
            message.success("Added");
        } catch (err) {
            message.error(err.response?.data || 'Error add resource property');
        }
    }

    //View
    const showViewModal = async (resource) => {
        try {
            const response = await axiosInstance.get(`/resource-property/${resource.id}`);
            setResourcesProperties(response.data);
            setIsViewModalVisible(true);
        } catch (err) {
            message.error(err.response?.data || 'Error fetching resource');
        }
    }

    const handleViewModalCancel = () => {
        setIsViewModalVisible(false);
    };

    const handleImportQuantity = async (values) => {

        try {
            const response = await axiosInstance.put(`/property-detail/import-quantity`, null, {
                params: {
                    propertyDetailId: values.propertyDetailId,
                    quantity: values.quantity,
                },
            });
            message.success("Added");
        } catch (err) {
            message.error(err.response?.data || 'Error import product');
        }
    }

    //Create Resource
    const handleFileChange = (info) => {
        if (info.file.status === 'done') {
            setFormData({
                ...formData,
                multipartFile: info.file.originFileObj,
            });
        }
    };

    const showResourceModal = () => {
        setIsModalVisible(true);
    };

    const handleResourceCancel = () => {
        setIsModalVisible(false);
    };

    const handleResourceCreate = async (values) => {
        // Prepare FormData
        const data = new FormData();
        data.append('name', values.name);
        data.append('resourceType', values.resourceType);
        data.append('availableQuantity', values.availableQuantity);
        data.append('resourceStatus', values.resourceStatus);
        if (formData.multipartFile) {
            data.append('multipartFile', formData.multipartFile);
        }

        try {
            const response = await axiosInstance.post('/resources', data, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            console.log(values);
            setIsModalVisible(false);
        } catch (error) {
            console.error('Error creating resource:', error);
            message.error(error.response.data)
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
                <button className="btn btn-success" onClick={showResourceModal} disabled>Create</button>
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
                                        <div className="d-flex justify-content-center">
                                            <button className="btn btn-success" onClick={() => showAddModal(resource)}>Add</button>
                                            <button className="btn btn-primary" onClick={() => showViewModal(resource)}>View</button>
                                            <button className="btn btn-danger" disabled>Delete</button>
                                        </div>
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    )
                })}
            </Row>

            {/* Modal */}
            <Modal title="Create Project" open={isModalVisible} onCancel={handleResourceCancel} footer={null}>
                <Form onFinish={handleResourceCreate}>
                    <Form.Item label="Upload Image">
                        <Upload
                            name="multipartFile"
                            beforeUpload={() => false} // Prevents automatic upload
                            onChange={handleFileChange}
                        >
                            <Button icon={<UploadOutlined />}>Click to Upload</Button>
                        </Upload>
                    </Form.Item>
                    <Form.Item name="name" label="Name" rules={[{ required: true, message: 'Please enter name' }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="availableQuantity" label="Quantity" rules={[{ required: true, message: 'Please enter quantity' }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item name="resourceType" label="Type" rules={[{ required: true, message: 'Please select type' }]}>
                        <Select>
                            <Option value="renewable">Renewable</Option>
                            <Option value="nonrenewable">Nonrenewable</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" htmlType="submit">Create</Button>
                    </Form.Item>
                </Form>
            </Modal>

            {/* Modal "Add Resource" */}
            <Modal
                title="Add Resource"
                open={isAddModalVisible}
                onCancel={handleAddModalCancel}
                footer={null}
            >
                {currentResource &&
                    <Form
                        layout="vertical"
                        onFinish={handleAddSubmit}
                    >
                        <Form.Item
                            name="id"
                            rules={[{ required: true, message: 'Please input the Resource Property Id!' }]}
                            initialValue={0}
                        >
                            <Input hidden />
                        </Form.Item>
                        <Form.Item
                            label="Resource ID"
                            name="resourceId"
                            rules={[{ required: true, message: 'Please select a Resource ID!' }]}
                        >
                            <Select placeholder="Select a Resource">
                                {resources.map((resource) => (
                                    <Option key={resource.id} value={resource.id}>
                                        {resource.resourceName}
                                    </Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item
                            label="Resource Property Name"
                            name="resourcePropName"
                            rules={[{ required: true, message: 'Please input the Resource Property Name!' }]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit">
                                Add Resource
                            </Button>
                        </Form.Item>
                    </Form>
                }
            </Modal>

            {/* Modal "View Resource" */}
            <Modal
                title="View Resource"
                open={isViewModalVisible}
                onCancel={handleViewModalCancel}
                footer={null}
            >
                {resourceProperties.map((property, propIndex) => {
                    return (<div key={propIndex}>
                        <h6>{property.name}</h6>
                        <Table className="mt-3" striped bordered hover variant="dark">
                            <thead>
                                <tr>
                                    <th>Image</th>
                                    <th>Name</th>
                                    <th>Quantity</th>
                                    <th>Import</th>
                                </tr>
                            </thead>
                            <tbody>
                                {property.detailList.map((detail) => {
                                    return (
                                        <tr key={detail.id}>
                                            <td>
                                                <div style={{ maxWidth: '80px' }}>
                                                    <img className="w-100" src={detail.image} alt="Resource" />
                                                </div>
                                            </td>
                                            <td>{detail.propertyDetailName}</td>
                                            <td>{detail.quantity}</td>
                                            <td>
                                                <Form name={`form-${detail.id}`} onFinish={handleImportQuantity}
                                                >
                                                    <Form.Item
                                                        name="propertyDetailId"
                                                        noStyle
                                                        initialValue={detail.id}
                                                    >
                                                        <Input hidden defaultValue={detail.id} />
                                                    </Form.Item>
                                                    <Form.Item
                                                        name="quantity"
                                                        rules={[
                                                            {
                                                                required: true,
                                                                message: 'Please input your quantity!',
                                                            },
                                                        ]}
                                                    >
                                                        <Input placeholder="quantity" />
                                                    </Form.Item>
                                                    <button type="submit" className="btn btn-primary card-button">
                                                        Import
                                                    </button>
                                                </Form>
                                            </td>
                                        </tr>
                                    )
                                })}
                            </tbody>
                        </Table>
                    </div>
                    )
                })}
            </Modal>
        </div>
    )
})