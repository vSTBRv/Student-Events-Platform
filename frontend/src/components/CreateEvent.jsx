import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

export default function CreateEvent() {
  const { id } = useParams();
  const isEdit = Boolean(id);

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [startDateTime, setStartDateTime] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endDate, setEndDate] = useState("");
  const [endTime, setEndTime] = useState("");
  const [seats, setSeats] = useState("");

  const [city, setCity] = useState("");
  const [street, setStreet] = useState("");
  const [houseNumber, setHouseNumber] = useState("");
  const [postalCode, setPostalCode] = useState("");

  const [categories, setCategories] = useState([]);
  const [selectedCategoryId, setSelectedCategoryId] = useState("");

  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8080/api/categories/undeleted", { credentials: "include" })
        .then((res) => res.json())
        .then((data) => {
          setCategories(data);
          if (data.length > 0) setSelectedCategoryId(data[0].id);
        })
        .catch(() => setError("Nie udało się pobrać kategorii."));

    if (isEdit) {
      fetch(`http://localhost:8080/api/events/${id}`, { credentials: "include" })
          .then((res) => res.json())
          .then((data) => {
            setTitle(data.name);
            setDescription(data.description);
            setStartDateTime(data.startDateTime.split("T")[0]);
            setStartTime(data.startDateTime.split("T")[1].slice(0, 5));
            setSeats(data.maxCapacity);
            setCity(data.locationDTO.city);
            setStreet(data.locationDTO.street);
            setHouseNumber(data.locationDTO.houseNumber);
            setPostalCode(data.locationDTO.postalCode);
            setEndDate(data.endDateTime.split("T")[0]);
            setEndTime(data.endDateTime.split("T")[1].slice(0, 5));
            const category = categories.find(cat => cat.name === data.category);
            if (category) {
              setSelectedCategoryId(category.id);
            }
          })
          .catch(() => setError("Nie udało się załadować wydarzenia."));
    }

  }, [id]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!title || !description || !startDateTime || !startTime || !seats || !city || !street || !houseNumber || !postalCode || !selectedCategoryId || !endDate || !endTime) {
      setError("Uzupełnij wszystkie pola.");
      return;
    }

    if (seats < 1) {
      setError("Liczba miejsc musi być większa od zera.");
      return;
    }

    const eventData = {
      name: title,
      description: description,
      startDateTime: `${startDateTime}T${startTime}`,  // zmień nazwę pola na startDateTime
      endDateTime: `${endDate}T${endTime}`,            // zmień nazwę pola na endDateTime
      maxCapacity: parseInt(seats),
      status: "PLANNED",                                // tego nie ma w DTO, ale może backend to akceptuje
      location: {
        city,
        street,
        houseNumber,
        postalCode,
      },
      categoryId: parseInt(selectedCategoryId),
    };


    const url = isEdit
        ? `http://localhost:8080/api/events/${id}`
        : "http://localhost:8080/api/events";

    fetch(url, {
      method: isEdit ? "PUT" : "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(eventData),
    })
        .then((res) => {
          if (!res.ok) throw new Error();
          return res.json();
        })
        .then(() => {
          setSuccess(true);
          navigate("/events");
        })
        .catch(() => setError("Błąd połączenia z serwerem."));
  };

  const handleCancel = () => {
    navigate("/events");
  };

  return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center px-4 py-12">
        <div className="bg-white p-8 rounded-xl shadow-md w-full max-w-xl">
          <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">
            {isEdit ? "Edytuj wydarzenie" : "Dodaj nowe wydarzenie"}
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

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <input
                  type="text"
                  placeholder="Miasto"
                  value={city}
                  onChange={(e) => setCity(e.target.value)}
                  className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
              />
              <input
                  type="text"
                  placeholder="Kod pocztowy"
                  value={postalCode}
                  onChange={(e) => setPostalCode(e.target.value)}
                  className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
              />
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <input
                  type="text"
                  placeholder="Ulica"
                  value={street}
                  onChange={(e) => setStreet(e.target.value)}
                  className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
              />
              <input
                  type="text"
                  placeholder="Numer budynku"
                  value={houseNumber}
                  onChange={(e) => setHouseNumber(e.target.value)}
                  className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
              />
            </div>

            <div className="flex gap-4">
              początek wydarzenia
              <input
                  type="date"
                  value={startDateTime}
                  onChange={(e) => setStartDateTime(e.target.value)}
                  className="w-1/2 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
              />
              <input
                  type="time"
                  value={startTime}
                  onChange={(e) => setStartTime(e.target.value)}
                  className="w-1/2 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
              />
            </div>

            <div className="flex gap-4">
              Koniece wydarzenia
              <input
                  type="date"
                  value={endDate}
                  onChange={(e) => setEndDate(e.target.value)}
                  className="w-1/2 px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
              />
              <input
                  type="time"
                  value={endTime}
                  onChange={(e) => setEndTime(e.target.value)}
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
                value={selectedCategoryId}
                onChange={(e) => setSelectedCategoryId(e.target.value)}
                className="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-amber-500"
            >
              {categories.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.name}
                  </option>
              ))}
            </select>

            <div className="flex flex-col gap-3 mt-6">
              <div className="flex flex-col sm:flex-row gap-3">
                <button type="submit" className="primary-btn flex-1">
                  {isEdit ? "Zapisz zmiany" : "Dodaj wydarzenie"}
                </button>
                <button
                    type="button"
                    onClick={handleCancel}
                    className="cancel-btn flex-1"
                >
                  Anuluj zmiany
                </button>
              </div>
            </div>
          </form>

          {error && <p className="text-red-600 mt-4 text-center">{error}</p>}
          {success && (
              <p className="text-green-600 mt-4 text-center">
                {isEdit ? "Wydarzenie zaktualizowane!" : "Wydarzenie zostało dodane!"}
              </p>
          )}
        </div>
      </div>
  );
}
