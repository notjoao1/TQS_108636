import { useContext, useEffect } from "react";
import { BusTripsContext } from "../context/BusTripsContext";
import { ICity } from "../types/BusTripsTypes";

export const DepartureCitiesDropdown = () => {
  const {
    selectedDepartureCity,
    setSelectedDepartureCity,
    departureCities,
    setArrivalCities,
    setSelectedArrivalCity,
  } = useContext(BusTripsContext) || {};

  // Fetch connected arrival cities when you click a departure city
  useEffect(() => {
    if (!selectedDepartureCity) {
      setArrivalCities?.([]);
      return;
    }
    const fetchConnectedLocations = async () => {
      try {
        const res = await fetch(
          `http://localhost:8080/api/locations?connectedTo=${selectedDepartureCity?.name}`
        );
        if (!res.ok) throw new Error(res.statusText);

        const data = (await res.json()) as ICity[];
        setArrivalCities?.(data);
      } catch (error) {
        console.error("Error fetching CONNECTED locations:", error);
      }
    };

    fetchConnectedLocations();
  }, [selectedDepartureCity]);

  const handleDepartureChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    const selectedId = parseInt(event.target.value, 10);
    const selectedCity = departureCities?.find(
      (city) => city.id === selectedId
    );
    setSelectedDepartureCity?.(selectedCity ?? null);
    setSelectedArrivalCity?.(null); // arrival city not selected
  };

  return (
    <div>
      <p className="text-2xl font-bold mt-5">Choose your departure city:</p>
      <select
        className="select select-bordered w-full max-w-xs mt-5"
        value={selectedDepartureCity?.id.toString() ?? ""}
        onChange={(e) => handleDepartureChange(e)}
      >
        <option value="">Select a city</option>
        {departureCities?.map((c) => (
          <option key={c.id} value={c.id.toString()}>
            {c.name}
          </option>
        ))}
      </select>
    </div>
  );
};
