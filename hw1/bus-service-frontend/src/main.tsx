import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { BusTripsContextProvider } from "./context/BusTripsContext.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import BrowseTripsPage from "./pages/BrowseTripsPage.tsx";
import ReserveTripPage from "./pages/ReserveTripPage.tsx";
import ReservationConfirmationPage from "./pages/ReservationConfirmationPage.tsx";
import ReservationDetailsPage from "./pages/ReservationDetailsPage.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <BrowseTripsPage />,
  },
  {
    path: "/reserve/:tripId",
    element: <ReserveTripPage />,
  },
  {
    path: "/confirmation",
    element: <ReservationConfirmationPage />,
  },
  {
    path: "/reservation_details",
    element: <ReservationDetailsPage />,
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <BusTripsContextProvider>
    <RouterProvider router={router} />
  </BusTripsContextProvider>
);
