import { useState } from "react";
function EventFilters({ onFilterChange }) {
    const [filters, setFilters] = useState({
        category: null,
        startDateFrom: null,
        startDateTo: null,
        status: null,
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        const newFilters = { ...filters, [name]: value };
        setFilters(newFilters);
        onFilterChange(newFilters);
    };

    const resetFilters = () => {
        const reset = {
            category: null,
            startDateFrom: null,
            startDateTo: null,
            status: null,
        };
        setFilters(reset);
        onFilterChange(reset);
    };

    return (
        <div className={"space-y-6"}>
            <div>
                <label className={"block text-sm font-medium text-gray-700"}>Kategoria</label>
                <select
                    name={"category"}
                    value={filters.category ?? ""}
                    onChange={handleChange}
                    className={"mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-amber-500 focus:border-amber-500"}
                    >
                    <option value={""}>Wszystkie</option>
                    <option value={"test1"}>Test1</option>
                    <option value={"test2"}>Test2</option>
                    <option value={"test3"}>Test3</option>
                </select>
            </div>

            <div>
                <label className={"block text-sm font-medium text-gray-700"}>Data od</label>
                <input
                    name={"startDateFrom"}
                    type={"date"}
                    value={filters.startDateFrom ?? "" }
                    onChange={handleChange}
                    className={"mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-amber500 focus:border-amber500"}
                    />
            </div>

            <div>
                <label className={"block text-sm font-medium text-gray-700"}>Data do</label>
                <input
                    name="startDateTo"
                    type="date"
                    value={filters.startDateTo ?? ""}
                    onChange={handleChange}
                    className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-amber-500 focus:border-amber-500"
                />
            </div>

            <div>
                <label className={"block text-sm font-medium text-gray-700"}>Status</label>
                <select
                    name={"status"}
                    value={filters.status ?? ""}
                    onChange={handleChange}
                    className={"mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-amber-500 focus:border-amber500"}
                    >
                    <option value={""}>Wszystkie</option>
                    <option value={"PLANNED"}>Zaplanowane</option>
                    <option value={"ONGOING"}>W trakcie</option>
                    <option value={"COMPLETED"}>Zakończone</option>
                    <option value={"CANCELLED"}>Odwołane</option>
                </select>
            </div>

            <button
                type={"button"}
                onClick={resetFilters}
                className={"w-full text-sm text-red-600 hover:underline"} >Wyczyść filtry</button>

        </div>
    );
}

export default EventFilters;