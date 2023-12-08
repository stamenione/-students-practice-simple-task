import { Login, Register } from "../config/endpoints";

import axios from "axios";

async function registerUser(registrationData) {
    return new Promise((resolve) => {
        axios
            .post(Register, {
                ...registrationData
            })
            .then((response) => {
                resolve(response.data);
            });
    });
}

async function loginUser(loginData) {
    return new Promise((resolve) => {
        axios
            .post(
                Login,
                {},
                {
                    params: {
                        username: loginData.email,
                        password: loginData.password
                    }
                }
            )
            .then((response) => {
                resolve(response.data);
            });
    });
}

export { registerUser, loginUser };
