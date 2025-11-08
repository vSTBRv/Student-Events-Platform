import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function RegisterStudent() {
    const navigate = useNavigate();

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState(false);

    const validateEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!firstName || !lastName || !email || !password || !confirmPassword) {
            setError("Uzupełnij wszystkie pola.");
            return;
        }

        if (!validateEmail(email)) {
            setError("Nieprawidłowy adres email.");
            return;
        }

        if (password.length < 6) {
            setError("Hasło musi mieć co najmniej 6 znaków.");
            return;
        }

        if (password !== confirmPassword) {
            setError("Hasła się nie zgadzają.");
            return;
        }

        try {
            const response = await fetch("http://localhost:8081/api/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    fullName: `${firstName} ${lastName}`,
                    email,
                    password,
                    userRole: 'STUDENT',
                    username: email
                }),
            });

            if (!response.ok) {
                const errorText = await response.text();
                setError(errorText || "Wystąpił błąd podczas rejestracji.");
                return;
            }

            console.log("Użytkownik zarejestrowany.");
            setSuccess(true);
            setError("");

            setTimeout(() => {
                navigate("/");
            }, 2000);


        } catch (err) {
            setError("Wystąpił błąd podczas rejestracji2.");
        }


    };


    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center px-4">
            <div className="bg-white p-8 rounded-xl shadow-md w-full max-w-md">
                <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">
                    Rejestracja Studenta
                </h2>

                <form onSubmit={handleSubmit} className="space-y-4">
                    <input
                        type="text"
                        placeholder="Imię"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
                    />

                    <input
                        type="text"
                        placeholder="Nazwisko"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
                    />

                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
                    />

                    <input
                        type="password"
                        placeholder="Hasło"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
                    />

                    <input
                        type="password"
                        placeholder="Powtórz hasło"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
                    />

                    <button
                        type="submit"
                        className="w-full bg-amber-500 text-white py-2 rounded-lg hover:bg-amber-600 transition"
                    >
                        Zarejestruj
                    </button>
                </form>

                {error && <p className="text-red-600 mt-4 text-center">{error}</p>}
                {success && (
                    <p className="text-green-600 mt-4 text-center">
                        Sukces! Za chwilę zostaniesz przekierowany do logowania...
                    </p>
                )}
            </div>
        </div>
    );
}
