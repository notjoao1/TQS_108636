import { useContext } from "react";
import { BusTripsContext } from "../context/BusTripsContext";

export const ArrivalCitiesDropdown = () => {
  const { selectedArrivalCity, setSelectedArrivalCity, arrivalCities } =
    useContext(BusTripsContext) || {};

  const handleArrivalChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedId = parseInt(event.target.value, 10);
    const selectedCity = arrivalCities?.find((city) => city.id === selectedId);
    setSelectedArrivalCity?.(selectedCity ?? null);
  };

  return (
    <div>
      <p className="text-2xl font-bold mt-5">Choose your arrival city:</p>
      <select
        className="select select-bordered w-full max-w-xs mt-5"
        value={selectedArrivalCity?.id.toString() ?? ""}
        onChange={(e) => handleArrivalChange(e)}
        disabled={arrivalCities?.length == 0}
      >
        {arrivalCities?.length == 0 ? (
          <option>Select a departure city first.</option>
        ) : (
          ""
        )}

        {arrivalCities?.length > 0 && !selectedArrivalCity ? (
          <option>Select a city</option>
        ) : (
          ""
        )}

        {arrivalCities?.map((c) => (
          <option key={c.id} value={c.id.toString()}>
            {c.name}
          </option>
        ))}
      </select>
    </div>
  );
};
