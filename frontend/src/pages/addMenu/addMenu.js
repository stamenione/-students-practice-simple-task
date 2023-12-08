import { CheckWeekends } from "../../helpers/checkWeekends";
import { CheckAvailableDatesinDatabase } from "../../helpers/checkAvailableDatesinDatabase";
import { UserContext } from "../../UserContext";
import { getAllUsedDates, setMenuData } from "../../service/mealService";
import { toastError, toastSuccess } from "../../helpers/toastHandler";
import { ToastContainer } from "react-toastify";
import { Link, useNavigate } from "react-router-dom";
import { useContext, useEffect, useState } from "react";

import moment from "moment";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import "react-toastify/dist/ReactToastify.css";
import "../../styles/AddMenu.css";

export default function AddMenu() {
    const { currentUser } = useContext(UserContext);
    const navigate = useNavigate();

    const [startDate, setStartDate] = useState("");
    const [imageName, setImageName] = useState();
    const [description, setDescription] = useState();
    const [imageURL, setImageURL] = useState();

    const [usedDates, setUsedDates] = useState([]);

    useEffect(() => {
        if (!(currentUser && currentUser.email !== "")) {
            navigate("/");
        } else {
            getAllUsedDates().then((response) => {
                if (typeof response == "string") {
                    toastError(response);
                } else {
                    setUsedDates(response);
                }
            });
        }
    }, [currentUser, navigate]);

    const handleStartDate = (e) => {
        if (
            !CheckWeekends(e) &&
            CheckAvailableDatesinDatabase(moment(e).format("YYYY-MM-DD"), usedDates)
        ) {
            setStartDate(e);
        } else {
            toastError("The chosen date " + moment(e).format("D.M.YYYY.") + " already exists.");
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMenuData(startDate, description, imageURL, imageName).then((response) => {
            if (typeof response == "string") {
                toastError(response);
            } else {
                toastSuccess("Successfully added menu");
            }
        });
    };

    return (
        <div className="content">
            <nav className="nav nav-pills nav-fill">
                <Link className="nav-link" to={"/addMeal"}>
                    Add Meal
                </Link>
                <Link className="nav-link active" to={"/addMenu"}>
                    Add Menu
                </Link>
                <Link className="nav-link" to={"/admin"}>
                    Add meal to menu
                </Link>
            </nav>
            <h1 className="my-5">Add Menu</h1>
            <form className="form-add-menu" onSubmit={handleSubmit}>
                <label className="calendar-text mb-1">
                    Add menu starting date (You can only add monday)
                </label>
                <Calendar
                    value={startDate instanceof Date ? startDate : null}
                    onChange={handleStartDate}
                    defaultView={"month"}
                    tileDisabled={({ date }) => date.getDay() !== 1}
                />
                <label className="mt-4">Menu description</label>
                <input
                    type={"text"}
                    className="form-control"
                    placeholder="Description"
                    name="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                />
                <label>Image name</label>
                <input
                    type={"text"}
                    className="form-control"
                    placeholder="Image name"
                    name="imageName"
                    value={imageName}
                    onChange={(e) => setImageName(e.target.value)}
                />
                <label>Add menu image</label>
                <input
                    type={"url"}
                    className="form-control "
                    placeholder="Image url"
                    name="imageURL"
                    value={imageURL}
                    onChange={(e) => setImageURL(e.target.value)}
                />
                <button type="submit" className="btn btn-outline-primary mt-3">
                    Add
                </button>
                <ToastContainer />
            </form>
        </div>
    );
}
