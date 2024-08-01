import { memo, useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import axiosInstance from "../config/AxiosConfig";
import { UploadOutlined } from "@ant-design/icons";
import { Button, Form, message, Select, Upload } from "antd";
import { useUser } from "../config/UserContext";
import { Card, Col, Container, Row } from "react-bootstrap";

export const ProjectDesign = memo(() => {
    const { projectId } = useParams();  // Extract projectId from URL
    const [designs, setDesigns] = useState([]);
    const [isApproved, setIsApproved] = useState(false);
    const { user } = useUser();

    //Take designs
    const fetchDesign = useCallback(async () => {
        try {
            const response = await axiosInstance.get('/designs/list', {
                params: {
                    projectId: projectId,
                }
            });
            console.log('Designs', response.data);
            setDesigns(response.data)

        } catch (err) {
            message.error(err.response?.data || 'Error fetching designs');
        }
    }, [projectId])

    useEffect(() => {
        fetchDesign();
    }, [fetchDesign]);

    useEffect(() => {
        setIsApproved(!designs.some(design => design.approverId === 0));
    }, [designs]);

    //Upload
    const props = {
        name: 'multipartFile',
        customRequest({ file, onSuccess, onError }) {
            const formData = new FormData();
            formData.append('multipartFile', file);
            formData.append('designerId', user.id);
            formData.append('projectId', projectId);

            axiosInstance.post(`/designs/upload`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(response => {
                    onSuccess(response.data, file);
                    message.success(`${file.name} file uploaded successfully.`);
                    fetchDesign();
                })
                .catch(error => {
                    onError(error);
                    message.error(`${file.name} file upload failed.`);
                });
        },
        showUploadList: false  // Hide the default upload list
    };

    //Set approve
    const [loading, setLoading] = useState(false);//Set loading to avoid send many request at the same time
    const navigate = useNavigate();

    const onFinish = async (values) => {
        try {
            setLoading(true);
            const req = {
                projectId: projectId,
                approverId: user.id,
                designList: designs,
                designStatus: values.designStatus,
            }
            const response = await axiosInstance.put('http://localhost:8080/designs/approve-list', req);

            // Kiểm tra trạng thái thành công từ server
            if (response.status === 200) {
                // Chuyển hướng đến trang khi thành công
                navigate(`/projects/${projectId}/prints`);
            } else {
                // Xử lý khi có lỗi từ server, ví dụ hiển thị thông báo lỗi
                message.error('Set approve failed. Please try again.');
            }
        } catch (error) {
            console.error('Error:', error); // Handle error
            message.error(error.response);
        } finally {
            setLoading(false); // Kết thúc trạng thái loading sau khi hoàn thành yêu cầu
        }
    };

    return (
        <div>
            <Container fluid className="bg-dark p-4 rounded mt-3 border border-1 border-primary-subtle">

                <Row className="content">
                    <Col lg={8} sm={12}>
                        <Row>
                            {
                                designs.map((design, index) => {
                                    return (
                                        <Col key={index}>
                                            <Card style={{ width: '18rem' }}>
                                                <Card.Img variant="top" src={design.filePath} />
                                                <Card.Body>
                                                    <Card.Title>{design.designStatus}</Card.Title>
                                                    <Card.Text>
                                                        Some quick example text to build on the card title and make up the
                                                        bulk of the card's content.
                                                    </Card.Text>
                                                </Card.Body>
                                            </Card>
                                        </Col>
                                    )
                                })
                            }
                        </Row>
                    </Col>
                    <Col lg={4} sm={12}>
                        <div
                            className="border border-1 border-primary-subtle p-2"
                            hidden={!user.authorities?.some((value) => {
                                return value === 'ROLE_LEADER';
                            })}
                        >
                            <Form name="approve-form" onFinish={onFinish}>
                                <Form.Item
                                    name="designStatus"
                                    rules={[
                                        {
                                            required: true,
                                            message: 'Please choose your status!',
                                        },
                                    ]}
                                >
                                    <Select disabled={isApproved}>
                                        <Select.Option value="approve">Approve</Select.Option>
                                        <Select.Option value="deny">Reject</Select.Option>
                                    </Select>
                                </Form.Item>
                                <Form.Item>
                                    <Button type="primary" htmlType="submit" loading={loading} disabled={isApproved}>
                                        Submit
                                    </Button>
                                </Form.Item>
                            </Form>
                        </div>
                    </Col>
                </Row>
                <Row
                    className="p-2"
                    hidden={!user.authorities?.some((value) => {
                        return value === 'ROLE_DESIGNER';
                    })}
                >
                    <Upload {...props}>
                        <Button icon={<UploadOutlined />} hidden={(isApproved && designs.length > 0) || designs.length === 1}>Click to Upload</Button>
                    </Upload>
                </Row>
                <Row
                    className="p-2 justify-content-between"
                >
                    <Col>
                        <Link to={`/projects/${projectId}`}>
                            <button className="btn btn-secondary">Back</button>
                        </Link>
                    </Col>
                    <Col style={{ textAlign: 'end' }}>
                        <Link to={`/projects/${projectId}/prints`} hidden={!isApproved}>
                            <button className="btn btn-primary">Print</button>
                        </Link>
                    </Col>
                </Row>
            </Container>
        </div>
    )
})