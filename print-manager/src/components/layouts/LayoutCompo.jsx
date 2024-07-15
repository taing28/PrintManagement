import { memo } from "react";
import { HeaderCompo } from "./HeaderCompo";
import { FooterCompo } from "./FooterCompo";
import { Outlet } from "react-router-dom";

export const LayoutCompo = memo(() => {
    return (
        <>
            <HeaderCompo />
            <Outlet />
            <FooterCompo />
        </>
    )
})