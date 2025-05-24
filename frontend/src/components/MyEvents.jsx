// src/components/MyEvents.jsx
import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { format } from "date-fns";

export default function MyEvents() {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/api/events/participated", {
            withCredentials: true,
        })
            .then((res) => {
                const mapped = res.data.map((event) => ({
                    id: event.id,
                    title: event.name,
                    description: event.comments,
                    date: format(new Date(event.startDateTime), "yyyy-MM-dd"),
                    time: format(new Date(event.startDateTime), "HH:mm"),
                    location: `${event.locationDTO.city}, ${event.locationDTO.street} ${event.locationDTO.houseNumber}`,
                    seats: event.maxCapacity - event.currentCapacity,
                }));
                setEvents(mapped);
                setLoading(false);
            })
            .catch((err) => {
                setError("Błąd podczas pobierania wydarzeń.");
                setLoading(false);
            });
    }, []);

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
                <p className="text-gray-600">Brak zapisanych wydarzeń.</p>
            ) : (
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl w-full">
                    {events.map((event) => (
                        <div
                            key={event.id}
                            onClick={() => navigate(`/events/${event.id}`)}
                            className="bg-white p-6 rounded-xl shadow hover:shadow-lg transition cursor-pointer"
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
                    ))}
                </div>
            )}
        </div>
    );
}
