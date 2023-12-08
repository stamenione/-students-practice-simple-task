import {
    GetAllLastAddedDates,
    GetAllMeals,
    GetAllUsedDates,
    GetMealTypes,
    GetThisWeeksMenu,
    SetMealData,
    SetMenuData,
    SetMenuMealData
} from "../config/endpoints";

import axios from "axios";
import moment from "moment/moment";

async function getMealTypes() {
    return new Promise((resolve) => {
        axios.get(GetMealTypes).then((response) => {
            resolve(response.data);
        });
    });
}

async function setMealData(mealData) {
    return new Promise((resolve) => {
        axios
            .post(SetMealData, {
                ...mealData
            })
            .then((response) => {
                resolve(response.data);
            });
    });
}

async function getAllUsedDates() {
    return new Promise((resolve) => {
        axios.get(GetAllUsedDates).then((response) => {
            resolve(response.data);
        });
    });
}

async function setMenuData(startDate, description, imageURL, imageName) {
    return new Promise((resolve) => {
        axios
            .post(SetMenuData, {
                startDate: moment(startDate).format("YYYY-MM-DD"),
                description: description,
                link: imageURL,
                name: imageName
            })
            .then((response) => {
                resolve(response.data);
            });
    });
}

async function getAllMeals() {
    return new Promise((resolve) => {
        axios.get(GetAllMeals).then((response) => {
            resolve(response.data);
        });
    });
}

async function getAllLastAddedDates() {
    return new Promise((resolve) => {
        axios.get(GetAllLastAddedDates).then((response) => {
            resolve(response.data);
        });
    });
}

async function setMenuMealData(menuDate, mealDate, meal) {
    return new Promise((resolve) => {
        axios
            .post(SetMenuMealData, {
                startDate: moment(menuDate).format("YYYY-MM-DD"),
                mealName: meal,
                mealDate: moment(mealDate).format("YYYY-MM-DD")
            })
            .then((response) => {
                resolve(response.data);
            });
    });
}

async function getThisWeeksMenu() {
    return new Promise((resolve) => {
        axios.get(GetThisWeeksMenu).then((response) => {
            resolve(response.data);
        });
    });
}

export {
    getMealTypes,
    setMealData,
    getAllUsedDates,
    setMenuData,
    getAllMeals,
    getAllLastAddedDates,
    setMenuMealData,
    getThisWeeksMenu
};
