import { memo } from "react";
import { Header } from "./Header";
import { Footer } from "./Footer";
import { Outlet } from "react-router-dom";

export const Layout = memo(() => {
    return (
        <>
            <Header />
            <Outlet />
            <Footer />
        </>
    )
})