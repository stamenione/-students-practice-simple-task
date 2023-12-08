export function clearLocalStorage() {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("chosen-one");
    localStorage.removeItem("orderedMeals");
}
