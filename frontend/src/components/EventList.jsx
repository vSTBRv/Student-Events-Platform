import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { FaTrashAlt, FaTimes } from "react-icons/fa";
import { format } from "date-fns";

export default function EventList({ filters }) {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
        const fetchEvents = async () => {
            setLoading(true);
            try {
                const params = new URLSearchParams();
                Object.entries(filters || {}).forEach(([key, value]) => {
                    if (value) params.append(key, value);
                });

                const response = await axios.get(`http://localhost:8080/api/events/filter?${params.toString()}`, {
                    withCredentials: true,
                });

                const mapped = Array.isArray(response.data)
                    ? response.data.map((event) => ({
                        id: event.id,
                        title: event.name,
                        description: event.comments,
                        date: format(new Date(event.startDateTime), "yyyy-MM-dd"),
                        time: format(new Date(event.startDateTime), "HH:mm"),
                        location: `${event.locationDTO.city}, ${event.locationDTO.street} ${event.locationDTO.houseNumber}`,
                        seats: event.maxCapacity - event.currentCapacity,
                        participating: event.participating,
                    }))
                    : [];

                setEvents(mapped);
                setError(null);
            } catch (error) {
                console.error("Błąd podczas pobierania danych:", error.message);
                setError(error.message);
                setEvents([]);
            } finally {
                setLoading(false);
            }
        };

        fetchEvents();
    }, [filters]);

    const handleEdit = (id) => {
        navigate(`/events/edit/${id}`);
    };

    const handleSoftDelete = (id) => {
        if (window.confirm("Czy na pewno chcesz UKRYĆ wydarzenie (SOFT DELETE)?")) {
            axios.delete(`http://localhost:8080/api/events/${id}`, { withCredentials: true })
                .then(() => {
                    setEvents(prevEvents => prevEvents.filter(event => event.id !== id));
                    alert("Wydarzenie zostało ukryte.");
                })
                .catch(() => {
                    alert("Błąd podczas ukrywania wydarzenia.");
                });
        }
    };

    const handleHardDelete = (id) => {
        if (window.confirm("Czy na pewno chcesz TRWALE USUNĄĆ wydarzenie (HARD DELETE)?")) {
            axios.delete(`http://localhost:8080/api/events/delete/${id}`, { withCredentials: true })
                .then(() => {
                    setEvents(prevEvents => prevEvents.filter(event => event.id !== id));
                    alert("Wydarzenie zostało TRWALE usunięte.");
                })
                .catch(() => {
                    alert("Błąd podczas trwałego usuwania wydarzenia.");
                });
        }
    };

    const handleJoin = (id) => {
        axios.post(`http://localhost:8080/api/events/${id}/register`, {}, { withCredentials: true })
            .then(() => {
                alert("Pomyślnie dołączono do wydarzenia.");
                setEvents(prevEvents => prevEvents.map(e => e.id === id ? { ...e, participating: true } : e));
            })
            .catch((error) => {
                alert("Nie można dołączyć: ");
            });
    };

    const handleUnregister = (id) => {
        axios.delete(`http://localhost:8080/api/events/${id}/unregister`, { withCredentials: true })
            .then(() => {
                alert("Pomyślnie zrezygnowano z wydarzenia.");
                setEvents(prevEvents => prevEvents.map(e => e.id === id ? { ...e, participating: false } : e));
            })
            .catch((error) => {
                alert("Nie można zrezygnować: ");
            });
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
                Wydarzenia
            </h1>

            {loading ? (
                <p>Ładowanie wydarzeń...</p>
            ) : error ? (
                <p className="text-red-500">Błąd: {error}</p>
            ) : events.length === 0 ? (
                <p className="text-gray-600">Brak wydarzeń do wyświetlenia.</p>
            ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl w-full">
                    {events
                        .filter(event => event.title.toLowerCase().includes(searchTerm.toLowerCase()))
                        .map((event) => (
                            <div
                                key={event.id}
                                onClick={() => navigate(`/events/${event.id}`)}
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

                                <div className="flex flex-col gap-2 mt-4">
                                    <button
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            event.participating ? handleUnregister(event.id) : handleJoin(event.id);
                                        }}
                                        className={`py-2 px-4 rounded-md transition mt-2 text-white ${event.participating ? "bg-red-500 hover:bg-red-600" : "bg-green-500 hover:bg-green-600"}`}
                                    >
                                        {event.participating ? "Zrezygnuj" : "Dołącz"}
                                    </button>
                                    <button
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            handleEdit(event.id);
                                        }}
                                        className="bg-amber-500 hover:bg-amber-600 text-white py-2 px-4 rounded-md transition"
                                    >
                                        Edytuj
                                    </button>

                                    <div className="flex justify-between items-center mt-2 text-sm text-gray-600">
                                        <button
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                handleSoftDelete(event.id);
                                            }}
                                            className="flex items-center gap-1 hover:text-red-500 transition"
                                        >
                                            <FaTrashAlt /> Usuń
                                        </button>

                                        <button
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                handleHardDelete(event.id);
                                            }}
                                            className="flex items-center gap-1 hover:text-red-700 transition"
                                        >
                                            <FaTimes /> Usuń na zawsze
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))}
                </div>
            )}
        </div>
    );
}
