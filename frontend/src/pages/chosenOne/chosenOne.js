import { UserContext } from "../../UserContext";
import { getTodayUsersFromUserOrder, setUserPayedData } from "../../service/chosenOneService";
import { useContext, useEffect, useState } from "react";
import { ToastContainer } from "react-toastify";
import { useNavigate } from "react-router-dom";

function ChosenOne() {
    const [users, setUsers] = useState([]);
    const { currentUser } = useContext(UserContext);
    const navigate = useNavigate();

    useEffect(() => {
        if (!(currentUser && currentUser.email !== "")) {
            navigate("/");
        } else {
            getTodayUsersFromUserOrder().then((response) => {
                setUsers(response);
            });
        }
    }, [currentUser, navigate]);

    const handleChange = (e, user) => {
        setUserPayedData(user.email);
    };

    return (
        <div className="content">
            <div>
                {users.length ? (
                    <>
                        <table className="w-75 mx-auto table border">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Amount</th>
                                    <th>Payed</th>
                                </tr>
                            </thead>
                            <tbody>
                                {users.map((user, index) => (
                                    // eslint-disable-next-line react/jsx-key
                                    <tr key={index}>
                                        <td>
                                            {user.firstName} {user.lastName}
                                        </td>
                                        <td>{user.email}</td>
                                        <td>{user.needToPay}</td>
                                        <td>
                                            <input
                                                type={"checkbox"}
                                                defaultChecked={user.payed}
                                                onChange={(e) => handleChange(e, user)}
                                                className="form-check-input"
                                            />
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </>
                ) : (
                    <>
                        <h4 className="text-center">There is not a single order for today</h4>
                    </>
                )}
            </div>
            <ToastContainer />
        </div>
    );
}

export default ChosenOne;
