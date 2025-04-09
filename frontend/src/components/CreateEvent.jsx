import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

export default function CreateEvent() {
  const { id } = useParams();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [location, setLocation] = useState("");
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");
  const [seats, setSeats] = useState("");
  const [category, setCategory] = useState("Inne");

  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  //  czyści jesli nie ma id
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

    if (id) {
      const eventToEdit = dummyEvents.find((e) => e.id === parseInt(id));
      if (eventToEdit) {
        setTitle(eventToEdit.title);
        setDescription(eventToEdit.description);
        setLocation(eventToEdit.location);
        setDate(eventToEdit.date);
        setTime(eventToEdit.time);
        setSeats(eventToEdit.seats);
        setCategory(eventToEdit.category);
      }
    } else {
      // Tryb dodawania – wyczyść formularz
      setTitle("");
      setDescription("");
      setLocation("");
      setDate("");
      setTime("");
      setSeats("");
      setCategory("Inne");
    }
  }, [id]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!title || !description || !location || !date || !time || !seats) {
      setError("Uzupełnij wszystkie pola.");
      return;
    }

    if (seats < 1) {
      setError("Liczba miejsc musi być większa od zera.");
      return;
    }

    setError("");
    setSuccess(true);

    const updatedEvent = {
      id,
      title,
      description,
      location,
      date,
      time,
      seats,
      category,
    };

    console.log("Zaktualizowane dane wydarzenia:", updatedEvent);

    // 🛠️ Tutaj podpiąć PATCH/PUT do backendu
  };

  return (
    <div className="min-h-screen bg-gray-100 flex items-center justify-center px-4 py-12">
      <div className="bg-white p-8 rounded-xl shadow-md w-full max-w-xl">
        <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">
          Edytuj wydarzenie
        </h2>

        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            placeholder="Nazwa wydarzenia"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
          />

          <textarea
            placeholder="Opis wydarzenia"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows="4"
            className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500 resize-none"
          />

          <input
            type="text"
            placeholder="Miejsce"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
            className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
          />

          <div className="flex gap-4">
            <input
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              className="w-1/2 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
            />
            <input
              type="time"
              value={time}
              onChange={(e) => setTime(e.target.value)}
              className="w-1/2 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
            />
          </div>

          <input
            type="number"
            placeholder="Liczba miejsc"
            value={seats}
            onChange={(e) => setSeats(e.target.value)}
            className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
          />

          <select
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
          >
            <option>Inne</option>
            <option>Kultura</option>
            <option>Sport</option>
            <option>Nauka</option>
            <option>Rozrywka</option>
          </select>

          <button
            type="submit"
            className="w-full bg-amber-500 text-white py-2 rounded-lg hover:bg-amber-600 transition"
          >
            Zapisz zmiany
          </button>
        </form>

        {error && <p className="text-red-600 mt-4 text-center">{error}</p>}
        {success && (
          <p className="text-green-600 mt-4 text-center">
            Zmiany zostały zapisane (symulacja) – sprawdź konsolę!
          </p>
        )}
      </div>
    </div>
  );
}
