import { useLocation } from "react-router-dom";
import Navbar from "../components/Navbar";
import { IReservation } from "../types/BusTripsTypes";
import ReservationDetails from "../components/ReservationDetails";

const ReservationConfirmationPage = () => {
  const location = useLocation();

  const reservationData: IReservation = location.state;

  return (
    <div className="flex flex-col">
      <Navbar />
      <div className="ml-52 mr-52 mt-10 mb-10">
        <h1 className="text-4xl font-bold italic">
          Thank you for using our services!
        </h1>
        <h3 className="pt-4 pb-10">
          Keep your{" "}
          <span className="font-extrabold text-green-800">
            reservation token
          </span>{" "}
          safe! You can use it to get informations about this reservation, such
          as the trip and your seat number!
        </h3>
        <ReservationDetails reservation={reservationData} />
      </div>
      <div></div>
    </div>
  );
};

export default ReservationConfirmationPage;
