import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

export default function EventRatings() {
    const { id } = useParams(); // eventId z URL
    const navigate = useNavigate();
    const [comments, setComments] = useState([]);
    const [averageRating, setAverageRating] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchRatings = async () => {
            try {
                const commentsRes = await axios.get(`http://localhost:8081/api/events/${id}/comments`, {
                    withCredentials: true,
                });

                const ratingRes = await axios.get(`http://localhost:8081/ap/events/${id}/rating`, {
                    withCredentials: true,
                });

                setComments(commentsRes.data);
                setAverageRating(ratingRes.data);
            } catch (err) {
                console.error("Błąd pobierania ocen:", err);
            } finally {
                setLoading(false);
            }
        };

        fetchRatings();
    }, [id]);

    return (
        <div className="min-h-screen bg-gray-100 py-10 px-4">
            <div className="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow">
                <h1 className="text-2xl font-bold mb-4 text-amber-600">Oceny i komentarze</h1>

                {loading ? (
                    <p>Ładowanie danych...</p>
                ) : (
                    <>
                        <div className="mb-6">
                            <h2 className="text-lg font-semibold">Średnia ocena:</h2>
                            <p className="text-2xl text-amber-500 font-bold">
                                {averageRating?.toFixed(2) || "Brak ocen"}
                            </p>
                        </div>

                        <h2 className="text-lg font-semibold mb-2">Komentarze uczestników:</h2>
                        {comments.length === 0 ? (
                            <p className="text-gray-500">Brak komentarzy.</p>
                        ) : (
                            <ul className="space-y-4">
                                {comments.map((comment) => (
                                    <li key={comment.id} className="border p-4 rounded">
                                        <p className="font-semibold text-gray-800">{comment.authorName}</p>
                                        <p className="text-gray-600">{comment.content}</p>
                                        <p className="text-sm text-gray-400">
                                            {new Date(comment.createdAt).toLocaleString()}
                                        </p>
                                    </li>
                                ))}
                            </ul>
                        )}
                    </>
                )}

                <div className="mt-6">
                    <button
                        onClick={() => navigate("/my-events")}
                        className="text-amber-600 underline"
                    >
                        ⬅ Wróć do moich wydarzeń
                    </button>
                </div>
            </div>
        </div>
    );
}
