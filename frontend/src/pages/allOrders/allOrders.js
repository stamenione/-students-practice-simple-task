import { UserContext } from "../../UserContext";
import { setUserOrder } from "../../service/orderService";
import { toastError } from "../../helpers/toastHandler";
import { useContext, useEffect, useState } from "react";
import { QuantityPicker } from "react-qty-picker";
import { ToastContainer } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { confirmAlert } from "react-confirm-alert";

import moment from "moment";
import "react-confirm-alert/src/react-confirm-alert.css";
import "../../styles/AllOrders.css";

function AllOrders() {
    const { currentUser, setCurrentUser } = useContext(UserContext);
    const navigate = useNavigate();
    const [orderSucceeded, setOrderSucceeded] = useState(false);
    const [totalPrice, setTotalPrice] = useState(0);

    useEffect(() => {
        if (!(currentUser && currentUser.email !== "")) {
            navigate("/");
        } else {
            if (localStorage.hasOwnProperty("orderedMeals")) {
                currentUser.orderedMeals = JSON.parse(localStorage.getItem("orderedMeals"));
            }

            let total = 0;
            currentUser.orderedMeals.forEach((item) => {
                total += item.price * item.quantity;
            });
            setTotalPrice(total);
        }
    }, [currentUser, navigate]);

    const onInputChange = (newQuantity, item) => {
        const updatedMeals = currentUser.orderedMeals.map((meal) => {
            if (meal.name === item.name) {
                return {
                    ...meal,
                    quantity: newQuantity
                };
            }
            return meal;
        });

        setCurrentUser({
            ...currentUser,
            orderedMeals: updatedMeals
        });

        localStorage.setItem("orderedMeals", JSON.stringify(updatedMeals));

        let total = 0;
        updatedMeals.forEach((meal) => {
            total += meal.price * meal.quantity;
        });
        setTotalPrice(total);
    };

    const handleDeleteOrder = async (item) => {
        const updateMeals = currentUser.orderedMeals.filter((meal) => meal.name !== item.name);
        setCurrentUser({
            email: currentUser.email,
            orderedMeals: updateMeals,
            theChosenOne: currentUser.theChosenOne
        });

        localStorage.setItem("orderedMeals", JSON.stringify(updateMeals));

        let total = 0;
        updateMeals.forEach((item) => {
            total += item.price * item.quantity;
        });
        setTotalPrice(total);
    };

    const handleConfirmOrder = async () => {
        setOrderSucceeded(true);

        setUserOrder(currentUser).then((response) => {
            if (typeof response == "string") {
                toastError(response);
            } else {
                localStorage.removeItem("orderedMeals");
                currentUser.orderedMeals = [];
                navigate("/successfulOrder");
            }
        });
    };

    const msg = `Are you sure you want to order this meal?`;

    const addDialog = () => {
        confirmAlert({
            title: "Order meal",
            message: msg,
            buttons: [
                {
                    label: "Confirm",
                    onClick: handleConfirmOrder,
                    className: "confirmation-button-confirm"
                },
                {
                    label: "Cancel",
                    className: "confirmation-button-cancel"
                }
            ]
        });
    };

    return (
        <div className="content">
            {currentUser.orderedMeals.length ? (
                <>
                    <table className="w-75 mx-auto table border">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Date</th>
                                <th>Quantity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {currentUser.orderedMeals.map((item) => (
                                // eslint-disable-next-line react/jsx-key
                                <tr key={item.name}>
                                    <td>{item.name}</td>
                                    <td>{item.price}</td>
                                    <td>{moment(item.mealDate).format("D.M.YYYY.")}</td>
                                    <td>
                                        <QuantityPicker
                                            key={item.name}
                                            min={1}
                                            value={item.quantity}
                                            onChange={(e) => onInputChange(e, item)}
                                        />
                                    </td>
                                    <td>
                                        <button
                                            type="button"
                                            className="btn btn-danger delete-button"
                                            onClick={() => handleDeleteOrder(item)}>
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <div className="confirm-order">
                        <label>Sum price: {totalPrice}</label>
                        <button onClick={addDialog}>Confirm</button>
                        {orderSucceeded && <ToastContainer />}
                    </div>
                </>
            ) : (
                <>
                    <h4 className="text-center">
                        Please first add at least one meal to your list of orders
                    </h4>
                </>
            )}
        </div>
    );
}

export default AllOrders;
