import { memo } from "react";
import { Outlet } from "react-router-dom";

export const AuthPage = memo(() => {
    return (
        <div className="wrapper-container">
            <div className="wrapper mt-4 mb-4">
                <Outlet />
            </div>
        </div>
    )
})