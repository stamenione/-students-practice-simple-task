import { Navigate, Outlet } from "react-router-dom";

import jwtDecode from "jwt-decode";

const AdminRoutes = () => {
    let isAdmin = false;
    if (localStorage.hasOwnProperty("token")) {
        if (jwtDecode(localStorage.getItem("token")).role[0] === "role_admin") {
            isAdmin = true;
        }
    }
    return isAdmin ? <Outlet /> : <Navigate to="/login" />;
};

export default AdminRoutes;
