import { useState } from "react";
import { QuantityPicker } from "react-qty-picker";
import { TimePicker } from "@mui/x-date-pickers/TimePicker";
import moment from "moment";

import "../../styles/AddCustomRestaurant.css";
import axios from "axios";
import { ToastContainer } from "react-toastify";
import { addCustomRestaurant } from "../../service/customRestaurantService";
import { toastError, toastSuccess } from "../../helpers/toastHandler";

export default function AddCustomRestaurant() {
    const [quantity, setQuantity] = useState(1);
    const [formFields, setFormFields] = useState([
        { name: "", price: "", quantity: 1, willOrder: 0 }
    ]);
    const [restaurantName, setRestaurantName] = useState("");
    const [orderTime, setOrderTime] = useState("");

    const handleFormChange = (event, index) => {
        let data = [...formFields];
        data[index][event.target.name] = event.target.value;
        setFormFields(data);
    };

    const handleCheckboxChange = (event, index) => {
        let data = [...formFields];
        data[index].willOrder = event.target.checked ? 1 : 0;
        setFormFields(data);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const orderData = { restaurantName, orderTime, meals: formFields };
        addCustomRestaurant(orderData).then((response) => {
            toastSuccess("Successfully added restaurant.");
        });

        setQuantity(1);
    };

    const onInputChange = (e, form) => {
        console.log(form);
        form.quantity = e;
    };

    const addFields = () => {
        let object = {
            name: "",
            price: "",
            quantity: 1,
            willOrder: 0
        };

        setFormFields([...formFields, object]);
    };

    const removeFields = (index) => {
        let data = [...formFields];
        data.splice(index, 1);
        setFormFields(data);
    };

    const handleS = (e) => {
        e.preventDefault();
    };

    const changeTime = (e) => {
        setOrderTime(moment(e).format("HH:mm"));
    };

    const changeName = (e) => {
        setRestaurantName(e.target.value);
    };

    return (
        <div className="content">
            <form className="form-add-restaurant" onSubmit={handleS}>
                <input
                    className="form-control"
                    name="restaurant-name"
                    placeholder="Restaurant Name"
                    onChange={(e) => changeName(e)}
                />
                <TimePicker className="time-picker" onChange={(e) => changeTime(e)} />

                {formFields.map((form, index) => {
                    return (
                        <div key={index}>
                            <br />
                            <label>Meal {index + 1}</label>
                            <input
                                className="form-control"
                                name="name"
                                placeholder="Name"
                                onChange={(event) => handleFormChange(event, index)}
                                value={form.name}
                            />
                            <input
                                className="form-control"
                                name="price"
                                placeholder="Price"
                                onChange={(event) => handleFormChange(event, index)}
                                value={form.price}
                            />
                            <label>I will order: </label>
                            <input
                                type="checkbox"
                                className="form-check-input mx-3"
                                onChange={(event) => handleCheckboxChange(event, index)}
                                checked={Boolean(form.willOrder)}
                            />
                            {Boolean(form.willOrder) && (
                                <QuantityPicker
                                    min={1}
                                    value={quantity}
                                    onChange={(e) => onInputChange(e, form)}
                                />
                            )}
                            {index !== 0 ? (
                                <>
                                    <button
                                        className="remove-button"
                                        onClick={() => removeFields(index)}>
                                        Remove
                                    </button>
                                </>
                            ) : (
                                <></>
                            )}
                        </div>
                    );
                })}
            </form>
            <div className="add-more-meals-button mt-5" style={{ textAlign: "center" }}>
                <button onClick={addFields}>Add More Meals...</button>
            </div>
            <br />
            <div style={{ textAlign: "center" }}>
                <button onClick={handleSubmit} className="btn btn-outline-primary mt-4">
                    Submit
                </button>
            </div>
            <ToastContainer />
        </div>
    );
}
