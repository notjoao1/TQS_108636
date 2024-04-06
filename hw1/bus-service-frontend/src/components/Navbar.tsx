import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { BusTripsContext } from "../context/BusTripsContext";

const Navbar = () => {
  const [currencies, setCurrencies] = useState<Record<string, string>>({});
  const { currency, setCurrency } = useContext(BusTripsContext) || {};

  useEffect(() => {
    const fetchCurrencies = async () => {
      const res = await fetch("https://api.frankfurter.app/currencies");
      if (!res.ok)
        console.error("Error fetching currencies, status code: " + res.status);
      const data = (await res.json()) as Record<string, string>;
      setCurrencies(data);
    };
    fetchCurrencies();
  }, []);

  return (
    <div className="navbar bg-base-300 rounded-box">
      <div className="flex-1 px-2 lg:flex-none">
        <button className="btn btn-ghost text-xl">
          <Link to={"/"}>FusBlix</Link>
        </button>
        <button className="btn btn-ghost text-xl">
          <Link to={"/reservation_details"}>Reservation Details</Link>
        </button>
      </div>
      <div className="flex justify-end flex-1 px-2">
        <div className="flex items-stretch">
          <div className="dropdown dropdown-end">
            <div
              tabIndex={0}
              role="button"
              className="btn btn-ghost rounded-btn"
            >
              Currency: <span className="text-yellow-400">{currency}</span>
            </div>
            <ul
              tabIndex={0}
              className="menu dropdown-content z-[1] p-2 shadow bg-base-100 rounded-box w-52 mt-4"
            >
              {Object.entries(currencies).map(([code, description]) => (
                <li key={code}>
                  <button
                    className="text-xs"
                    value={code}
                    onClick={() => setCurrency?.(code)}
                  >
                    {code} - {description}
                  </button>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
