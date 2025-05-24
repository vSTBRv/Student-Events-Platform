// src/components/RateEventForm.jsx
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

export default function RateEventForm() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [rating, setRating] = useState(5);
    const [comment, setComment] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState(false);
    const [alreadyRated, setAlreadyRated] = useState(false);
    const [userRating, setUserRating] = useState(null);

    useEffect(() => {
        const checkExistingRating = async () => {
            try {
                const res = await axios.get(`http://localhost:8080/api/events/${id}/rating`, {
                    withCredentials: true,
                });

                if (res.data.userRating !== null) {
                    setAlreadyRated(true);
                    setUserRating(res.data.userRating);
                }
            } catch (err) {
                console.error("Błąd sprawdzania oceny:", err);
            }
        };

        checkExistingRating();
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (rating < 1 || rating > 5 || comment.trim() === "") {
            setError("Podaj ocenę od 1 do 5 i komentarz.");
            return;
        }

        try {
            await axios.post(`http://localhost:8080/api/events/${id}/rating`, {
                value: rating,
            }, { withCredentials: true });

            await axios.post(`http://localhost:8080/api/events/${id}/comments`, {
                content: comment,
            }, { withCredentials: true });

            setSuccess(true);
            setTimeout(() => navigate("/my-events"), 1500);
        } catch (err) {
            console.error("Błąd wysyłania danych:", err);
            setError("Nie udało się wysłać danych.");
        }
    };

    if (alreadyRated) {
        return (
            <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
                <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-xl text-center">
                    <h2 className="text-2xl font-semibold text-gray-800 mb-4">Już oceniłeś to wydarzenie</h2>
                    <p className="text-gray-700 mb-6">Dziękujemy za Twoją opinię! Twoja ocena: <strong>{userRating}/5</strong></p>
                    <button
                        onClick={() => navigate("/my-events")}
                        className="text-amber-600 underline"
                    >
                        ⬅ Wróć do moich wydarzeń
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
            <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-xl">
                <h2 className="text-2xl font-semibold text-gray-800 mb-6 text-center">
                    Oceń wydarzenie
                </h2>

                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label className="block mb-2 font-medium">Ocena (1–5):</label>
                        <input
                            type="number"
                            value={rating}
                            min={1}
                            max={5}
                            onChange={(e) => setRating(Number(e.target.value))}
                            className="w-full px-4 py-2 border rounded-lg"
                        />
                    </div>

                    <div>
                        <label className="block mb-2 font-medium">Komentarz:</label>
                        <textarea
                            value={comment}
                            onChange={(e) => setComment(e.target.value)}
                            rows={4}
                            className="w-full px-4 py-2 border rounded-lg resize-none"
                            placeholder="Podziel się opinią o wydarzeniu..."
                        ></textarea>
                    </div>

                    {error && <p className="text-red-600">{error}</p>}
                    {success && <p className="text-green-600">Dziękujemy za opinię!</p>}

                    <div className="flex justify-between mt-4">
                        <button
                            type="submit"
                            className="bg-amber-600 hover:bg-amber-700 text-white px-4 py-2 rounded-lg"
                        >
                            Wyślij ocenę
                        </button>
                        <button
                            type="button"
                            onClick={() => navigate("/my-events")}
                            className="text-gray-600 underline"
                        >
                            Anuluj
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
