import { useContext, useEffect, useState } from "react";
import { IReservation, ITripDetails } from "../types/BusTripsTypes";
import { getRouteStopsString } from "../utils/RouteUtils";
import { BusTripsContext } from "../context/BusTripsContext";
import { useNavigate } from "react-router-dom";

interface ReserveTripContentProps {
  tripId: string;
}

const ReserveTripContent: React.FC<ReserveTripContentProps> = (
  props: ReserveTripContentProps
) => {
  const navigate = useNavigate();

  const {
    tripDetails,
    setTripDetails,
    selectedDepartureCity,
    selectedArrivalCity,
    currency,
  } = useContext(BusTripsContext) || {};

  const tripId = props.tripId;

  useEffect(() => {
    const fetchTripDetails = async () => {
      try {
        const res = await fetch(
          `http://localhost:8080/api/trips/${tripId}?currency=${currency}`
        );
        if (!res.ok) throw new Error(res.statusText);

        const data = (await res.json()) as ITripDetails;
        setTripDetails?.(data);
        setSeatNumber(data.availableSeatNumbers[0].toString());
      } catch (error) {
        console.error("Error fetching CONNECTED locations:", error);
      }
    };
    fetchTripDetails();
  }, []);

  const [name, setName] = useState<string>("");
  const [creditCardNumber, setCreditCardnumber] = useState<string>("");
  const [expMonth, setExpMonth] = useState<string>("");
  const [expYear, setExpYear] = useState<string>("");
  const [seatNumber, setSeatNumber] = useState<string>("");

  const handleConfirmButton = () => {
    if (name === "" || seatNumber === "") return;
    const reqBody = {
      clientName: name,
      tripId: tripDetails?.id,
      seatNumber: parseInt(seatNumber),
    };
    fetch(`http://localhost:8080/api/reservations`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(reqBody),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Request failed: " + response.status);
        }
        return response.json();
      })
      .then((data: IReservation) => {
        navigate("/confirmation", { state: data });
      })
      .catch((error) => {
        console.error("Error making the request:", error);
      });
  };

  return (
    <div>
      <h2 className="text-4xl">
        Reservation for a trip from{" "}
        <span className="font-bold text-cyan-800">
          {selectedDepartureCity?.name}
        </span>{" "}
        to{" "}
        <span className="font-bold text-blue-800">
          {selectedArrivalCity?.name}
        </span>
      </h2>
      <div className="pt-4 pl-4 pb-10">
        <p>Trip Number: {tripDetails?.id}</p>
        <p>
          Cost: {tripDetails?.price} {currency}
        </p>
        <p>
          Departure: {new Date(tripDetails?.departureTime).toLocaleString()}
        </p>
        <p>Route: {getRouteStopsString(tripDetails?.route)}</p>
        <p>Distance: {tripDetails?.route.totalDistanceKm} km</p>
      </div>
      <hr className="border-zinc-700" />
      <div className="pt-4 flex flex-col">
        <div className="flex flex-col pb-4">
          <label className="pb-1 font-bold" htmlFor="name">
            Name*
          </label>
          <input
            className="input input-bordered w-full max-w-xs"
            id="name"
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="Your name"
          />
        </div>
        <div className="flex flex-col pb-4">
          <label className="pb-1 font-bold" htmlFor="credit_card">
            Credit Card Number
          </label>
          <input
            className="input input-bordered w-full max-w-xs"
            id="cc_number"
            type="number"
            value={creditCardNumber}
            onChange={(e) => setCreditCardnumber(e.target.value)}
            placeholder="Your credit card number"
          />
        </div>
        <div className="flex flex-col pb-4">
          <label className="pb-1 font-bold" htmlFor="credit_card">
            Expiration Month
          </label>
          <input
            className="input input-bordered w-full max-w-xs"
            id="exp_month"
            type="number"
            value={expMonth}
            onChange={(e) => setExpMonth(e.target.value)}
            placeholder="MM"
          />
        </div>
        <div className="flex flex-col pb-4">
          <label className="pb-1 font-bold" htmlFor="credit_card">
            Expiration Year
          </label>
          <input
            className="input input-bordered w-full max-w-xs"
            id="exp_year"
            type="number"
            value={expYear}
            onChange={(e) => setExpYear(e.target.value)}
            placeholder="YYYY"
          />
        </div>
        <div className="flex flex-col pb-4">
          <div className="font-bold pb-1">Seat Number*</div>
          <select
            className="select select-bordered w-full max-w-xs"
            value={seatNumber}
            onChange={(e) => setSeatNumber(e.target.value)}
          >
            {tripDetails?.availableSeatNumbers.map((n) => (
              <option value={n} key={n}>
                {n}
              </option>
            ))}
          </select>
        </div>
        <div className="text-red-500">* Required</div>
      </div>
      <div className="pt-4">
        <button
          onClick={() => handleConfirmButton()}
          className="btn btn-success text-green-200"
        >
          Confirm Details
        </button>
      </div>
    </div>
  );
};

export default ReserveTripContent;
