import { GetTodayUsersFromUserOrder, SetUserPayedData } from "../config/endpoints";

import axios from "axios";

async function getTodayUsersFromUserOrder() {
    return new Promise((resolve) => {
        axios.get(GetTodayUsersFromUserOrder).then((response) => {
            resolve(response.data);
        });
    });
}

async function setUserPayedData(email) {
    return new Promise((resolve) => {
        axios.post(SetUserPayedData(email)).then((response) => {
            resolve(response.data);
        });
    });
}

export { getTodayUsersFromUserOrder, setUserPayedData };
