import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Header from "./components/header/header";
import Home from "./pages/home/home";
import Register from "./pages/register/register";
import Login from "./pages/login/login";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Error from "./pages/error/error";
import Profile from "./pages/profile/profile";
import { UserProvider } from "./UserContext";
import Logout from "./components/logout/logout";
import OrderMeal from "./pages/orderMeal/orderMeal";
import AddMeal from "./pages/addMeal/addMeal";
import AddMealMenu from "./pages/addMealMenu/addMealMenu";
import AddMenu from "./pages/addMenu/addMenu";
import Footer from "./components/footer/footer";
import AllOrders from "./pages/allOrders/allOrders";
import ChosenOne from "./pages/chosenOne/chosenOne";
import AdminRoutes from "./AdminRoutes";
import UserRoutes from "./UserRoutes";
import ChosenOneRoutes from "./ChosenOneRoutes";
import BackgroundVideo from "./components/backgroundVideo/backgroundVideo";
import SuccessfulOrder from "./pages/successfulOrder/successfulOrder";
import AddCustomRestaurant from "./pages/addCustomRestaurant/addCustomRestaurant";
import OrderFromCustomRestaurant from "./pages/orderFromCustomRestaurant/orderFromCustomRestaurant";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";

function App() {
    return (
        <LocalizationProvider dateAdapter={AdapterDateFns}>
            <UserProvider>
                <BrowserRouter>
                    <BackgroundVideo />
                    <Header />
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/error" element={<Error />} />
                        <Route path="/logout" element={<Logout />} />
                        <Route element={<UserRoutes />}>
                            <Route path="/orderMeal" element={<OrderMeal />} />
                            <Route path="/profile" element={<Profile />} />
                            <Route path="/allOrders" element={<AllOrders />} />
                            <Route path="/successfulOrder" element={<SuccessfulOrder />} />
                            <Route path="/addCustomRestaurant" element={<AddCustomRestaurant />} />
                            <Route
                                path="/orderFromCustomRestaurant"
                                element={<OrderFromCustomRestaurant />}
                            />
                        </Route>
                        <Route element={<ChosenOneRoutes />}>
                            <Route path="/chosenOne" element={<ChosenOne />} />
                        </Route>
                        <Route element={<AdminRoutes />}>
                            <Route path="/addMeal" element={<AddMeal />} />
                            <Route path="/addMenu" element={<AddMenu />} />
                            <Route path="/admin" element={<AddMealMenu />} />
                        </Route>
                        <Route path="*" element={<Navigate replace to="/error" />} />
                    </Routes>
                    <Footer />
                </BrowserRouter>
            </UserProvider>
        </LocalizationProvider>
    );
}

export default App;
