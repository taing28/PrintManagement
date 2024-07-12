import { memo } from "react";
import { useNavigate } from "react-router-dom";

export const Header = memo(() => {
    const navigate = useNavigate();

    const logOut = () => {
        localStorage.removeItem('authToken');
        navigate('/auth/login');
    }

    return (
        <>
            <h1>Header</h1>
            <button onClick={logOut} className="btn btn-danger">Log out</button>
        </>
    )
})