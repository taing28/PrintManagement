import Search from "antd/es/transfer/search";
import { memo, useCallback, useState } from "react";
import { Table } from "react-bootstrap";
import axiosInstance from "../config/AxiosConfig";
import { message, Row } from "antd";
import { useUser } from "../config/UserContext";

export const Delivery = memo(() => {
    const { user } = useUser();
    const [deliveries, setDeliveries] = useState([]);

    //fetch deliveries
    const fetchDeliveries = useCallback(async () => {
        try {
            const response = await axiosInstance.get('/delivery');
            console.log('Deliveries',response.data);
            setDeliveries(response.data)
            
        } catch (err) {
            message.error(err.response?.data || 'Error fetching users');
        }
    }, [])

    //re-render
    useState(() => {
        fetchDeliveries();
    }, [fetchDeliveries])

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
                <button className="btn btn-success" >Future function</button>
            </Row>
            <Table className="mt-3" striped bordered hover variant="dark">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Customer</th>
                        <th>Address</th>
                        <th>Method</th>
                        <th>Deliver</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {deliveries.map((deliver, index) => {
                        return (
                            <tr key={index}>
                                <td>{deliver.project.projectName}</td>
                                <td>{deliver.customer.fullName}</td>
                                <td>{deliver.deliveryAddress}</td>
                                <td>{deliver.method.shippingMethodName}</td>
                                <td>{deliver.deliver.fullName}</td>
                                <td>{deliver.deliveryStuatus}</td>
                                <td>
                                    <div className="d-flex justify-content-evenly">
                                        <button className="btn btn-success">Set status</button>
                                    </div>
                                </td>
                            </tr>
                        )
                    })}
                </tbody>
            </Table>
        </div>
    )
})