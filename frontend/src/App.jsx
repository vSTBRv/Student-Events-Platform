import { Routes, Route } from "react-router-dom";
import CreateEvent from "./components/CreateEvent";
import Login from "./components/Login";
import RegisterSelect from "./components/RegisterSelect";
import RegisterStudent from "./components/RegisterStudent";
import RegisterOrganization from "./components/RegisterOrganization";

function App() {
    return (
        <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/register" element={<RegisterSelect />} />
            <Route path="/register/student" element={<RegisterStudent />} />
            <Route path="/register/organization" element={<RegisterOrganization />} />
            <Route path="/events/new" element={<CreateEvent />} />
        </Routes>
    );
}

export default App;
