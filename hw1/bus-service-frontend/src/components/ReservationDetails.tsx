import { IReservation } from "../types/BusTripsTypes";

interface ReservationDetailsProps {
  reservation: IReservation;
}

const ReservationDetails: React.FC<ReservationDetailsProps> = (
  props: ReservationDetailsProps
) => {
  const reservation = props.reservation;
  console.log(props.reservation);
  return (
    <div>
      <p className="text-xl pt-4 pb-2">
        <span className="font-bold">Reservation Token:</span> {reservation.id}
      </p>
      <p className="text-xl pb-2">
        <span className="font-bold">Client Name:</span> {reservation.clientName}
      </p>
      <p className="text-xl pb-2">
        <span className="font-bold">Seat Number:</span> {reservation.seatNumber}
      </p>
      <p className="text-xl pb-2">
        <span className="font-bold">Trip leaves at:</span>{" "}
        {new Date(reservation.trip?.departureTime).toLocaleString()}
      </p>
    </div>
  );
};

export default ReservationDetails;
