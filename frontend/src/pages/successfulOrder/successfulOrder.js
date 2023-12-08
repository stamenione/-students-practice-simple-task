import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

import "../../styles/SuccessfulOrder.css";

function SuccessfulOrder() {
    const navigate = useNavigate();
    const handleClick = () => {
        navigate("/");
    };

    useEffect(() => {
        setTimeout(() => navigate("/"), 2000);
    }, [navigate]);

    return (
        <div className="content">
            <h3 className="text-succ">You have successfully placed your order</h3>
            <button className="btn-succ" onClick={handleClick}>
                Home page
            </button>
        </div>
    );
}

export default SuccessfulOrder;
