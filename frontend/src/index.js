import { GetThisWeeksMenu, Login, Register } from "./config/endpoints";
import { toastError } from "./helpers/toastHandler";

import ReactDOM from "react-dom/client";
import App from "./App";
import axios from "axios";
import "./index.css";

axios.interceptors.request.use((request) => {
    const url = process.env.REACT_APP_BASE_URL;
    const req = request.url;
    request.url = url + req;

    if (req === Register || req === Login || req === GetThisWeeksMenu) {
        return request;
    } else {
        if (localStorage.hasOwnProperty("token")) {
            request.headers.Authorization = `Bearer ${localStorage.getItem("token")}`;
        }
        return request;
    }
});

axios.interceptors.response.use(
    (response) => {
        return response;
    },
    (err) => {
        if (
            err.response.status === 401 ||
            err.response.status === 402 ||
            err.response.status === 403
        ) {
            localStorage.removeItem("token");
            localStorage.removeItem("email");
            localStorage.removeItem("chosen-one");
            localStorage.removeItem("orderedMeals");

            window.location.reload();
        }

        if (
            err.response.status === 400 ||
            err.response.status === 404 ||
            err.response.status === 400 ||
            err.response.status === 409
        ) {
            toastError(err.response.data.message);
        }
    }
);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://cra.link/PWA
// serviceWorkerRegistration.unregister();

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
