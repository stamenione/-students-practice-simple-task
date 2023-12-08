import { UserContext } from "../../UserContext";
import { getCurrentMeals } from "../../service/orderService";
import { toastError } from "../../helpers/toastHandler";
import { setOrderMeals } from "../../helpers/setOrderMeals";
import { useContext, useEffect, useState } from "react";
import { QuantityPicker } from "react-qty-picker";
import { toast, ToastContainer } from "react-toastify";
import { useNavigate } from "react-router-dom";

import moment from "moment";
import "react-toastify/dist/ReactToastify.css";
import "../../styles/OrderMeal.css";

function OrderMeal() {
    const { currentUser, setCurrentUser } = useContext(UserContext);
    const navigate = useNavigate();
    const [quantity] = useState(1);
    const date = new Date();
    const currentDate = moment(date).format("YYYY-MM-DD");
    date.setDate(date.getDate() + 1);
    const tomorrowsDate = moment(date).format("YYYY-MM-DD");
    const [todayMenuItems, setTodayMenuItems] = useState([]);
    const [tomorrowMenuItems, setTomorrowMenuItems] = useState([]);

    const handleOrderClick = async (item) => {
        const orderedMeal = {
            name: item.name,
            price: item.price,
            mealDate: item.mealDate,
            mealType: item.mealType,
            menuStartingDate: item.menuStartingDate,
            email: currentUser.email,
            quantity: item.quantity
        };

        if (orderedMeal.quantity === undefined) {
            orderedMeal.quantity = 1;
        }

        if (currentUser.orderedMeals !== undefined) {
            const obj = currentUser.orderedMeals.find((el) => el.name === orderedMeal.name);
            if (obj) {
                obj.quantity = obj.quantity + orderedMeal.quantity;
            } else {
                setCurrentUser({
                    email: currentUser,
                    orderedMeals: [orderedMeal],
                    theChosenOne: currentUser.theChosenOne
                });
            }
        } else {
            setCurrentUser({
                email: currentUser,
                orderedMeals: [orderedMeal],
                theChosenOne: currentUser.theChosenOne
            });
        }

        setOrderMeals(orderedMeal);

        toast("successfully ordered meal");
    };

    const onInputChange = (e, item) => {
        item.quantity = e;
    };

    useEffect(() => {
        if (!(currentUser && currentUser.email !== "")) {
            navigate("/");
        } else {
            getCurrentMeals().then((response) => {
                const transformedMenuItems = response.meals.map((meal) => {
                    return {
                        name: meal.name,
                        price: meal.price,
                        mealDate: meal.mealDate,
                        mealType: meal.mealType,
                        menuStartingDate: response.startDate
                    };
                });

                if ((typeof transformedMenuItems).toString() === "object") {
                    setTodayMenuItems(
                        transformedMenuItems.filter((m) => {
                            return m.mealDate === currentDate;
                        })
                    );

                    setTomorrowMenuItems(
                        transformedMenuItems.filter((m) => {
                            return m.mealDate === tomorrowsDate;
                        })
                    );
                } else {
                    toastError(transformedMenuItems);
                }
            });
        }
    }, [currentDate, currentUser, navigate, tomorrowsDate]);

    const handleOthersClick = () => {
        navigate("/orderFromCustomRestaurant");
    };

    return (
        <div className="content">
            <h4 className="text-center my-3">For today</h4>
            {todayMenuItems.length > 0 ? (
                <>
                    <table className="w-75 mx-auto table border">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {todayMenuItems.map((item) => {
                                return (
                                    <tr key={item.name}>
                                        <td className="td-size">{item.name}</td>
                                        <td className="td-size">{item.price}</td>
                                        <td>
                                            <QuantityPicker
                                                min={1}
                                                value={quantity}
                                                onChange={(e) => onInputChange(e, item)}
                                            />
                                        </td>
                                        <td>
                                            <button onClick={() => handleOrderClick(item)}>
                                                Order
                                            </button>
                                        </td>
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </>
            ) : (
                <>
                    <h5 className="text-center mt-4 mb-5">No meals on menu for this date</h5>
                </>
            )}

            <h4 className="text-center my-3">For tomorrow</h4>
            {tomorrowMenuItems.length > 0 ? (
                <>
                    <table className="w-75 mx-auto table border">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {tomorrowMenuItems.map((item) => {
                                return (
                                    <tr key={item.name}>
                                        <td className="td-size">{item.name}</td>
                                        <td className="td-size">{item.price}</td>
                                        <td>
                                            <QuantityPicker
                                                min={1}
                                                value={quantity}
                                                onChange={(e) => onInputChange(e, item)}
                                            />
                                        </td>
                                        <td>
                                            <button onClick={() => handleOrderClick(item)}>
                                                Order
                                            </button>
                                        </td>
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </>
            ) : (
                <>
                    <h5 className="text-center mt-4 mb-5">No meals on menu for this date</h5>
                </>
            )}
            <div style={{ textAlign: "center" }}>
                <button className="mt-5" onClick={handleOthersClick}>
                    Order from other restaurants
                </button>
            </div>

            <ToastContainer />
        </div>
    );
}

export default OrderMeal;
