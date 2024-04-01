import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <div>
      <div className="navbar bg-neutral text-neutral-content">
        <button className="btn btn-ghost text-xl">
          <Link to={"/"}>Home</Link>
        </button>
        <button className="btn btn-ghost text-xl">
          <Link to={"/reservation_details"}>Reservation Details</Link>
        </button>
      </div>
    </div>
  );
};

export default Navbar;
