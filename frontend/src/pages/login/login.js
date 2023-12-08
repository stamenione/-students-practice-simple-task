import { UserContext } from "../../UserContext";
import { loginUser } from "../../service/authService";
import { isUserTheChosenOne } from "../../service/profileService";
import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import jwtDecode from "jwt-decode";
import "../../styles/Login.css";

export default function Login() {
    const [loginData, setLoginData] = useState({
        email: "",
        password: ""
    });

    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const { currentUser, setCurrentUser } = useContext(UserContext);

    const handleLoginData = (property, value) => {
        setLoginData({
            ...loginData,
            [property]: value
        });
    };

    useEffect(() => {
        if (currentUser && localStorage.hasOwnProperty("email")) {
            currentUser.email = localStorage.getItem("email");
        }

        if (currentUser && currentUser.email !== "") {
            navigate("/");
        }
    }, [currentUser, navigate]);

    useEffect(() => {
        if (localStorage.hasOwnProperty("order") && localStorage.getItem("order") === "true") {
            if (currentUser && currentUser.email !== "") {
                navigate("/orderMeal");
                localStorage.setItem("order", "false");
            }
        } else if (currentUser && currentUser.email !== "") {
            navigate("/");
        }
    }, [currentUser, navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();
        loginUser(loginData).then((response) => {
            if (response && response.access_token) {
                const token = response.access_token;
                const user = jwtDecode(token);
                localStorage.setItem("token", token);

                isUserTheChosenOne(user.sub).then((response) => {
                    setCurrentUser({ email: user.sub, orderedMeals: [], theChosenOne: response });
                    localStorage.setItem("email", user.sub);
                    localStorage.setItem("chosen-one", response);
                });
            } else {
                setError(response.message);
            }
        });
    };

    return (
        <div className="content">
            <div className="login-container">
                <form className="form-login" onSubmit={handleSubmit}>
                    {error && <div className="alert alert-danger">{error}</div>}
                    <div className="mb-3">
                        <label className="form-label mb-2">Login</label>
                        <input
                            type={"email"}
                            className="form-control mb-3"
                            placeholder="Email"
                            name="email"
                            value={loginData["email"]}
                            onChange={(e) => handleLoginData("email", e.target.value)}
                            required
                        />
                        <input
                            type={"password"}
                            className="form-control mb-3"
                            placeholder="Password"
                            name="password"
                            value={loginData["password"]}
                            onChange={(e) => handleLoginData("password", e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="btn btn-outline-primary mb-3">
                        Login
                    </button>
                    <br />
                    <Link className="mt-3 text-decoration-underline" to="/Register">
                        Sign up here
                    </Link>
                </form>
            </div>
        </div>
    );
}
