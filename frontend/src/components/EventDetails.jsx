import { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function EventDetails() {
    const { id } = useParams();
    const [event, setEvent] = useState(null);
    const [currentUser, setCurrentUser] = useState(null);
    const [error, setError] = useState("");
    const [participants, setParticipants] = useState([]);
    const [showParticipants, setShowParticipants] = useState(false);
    const [participantsError, setParticipantsError] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const fetchEvent = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/events/${id}`, {
                    withCredentials: true,
                });
                setEvent(response.data);
                console.log("Event z backendu: ", response.data);
            } catch (err) {
                console.error("Błąd pobierania wydarzenia:", err);
                setError("Nie udało się pobrać szczegółów wydarzenia");
            }
        };

        fetchEvent();
    }, [id]);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/me`, { withCredentials: true })
            .then(response => {
                console.log("👤 Zalogowany użytkownik:", response.data);
                setCurrentUser(response.data)
            })
        .catch(error => {
            console.log("Błąd pobierania danych zalogowanego użytkownika", error);
            setCurrentUser(null);
        });
    }, [])

    const fetchParticipants = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/events/${id}/participants`, {
                withCredentials: true,
            });
            setParticipants(response.data);
            setShowParticipants(true);
            setParticipantsError("");
        } catch (err) {
            console.error("Błąd pobierania uczestników:", err);
            setParticipantsError("Nie udało się pobrać uczestników.");
        }
    };

    if (error) {
        return <p className="text-red-600 mt-4 text-center">{error}</p>;
    }
    if (!event) {
        return <p className="text-gray-600 mt-4 text-center">Ładowanie...</p>;
    }

    return (
        <div className="event-container">
            <h1 className="event-title event-heading">{event.name}</h1>
            <p className="event-dates">
                Od {new Date(event.startDateTime).toLocaleString()} do{" "}
                {new Date(event.endDateTime).toLocaleString()}
            </p>
            <p className="event-description-text">{event.description}</p> {/* poprawione */}

            <div className="event-description">
                <div className="event-info">
                    <ul>
                        <li>
              <span className="event-paragraph">
                <strong>Miasto: </strong>
                  {event.locationDTO?.city || "Brak danych"}
              </span>
                            <br />
                        </li>
                        <li>
              <span className="event-paragraph">
                <strong>Ulica: </strong>
                  {event.locationDTO?.street} {event.locationDTO?.houseNumber}
              </span>
                        </li>
                        <li>
              <span className="event-paragraph">
                <strong>Kod pocztowy: </strong>
                  {event.locationDTO?.postalCode}
              </span>
                        </li>
                        <li>
              <span className="event-paragraph">
                <strong>Limit miejsc: </strong>
                  {event.maxCapacity}
              </span>
                        </li>
                    </ul>
                </div>
            </div>

            <div className="mt-6">
                <div className="flex flex-wrap gap-4">
                    <button
                        onClick={fetchParticipants}
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
                    >
                        Wyświetl uczestników
                    </button>

                    {currentUser &&
                        currentUser.userRole === "ORGANIZATION" &&
                        currentUser.id === event.createdBy && (
                            <button
                                onClick={()=> navigate(`/events/${event.id}/message`)}
                                className={"bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"}>
                                Wyślij wiadomość do uczestników
                            </button>
                        )}

                </div>

                {participantsError && (
                    <p className="text-red-500 mt-2">{participantsError}</p>
                )}

                {showParticipants && (
                    <div className="mt-4">
                        <h2 className="text-xl font-bold mb-2">Uczestnicy:</h2>
                        {participants.length > 0 ? (
                            <ul className="list-disc list-inside">
                                {participants.map((user, index) => (
                                    <li key={index}>{user.fullName}</li>
                                ))}
                            </ul>
                        ) : (
                            <p>Brak uczestników.</p>
                        )}
                    </div>
                )}
            </div>

            <div className="flex justify-end mt-6">
                <Link to={"/events"} className="event-back-button">
                    Wróć do listy wydarzeń
                </Link>
            </div>
        </div>
    );
}

export default EventDetails;
