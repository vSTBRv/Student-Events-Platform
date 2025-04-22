import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios"; // Importujemy axios

export default function EventList() {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Retrieve credentials from localStorage
        // const credentials = localStorage.getItem("authCredentials");
        //
        // if (!credentials) {
        //     setError("Brak danych logowania");
        //     setLoading(false);
        //     return;
        // }

        console.log("Cookies przy wysyłaniu zapytania:", document.cookie);
        axios
            .get("http://localhost:8080/api/events", {
                // headers: {
                //     "Authorization": `Basic ${credentials}`, // Pass credentials in the header
                // },
                withCredentials: true
            })
            .then((response) => {
                console.log("Odebrane dane z backendu:", response.data);
                console.log("Surowa odpowiedź:", response);
                const mapped = Array.isArray(response.data) ? response.data.map((event) => ({
                    id: event.id,
                    title: event.name,
                    description: event.comments,
                    date: event.startDateTime.split("T")[0],
                    time: event.startDateTime.split("T")[1].substring(0, 5),
                    location: `${event.locationCity}, ${event.locationStreet} ${event.locationHouseNumber}`,
                    // category: event.statusLabel,
                    category: event.category.name,
                    seats: event.capacity,
                })):[];

                setEvents(mapped);
                setLoading(false);
            })
            .catch((error) => {
                console.error("Błąd podczas pobierania danych:", error.message);
                setError(error.message);
                setLoading(false);
            });
    }, []);

    const handleEdit = (id) => {
        navigate(`/events/edit/${id}`);
    };

    return (
        <div className="min-h-screen bg-gray-100 py-10 px-4 flex flex-col items-center">
            <h1 className="text-3xl font-bold text-amber-600 mb-8">
                Nadchodzące wydarzenia
            </h1>

            {loading ? (
                <p>Ładowanie wydarzeń...</p>
            ) : error ? (
                <p className="text-red-500">Błąd: {error}</p>
            ) : events.length === 0 ? (
                <p className="text-gray-600">Brak wydarzeń do wyświetlenia.</p>
            ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl w-full">
                    {events.map((event) => (
                        <div
                            key={event.id}
                            className="bg-white p-6 rounded-xl shadow hover:shadow-lg transition flex flex-col justify-between"
                        >
                            <div>
                                <h2 className="text-xl font-semibold text-gray-800 mb-2">
                                    {event.title}
                                </h2>
                                <p className="text-gray-600 mb-2">{event.description}</p>
                                <div className="text-sm text-gray-500 mb-1">
                                    📅 {event.date} ⏰ {event.time}
                                </div>
                                <div className="text-sm text-gray-500 mb-1">
                                    📍 {event.location}
                                </div>
                                <div className="text-sm text-gray-500 mb-1">
                                    🎫 Miejsca: {event.seats}
                                </div>
                                <div className="text-sm text-amber-600 font-medium mt-2">
                                    Status: {event.category}
                                </div>
                            </div>

                            <button
                                onClick={() => handleEdit(event.id)}
                                className="mt-4 bg-amber-500 hover:bg-amber-600 text-white py-2 px-4 rounded-md transition"
                            >
                                Edytuj
                            </button>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}
