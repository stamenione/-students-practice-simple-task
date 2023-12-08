import { useNavigate } from "react-router-dom";
import "../../styles/Error.css";

function Error() {
    const navigate = useNavigate();
    const handleErrorClick = () => {
        navigate("/");
    };

    return (
        <div className="content">
            <h3 className="text-error">Ops, something is wrong. Back to home page.</h3>
            <button className="btn-error" onClick={handleErrorClick}>
                Home page
            </button>
        </div>
    );
}

export default Error;
