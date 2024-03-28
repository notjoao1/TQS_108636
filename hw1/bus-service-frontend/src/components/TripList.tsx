import { useContext, useEffect } from "react";
import { BusTripsContext } from "../context/BusTripsContext";
import { IRoute, ITrip } from "../types/BusTripsTypes";

const TripList = () => {
  const { selectedDepartureCity, selectedArrivalCity, trips, setTrips } =
    useContext(BusTripsContext) || {};

  useEffect(() => {
    if (!selectedArrivalCity) return;

    const fetchTripsForSelectedCities = async () => {
      try {
        const res = await fetch(
          `http://localhost:8080/api/trips?from=${selectedDepartureCity?.name}&to=${selectedArrivalCity?.name}&upcoming=True`
        );
        if (!res.ok) throw new Error(res.statusText);

        const data = (await res.json()) as ITrip[];
        console.log("data", data);
        setTrips?.(data);
        console.log(typeof data[0].departureTime);
      } catch (error) {
        console.error("Error fetching UPCOMING TRIPS:", error);
      }
    };

    fetchTripsForSelectedCities();
  }, [selectedArrivalCity]);

  const getRouteStopsString = (route: IRoute): string => {
    let retString = "";
    for (let i = 0; i < route.routeStops.length - 1; i++) {
      retString = retString + `${route.routeStops[i].location.name} -> `;
    }
    retString =
      retString +
      `${route.routeStops[route.routeStops.length - 1].location.name}`;

    return retString;
  };

  return (
    <div className="mt-10">
      <div className="text-2xl font-bold">
        Trips from {selectedDepartureCity?.name} to {selectedArrivalCity?.name}:
        <div className="flex gap-2 border rounded-md border-white p-4 mt-10">
          {trips?.map((t) => {
            return (
              <div className="card w-96 bg-base-100 shadow-xl" key={t.id}>
                <div className="card-body text-sm">
                  <h2 className="card-title text-2xl font-extrabold">
                    {t.route.routeStops[0].location.name} to{" "}
                    {
                      t.route.routeStops[t.route.routeStops.length - 1].location
                        .name
                    }
                  </h2>
                  <span className="text-sm">
                    <span className="font-extrabold">Route: </span>
                    {getRouteStopsString(t.route)}
                  </span>
                  <p>Leaves at: {new Date(t.departureTime).toLocaleString()}</p>
                  <p>Distance: {t.route.totalDistanceKm}km</p>
                  <p>Price: {t.priceEuro} EUR</p>
                  <div className="card-actions justify-end">
                    <button className="btn btn-primary">Buy Now</button>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
};

export default TripList;
