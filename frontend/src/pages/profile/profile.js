import { UserContext } from "../../UserContext";
import { getUserData, setUserData } from "../../service/profileService";
import { toastError, toastSuccess } from "../../helpers/toastHandler";
import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";

import "react-toastify/dist/ReactToastify.css";
import "../../styles/Profile.css";

function Profile() {
    const { currentUser } = useContext(UserContext);
    const navigate = useNavigate();

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [number, setNumber] = useState("");
    const [streetName, setStreetName] = useState("");
    const [streetNumber, setStreetNumber] = useState("");

    useEffect(() => {
        if (!(currentUser && currentUser.email !== "")) {
            navigate("/");
        }
    }, [currentUser, navigate]);

    useEffect(() => {
        try {
            getUserData().then((response) => {
                const { firstName, lastName, email, password, number, streetName, streetNumber } =
                    response;
                setFirstName(firstName);
                setLastName(lastName);
                setEmail(email);
                setPassword(password);
                setNumber(number);
                setStreetName(streetName);
                setStreetNumber(streetNumber);
            });
        } catch (err) {
            toastError(err.response?.message);
        }
    }, [currentUser, navigate]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const userData = { firstName, lastName, email, password, number, streetName, streetNumber };
        setUserData(userData).then((response) => {
            toastSuccess("Update successful.");
            setPassword("");
        });
    };

    return (
        <div className="content">
            <form className="form-profile" onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label mb-2">Change profile ({email})</label>
                    <br />
                    <label>Name:</label>
                    <input
                        type={"text"}
                        className="form-control mb-3"
                        placeholder="First Name"
                        name="firstname"
                        value={firstName || ""}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                    <label>Lastname:</label>
                    <input
                        type={"text"}
                        className="form-control mb-3"
                        placeholder="Last Name"
                        name="lastname"
                        value={lastName || ""}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                    <label>Phone:</label>
                    <input
                        type={"text"}
                        className="form-control mb-3"
                        placeholder="Number"
                        name="number"
                        value={number || ""}
                        onChange={(e) => setNumber(e.target.value)}
                        required
                    />
                    <label>Street name:</label>
                    <input
                        type={"text"}
                        className="form-control mb-3"
                        placeholder="Street Name"
                        name="streetname"
                        value={streetName || ""}
                        onChange={(e) => setStreetName(e.target.value)}
                        required
                    />
                    <label>Street number:</label>
                    <input
                        type={"text"}
                        className="form-control mb-3"
                        placeholder="Steet Number"
                        name="streetnumber"
                        value={streetNumber || ""}
                        onChange={(e) => setStreetNumber(e.target.value)}
                        required
                    />
                    <br />
                    <label>Enter password to confirm:</label>
                    <input
                        type={"password"}
                        className="form-control mb-3"
                        placeholder="Password"
                        name="password"
                        value={password || ""}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-outline-primary mx-1">
                    Change
                </button>

                <ToastContainer />
            </form>
        </div>
    );
}

export default Profile;
