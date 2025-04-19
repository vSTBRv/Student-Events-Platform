import { useState, useEffect } from "react";
import {data, useParams} from "react-router-dom";
import { useNavigate } from "react-router-dom";  // potrzebne do przycisku Anuluj

export default function CreateEvent() {
  const { id } = useParams();
  const isEdit = Boolean(id);

  // Pola formularza
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");
  const [seats, setSeats] = useState("");
  const [category, setCategory] = useState("Inne");

  // Rozbity adres
  const [city, setCity] = useState("");
  const [street, setStreet] = useState("");
  const [houseNumber, setHouseNumber] = useState("");
  const [postalCode, setPostalCode] = useState("");

  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  // inicjalizacja navigate w celu utworzenia przycisku ANULUJ
  const navigate = useNavigate();


  useEffect(() => {
    if (isEdit) {
      fetch(`http://localhost:8080/api/events/${id}`, {
        method: "GET",
        credentials: "include",
      })

          .then(res => res.json())
          .then(data => {
            setTitle(data.name);
            setDescription(data.comments);
            setDate(data.startDateTime.split("T")[0]);
            setTime(data.startDateTime.split("T")[1].slice(0, 5));
            setSeats(data.capacity);
            setCategory(data.category || "Inne");
            setCity(data.locationCity);
            setStreet(data.locationStreet);
            setHouseNumber(data.locationHouseNumber);
            setPostalCode(data.locationPostalCode);
          })
          .catch(() => setError("Nie udało się załadować wydarzenia."));
    }

      // const dummyEvents = [
      //   {
      //     id: 1,
      //     title: "Hackathon Uczelniany",
      //     description: "24h kodowania, pizza, nagrody! ",
      //     date: "2025-05-10",
      //     time: "09:00",
      //     city: "Poznań",
      //     street: "Polna",
      //     houseNumber: "12A",
      //     postalCode: "60-100",
      //     category: "Nauka",
      //     seats: 50,
      //   },
      // ];



      // const eventToEdit = dummyEvents.find((e) => e.id === parseInt(id));
      // if (eventToEdit) {
      //   setTitle(eventToEdit.title);
      //   setDescription(eventToEdit.description);
      //   setDate(eventToEdit.date);
      //   setTime(eventToEdit.time);
      //   setSeats(eventToEdit.seats);
      //   setCategory(eventToEdit.category);
      //   setCity(eventToEdit.city);
      //   setStreet(eventToEdit.street);
      //   setHouseNumber(eventToEdit.houseNumber);
      //   setPostalCode(eventToEdit.postalCode);
      // }
    // }
  }, [id]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!title || !description || !date || !time || !seats || !city || !street || !houseNumber || !postalCode) {
      setError("Uzupełnij wszystkie pola.");
      return;
    }

    if (seats < 1) {
      setError("Liczba miejsc musi być większa od zera.");
      return;
    }

    setError("");
    setSuccess(true);

    // const eventData = {
    //   title,
    //   description,
    //   date,
    //   time,
    //   seats,
    //   category,
    //   city,
    //   street,
    //   houseNumber,
    //   postalCode,
    // };

    const eventData = {
      name: title,
      comments: description,
      startDate: `${date}T${time}`,
      endDate: `${date}T${time}`,
      maxCapacity: parseInt(seats),
      status: "PLANNED",
      locationId: 1
    };


    console.log(isEdit ? "🛠️ PATCH dane do backendu:" : "🛠️ POST dane do backendu:", eventData);

    // 🛠️ Tutaj backend podłącza odpowiedni fetch (POST lub PATCH)

    const url = isEdit
      ? `http://localhost:8080/api/events/${id}`
      : "http://localhost:8080/api/events";

    fetch(url, {
      method: isEdit ? "PUT" : "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(eventData),
    })
      .then((res) => {
        if(!res.ok) throw new Error();
        return res.json();
      })
      .then((data) => {
        // obsługa sukcesu
        navigate("/events")
      })
      .catch((err) => {
        setError("Błąd połączenia z serwerem.");
      });
  };

  const handleCancel = () => {
    navigate("/events");
  }

  const handleDelete = () => {
    if (!id) return;

    const confirmDelete = window.confirm("Czy na pewno chcesz usunąć to wydarzenie?");
    if (!confirmDelete) return;

    console.log("🗑️ DELETE wydarzenie:", id);

    // 🛠️ Tutaj backend podłącza DELETE
    /*
    fetch(`http://localhost:8080/api/events/${id}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (!res.ok) throw new Error("Błąd usuwania");
        // np. przekierowanie na listę wydarzeń
      })
      .catch((err) => {
        setError("Nie udało się usunąć wydarzenia.");
      });
    */
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

            {/*<button*/}
            {/*    type="submit"*/}
            {/*    // className="primary-btn w-full bg-amber-500 text-white py-2 rounded-lg hover:bg-amber-600 transition"*/}
            {/*    className={"primary-btn w-full sm:w-1/2"}*/}
            {/*>*/}
            {/*  {isEdit ? "Zapisz zmiany" : "Dodaj wydarzenie"}*/}
            {/*</button>*/}
          </form>

          {/*{isEdit && (*/}
          {/*    <div className="mt-6">*/}
          {/*      <button*/}
          {/*          onClick={handleDelete}*/}
          {/*          className="w-full bg-red-500 text-white py-2 rounded-lg hover:bg-red-600 transition"*/}
          {/*      >*/}
          {/*        Usuń wydarzenie*/}
          {/*      </button>*/}
          {/*    </div>*/}
          {/*)}*/}

          {/*{error && <p className="text-red-600 mt-4 text-center">{error}</p>}*/}
          {/*{success && (*/}
          {/*    <p className="text-green-600 mt-4 text-center">*/}
          {/*      {isEdit ? "Wydarzenie zaktualizowane!" : "Wydarzenie zostało dodane!"} (symulacja)*/}
          {/*    </p>*/}
          {/*)}*/}

          {/*{isEdit && (*/}
          {/*    <div className={"mt-6"}>*/}
          {/*      <button*/}
          {/*          type={"button"}*/}
          {/*          onClick={handleCancel}*/}
          {/*          className={"w-full bg-red-500 text-white py-2 rounded-lg hover:bg-red-600 transition"}*/}
          {/*      >Anuluj zmiany*/}
          {/*      </button>*/}
          {/*    </div>*/}
          {/*)}*/}

          {isEdit && (
              <div className={"mt-6 flex flex-col gap-3"}>
                <div className={"flex flex-col sm:flex-row gap-3"}>
                  <button
                    type={"button"}
                    className={"primary-btn flex-1"}
                    >
                    {isEdit ? "Zapisz zmiany" : "Dodaj wydarzenie"}
                  </button>

                  <button
                      type={"button"}
                      onClick={handleCancel}
                      // className={"w-full border border-gray-400 bg-white text-gray-700 py-2 hover:bg-gray-100 transition"} >
                      className={"cancel-btn flex-1"} >
                    Anuluj zmiany
                  </button>
                </div>

                <button
                  onClick={handleDelete}
                  // className={"w-full bg-red-500 text-white py-2 rounded-lg hover:bg-red-600 transition font-semibold"}>
                  className={"danger-btn"} >
                  Usuń wydarzenie
                </button>
              </div>

          )}

          {error && (
              <p className={"text-red-600 mt-4 text-center"}>{error}</p>
          )}

          {success && (
              <p className={"text-green-600 mt-4 text-center"}>
                {isEdit?"Wydarzenie zaktualizowane!":"Wydarzenie zostało dodane!"} (symulacja)
              </p>
          )}
        </div>
      </div>
  );
}
