import { UserContext } from "../../UserContext";
import { getAllLastAddedDates, getAllMeals, setMenuMealData } from "../../service/mealService";
import { toastError, toastSuccess } from "../../helpers/toastHandler";
import { useContext, useEffect, useState } from "react";
import { ToastContainer } from "react-toastify";
import { Link, useNavigate } from "react-router-dom";

import moment from "moment";
import Calendar from "react-calendar";
import Select from "react-select";
import "react-toastify/dist/ReactToastify.css";
import "../../styles/AddMealMenu.css";

export default function AddMealMenu() {
    const { currentUser } = useContext(UserContext);
    const navigate = useNavigate();

    const [menuDate, setMenuDate] = useState();
    const [mealDate, setMealDate] = useState(new Date());
    const [meal, setMeal] = useState();

    const [allMeals, setAllMeals] = useState([]);
    const [allNextDates, setAllNextDates] = useState([]);

    useEffect(() => {
        if (!(currentUser && currentUser.email !== "")) {
            navigate("/");
        } else {
            getAllMeals().then((response) => {
                if (typeof response == "string") {
                    toastError(response);
                } else {
                    setAllMeals(response.map((t) => ({ value: t, label: t })));
                }
            });

            getAllLastAddedDates().then((response) => {
                if (typeof response == "string") {
                    toastError(response);
                } else {
                    setAllNextDates(
                        response.map((t) => ({ value: t, label: moment(t).format("D.M.YYYY.") }))
                    );
                }
            });
        }
    }, [currentUser, navigate]);

    const changeMeal = (e) => {
        setMeal(e.value);
    };

    const changeDate = (e) => {
        setMenuDate(e.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMenuMealData(menuDate, mealDate, meal).then((response) => {
            if (typeof response == "string") {
                toastError(response);
            } else {
                toastSuccess("Successfully added meal to menu.");
            }
        });
    };

    return (
        <div className="content">
            <nav className="nav nav-pills nav-fill">
                <Link to={"/addMeal"} className="nav-link">
                    Add Meal
                </Link>
                <Link to={"/addMenu"} className="nav-link">
                    Add Menu
                </Link>
                <Link to={"/admin"} className="nav-link active">
                    Add meal to menu
                </Link>
            </nav>
            <h1 className="my-5">Add meal to menu</h1>
            <form className="form-add-meal-menu" onSubmit={handleSubmit}>
                <label>Choose the menu u wanna add meal to</label>
                <Select
                    className="select-picker"
                    options={allNextDates}
                    onChange={(e) => changeDate(e)}
                    defaultValue={allNextDates[0]}
                />
                <label className="calendar-text mt-4 text-center mb-1">
                    Chose the day for the meal
                </label>
                <Calendar
                    value={mealDate}
                    onChange={setMealDate}
                    defaultView={"month"}
                    tileDisabled={({ date }) => date.getDay() === 0 || date.getDay() === 6}
                />
                <label className="mt-4">Select meal that you want to add to the menu</label>
                <Select
                    className="select-picker"
                    options={allMeals}
                    onChange={(e) => changeMeal(e)}
                    defaultValue={allMeals[0]}
                />
                <button type="submit" className="btn btn-outline-primary mt-4">
                    Add
                </button>

                <ToastContainer />
            </form>
        </div>
    );
}
