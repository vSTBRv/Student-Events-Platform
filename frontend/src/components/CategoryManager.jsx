import React, { useEffect, useState } from "react";
import axios from "axios";

const CategoryManager = () => {
    const [categories, setCategories] = useState([]);
    const [newName, setNewName] = useState("");
    const [editId, setEditId] = useState(null);
    const [editName, setEditName] = useState("");
    const [error, setError] = useState(null);

    // Pobierz wszystkie kategorie
    const fetchCategories = async () => {
        try {
            const { data } = await axios.get("http://localhost:8080/api/categories",{
                withCredentials: true,
            });
            setCategories(data);
            setError(null);
        } catch (e) {
            setError(e.response?.data?.message || "Błąd pobierania kategorii");
        }
    };

    useEffect(() => {
        fetchCategories();
    }, []);

    // Dodaj nową kategorię
    const handleAdd = async () => {
        if (!newName.trim()) {
            setError("Nazwa kategorii nie może być pusta");
            return;
        }

        try {
            const res = await axios.post("http://localhost:8080/api/categories", { name: newName, },
                {withCredentials: true});
            if (res.status !== 201) {
                throw new Error("Błąd dodawania kategorii");
            }
            setNewName("");
            fetchCategories();
            setError(null);
        } catch (e) {
            setError(e.response?.data?.message || e.message);
        }
    };

    // Usuń kategorię (soft delete)
    const handleDelete = async (id) => {
        if (!window.confirm("Na pewno chcesz usunąć tę kategorię?")) return;

        try {
            const res = await axios.delete(`http://localhost:8080/api/categories/${id}/soft-delete`,{
                withCredentials: true,
            });
            if (res.status !== 204) {
                throw new Error("Błąd usuwania kategorii");
            }
            fetchCategories();
            setError(null);
        } catch (e) {
            setError(e.response?.data?.message || e.message);
        }
    };

    // Zacznij edytować kategorię
    const startEdit = (id, name) => {
        setEditId(id);
        setEditName(name);
        setError(null);
    };

    // Anuluj edycję
    const cancelEdit = () => {
        setEditId(null);
        setEditName("");
    };

    // Zapisz edycję
    const handleEditSave = async () => {
        if (!editName.trim()) {
            setError("Nazwa kategorii nie może być pusta");
            return;
        }

        try {
            const res = await axios.put(`http://localhost:8080/api/categories/${editId}`, { name: editName },
                {withCredentials: true});
            if (res.status !== 204) {
                throw new Error("Błąd aktualizacji kategorii");
            }
            setEditId(null);
            setEditName("");
            fetchCategories();
            setError(null);
        } catch (e) {
            setError(e.response?.data?.message || e.message);
        }
    };
    const handleRestore = async (id) => {
        if (!window.confirm("Na pewno chcesz przywrócić tę kategorię?")) return;

        try {
            const res = await axios.patch(`http://localhost:8080/api/categories/soft-deleted/${id}`, {}, {
                withCredentials: true,
            });
            if (res.status !== 204) {
                throw new Error("Błąd przywracania kategorii");
            }
            fetchCategories();
            setError(null);
        } catch (e) {
            setError(e.response?.data?.message || e.message);
        }
    };

    return (
        <div style={{ maxWidth: 600, margin: "auto", padding: 20 }}>
            <h2>Kategorie</h2>

            {error && <div style={{ color: "red", marginBottom: 10 }}>{error}</div>}

            <ul>
                {categories.map(({ id, name, deleted }) => (
                    <li key={id} style={{ marginBottom: 10 }}>
                        {editId === id ? (
                            <>
                                <input
                                    type="text"
                                    value={editName}
                                    onChange={(e) => setEditName(e.target.value)}
                                />
                                <button onClick={handleEditSave}>Zapisz</button>
                                <button onClick={cancelEdit}>Anuluj</button>
                            </>
                        ) : (
                            <>
                                <strong style={{ textDecoration: deleted ? 'line-through' : 'none', color: deleted ? 'gray' : 'inherit' }}>
                                    {name}
                                </strong>{" "}
                                {!deleted ? (
                                    <>
                                        <button onClick={() => startEdit(id, name)}>Edytuj</button>{" "}
                                        <button onClick={() => handleDelete(id)}>Usuń</button>
                                    </>
                                ) : (
                                    <button onClick={() => handleRestore(id)}>Przywróć</button>
                                )}
                            </>
                        )}
                    </li>
                ))}
            </ul>

            {/* Dodawanie nowej kategorii */}
            <div style={{ marginTop: 20 }}>
                <input
                    type="text"
                    placeholder="Nowa kategoria"
                    value={newName}
                    onChange={(e) => setNewName(e.target.value)}
                />
                <button onClick={handleAdd}>Dodaj</button>
            </div>
        </div>
    );
};

export default CategoryManager;
