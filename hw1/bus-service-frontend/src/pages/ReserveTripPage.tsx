import { useParams } from "react-router-dom";
import Navbar from "../components/Navbar";
import ReserveTripContent from "../components/ReserveTripContent";

const ReserveTripPage = () => {
  const { tripId } = useParams();

  return (
    <div className="flex flex-col">
      <Navbar />
      <div className="ml-52 mr-52 mt-10 mb-10">
        <ReserveTripContent tripId={tripId!} />
      </div>
    </div>
  );
};

export default ReserveTripPage;
