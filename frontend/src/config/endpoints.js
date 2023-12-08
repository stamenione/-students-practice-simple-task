export const Login = "/login";
export const Register = "/register";
export const GetThisWeeksMenu = "/menu";
export const UpdateUserDetails = "/api/user/updateUserDetails";
export const GetUserData = "/api/user/userDetails";
export const SetUserOrder = "/api/user/orderMeal";
export const SetMenuData = "/api/admin/addMenu";
export const GetAllUsedDates = "/api/admin/allUsedDates";
export const SetMenuMealData = "/api/admin/addMealToMenu";
export const GetAllMeals = "/api/admin/allMeals";
export const GetAllLastAddedDates = "/api/admin/nextMenu";
export const GetMealTypes = "/api/admin/allMealTypes";
export const SetMealData = "/api/admin/addMeal";
export const GetCurrentMeals = "/api/user/currentMeals";
export const GetTodayUsersFromUserOrder = "/api/user/todayUsers";
export const AddCustomRestaurant = "/api/restaurant/addRestaurant";

export const SetUserPayedData = (email) => `/api/user/markPayed?email=${email}`;
export const IsUserTheChosenOne = (email) => `/api/user/userDetails?email=${email}`;
