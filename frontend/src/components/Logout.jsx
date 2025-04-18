import { useNavigate } from "react-router-dom";

function Logout({ onLogout }) {
    const navigate = useNavigate();

    const handleLogout = async () => {
        await fetch("http://localhost:8080/api/logout", {
            method: "POST",
            credentials: "include",
        });

        localStorage.removeItem("user");
        localStorage.removeItem("authCredentials");
        localStorage.removeItem("isLoggedIn");
        onLogout?.();
        navigate("/login");
    };

    return (
        // <button onClick={handleLogout} className="hover:text-gray-200 focus:outline-none">
        //     Logout
        // </button>
        //

        <span onClick={handleLogout} className="navbar-link" >Logout</span>

    );
}

export default Logout;
