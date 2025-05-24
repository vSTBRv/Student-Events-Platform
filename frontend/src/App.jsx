import { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import CreateEvent from "./components/CreateEvent";
import Login from "./components/Login";
import RegisterSelect from "./components/RegisterSelect";
import RegisterStudent from "./components/RegisterStudent";
import RegisterOrganization from "./components/RegisterOrganization";
import EventList from "./components/EventList";
import Navbar from "./components/Navbar";
import EventDetails from "./components/EventDetails.jsx";
import EventPage from "./components/EventPage.jsx";
import ManageUsers from "./components/ManageUsers";
import EventsDeleted from "./components/EventsDeleted";
import SendMessage from "./components/SendMessage.jsx";
import MyEvents from "./components/MyEvents";
import EventRatings from "./components/EventRatings";
import RateEventForm from "./components/RateEventForm";



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
          <Route path={"/events/:id"} element={<EventDetails />} />
        <Route path="/events/new" element={<CreateEvent />} />
          <Route path="/events" element={<EventPage />} />
        {/*<Route path="/events" element={<EventList />} />*/}
        <Route path="/events/edit/:id" element={<CreateEvent />} />
          <Route path="/admin/users" element={<ManageUsers />} />
          <Route path="/admin/deleted-events" element={<EventsDeleted />} />
          <Route path="/events/:id/message" element={<SendMessage />} />
          <Route path="/my-events" element={<MyEvents />} />
          <Route path="/events/:id/ratings" element={<EventRatings />} />
          <Route path="/events/:id/rate" element={<RateEventForm />} />

      </Routes>
    </div>
  );
}

export default App;
