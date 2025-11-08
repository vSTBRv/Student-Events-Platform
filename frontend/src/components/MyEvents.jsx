import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { format } from "date-fns";

export default function MyEvents() {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [userRole, setUserRole] = useState(null);
    const [userEmail, setUserEmail] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const res = await axios.get("http://localhost:8081/api/me", {
                    withCredentials: true,
                });
                setUserRole(res.data.userRole);
                setUserEmail(res.data.email);
            } catch (err) {
                console.error("Błąd pobierania użytkownika:", err);
            }
        };

        fetchUser();
    }, []);

    useEffect(() => {
        const fetchEvents = async () => {
            setLoading(true);
            try {
                let response;

                if (userRole === "STUDENT") {
                    response = await axios.get("http://localhost:8081/api/events/participated", {
                        withCredentials: true,
                    });
                } else if (userRole === "ORGANIZATION") {
                    response = await axios.get("http://localhost:8081/api/events", {
                        withCredentials: true,
                    });
                }

                const allEvents = response.data || [];

                const filteredEvents = userRole === "STUDENT"
                    ? allEvents
                    : allEvents.filter(e => e.createdBy?.email === userEmail);

                const eventsWithRatings = await Promise.all(
                    filteredEvents.map(async (event) => {
                        let alreadyRated = false;
                        if (userRole === "STUDENT") {
                            try {
                                const res = await axios.get(`http://localhost:8081/api/events/${event.id}/rating`, {
                                    withCredentials: true,
                                });
                                alreadyRated = res.data.userRating !== null;
                            } catch {
                                alreadyRated = false;
                            }
                        }

                        return {
                            id: event.id,
                            title: event.name,
                            description: event.description,
                            date: format(new Date(event.startDateTime), "yyyy-MM-dd"),
                            time: format(new Date(event.startDateTime), "HH:mm"),
                            location: `${event.locationDTO.city}, ${event.locationDTO.street} ${event.locationDTO.houseNumber}`,
                            seats: event.maxCapacity - event.currentCapacity,
                            alreadyRated,
                        };
                    })
                );

                setEvents(eventsWithRatings);
                setError(null);
            } catch (err) {
                setError("Błąd podczas pobierania wydarzeń.");
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        if (userRole && userEmail) {
            fetchEvents();
        }
    }, [userRole, userEmail]);

    return (
        <div className="min-h-screen bg-gray-100 py-10 px-4 flex flex-col items-center">
            <h1 className="text-3xl font-bold text-amber-600 mb-8">
                Moje wydarzenia
            </h1>

            {loading ? (
                <p>Ładowanie wydarzeń...</p>
            ) : error ? (
                <p className="text-red-500">{error}</p>
            ) : events.length === 0 ? (
                <p className="text-gray-600">Brak wydarzeń do wyświetlenia.</p>
            ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl w-full">
                    {events.map((event) => (
                        <div
                            key={event.id}
                            className="bg-white p-6 rounded-xl shadow hover:shadow-lg transition relative"
                        >
                            <div
                                onClick={() => navigate(`/events/${event.id}`)}
                                className="cursor-pointer"
                            >
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

                            {userRole === "ORGANIZATION" && (
                                <button
                                    onClick={() => navigate(`/events/${event.id}/ratings`)}
                                    className="mt-4 bg-blue-500 hover:bg-blue-600 text-white py-1 px-3 rounded text-sm"
                                >
                                    Zobacz oceny
                                </button>
                            )}

                            {userRole === "STUDENT" && !event.alreadyRated && (
                                <button
                                    onClick={() => navigate(`/events/${event.id}/rate`)}
                                    className="mt-4 bg-amber-500 hover:bg-amber-600 text-white py-1 px-3 rounded text-sm"
                                >
                                    Oceń wydarzenie
                                </button>
                            )}
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}
