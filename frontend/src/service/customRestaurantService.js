import axios from "axios";
import { AddCustomRestaurant } from "../config/endpoints";

async function addCustomRestaurant(orderData) {
    return new Promise((resolve) => {
        axios.post(AddCustomRestaurant, orderData).then((response) => {
            resolve(response.data);
        });
    });
}

export { addCustomRestaurant };
