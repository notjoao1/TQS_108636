import { useContext, useEffect } from "react";
import { BusTripsContext } from "../context/BusTripsContext";
import { ITrip } from "../types/BusTripsTypes";
import { Link } from "react-router-dom";
import { getRouteStopsString } from "../utils/RouteUtils";

const TripList = () => {
  const {
    selectedDepartureCity,
    selectedArrivalCity,
    trips,
    setTrips,
    currency,
  } = useContext(BusTripsContext) || {};

  useEffect(() => {
    if (!selectedArrivalCity) {
      setTrips?.([]);
      return;
    }

    const fetchTripsForSelectedCities = async () => {
      console.log("FETCHING TRIPS, SELECTED CURRENCY: ", currency);
      try {
        const res = await fetch(
          `http://localhost:8080/api/trips?from=${selectedDepartureCity?.name}&to=${selectedArrivalCity?.name}&upcoming=True&currency=${currency}`
        );
        if (!res.ok) throw new Error(res.statusText);

        const data = (await res.json()) as ITrip[];
        setTrips?.(data);
        console.log("merda", data);
      } catch (error) {
        console.error("Error fetching UPCOMING TRIPS:", error);
      }
    };

    fetchTripsForSelectedCities();
  }, [selectedArrivalCity, currency]);

  if (!selectedArrivalCity) return;

  return (
    <div className="mt-10">
      {!selectedArrivalCity}
      <div className="text-2xl font-bold">
        Trips from {selectedDepartureCity?.name} to {selectedArrivalCity?.name}:
        <div className="flex gap-2 p-1 mt-4">
          {trips?.length > 0 ? (
            <>
              {trips?.map((t) => {
                return (
                  <div
                    className="card w-96 bg-base-100 shadow-xl border border-zinc-700"
                    key={t.id}
                  >
                    <div className="card-body text-sm">
                      <h2 className="card-title text-2xl font-extrabold">
                        {t.route.routeStops[0].location.name} to{" "}
                        {
                          t.route.routeStops[t.route.routeStops.length - 1]
                            .location.name
                        }
                      </h2>
                      <span className="text-sm">
                        <span className="font-extrabold">Route: </span>
                        {getRouteStopsString(t.route)}
                      </span>
                      <p>
                        Leaves at: {new Date(t.departureTime).toLocaleString()}
                      </p>
                      <p>Distance: {t.route.totalDistanceKm}km</p>
                      <p>
                        Price: {t.price}{" "}
                        <span className="text-yellow-500">{currency}</span>
                      </p>
                      <div className="card-actions justify-end">
                        <Link
                          className="btn btn-primary"
                          to={`reserve/${t.id}`}
                        >
                          Buy Now
                        </Link>
                      </div>
                    </div>
                  </div>
                );
              })}
            </>
          ) : (
            <div className="card w-96 bg-base-100 shadow-xl border border-zinc-700">
              <div className="card-body text-xl text-red-600">
                <p>No trips available for this route</p>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default TripList;
