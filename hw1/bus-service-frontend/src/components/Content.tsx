import { useContext, useEffect } from "react";
import { DepartureCitiesDropdown } from "./DepartureCitiesDropdown";
import { ArrivalCitiesDropdown } from "./ArrivalCitiesDropdown";
import { BusTripsContext } from "../context/BusTripsContext";
import { ICity } from "../types/BusTripsTypes";
import TripList from "./TripList";

const Content = () => {
  const { setDepartureCities } = useContext(BusTripsContext) || {};

  useEffect(() => {
    const fetchAllLocations = async () => {
      try {
        const res = await fetch(`http://localhost:8080/api/locations`);
        if (!res.ok) throw new Error(res.statusText);
        const data = (await res.json()) as ICity[];
        setDepartureCities?.(data);
      } catch (error) {
        console.error("Error fetching ALL locations:", error);
      }
    };

    fetchAllLocations();
  }, []);

  return (
    <div>
      <p className="text-4xl font-bold">Welcome to our Bus Trip Service!</p>
      <p className="mt-10">We offer the best prices!</p>
      <div className="flex flex-row gap-40">
        <div className="flex-col">
          <DepartureCitiesDropdown />
        </div>
        <div className="flex-col">
          <ArrivalCitiesDropdown />
        </div>
      </div>
      <TripList />
    </div>
  );
};

export default Content;
