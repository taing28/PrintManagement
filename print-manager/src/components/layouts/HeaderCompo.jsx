import { memo } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from 'antd'
import Dropdown from 'react-bootstrap/Dropdown';
import NavItem from 'react-bootstrap/NavItem';
import NavLink from 'react-bootstrap/NavLink';
import { Header } from "antd/es/layout/layout";

export const HeaderCompo = memo(() => {
  const navigate = useNavigate();

  const logOut = () => {
    localStorage.removeItem('authToken');
    navigate('/auth/login');
  }

  return (
    <Header className="navbar navbar-expand-lg bg-dark ps-4 pe-4" data-bs-theme="dark">
      <Link className="navbar-brand" to={"/"}>Ink Mastery</Link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
        <div className="navbar-nav ms-auto mb-2 mb-lg-0">

          <Dropdown as={NavItem}>
            <Dropdown.Toggle as={NavLink}>
              <img src="https://yt3.ggpht.com/ytc/AIdro_mimC9VbaEtPyRCUL55_Vduus-POLQbK2d7hopubLvgA-UFiK0RjYIlhwdeLV7yKNeYJA=s88-c-k-c0x00ffffff-no-rj"
                alt="Avatar"
                className="user-avatar" />Gia Tai</Dropdown.Toggle>
            <Dropdown.Menu className="drop-down-list">
              <Dropdown.Item><Link to={"/profile/"}>Profile</Link></Dropdown.Item>
              <Dropdown.Item><button onClick={logOut} className="btn btn-danger">Log out</button></Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </div>
    </Header>
  )
})