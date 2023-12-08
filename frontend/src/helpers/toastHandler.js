import { toast } from "react-toastify";

function toastError(message) {
    toast.error(message);
}

function toastSuccess(message) {
    toast.success(message);
}

function toastInfo(message) {
    toast.info(message);
}

export { toastError, toastSuccess, toastInfo };
