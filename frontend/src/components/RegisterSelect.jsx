import { useNavigate } from "react-router-dom";

export default function RegisterSelect() {
    const navigate = useNavigate();

    return (
        <div className="min-h-screen bg-gray-100 flex flex-col items-center justify-center px-4">
            <h1 className="text-3xl font-bold text-amber-600 mb-8 text-center">
                Zarejestruj się jako:
            </h1>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 w-full max-w-4xl">
                {/* STUDENT */}
                <div
                    onClick={() => navigate("/register/student")}
                    className="cursor-pointer bg-white rounded-xl shadow-md p-8 text-center hover:shadow-lg hover:bg-amber-50 transition"
                >
                    <h2 className="text-2xl font-semibold text-gray-800 mb-2">🎓 Student</h2>
                    <p className="text-gray-600">
                        Dołącz jako student, aby przeglądać i zapisywać się na wydarzenia organizowane przez uczelnię i organizacje.
                    </p>
                </div>

                {/* ORGANIZACJA */}
                <div
                    onClick={() => navigate("/register/organization")}
                    className="cursor-pointer bg-white rounded-xl shadow-md p-8 text-center hover:shadow-lg hover:bg-amber-50 transition"
                >
                    <h2 className="text-2xl font-semibold text-gray-800 mb-2">🏛️ Organizacja</h2>
                    <p className="text-gray-600">
                        Zarejestruj organizację, aby tworzyć i zarządzać wydarzeniami dla społeczności studenckiej.
                    </p>
                </div>
            </div>
        </div>
    );
}
