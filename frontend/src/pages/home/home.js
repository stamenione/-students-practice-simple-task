import { UserContext } from "../../UserContext";
import { getThisWeeksMenu } from "../../service/mealService";
import { toastError } from "../../helpers/toastHandler";
import { useNavigate } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { ToastContainer } from "react-toastify";

import moment from "moment/moment";
import "../../styles/Home.css";

function Home() {
    const navigate = useNavigate();

    const { currentUser } = useContext(UserContext);

    const [menuDate, setMenuDate] = useState();
    const [menuDescription, setMenuDescription] = useState();
    const [meals, setMeals] = useState([]);

    useEffect(() => {
        if (currentUser && localStorage.hasOwnProperty("email")) {
            currentUser.email = localStorage.getItem("email");
        }
    }, [currentUser]);

    const handleClick = () => {
        if (currentUser && currentUser.email !== "") {
            navigate("/orderMeal");
        } else {
            localStorage.setItem("order", "true");
            navigate("/login");
        }
    };

    useEffect(() => {
        getThisWeeksMenu().then((response) => {
            if (typeof response == "string") {
                toastError(response);
            } else {
                setMenuDate(response.startDate);
                setMenuDescription(response.description);

                const meals = response.meals.map((meal) => {
                    if (meal.dayBefore === 1) {
                        return {
                            name: meal.name + " (must order day before)",
                            price: meal.price,
                            mealType: meal.mealType,
                            mealDate: meal.mealDate
                        };
                    } else {
                        return {
                            name: meal.name,
                            price: meal.price,
                            mealType: meal.mealType,
                            mealDate: meal.mealDate
                        };
                    }
                });
                setMeals(meals);
            }
        });
    }, [menuDate]);

    return (
        <div className="content">
            {meals.length ? (
                <>
                    <button className="btn-home d-flex mx-auto mb-5" onClick={handleClick}>
                        Order
                    </button>

                    <div className="w-auto mx-auto">
                        <h3 className="text-center pb-3">
                            Menu {moment(menuDate).format("D.M.YYYY.")}
                            <br />({menuDescription})
                        </h3>
                        <table className="w-75 mx-auto table border">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Type</th>
                                    <th>Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                {meals.map((meal) => (
                                    <tr key={meal.name}>
                                        <td>{meal.name}</td>
                                        <td>{meal.price}</td>
                                        <td>{meal.mealType}</td>
                                        <td>{moment(meal.mealDate).format("D.M.YYYY.")}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </>
            ) : (
                <>
                    <h4 className="text-center">There is no a menu for this week yet</h4>
                </>
            )}

            <ToastContainer />
        </div>
    );
}

export default Home;
