import { Ride, Stop } from "Models/Ride";
import moment from "moment";
import React from "react";

interface SingleRideProps {
    ride: Ride;
    mode: "nextHour" | "restDay" | "restWeek";
    timeMoment: moment.Moment;
    onCheckRideClicked?: (ride: Ride) => void;
}

const rideId = (ride: Ride) => {
    return [
        ride.id.id,
        ride.route.number,
        ride.occurrence.day.day,
        ride.occurrence.time.hours,
        ride.occurrence.time.minutes
    ].join("-");
}

export const SingleRide = (props: SingleRideProps) => {
    const setRideForChecking = (event: React.MouseEvent, ride: Ride) => {
        event.preventDefault();
        event.stopPropagation();

        props.onCheckRideClicked!(ride);
    }

    const getModeTime = () => {
        switch (props.mode) {
            case "nextHour":
                return (
                    <>
                        <h4>
                            In {moment(props.ride.occurrenceStamp).from(props.timeMoment, true)}
                        </h4>
                        <h5>
                            At exactly {moment(props.ride.occurrenceStamp).format("h:mm a")}
                        </h5>
                    </>
                )
            case "restDay":
                return (
                    <h4>
                        At {moment(props.ride.occurrenceStamp).format("h:mm a")}
                    </h4>
                )
            case "restWeek":
                return (
                    <h4>
                        On {moment(props.ride.occurrenceStamp).format("dddd")} at {moment(props.ride.occurrenceStamp).format("h:mm a")}
                    </h4>
                );
        }
    };

    const stopsToView = (stops: Stop[]) => {
        return stops.map(s => (
            <li>
                {s.name} ({s.number}) [
                <a href={`https://maps.google.com/maps?q=loc:${s.location.latitude},${s.location.longitude}`}
                   target="_blank">
                    google maps
                </a>
                ].
            </li>
        ));
    };

    return (
        <div key={rideId(props.ride)} className="m-1 p-3 d-flex flex-column align-items-center justify-content-center"
             style={{
                 width: "90vw",
                 border: "dashed 3px darkgray"
             }}>
            {getModeTime()}
            <h3>
                {props.ride.route.name} ({props.ride.route.number})
                <span> </span>
                <button className="btn btn-outline-dark btn-sm" type="button" data-toggle="collapse"
                        data-target={`#stops-${rideId(props.ride)}`} aria-expanded="false"
                        aria-controls={`stops-${rideId(props.ride)}`}>
                    Show Stops
                </button>
            </h3>
            <h5>
                With a length of {(props.ride.route.length.meters / 1000).toFixed(1)} KM
            </h5>
            <div className="collapse" id={`stops-${rideId(props.ride)}`}>
                <div className="card card-body d-flex flex-column">
                    <ol className="m-0">
                        {stopsToView(props.ride.stops)}
                    </ol>
                </div>
            </div>
            {props.mode === "nextHour" ?
                <button type="button" className="btn btn-outline-primary"
                        onClick={e => setRideForChecking(e, props.ride)}>
                    Check this ride
                </button> :
                undefined}
        </div>
    )
}
