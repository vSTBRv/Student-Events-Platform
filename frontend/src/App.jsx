import { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import CreateEvent from "./components/CreateEvent";
import Login from "./components/Login";
import RegisterSelect from "./components/RegisterSelect";
import RegisterStudent from "./components/RegisterStudent";
import RegisterOrganization from "./components/RegisterOrganization";
import EventList from "./components/EventList";
import Navbar from "./components/Navbar";

function App() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const loggedIn = localStorage.getItem("isLoggedIn") === "true";
        setIsLoggedIn(loggedIn);
    }, []);

  return (
    <div>
      <Navbar isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />

      <Routes>
        <Route path="/" element={<Login setIsLoggedIn={setIsLoggedIn}/>} />
        <Route path="/register" element={<RegisterSelect />} />
        <Route path="/register/student" element={<RegisterStudent />} />
        <Route
          path="/register/organization"
          element={<RegisterOrganization />}
        />
        <Route path="/events/new" element={<CreateEvent />} />
        <Route path="/events" element={<EventList />} />
        <Route path="/events/edit/:id" element={<CreateEvent />} />
      </Routes>
    </div>
  );
}

export default App;
