import { useEffect, useState } from "react";

export default function ManageUsers() {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        fetch("http://localhost:8080/api/users", {
            credentials: "include",
        })
            .then((res) => res.json())
            .then((data) => setUsers(data))
            .catch(() => setError("Błąd podczas pobierania użytkowników."));
    }, []);

    const handleRoleChange = (id, newRole) => {
        const user = users.find((u) => u.id === id);
        if (!user) return;

        const updatedUser = {...user, userRole: newRole};

        fetch(`http://localhost:8080/api/users/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            credentials: "include",
            body: JSON.stringify(updatedUser),
        })
            .then((res) => {
                if (!res.ok) throw new Error();
                setUsers((prev) =>
                    prev.map((u) => (u.id === id ? updatedUser : u))
                );
            })
            .catch(() => setError("Nie udało się zmienić roli."));
    };

    const handleToggleEnabled = (id) => {
        const user = users.find((u) => u.id === id);
        if (!user) return;

        const updatedUser = {...user, enabled: !user.enabled};

        fetch(`http://localhost:8080/api/users/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            credentials: "include",
            body: JSON.stringify(updatedUser),
        })
            .then((res) => {
                if (!res.ok) throw new Error();
                setUsers((prev) =>
                    prev.map((u) => (u.id === id ? updatedUser : u))
                );
            })
            .catch(() => setError("Nie udało się zaktualizować statusu."));
    };

    const handleDeleteUser = (id) => {
        const confirmDelete = window.confirm("Czy na pewno chcesz usunąć tego użytkownika?");
        if (!confirmDelete) return;

        fetch(`http://localhost:8080/api/users/${id}`, {
            method: "DELETE",
            credentials: "include",
        })
            .then((res) => {
                if (!res.ok) throw new Error();
                setUsers((prev) => prev.filter((u) => u.id !== id));
            })
            .catch(() => setError("Nie udało się usunąć użytkownika."));
    };

    return (
        <div className="event-container">
            <h1 className="event-title">Zarządzanie użytkownikami</h1>
            {error && <p className="text-red-500 text-center">{error}</p>}
            <div className="overflow-x-auto rounded-xl shadow">
                <table className="min-w-full bg-white rounded-xl overflow-hidden text-sm text-gray-700">
                    <thead className="bg-amber-100 text-amber-800 uppercase text-xs">
                    <tr>
                        <th className="px-6 py-4 text-left">Email</th>
                        <th className="px-6 py-4 text-left">Rola</th>
                        <th className="px-6 py-4 text-left">Status</th>
                        <th className="px-6 py-4 text-left">Akcje</th>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map((user, idx) => (
                        <tr
                            key={user.id}
                            className={idx % 2 === 0 ? "bg-gray-50" : "bg-white"}
                        >
                            <td className="px-6 py-4">{user.email}</td>
                            <td className="px-6 py-4">
                                <select
                                    value={user.userRole}
                                    onChange={(e) => handleRoleChange(user.id, e.target.value)}
                                    className="border border-gray-300 rounded-md px-2 py-1"
                                >
                                    <option value="STUDENT">STUDENT</option>
                                    <option value="ORGANIZATION">ORGANIZATION</option>
                                    <option value="ADMIN">ADMIN</option>
                                </select>
                            </td>
                            <td className="px-6 py-4">
                                <span
                                    className={
                                        user.enabled
                                            ? "text-green-600 font-medium"
                                            : "text-red-500 font-medium"
                                    }
                                >
                                    {user.enabled ? "Aktywny" : "Zablokowany"}
                                </span>
                            </td>
                            <td className="px-6 py-4 flex flex-col gap-2 sm:flex-row">
                                <button
                                    onClick={() => handleToggleEnabled(user.id)}
                                    className="primary-btn w-full sm:w-auto"
                                >
                                    {user.enabled ? "Zablokuj" : "Odblokuj"}
                                </button>
                                <button
                                    onClick={() => handleDeleteUser(user.id)}
                                    className="danger-btn w-full sm:w-auto"
                                >
                                    Usuń
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
