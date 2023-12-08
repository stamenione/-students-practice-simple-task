import { getMealTypes, setMealData } from "../../service/mealService";
import { UserContext } from "../../UserContext";
import { toastError, toastSuccess } from "../../helpers/toastHandler";
import { ToastContainer } from "react-toastify";
import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import Select from "react-select";
import "react-toastify/dist/ReactToastify.css";
import "../../styles/AddMeal.css";

export default function AddMeal() {
    const { currentUser } = useContext(UserContext);
    const navigate = useNavigate();

    const [name, setName] = useState();
    const [price, setPrice] = useState();
    const [dayBefore, setDayBefore] = useState();
    const [mealType, setMealType] = useState();

    const [isChecked, setIsChecked] = useState(false);

    const [types, setTypes] = useState([]);

    useEffect(() => {
        if (!(currentUser && currentUser.email !== "")) {
            navigate("/");
        } else {
            getMealTypes().then((response) => {
                if (typeof response == "string") {
                    toastError(response);
                } else {
                    setTypes(response.map((t) => ({ value: t, label: t })));
                }
            });
        }
    }, [currentUser, navigate]);

    const onInputChange = (e) => {
        setIsChecked(e.target.checked);
        if (!isChecked) {
            setDayBefore(1);
        } else {
            setDayBefore(0);
        }
    };

    const changeType = (e) => {
        setMealType(e.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const mealData = { name, price, dayBefore, mealType };
        setMealData(mealData).then((response) => {
            if (typeof response == "string") {
                toastError(response);
            } else {
                toastSuccess("Successfully added meal");
            }
        });
    };

    return (
        <div className="content">
            <div>
                <nav className="nav nav-pills nav-fill">
                    <Link className="nav-link active" to={"/addMeal"}>
                        Add Meal
                    </Link>
                    <Link className="nav-link" to={"/addMenu"}>
                        Add Menu
                    </Link>
                    <Link className="nav-link" to={"/admin"}>
                        Add meal to menu
                    </Link>
                </nav>
            </div>
            <h1 className="my-5">Add Meal</h1>
            <form className="form-add-meal" onSubmit={handleSubmit}>
                <label>Meal name</label>
                <input
                    type={"text"}
                    className="form-control"
                    placeholder="Meal name"
                    name="name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <label>Meal price</label>
                <input
                    type={"text"}
                    className="form-control "
                    placeholder="Price"
                    name="price"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                />
                <label>Check, and meal will be available to order day before.</label>
                <br />
                <input type={"checkbox"} checked={isChecked} onChange={(e) => onInputChange(e)} />
                <br />
                <label>Select meal type</label>
                <Select
                    className="select-picker"
                    options={types}
                    onChange={(e) => changeType(e)}
                    defaultValue={types[0]}
                />
                <button type="submit" className="btn btn-outline-primary mt-4">
                    Add
                </button>

                <ToastContainer />
            </form>
        </div>
    );
}
