import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { BusTripsContextProvider } from "./context/BusTripsContext.tsx";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <BusTripsContextProvider>
      <App />
    </BusTripsContextProvider>
  </React.StrictMode>
);
