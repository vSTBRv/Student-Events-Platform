import { Link } from "react-router-dom";
import Logout from "./Logout.jsx"
import {useEffect, useState} from "react";

function Navbar() {
  const [isLoggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    // setLoggedIn(document.cookie.includes("JSESSIONID")); // To wykrywa czy jest sesja -- nie działa ta opcja,
      const loggedIn = localStorage.getItem("isLoggedIn") === "true";
      setLoggedIn(loggedIn);
  }, []);

  // return (
  //   <nav className="text-white px-4 py-3 shadow-md">
  //     <div className="container mx-auto flex justify-between items-center">
  //       <div className="text-lg font-bold">
  //         <Link to="/">Eventify</Link>
  //       </div>
  //       <ul className="flex space-x-6">
  //         <li>
  //           <Link to="/" className="hover:text-gray-200">
  //             Login
  //           </Link>
  //         </li>
  //         <li>
  //           <Link to="/register" className="hover:text-gray-200">
  //             Register
  //           </Link>
  //         </li>
  //         <li>
  //           <Link to="/events" className="hover:text-gray-200">
  //             Event List
  //           </Link>
  //         </li>
  //         <li>
  //           <Link to="/events/new" className="hover:text-gray-200">
  //             Create Event
  //           </Link>
  //         </li>
  //         <li>
  //           <Logout onLogout={() => setLoggedIn(false)} />
  //         </li>
  //       </ul>
  //     </div>
  //   </nav>
  // );

  return (
      <nav className={"text-white px-4 py-3 shadow-md"}>
        <div className={"container mx-auto flex justify-between items-center"}>
          <div className={"text-lg font-bold"}>
            <Link to={"/"}>Eventify</Link>
          </div>
          <ul className={"flex space-x-6"}>
            {!isLoggedIn ? (
                <>
                  <li>
                    <Link to={"/"} className={"hover:text-gray-200"}>
                      Login
                    </Link>
                  </li>
                  <li>
                    <Link to={"/register"} className={"hover:text-gray-200"}>
                      Register
                    </Link>
                  </li>
                </>
            ) : (
                <>
                  <li>
                    <Link to={"/events"} className={"hover:text-gray-200"}>
                      Event List
                    </Link>
                  </li>
                  <li>
                    <Link to={"/events/new"} className={"hover:text-gray-200"}>
                      Create Event
                    </Link>
                  </li>
                  <li>
                    <Logout onLogout={() => setLoggedIn(false)} />
                  </li>
                </>
            )}
          </ul>
        </div>
      </nav>
  );
}

export default Navbar;
