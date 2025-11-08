import keycloak from "../keycloak";

function Login({ setIsLoggedIn }) {
    const handleLogin = async () => {
        try {
            const authenticated = await keycloak.init({
                onLoad: "login-required",
                checkLoginIframe: false,
                pkceMethod: "S256",
                redirectUri: "http://localhost:5173/events", // po zalogowaniu
            });

            if (authenticated) {
                console.log("Zalogowano przez Keycloak!");
                console.log("Token:", keycloak.token);

                localStorage.setItem("isLoggedIn", "true");
                localStorage.setItem("token", keycloak.token);
                localStorage.setItem("userEmail", keycloak.tokenParsed.email);
                setIsLoggedIn(true);
                window.location.href = "/events";
            }
        } catch (error) {
            console.error("Błąd logowania przez Keycloak:", error);
        }
    };

    return (
        <div className="bg-gray-50 min-h-screen flex items-center justify-center px-4">
            <div className="bg-white p-8 rounded-xl shadow-md w-full max-w-sm text-center">
                <h2 className="text-3xl font-semibold mb-6 text-gray-800">
                    Logowanie
                </h2>
                <button
                    onClick={handleLogin}
                    className="w-full bg-amber-500 text-white py-2 rounded-lg hover:bg-amber-600 transition"
                >
                    Zaloguj przez Keycloak
                </button>
            </div>
        </div>
    );
}

export default Login;
