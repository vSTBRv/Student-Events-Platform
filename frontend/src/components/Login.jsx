import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Login({ setIsLoggedIn }) {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new URLSearchParams();
    formData.append("username", login);
    formData.append("password", password);

    // const credentials = btoa(`${login}:${password}`); // Base64 encoding // Nie używamy Base64

    try {
      // const response = await axios.post(
      //     "http://localhost:8080/api/login",
      //     null, // No body required for Basic Auth
      //     {
      //       headers: {
      //         // "Authorization": `Basic ${credentials}`, // Basic Auth Header
      //       },
      //       withCredentials: true // potrzeba aby działały ciasteczka - jak nie pasuje - wywalić!!!
      //     }
      // );

      const response = await axios.post("http://localhost:8080/api/login", formData, {
        withCredentials: true,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });

      if (response.status === 200) {
        console.log("Zalogowano!", response);
        // Store credentials in localStorage for use in EventList
        // localStorage.setItem("authCredentials", credentials);

        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("userEmail", login); // ZAPISZ EMAIL!
        setIsLoggedIn(true);
        setSuccess(true);
        setError("");
        navigate("/events");

      } else {
        setError("Błąd serwera");
        setSuccess(false);
      }
    } catch (err) {
      console.error(err);
      if (err.response && err.response.status === 401) {
        setError("Nieprawidłowy login lub hasło");
      } else {
        setError("Błąd połączenia z serwerem");
      }
      setSuccess(false);
    }
  };

  return (
      <div className="bg-gray-50 min-h-screen flex items-center justify-center px-4">
        <div className="bg-white p-8 rounded-xl shadow-md w-full max-w-sm">
          <h2 className="text-3xl font-semibold mb-6 text-center text-gray-800">
            Logowanie
          </h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <input
                type="text"
                placeholder="Login"
                value={login}
                onChange={(e) => setLogin(e.target.value)}
                required
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-amber-500"
            />
            <input
                type="password"
                placeholder="Hasło"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-amber-500"
            />
            <button
                type="submit"
                className="w-full bg-amber-500 text-white py-2 rounded-lg hover:bg-amber-600 transition"
            >
              Zaloguj
            </button>
          </form>
          <p className="mt-4 text-sm">
            Nie masz konta?{" "}
            <a href="/register" className="text-amber-600 hover:underline">
              Zarejestruj się
            </a>
          </p>

          {error && <p className="text-red-600 mt-4 text-center">{error}</p>}
          {success && (
              <p className="text-green-600 mt-4 text-center">
                Zalogowano pomyślnie!
              </p>
          )}
        </div>
      </div>
  );
}

export default Login;
