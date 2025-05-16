import { useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";

function SendMessage() {
    const { id } = useParams();
    const [message, setMessage] = useState("");
    const [status, setStatus] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();

        axios.post(
            `http://localhost:8080/api/events/${id}/message`,
            { message },
            {
                withCredentials: true,
                headers: { "Content-Type": "application/json" },
            }
        )
            .then(() => setStatus("Wiadomość została wysłana!"))
            .catch(() => setStatus("Błąd podczas wysyłania wiadomości."));
    };

    return (
        <div className="max-w-xl mx-auto mt-10 p-6 bg-white rounded-xl shadow">
            <h2 className="text-2xl font-bold mb-4">
                Wyślij wiadomość do uczestników wydarzenia #{id}
            </h2>
            <form onSubmit={handleSubmit} className="space-y-4">
                <textarea
                    value={message}
                    onChange={(e) => setMessage(e.target.value)}
                    placeholder="Wpisz wiadomość..."
                    rows={5}
                    required
                    className="w-full border p-2 rounded"
                />
                <button
                    type="submit"
                    className="bg-amber-500 text-white px-4 py-2 rounded hover:bg-amber-600 transition"
                >
                    Wyślij
                </button>
            </form>
            {status && <p className="mt-4 text-center">{status}</p>}
        </div>
    );
}

export default SendMessage;
