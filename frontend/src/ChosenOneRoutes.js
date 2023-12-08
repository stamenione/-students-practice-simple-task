import { Navigate, Outlet } from "react-router-dom";

const ChosenOneRoutes = () => {
    let isChosenOne = false;
    if (localStorage.hasOwnProperty("chosen-one")) {
        if (localStorage.getItem("chosen-one") === "1") {
            isChosenOne = true;
        }
    }
    return isChosenOne ? <Outlet /> : <Navigate to="/login" />;
};

export default ChosenOneRoutes;
