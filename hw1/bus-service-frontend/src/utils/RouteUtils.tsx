import { IRoute } from "../types/BusTripsTypes";

export const getRouteStopsString = (route: IRoute | undefined): string => {
  if (!route) {
    return "";
  }

  let retString = "";
  for (let i = 0; i < route.routeStops.length - 1; i++) {
    retString = retString + `${route.routeStops[i].location.name} -> `;
  }
  retString =
    retString +
    `${route.routeStops[route.routeStops.length - 1].location.name}`;

  return retString;
};
