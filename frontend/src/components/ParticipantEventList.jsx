function ParticipantEventList({ eventList }) {
    if (!eventList.length) {
        return <p className="text-gray-500">Brak wydarzeń w danym okresie</p>;
    }

    return (
        <div className="space-y-4 mt-6">
            {eventList.map((event) => (
                <div key={event.eventId} className="p-4 border rounded-xl shadow flex justify-between">
                    <div>
                        <p className="font-semibold">{event.eventName}</p>
                        <p className="text-sm text-gray-500">{event.eventDate}</p>
                    </div>
                    <div className="text-right">
                        <span className="text-lg font-bold">{event.participantCount}</span>
                    </div>
                </div>
            ))}
        </div>
    );
}

export default ParticipantEventList;
