import React, { createContext, useState } from "react";
import { ICity, IRoute, ITrip } from "../types/BusTripsTypes";

interface BusTripsContextType {
  departureCities: ICity[];
  setDepartureCities: React.Dispatch<React.SetStateAction<ICity[]>>;
  arrivalCities: ICity[];
  setArrivalCities: React.Dispatch<React.SetStateAction<ICity[]>>;

  selectedDepartureCity: ICity | null;
  setSelectedDepartureCity: React.Dispatch<React.SetStateAction<ICity | null>>;
  selectedArrivalCity: ICity | null;
  setSelectedArrivalCity: React.Dispatch<React.SetStateAction<ICity | null>>;

  routes: IRoute[];
  setRoutes: React.Dispatch<React.SetStateAction<IRoute[]>>;

  trips: ITrip[];
  setTrips: React.Dispatch<React.SetStateAction<ITrip[]>>;
}

export const BusTripsContext = createContext<BusTripsContextType | undefined>(
  undefined
);

export const BusTripsContextProvider: React.FC<{
  children: React.ReactNode;
}> = ({ children }) => {
  const [departureCities, setDepartureCities] = useState<ICity[]>([]);
  const [arrivalCities, setArrivalCities] = useState<ICity[]>([]);
  const [selectedDepartureCity, setSelectedDepartureCity] =
    useState<ICity | null>(null);
  const [selectedArrivalCity, setSelectedArrivalCity] = useState<ICity | null>(
    null
  );
  const [routes, setRoutes] = useState<IRoute[]>([]);
  const [trips, setTrips] = useState<ITrip[]>([]);

  return (
    <BusTripsContext.Provider
      value={{
        departureCities,
        setDepartureCities,
        arrivalCities,
        setArrivalCities,
        selectedDepartureCity,
        setSelectedDepartureCity,
        selectedArrivalCity,
        setSelectedArrivalCity,
        routes,
        setRoutes,
        trips,
        setTrips,
      }}
    >
      {children}
    </BusTripsContext.Provider>
  );
};
