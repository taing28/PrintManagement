import { memo } from "react";
import { Outlet } from "react-router-dom";

export const AuthPage = memo(() => {
    return (
        <div className="wrapper">
        <Outlet/>
        </div>
    )
})