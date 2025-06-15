import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { FaUndo } from "react-icons/fa";

export default function DeletedEvents() {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
        const fetchDeletedEvents = async () => {
            setLoading(true);
            try {
                const response = await axios.get("http://localhost:8080/api/events/deleted", {
                    withCredentials: true,
                });

                const mapped = Array.isArray(response.data)
                    ? response.data.map((event) => ({
                        id: event.id,
                        title: event.name,
                        description: event.comments,
                        date: event.startDateTime.split("T")[0],
                        time: event.startDateTime.split("T")[1].substring(0, 5),
                        location: `${event.locationCity}, ${event.locationStreet} ${event.locationHouseNumber}`,
                        seats: event.capacity,
                    }))
                    : [];

                setEvents(mapped);
                setError(null);
            } catch (error) {
                console.error("Błąd podczas pobierania usuniętych wydarzeń:", error.message);
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchDeletedEvents();
    }, []);

    const handleRestore = async (id) => {
        const confirmRestore = window.confirm("Czy na pewno chcesz przywrócić to wydarzenie?");
        if (!confirmRestore) return;

        try {
            await axios.patch(`http://localhost:8080/api/events/deleted/${id}`, null, {
                withCredentials: true,
            });
            setEvents((prev) => prev.filter((e) => e.id !== id));
            alert("Wydarzenie zostało przywrócone.");
        } catch (error) {
            alert("Błąd podczas przywracania wydarzenia.");
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 py-10 px-4 flex flex-col items-center">
            <div className={"w-full max-w-6xl flex justify-end mb-6"}>
                <input
                    type={"text"}
                    placeholder={"Szukaj po nazwie..."}
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className={"mb-6 px-4 py-2 border border-gray-300 rounded-md w-full max-w-md"}
                />
            </div>

            <h1 className="text-3xl font-bold text-amber-600 mb-8">
                Usunięte wydarzenia
            </h1>

            {loading ? (
                <p>Ładowanie...</p>
            ) : error ? (
                <p className="text-red-500">Błąd: {error}</p>
            ) : events.length === 0 ? (
                <p className="text-gray-600">Brak usuniętych wydarzeń.</p>
            ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl w-full">
                    {events
                        .filter((event) =>
                            event.title.toLowerCase().includes(searchTerm.toLowerCase())
                        )
                        .map((event) => (
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
                                </div>

                                <div className="mt-4">
                                    <button
                                        onClick={() => handleRestore(event.id)}
                                        className="bg-green-600 hover:bg-green-700 text-white py-2 px-4 rounded-md transition w-full flex items-center justify-center gap-2"
                                    >
                                        <FaUndo /> Przywróć
                                    </button>
                                </div>
                            </div>
                        ))}
                </div>
            )}
        </div>
    );
}
