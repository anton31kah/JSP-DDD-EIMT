export interface RideId {
    id: string;
}

interface Distance {
    meters: number;
}

interface Route {
    name: string;
    number: string;
    length: Distance;
}

interface Time {
    hours: number;
    minutes: number;
}

type DayOfWeek = "Monday" | "Tuesday" | "Wednesday" | "Thursday" | "Friday" | "Saturday" | "Sunday";

interface Day {
    day: DayOfWeek;
}

interface TimePoint {
    time: Time;
    day: Day;
}

interface MapPoint {
    latitude: number;
    longitude: number;
}

interface StopId {
    id: string;
}

export interface Stop {
    id: StopId;
    name: string;
    number: string;
    location: MapPoint;
}

export interface Ride {
    id: RideId;
    route: Route;
    occurrence: TimePoint;
    occurrenceStamp: string;
    stops: Stop[];
}
