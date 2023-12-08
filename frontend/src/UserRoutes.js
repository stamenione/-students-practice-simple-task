import { Navigate, Outlet } from "react-router-dom";

import jwtDecode from "jwt-decode";

const UserRoutes = () => {
    let isUser = false;
    if (localStorage.hasOwnProperty("token")) {
        if (jwtDecode(localStorage.getItem("token")).sub !== "") isUser = true;
    }
    return isUser ? <Outlet /> : <Navigate to="/login" />;
};

export default UserRoutes;
