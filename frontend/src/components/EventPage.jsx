import EventFilters from "./EventFilters.jsx";
import EventList from "./EventList.jsx";
import { useState } from "react";

function EventPage() {
    const [filters, setFilters] = useState({})

    return(
        <div className={"flex flex-col md:flex-row min-h-screen"}>
            <aside className={"w-full md:w-64 bg-white shadow-md p-4 border-r"}>
                <h2 className={"text-xl font-semibold md-4"}>Filtry wydarzeń</h2>
                <EventFilters onFilterChange={setFilters} />
            </aside>

            <main className={"flex-1 p-4"}>
                <h1 className={"text-3xl font-bold mb-8 text-amber-600"}>Wydarzenia</h1>
                <EventList filters={filters} />
            </main>
        </div>
    );
}

export default EventPage;