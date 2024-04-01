import { useState } from "react";
import Navbar from "../components/Navbar";
import { IReservation } from "../types/BusTripsTypes";
import ReservationDetails from "../components/ReservationDetails";

const ReservationDetailsPage = () => {
  const [confirmed, setConfirmed] = useState<boolean>(false);
  const [reservationToken, setReservationToken] = useState<string>("");

  const [reservationData, setReservationData] = useState<IReservation | null>(
    null
  );

  const handleConfirmButton = () => {
    console.log(reservationToken);
    fetch(`http://localhost:8080/api/reservations/${reservationToken}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Reservation fetch failed"); // Handle errors
        }
        return response.json(); // Parse JSON response
      })
      .then((data: IReservation) => {
        setReservationData(data);
        console.log(data);
        setConfirmed(true);
      })
      .catch((error) => {
        console.error("Error fetching reservation:", error);
        // Handle the error appropriately (e.g., display an error message)
      });
  };

  return (
    <div className="flex flex-col">
      <Navbar />
      <div className="flex justify-center ml-52 mr-52 mt-10 mb-10">
        {confirmed ? (
          <div>
            <div className="text-4xl">Reservation Details</div>
            <ReservationDetails reservation={reservationData!} />
          </div>
        ) : (
          <label className="form-control w-full max-w-sm text-9xl">
            <div className="label">
              <span className="label-text">Input your reservation token</span>
            </div>
            <input
              type="text"
              value={reservationToken}
              onChange={(e) => setReservationToken(e.target.value)}
              placeholder="Type here"
              className="input input-bordered w-full max-w-xl"
            />
            <button
              onClick={() => handleConfirmButton()}
              className="btn btn-success text-green-200"
            >
              Confirm Details
            </button>
          </label>
        )}
      </div>
    </div>
  );
};

export default ReservationDetailsPage;
