import TripsContent from "../components/TripsContent";
import Navbar from "../components/Navbar";

function BrowseTripsPage() {
  return (
    <div className="flex flex-col">
      <Navbar />
      <div className="ml-52 mr-52 mt-10 mb-10">
        <TripsContent />
      </div>
    </div>
  );
}

export default BrowseTripsPage;
