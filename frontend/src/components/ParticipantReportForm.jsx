import React, {useState} from 'react';
import axios from 'axios';
import ParticipantEventList from './ParticipantEventList';

function ParticipantReportForm() {
    const [fromDate, setFromDate] = useState('')
    const [toDate, setToDate] = useState('')
    const [count, setCount] = useState(null)
    const [error, setError] = useState(null)
    const [eventList, setEventList] = useState([])

    const handleSubmit = async (e) => {
        console.log("Zapytanie: ", {
            fromDate: fromDate + 'T00:00:00',
            toDate: toDate + 'T23:59:59',
        });
        e.preventDefault();
        setError('');
        try{
            const params = {
                fromDate: fromDate + 'T00:00:00',
                toDate: toDate + 'T23:59:59',
            };

            const [countResp, listResp] = await Promise.all([
                axios.get('/api/reports/participant-count', {
                    params, withCredentials: true
                }),
                axios.get('/api/reports/participant-count-per-event', {
                    params, withCredentials: true
                })
            ]);

            setCount(countResp.data.participantCount);
            setEventList(listResp.data);

            // eslint-disable-next-line no-unused-vars
        } catch (err) {
            setError('Błąd pobierania danych');
            setCount(null);
            setEventList([]);
        }
    };

    return (
        <div className={"p-4 max-w-md mx-aut border rounded-2xl shadow"}>
            {/*<h2 className={"text-xl font-bold mb-4"}>Raport: Liczba uczestników</h2>*/}
            <form onSubmit={handleSubmit} className={"space-y-3"}>

                <div>
                    <label className={"block text-sm"}>Data początkowa:</label>
                    <input
                        type={"date"}
                        value={fromDate}
                        onChange={(e)=>setFromDate(e.target.value)}
                        className={"w-full border p-2 rounded"}
                        required={true}
                        />
                </div>

                <div>
                    <label className={"blcok text-sm"}>Data końcowa:</label>
                    <input
                        type={"date"}
                        value={toDate}
                        onChange={(e)=>setToDate(e.target.value)}
                        className={"w-full border p-2 rounded"}
                        required={true}
                        />
                </div>

                <button type={"submit"} className={"bg-amber-600 hover:bg-amber-700 text-white px-4 py-2 rounded"}>Generuj raport</button>
            </form>

            {eventList.length > 0 && (
                <div className={"mt-4 text-lg font-medium"}>
                    Łączna liczba uczestników: {count}
                    <ParticipantEventList eventList={eventList}/>
                </div>
            )}

            {error && (
                <div className={"mt-4 text-red-500"}>
                    {error}
                </div>
            )}

        </div>
    )

}

export default ParticipantReportForm;