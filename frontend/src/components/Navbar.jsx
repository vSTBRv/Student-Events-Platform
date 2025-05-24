import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FaSignOutAlt, FaUsersCog, FaPlus, FaList } from "react-icons/fa";

export default function Navbar({ isLoggedIn, setIsLoggedIn }) {
    const [isAdmin, setIsAdmin] = useState(false);
    const [userRole, setUserRole] = useState(null);
    const navigate = useNavigate();

    // useEffect(() => {
    //     const checkRole = async () => {
    //         if (!isLoggedIn) return;
    //
    //         try {
    //             const response = await fetch("http://localhost:8080/api/users", {
    //                 method: "GET",
    //                 credentials: "include",
    //             });
    //
    //             if (response.ok) {
    //                 const users = await response.json();
    //                 const email = localStorage.getItem("userEmail");
    //                 const currentUser = users.find((u) => u.email === email);
    //                 if (currentUser) {
    //                     setUserRole(currentUser.userRole);
    //                     if (currentUser.userRole === "ADMIN") {
    //                         setIsAdmin(true);
    //                     }
    //                 }
    //             }
    //         } catch (error) {
    //             console.error("Błąd przy sprawdzaniu roli:", error);
    //         }
    //     };
    //     checkRole();
    // }, [isLoggedIn]);

    useEffect(() => {
        const checkRole = async () => {
            if (!isLoggedIn) return;

            try {
                const response = await fetch("http://localhost:8080/api/me", {
                    method: "GET",
                    credentials: "include",
                });

                if (response.ok) {
                    const currentUser = await response.json();
                    setUserRole(currentUser.userRole);
                    if (currentUser.userRole === "ADMIN") {
                        setIsAdmin(true);
                    }
                } else {
                    console.warn("Nie udało się pobrać /api/me:", response.status);
                }
            } catch (error) {
                console.error("Błąd przy sprawdzaniu roli:", error);
            }
        };

        checkRole();
    }, [isLoggedIn]);


    const handleLogout = async () => {
        try {
            await fetch("http://localhost:8080/api/logout", {
                method: "POST",
                credentials: "include",
            });
            localStorage.removeItem("isLoggedIn");
            localStorage.removeItem("userEmail");
            setIsLoggedIn(false);
            setIsAdmin(false);
            setUserRole(null);
            navigate("/");
        } catch (error) {
            console.error("Błąd przy wylogowywaniu:", error);
        }
    };

    const handleLogoClick = () => {
        navigate(isLoggedIn ? "/events" : "/");
    };

    return (
        <nav className="text-amber-600 px-4 py-3 shadow-md">
            <div className="container mx-auto flex justify-between items-center">
                <div
                    onClick={handleLogoClick}
                    className="text-lg font-bold cursor-pointer hover:text-gray-200"
                >
                    Eventify
                </div>

                <ul className="flex space-x-6">
                    {!isLoggedIn ? (
                        <>
                            <li>
                                <Link to="/" className="hover:text-gray-200">
                                    Login
                                </Link>
                            </li>
                        </>
                    ) : (
                        <>
                            <li>
                                <Link to="/events" className="hover:text-gray-200 flex items-center gap-1">
                                    <FaList /> Wydarzenia
                                </Link>
                            </li>
                            {["STUDENT", "ORGANIZATION"].includes(userRole) && (
                                <li>
                                    <Link to="/my-events" className="hover:text-gray-200">
                                        Moje wydarzenia
                                    </Link>
                                </li>
                            )}

                            {userRole !== "STUDENT" && (
                                <li>
                                    <Link to="/events/new" className="hover:text-gray-200 flex items-center gap-1">
                                        <FaPlus /> Utwórz wydarzenie
                                    </Link>
                                </li>

                            )}

                            {isAdmin && (
                                <>
                                    <li>
                                        <Link to="/admin/users" className="hover:text-gray-200 flex items-center gap-1">
                                            <FaUsersCog /> Użytkownicy
                                        </Link>
                                    </li>
                                    <li>
                                        <Link to="/admin/deleted-events" className="hover:text-gray-200 flex items-center gap-1">
                                            🗑️ Usunięte
                                        </Link>
                                    </li>
                                </>
                            )}


                            <li>
                                <button
                                    onClick={handleLogout}
                                    className="hover:text-gray-200 flex items-center gap-1"
                                >
                                    <FaSignOutAlt /> Wyloguj się
                                </button>
                            </li>
                        </>
                    )}
                </ul>
            </div>
        </nav>
    );
}
