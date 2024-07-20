import { message, Popconfirm, Row } from "antd";
import { Table } from "react-bootstrap"
import Search from "antd/es/transfer/search";
import { memo, useState } from "react";
import axiosInstance from "../config/AxiosConfig";

export const User = memo(() => {
    const [userList, setUserList] = useState([]);

    const fetchUsers = async () => {
        try {
            const response = await axiosInstance.get('/users');
            console.log('Users', response.data);
            setUserList(response.data)
        } catch (err) {
            message.error(err.response?.data || 'Error fetching users');
        }
    }
    //re-render
    useState(() => {
        fetchUsers();
    }, [])

    //Handle Delete
    const confirm = async (userId) => {
        try {
            await axiosInstance.put(`/users/${userId}`);
            message.success('User status change successfully');
            // Gọi lại hàm fetchTeams để cập nhật danh sách các đội
            fetchUsers();
        } catch (error) {
            console.error('Error change status user:', error);
            message.error('Failed to change status user');
        }
    };

    const cancel = (e) => {
        console.log(e);
        message.error('Click on No');
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
                <button className="btn btn-success" >Future function</button>
            </Row>
            <Table className="mt-3" striped bordered hover variant="dark">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Full name</th>
                        <th>DOB</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Team</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {userList.map((user, index) => {
                        return (
                            <tr key={index} hidden={!user.active}>
                                <td>{user.userName}</td>
                                <td>{user.fullName}</td>
                                <td>{user.dateOfBirth}</td>
                                <td>{user.email}</td>
                                <td>{user.phoneNumber}</td>
                                <td>{user.teamId}</td>
                                <td>
                                    <div className="d-flex justify-content-evenly">
                                        <button className="btn btn-warning ">Role</button>
                                        <button className="btn btn-success">Team</button>
                                        <Popconfirm
                                            title="Disable the user"
                                            description="Are you sure to ban this user?"
                                            onConfirm={() => confirm(user.id)}
                                            onCancel={cancel}
                                            okText="Yes"
                                            cancelText="No"
                                        >
                                            <button className="btn btn-danger">Ban</button>
                                        </Popconfirm>
                                    </div>
                                </td>
                            </tr>
                        )
                    })}
                </tbody>
            </Table>
            <h3 style={{color:'#fff'}}>Disabled users:</h3>
            <Table className="mt-3" striped bordered hover variant="dark">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Full name</th>
                        <th>DOB</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Team</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {userList.map((user, index) => {
                        return (
                            <tr key={index} hidden={user.active}>
                                <td>{user.userName}</td>
                                <td>{user.fullName}</td>
                                <td>{user.dateOfBirth}</td>
                                <td>{user.email}</td>
                                <td>{user.phoneNumber}</td>
                                <td>{user.teamId}</td>
                                <td>
                                    <div className="d-flex justify-content-evenly">
                                        <Popconfirm
                                            title="Activate the user"
                                            description="Are you sure to active this user?"
                                            onConfirm={() => confirm(user.id)}
                                            onCancel={cancel}
                                            okText="Yes"
                                            cancelText="No"
                                        >
                                            <button className="btn btn-secondary">Activate</button>
                                        </Popconfirm>
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