import { useEffect, useState } from "react";
import {Link, useParams} from "react-router-dom";
import axios from "axios";

function EventDetails() {
    const { id } = useParams();
    const [event, setEvent] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchEvent = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/events/${id}`, {
                    withCredentials: true,
                });
                console.log(response.data); // DEBUG
                setEvent(response.data);
                console.log("Ustawiono event:", event);
            } catch (err) {
                console.error("Błąd pobierania wydarzenia:", err);
                setError("Nie udało się pobrać szczegółów wydarzenia");
            }
        };

        fetchEvent();
    }, [id]);

    if(error){
        return <p className={"text-red-600 mt-4 text-center"}>{error}</p>
    }
    if(!event){
        return <p className={"text-gray-600 mt-4 text-center"}>Ładowanie...</p>
    }

    return (
        // <div className={"max-w-3xl mx-auto p-6 bg-white shadow-md rounded-xl mt-10"}>
        //     <h1 className={"text-3xl font-bold mb-4"}>{event.name}</h1>
        //     <p><strong>Kategoria:</strong>{event.category}</p>
        //     <p><strong>Status:</strong>{event.statusLabel}</p>
        //     <p><strong>Miasto:</strong>{event.locationCity}</p>
        //     <p><strong>Ulica:</strong>{event.locationStreet} {event.locationHouseNumber}</p>
        //     <p><strong>Kod pocztowy:</strong>{event.locationPostalCode}</p>
        //     <p><strong>Data rozpoczęcia:</strong>{new Date(event.startDateTime).toLocaleString()}</p>
        //     <p><strong>Data zakończenia:</strong>{new Date(event.endDateTime).toLocaleString()}</p>
        //     <p><strong>Pojemność:</strong>{event.capacity}</p>
        // </div>

        <div className="event-container">
            <h1 className="event-title event-heading">{event.name}</h1>
            <p className="event-dates">Od {new Date(event.startDateTime).toLocaleString()} do {new Date(event.endDateTime).toLocaleString()}</p>
            <p className="event-description-text">
                {event.comments}
            </p>
            <div className="event-description">
                <div className="event-info">
                    <ul>
                        <li>
                            <span className="event-paragraph"><strong>Miasto: </strong>{event.locationCity}</span><br />
                        </li>
                        <li>
                            <span className="event-paragraph"><strong>Ulica: </strong>{event.locationStreet} {event.locationHouseNumber}</span>
                        </li>
                        <li>
                            <span className="event-paragraph"><strong>Kod pocztowy: </strong>{event.locationPostalCode}</span>
                        </li>
                        <li>
                            <span className="event-paragraph"><strong>Limit miejsc: </strong>{event.capacity}</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div className="flex justify-end">
                <Link to={"/events"} className={"event-back-button"}>Wróć do listy wydarzeń</Link>
            </div>
        </div>

    );
}

export default EventDetails;