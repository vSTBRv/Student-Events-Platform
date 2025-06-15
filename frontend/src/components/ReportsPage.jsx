import Participants from "./ParticipantReportForm.jsx";
import ParticipantReportForm from "./ParticipantReportForm.jsx";

function ReportsPage() {
    return (
        <div className={"max-w-4xl mx-auto mt-10 p-6 bg-white rounded-xl shadow"}>
            <div className={"bg-white rounded-2xl shadow p-6 border"}>
                <h2 className="text-2xl font-bold text-amber-600 mb-6 text-center capitalize">Raport: Liczba uczestników</h2>
                <div className="flex justify-center">
                    <ParticipantReportForm />
                </div>
            </div>

        </div>
    );
}

export default ReportsPage;