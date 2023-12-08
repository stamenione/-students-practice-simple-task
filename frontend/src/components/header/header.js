import "../../styles/Header.css";
import { UserContext } from "../../UserContext";

import { useContext, useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import { FaBars, FaTimes } from "react-icons/fa";

import jwtDecode from "jwt-decode";

function Header() {
    const { currentUser } = useContext(UserContext);
    const navRef = useRef();
    const [showMenu, setShowMenu] = useState(false);

    useEffect(() => {
        if (currentUser && localStorage.hasOwnProperty("email")) {
            currentUser.email = localStorage.getItem("email");
        }
    }, [currentUser]);

    const showNavbar = () => {
        setShowMenu(!showMenu);
    };

    const closeNavbar = () => {
        setShowMenu(false);
    };

    return (
        <header className="header-class">
            <nav className="sticky-top">
                <Link to="/">
                    <h1 className="company-logo">ST-meal</h1>
                </Link>

                <button className={showMenu ? "nav-btn-clicked" : "nav-btn"} onClick={showNavbar}>
                    {showMenu ? <FaTimes /> : <FaBars />}
                </button>
                <ul className={`nav-links ${showMenu ? "show-menu" : ""}`} ref={navRef}>
                    {localStorage.hasOwnProperty("email") &&
                    localStorage.hasOwnProperty("token") &&
                    localStorage.getItem("email") !== "" &&
                    jwtDecode(localStorage.getItem("token")).role[0] === "role_admin" ? (
                        <>
                            {localStorage.hasOwnProperty("chosen-one") &&
                            localStorage.getItem("chosen-one") === "1" ? (
                                <li>
                                    <Link to="/chosenOne" onClick={closeNavbar}>
                                        Chosen one
                                    </Link>
                                </li>
                            ) : (
                                <></>
                            )}
                            <li>
                                <Link to="/addCustomRestaurant" onClick={closeNavbar}>
                                    Custom order
                                </Link>
                            </li>
                            <li>
                                <Link to="/allOrders" onClick={closeNavbar}>
                                    Orders
                                </Link>
                            </li>
                            <li>
                                <Link to="/admin" onClick={closeNavbar}>
                                    Admin Menu
                                </Link>
                            </li>
                            <li>
                                <Link to="/logout" onClick={closeNavbar}>
                                    Log out
                                </Link>
                            </li>
                        </>
                    ) : (
                        <>
                            {localStorage.hasOwnProperty("email") &&
                            localStorage.getItem("email") !== "" ? (
                                <>
                                    {localStorage.hasOwnProperty("chosen-one") &&
                                    localStorage.getItem("chosen-one") === "1" ? (
                                        <li>
                                            <Link to="/chosenOne" onClick={closeNavbar}>
                                                Chosen One
                                            </Link>
                                        </li>
                                    ) : (
                                        <></>
                                    )}
                                    <li>
                                        <Link to="/addCustomRestaurant" onClick={closeNavbar}>
                                            Custom order
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to="/allOrders" onClick={closeNavbar}>
                                            Orders
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to="/profile" onClick={closeNavbar}>
                                            Profile
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to="/logout" onClick={closeNavbar}>
                                            Log out
                                        </Link>
                                    </li>
                                </>
                            ) : (
                                <>
                                    <li>
                                        <Link to="/login" onClick={closeNavbar}>
                                            Login
                                        </Link>
                                    </li>
                                </>
                            )}
                        </>
                    )}
                </ul>
            </nav>
        </header>
    );
}

export default Header;
