import { memo, useCallback, useEffect, useState } from "react";
import { Card, Col, Row } from "react-bootstrap";
import { message } from "antd";
import axiosInstance from "../config/AxiosConfig";

export const Home = memo(() => {
  const [designs, setDesigns] = useState([]);

  //Take designs
  const fetchDesign = useCallback(async () => {
    try {
      const response = await axiosInstance.get('/designs');
      console.log('Designs: ', response.data);
      setDesigns(response.data)

    } catch (err) {
      message.error(err.response?.data || 'Error fetching designs');
    }
  }, [])

  useEffect(() => {
    fetchDesign();
  }, [fetchDesign]);

  return (
    <div>
      <h1 className="text-center text-white pt-2">HOME PAGE</h1>
        <Row>
          {
            designs.map((design, index) => {
              return (
                <Col key={index} lg={3} className="pt-3">
                  <Card style={{ width: '18rem' }}>
                    <Card.Img variant="top" src={design.filePath} />
                  </Card>
                </Col>
              )
            })
          }
        </Row>
    </div>
  )
});