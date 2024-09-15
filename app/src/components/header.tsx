import { Link } from "react-router-dom";
import { useAuth } from "../hooks/use-auth";

export const Header = () => {
  const { user, logout } = useAuth();

  return (
    <header className="flex justify-between px-4 py-3 border-b border-gray-200">
      <Link to="/">
        <h1 className="text-xl font-medium">Poll App</h1>
      </Link>

      <nav>
        <ul className="flex items-center">
          <li>
            <Link
              className="px-2 py-1 rounded-lg hover:bg-gray-100 text-gray-700 h-10"
              to="/"
            >
              Home
            </Link>
          </li>
          <li>
            <Link
              className="px-2 py-1 rounded-lg hover:bg-gray-100 text-gray-700 h-10"
              to="/polls/create"
            >
              Create poll
            </Link>
          </li>
          {!!user && (
            <li>
              <button
                onClick={() => logout()}
                className="px-2 py-1 rounded-lg hover:bg-gray-100 text-gray-700 h-10"
              >
                Log out
              </button>
            </li>
          )}
          {!user && (
            <>
              <li>
                <Link
                  className="px-2 py-1 rounded-lg hover:bg-gray-100 text-gray-700 h-10"
                  to="/login"
                >
                  Login
                </Link>
              </li>
              <li>
                <Link
                  className="px-2 py-1 rounded-lg hover:bg-gray-100 text-gray-700 h-10"
                  to="/register"
                >
                  Register
                </Link>
              </li>
            </>
          )}
        </ul>
      </nav>
    </header>
  );
};
