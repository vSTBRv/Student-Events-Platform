import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; // Import for navigation

export default function EventList() {
  const [events, setEvents] = useState([]);
  const navigate = useNavigate();

  // Dummy event data
  useEffect(() => {
    const dummyEvents = [
      {
        id: 1,
        title: "Hackathon Uczelniany",
        description: "24h kodowania, pizza, nagrody!",
        date: "2025-05-10",
        time: "09:00",
        location: "Budynek A, sala 101",
        category: "Nauka",
        seats: 50,
      },
      {
        id: 2,
        title: "Wieczór planszówek",
        description: "Integracja i gry planszowe dla studentów.",
        date: "2025-05-12",
        time: "17:30",
        location: "Klub Studencki",
        category: "Rozrywka",
        seats: 30,
      },
      {
        id: 3,
        title: "Bieg po Kampusie",
        description: "Zawody biegowe dla każdego poziomu.",
        date: "2025-05-15",
        time: "12:00",
        location: "Stadion uczelniany",
        category: "Sport",
        seats: 100,
      },
    ];

    setEvents(dummyEvents);
  }, []);

  const handleEdit = (id) => {
    navigate(`/events/edit/${id}`);
  };

  return (
    <div className="min-h-screen bg-gray-100 py-10 px-4 flex flex-col items-center">
      <h1 className="text-3xl font-bold text-amber-600 mb-8">
        Nadchodzące wydarzenia
      </h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl w-full">
        {events.length === 0 ? (
          <p className="text-gray-600">Brak wydarzeń do wyświetlenia.</p>
        ) : (
          events.map((event) => (
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
                  Kategoria: {event.category}
                </div>
              </div>

              <button
                onClick={() => handleEdit(event.id)}
                className="mt-4 bg-amber-500 hover:bg-amber-600 text-white py-2 px-4 rounded-md transition"
              >
                Edytuj
              </button>
            </div>
          ))
        )}
      </div>
    </div>
  );
}
