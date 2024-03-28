export interface ICity {
  id: number;
  name: string;
}

export interface IRouteStop {
  id: number;
  location: ICity;
  stopNumber: number;
  distanceKmFromLastStop: number;
}

export interface IRoute {
  id: number;
  totalDistanceKm: number;
  routeStops: IRouteStop[];
}

export interface ITrip {
  id: number;
  route: IRoute;
  departureTime: Date;
  numberOfSeats: number;
  priceEuro: number;
}
