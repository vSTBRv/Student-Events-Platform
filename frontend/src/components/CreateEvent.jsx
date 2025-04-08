import { useState } from "react";

export default function CreateEvent() {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [location, setLocation] = useState("");
    const [date, setDate] = useState("");
    const [time, setTime] = useState("");
    const [seats, setSeats] = useState("");
    const [category, setCategory] = useState("Inne"); // domyślna kategoria

    const [error, setError] = useState("");
    const [success, setSuccess] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();

        // Prosta walidacja
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

        // Dane do wysłania na backend
        const eventData = {
            title,
            description,
            location,
            date,
            time,
            seats,
            category,
        };

        console.log("Dane wydarzenia do wysłania na backend:", eventData);

        // 🛠️ BACKEND: Tutaj chłopaki mogą podpiąć fetch/axios
    };

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center px-4 py-12">
            <div className="bg-white p-8 rounded-xl shadow-md w-full max-w-xl">
                <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">
                    Dodaj nowe wydarzenie
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
                        Dodaj wydarzenie
                    </button>
                </form>

                {error && <p className="text-red-600 mt-4 text-center">{error}</p>}
                {success && (
                    <p className="text-green-600 mt-4 text-center">
                        Wydarzenie zostało dodane (symulacja) – sprawdź konsolę!
                    </p>
                )}
            </div>
        </div>
    );
}
