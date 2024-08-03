import { memo } from "react";
import { Link, useNavigate } from "react-router-dom";
import Dropdown from 'react-bootstrap/Dropdown';
import NavItem from 'react-bootstrap/NavItem';
import NavLink from 'react-bootstrap/NavLink';
import { Header } from "antd/es/layout/layout";
import { BellOutlined } from '@ant-design/icons'
import { useUser } from "../config/UserContext";
import Cookies from 'js-cookie';

export const HeaderCompo = memo(() => {
  const { user } = useUser();

  const navigate = useNavigate();

  const logOut = () => {
    Cookies.remove('authToken');
    navigate('/auth/login');
  }

  return (
    <Header className="navbar navbar-expand-lg bg-dark ps-4 pe-4" data-bs-theme="dark">
      <Link className="navbar-brand" to={"/"}>Ink Mastery</Link>

      <div className="navbar-nav ms-auto mb-2 mb-lg-0">
        <Dropdown as={NavItem}>
          <Dropdown.Toggle as={NavLink}>
            <BellOutlined />
          </Dropdown.Toggle>
          <Dropdown.Menu className="drop-down-list">
            {user.notificationList && user.notificationList.length > 0 ? user.notificationList.map((noti, index) => {
              return (
                <Dropdown.Item key={index} onClick={() => navigate(noti.link)}>{noti.content}</Dropdown.Item>
              )
            }) : <Dropdown.Item>Nothing yet</Dropdown.Item>}

          </Dropdown.Menu>
        </Dropdown>

        <Dropdown as={NavItem}>
          <Dropdown.Toggle as={NavLink}>
            <img src="https://yt3.ggpht.com/ytc/AIdro_mimC9VbaEtPyRCUL55_Vduus-POLQbK2d7hopubLvgA-UFiK0RjYIlhwdeLV7yKNeYJA=s88-c-k-c0x00ffffff-no-rj"
              alt="Avatar"
              className="user-avatar" />{user.name}</Dropdown.Toggle>
          <Dropdown.Menu className="drop-down-list">
            <Dropdown.Item onClick={() => { navigate(`/profile/${user.name}`) }}>Profile</Dropdown.Item>
            <Dropdown.Item><button onClick={logOut} className="btn btn-danger">Log out</button></Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </div>
    </Header>
  )
})