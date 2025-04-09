import { Link } from "react-router-dom";

function Navbar() {
    return (
        <nav className="bg-blue-600 text-white px-4 py-3 shadow-md">
            <div className="container mx-auto flex justify-between items-center">
                <div className="text-lg font-bold">
                    <Link to="/">Eventify</Link>
                </div>
                <ul className="flex space-x-6">
                    <li>
                        <Link to="/" className="hover:text-gray-200">Login</Link>
                    </li>
                    <li>
                        <Link to="/register" className="hover:text-gray-200">Register</Link>
                    </li>
                    <li>
                        <Link to="/events" className="hover:text-gray-200">Event List</Link>
                    </li>
                    <li>
                        <Link to="/events/new" className="hover:text-gray-200">Create Event</Link>
                    </li>
                </ul>
            </div>
        </nav>
    );
}

export default Navbar;
