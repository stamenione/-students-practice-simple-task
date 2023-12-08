import { GetUserData, IsUserTheChosenOne, UpdateUserDetails } from "../config/endpoints";

import axios from "axios";

async function getUserData() {
    return new Promise((resolve) => {
        axios.get(GetUserData).then((response) => {
            resolve(response.data);
        });
    });
}

async function isUserTheChosenOne(email) {
    return new Promise((resolve) => {
        axios.get(IsUserTheChosenOne(email)).then((response) => {
            resolve(response.data.theChosenOne);
        });
    });
}

async function setUserData(userData) {
    return new Promise((resolve) => {
        axios
            .put(UpdateUserDetails, {
                ...userData
            })
            .then((response) => {
                resolve(response.data);
            });
    });
}

export { getUserData, setUserData, isUserTheChosenOne };
