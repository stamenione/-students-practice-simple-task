export function setOrderMeals(orderedMeal) {
    if (!localStorage.hasOwnProperty("orderedMeals")) {
        localStorage.setItem("orderedMeals", JSON.stringify([]));
        const stored = JSON.parse(localStorage.getItem("orderedMeals"));
        stored.push(JSON.parse(JSON.stringify(orderedMeal)));
        localStorage.setItem("orderedMeals", JSON.stringify(stored));
    } else {
        const storedMeals = JSON.parse(localStorage.getItem("orderedMeals"));
        const ob = storedMeals.find((el) => el.name == orderedMeal.name);
        if (ob) {
            ob.quantity = ob.quantity + orderedMeal.quantity;
            localStorage.setItem("orderedMeals", JSON.stringify(storedMeals));
        } else {
            storedMeals.push(JSON.parse(JSON.stringify(orderedMeal)));
            localStorage.setItem("orderedMeals", JSON.stringify(storedMeals));
        }
    }
}
