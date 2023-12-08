import { UserContext } from "../../UserContext";
import { useContext, useEffect } from "react";
import { Navigate } from "react-router-dom";
import { clearLocalStorage } from "../../helpers/clearLocalStorage";

function Logout() {
    const { setCurrentUser } = useContext(UserContext);

    useEffect(() => {
        setCurrentUser(null);
        clearLocalStorage();
    }, [setCurrentUser]);

    return <Navigate to="/login" replace={true} />;
}

export default Logout;
