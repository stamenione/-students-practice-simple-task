import { registerUser } from "../../service/authService";
import { toastError } from "../../helpers/toastHandler";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";

import "react-toastify/dist/ReactToastify.css";
import "../../styles/Register.css";

export default function Register() {
    const [registrationData, setRegistrationData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        number: "",
        streetName: "",
        streetNumber: ""
    });

    const navigate = useNavigate();

    const handleRegistrationData = (property, value) => {
        setRegistrationData({
            ...registrationData,
            [property]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        registerUser(registrationData).then((response) => {
            if (typeof response == "string") {
                toastError(response);
            } else {
                navigate("/login");
            }
        });
    };

    return (
        <div className="content">
            <form className="form-register" onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label mb-2">Register</label>
                    <input
                        type={"text"}
                        required
                        className="form-control mb-3"
                        placeholder="First Name"
                        name="firstname"
                        value={registrationData["firstName"]}
                        onChange={(e) => handleRegistrationData("firstName", e.target.value)}
                    />
                    <input
                        type={"text"}
                        required
                        className="form-control mb-3"
                        placeholder="Last Name"
                        name="lastname"
                        value={registrationData["lastName"]}
                        onChange={(e) => handleRegistrationData("lastName", e.target.value)}
                    />
                    <input
                        type={"email"}
                        required
                        className="form-control mb-3"
                        placeholder="Email"
                        name="email"
                        value={registrationData["email"]}
                        onChange={(e) => handleRegistrationData("email", e.target.value)}
                    />
                    <input
                        type={"password"}
                        required
                        className="form-control mb-3"
                        placeholder="Password"
                        name="password"
                        value={registrationData["password"]}
                        onChange={(e) => handleRegistrationData("password", e.target.value)}
                    />
                    <input
                        type={"text"}
                        required
                        className="form-control mb-3"
                        placeholder="Number"
                        name="number"
                        value={registrationData["number"]}
                        onChange={(e) => handleRegistrationData("number", e.target.value)}
                    />
                    <input
                        type={"text"}
                        required
                        className="form-control mb-3"
                        placeholder="Street Name"
                        name="streetname"
                        value={registrationData["streetName"]}
                        onChange={(e) => handleRegistrationData("streetName", e.target.value)}
                    />
                    <input
                        type={"text"}
                        required
                        className="form-control mb-3"
                        placeholder="Steet Number"
                        name="streetnumber"
                        value={registrationData["streetNumber"]}
                        onChange={(e) => handleRegistrationData("streetNumber", e.target.value)}
                    />
                </div>
                <button type="submit" className="btn btn-outline-primary">
                    Register
                </button>

                <ToastContainer />
            </form>
        </div>
    );
}
