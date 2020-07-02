import { RideId } from "Models/Ride";
import { Conductor } from "Models/Conductor";

interface CompletedRideId {
    id: string;
}

export interface CompletedRide {
    id: CompletedRideId;
    rideId: RideId;
    completedOn: string;
    conductor: Conductor;
}
