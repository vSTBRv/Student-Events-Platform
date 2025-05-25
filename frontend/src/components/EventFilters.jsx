import { useEffect, useState } from "react";
import axios from "axios";
import { FaRedo } from "react-icons/fa";

export default function EventFilters({ onFilterChange }) {
    const [filters, setFilters] = useState({
        category: null,
        startDateFrom: null,
        startDateTo: null,
        status: null,
    });

    const [categories, setCategories] = useState([]);

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/categories", {
                    withCredentials: true,
                });
                setCategories(response.data);
            } catch (error) {
                console.error("Błąd pobierania kategorii:", error);
            }
        };
        fetchCategories();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        const newFilters = { ...filters, [name]: value || null };
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
        <div className="bg-white p-6 rounded-xl shadow-md w-full max-w-sm mx-auto">
            <h2 className="text-xl font-semibold text-amber-600 mb-4 text-center">Filtruj wydarzenia</h2>

            <div className="flex flex-col gap-4">
                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Kategoria</label>
                    <select
                        name="category"
                        value={filters.category ?? ""}
                        onChange={handleChange}
                        className="w-full px-3 py-2 border rounded-md focus:ring-amber-500 focus:border-amber-500"
                    >
                        <option value="">Wszystkie</option>
                        {categories.map((cat) => (
                            <option key={cat.id} value={cat.name}>
                                {cat.name}
                            </option>
                        ))}
                    </select>
                </div>

                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
                    <select
                        name="status"
                        value={filters.status ?? ""}
                        onChange={handleChange}
                        className="w-full px-3 py-2 border rounded-md focus:ring-amber-500 focus:border-amber-500"
                    >
                        <option value="">Wszystkie</option>
                        <option value="PLANNED">Zaplanowane</option>
                        <option value="ONGOING">W trakcie</option>
                        <option value="COMPLETED">Zakończone</option>
                        <option value="CANCELLED">Odwołane</option>
                    </select>
                </div>

                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Data od</label>
                    <input
                        type="date"
                        name="startDateFrom"
                        value={filters.startDateFrom ?? ""}
                        onChange={handleChange}
                        className="w-full px-3 py-2 border rounded-md focus:ring-amber-500 focus:border-amber-500"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Data do</label>
                    <input
                        type="date"
                        name="startDateTo"
                        value={filters.startDateTo ?? ""}
                        onChange={handleChange}
                        className="w-full px-3 py-2 border rounded-md focus:ring-amber-500 focus:border-amber-500"
                    />
                </div>
            </div>

            <div className="mt-6 text-center">
                <button
                    type="button"
                    onClick={resetFilters}
                    className="inline-flex items-center gap-2 text-sm text-red-600 hover:underline"
                >
                    <FaRedo /> Wyczyść filtry
                </button>
            </div>
        </div>
    );
}
