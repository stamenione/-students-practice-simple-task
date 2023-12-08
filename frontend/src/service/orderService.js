import { GetCurrentMeals, SetUserOrder } from "../config/endpoints";

import axios from "axios";

async function getCurrentMeals() {
    return new Promise((resolve) => {
        axios.get(GetCurrentMeals).then((response) => {
            resolve(response.data);
        });
    });
}

async function setUserOrder(currentUser) {
    const userOrder = currentUser.orderedMeals.map((meal) => {
        return {
            email: currentUser.email,
            menuStartingDate: meal.menuStartingDate,
            name: meal.name,
            price: meal.price,
            mealDate: meal.mealDate,
            mealType: meal.mealType,
            quantity: meal.quantity
        };
    });

    return new Promise((resolve) => {
        axios.post(SetUserOrder, userOrder).then((response) => {
            resolve(response.data);
        });
    });
}

export { getCurrentMeals, setUserOrder };
