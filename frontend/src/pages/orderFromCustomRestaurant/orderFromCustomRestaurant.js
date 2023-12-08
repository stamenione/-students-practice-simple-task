import "../../styles/OrderFromCustomRestaurant.css";
import Select from "react-select";
import { QuantityPicker } from "react-qty-picker";
import { ToastContainer } from "react-toastify";
import { useState, useEffect, useContext } from "react";
import axios from "axios";
import { confirmAlert } from "react-confirm-alert";
import { setUserOrder } from "../../service/orderService";
import { toastError, toastSuccess } from "../../helpers/toastHandler";
import { UserContext } from "../../UserContext";

export default function OrderFromCustomRestaurant() {
    const [todayMenuItems, setTodayMenuItems] = useState([]);
    const [restaurants, setRestaurants] = useState([]);
    const [selectedRestaurant, setSelectedRestaurant] = useState(null);
    const [quantity] = useState(1);
    const { currentUser } = useContext(UserContext);

    useEffect(() => {
        axios.get("/api/restaurant/allRestaurants").then((response) => {
            setRestaurants(response.data);
        });
    }, []);

    useEffect(() => {
        if (selectedRestaurant) {
            axios
                .get(`/api/restaurant/restaurantMenu?name=${selectedRestaurant}`)
                .then((response) => {
                    setTodayMenuItems(response.data.map((item) => ({ ...item, quantity: 1 })));
                    console.log(response.data);
                });
        } else {
            setTodayMenuItems([]);
        }
    }, [selectedRestaurant]);

    const handleRestaurantChange = (selectedOption) => {
        setSelectedRestaurant(selectedOption.value);
    };

    const options = restaurants
        ? restaurants.map((restaurant) => {
              return {
                  value: restaurant,
                  label: restaurant
              };
          })
        : [];

    const handleConfirmOrder = async (meal) => {
        const orderData = {
            mealName: meal.name,
            mealPrice: meal.price,
            restaurantName: selectedRestaurant,
            email: currentUser.email,
            quantity: meal.quantity
        };

        axios
            .post("/api/restaurant/order", orderData)
            .then((response) => {
                toastSuccess("Successfully ordered meal.");
            })
            .catch((error) => {
                toastError(error.response.data.message);
            });
    };

    const onInputChange = (e, item) => {
        item.quantity = e;
    };

    const addDialog = (meal) => {
        console.log(meal);
        const msg = `Are you sure you want to order ${meal.name}?`;
        confirmAlert({
            title: "Order meal",
            message: msg,
            buttons: [
                {
                    label: "Confirm",
                    onClick: () => handleConfirmOrder(meal),
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
            <Select className="select-picker" options={options} onChange={handleRestaurantChange} />
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
                                            <button onClick={() => addDialog(item)}>Order</button>
                                        </td>
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </>
            ) : (
                <>
                    <h5 className="text-center mt-4 mb-5">No meals on this menu</h5>
                </>
            )}
            <ToastContainer />
        </div>
    );
}
