import React, { useEffect, useState } from "react";
import axios from "axios";

export default function CategoryManager() {
    const [categories, setCategories] = useState([]);
    const [newName, setNewName] = useState("");
    const [editId, setEditId] = useState(null);
    const [editName, setEditName] = useState("");
    const [error, setError] = useState(null);

    const fetchCategories = async () => {
        try {
            const { data } = await axios.get("http://localhost:8081/api/categories", { withCredentials: true });
            setCategories(data);
            setError(null);
        } catch (e) {
            setError(e.response?.data?.message || "Błąd pobierania kategorii");
        }
    };

    useEffect(() => {
        fetchCategories();
    }, []);

    const handleAdd = async () => {
        if (!newName.trim()) {
            setError("Nazwa kategorii nie może być pusta");
            return;
        }

        try {
            const res = await axios.post("http://localhost:8081/api/categories", { name: newName }, { withCredentials: true });
            if (res.status !== 201) throw new Error("Błąd dodawania kategorii");
            setNewName("");
            fetchCategories();
        } catch (e) {
            setError(e.response?.data?.message || e.message);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm("Na pewno chcesz usunąć tę kategorię?")) return;

        try {
            const res = await axios.delete(`http://localhost:8081/api/categories/${id}/soft-delete`, { withCredentials: true });
            if (res.status !== 204) throw new Error("Błąd usuwania kategorii");
            fetchCategories();
        } catch (e) {
            setError(e.response?.data?.message || e.message);
        }
    };

    const handleRestore = async (id) => {
        if (!window.confirm("Na pewno chcesz przywrócić tę kategorię?")) return;

        try {
            const res = await axios.patch(`http://localhost:8081/api/categories/soft-deleted/${id}`, {}, { withCredentials: true });
            if (res.status !== 204) throw new Error("Błąd przywracania kategorii");
            fetchCategories();
        } catch (e) {
            setError(e.response?.data?.message || e.message);
        }
    };

    const startEdit = (id, name) => {
        setEditId(id);
        setEditName(name);
        setError(null);
    };

    const cancelEdit = () => {
        setEditId(null);
        setEditName("");
    };

    const handleEditSave = async () => {
        if (!editName.trim()) {
            setError("Nazwa kategorii nie może być pusta");
            return;
        }

        try {
            const res = await axios.put(`http://localhost:8081/api/categories/${editId}`, { name: editName }, { withCredentials: true });
            if (res.status !== 204) throw new Error("Błąd aktualizacji kategorii");
            cancelEdit();
            fetchCategories();
        } catch (e) {
            setError(e.response?.data?.message || e.message);
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 py-10 px-4 flex flex-col items-center">
            <div className="w-full max-w-2xl bg-white rounded-xl shadow p-6">
                <h1 className="text-2xl font-bold text-amber-600 mb-4">Zarządzanie kategoriami</h1>

                {error && <div className="text-red-500 mb-4">{error}</div>}

                <ul className="divide-y divide-gray-200 mb-6">
                    {categories.map(({ id, name, deleted }) => (
                        <li key={id} className="py-3 flex justify-between items-center">
                            {editId === id ? (
                                <>
                                    <input
                                        type="text"
                                        value={editName}
                                        onChange={(e) => setEditName(e.target.value)}
                                        className="border p-2 rounded w-full max-w-sm"
                                    />
                                    <div className="space-x-2">
                                        <button onClick={handleEditSave} className="bg-green-500 text-white px-3 py-1 rounded">Zapisz</button>
                                        <button onClick={cancelEdit} className="text-gray-600 hover:underline">Anuluj</button>
                                    </div>
                                </>
                            ) : (
                                <>
                                    <span className={`font-medium ${deleted ? "line-through text-gray-400" : "text-gray-800"}`}>{name}</span>
                                    {!deleted ? (
                                        <div className="space-x-2">
                                            <button onClick={() => startEdit(id, name)} className="bg-blue-500 text-white px-3 py-1 rounded">Edytuj</button>
                                            <button onClick={() => handleDelete(id)} className="bg-red-500 text-white px-3 py-1 rounded">Usuń</button>
                                        </div>
                                    ) : (
                                        <button onClick={() => handleRestore(id)} className="bg-yellow-500 text-white px-3 py-1 rounded">Przywróć</button>
                                    )}
                                </>
                            )}
                        </li>
                    ))}
                </ul>

                <div className="flex gap-4">
                    <input
                        type="text"
                        placeholder="Nowa kategoria"
                        value={newName}
                        onChange={(e) => setNewName(e.target.value)}
                        className="border px-4 py-2 rounded w-full"
                    />
                    <button onClick={handleAdd} className="bg-amber-600 text-white px-4 py-2 rounded">
                        Dodaj
                    </button>
                </div>
            </div>
        </div>
    );
}
