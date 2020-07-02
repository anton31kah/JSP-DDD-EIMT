import React from "react";
import "bootstrap/js/src/collapse.js";
import { Header, HeaderTab } from "Components/Header/Header";
import { Ride } from "Models/Ride";
import { RidesApi } from "Services/RidesApi";
import moment from "moment";
import { SingleRide } from "./SingleRide";
import { RideCheckingForm } from "./RideCheckingForm";

export const RidesChecking = (props: any) => {
    const [time, setTime] = React.useState<string>("");

    const [rides, setRides] = React.useState<Ride[]>([]);
    const [rideToCheck, setRideToCheck] = React.useState<Ride | undefined>();

    React.useEffect(() => {
        const fetchRides = async () => {
            let response = await RidesApi.getRides();
            setRides(response.data);
        };

        fetchRides();
    }, [time]);

    const filterNextHour = () => {
        let timeMoment = moment.utc(time);

        let filteredRides = rides.filter(r =>
            moment.utc(r.occurrenceStamp).diff(timeMoment, "minutes") < 60
        );

        return filteredRides.map(r => (
            <SingleRide ride={r} mode="nextHour" timeMoment={timeMoment} onCheckRideClicked={setRideToCheck}/>
        ));
    };

    const filterRestOfDay = () => {
        let timeMoment = moment.utc(time);
        let currentDayOfWeek = timeMoment.format("dddd");

        const filteredRides = rides.filter(r =>
            moment.utc(r.occurrenceStamp).diff(timeMoment, "minutes") >= 60
            && r.occurrence.day.day === currentDayOfWeek
        );

        return filteredRides.map(r => (
            <SingleRide ride={r} mode="restDay" timeMoment={timeMoment}/>
        ));
    };

    const filterRestOfWeek = () => {
        let timeMoment = moment.utc(time);
        let currentDayOfWeek = timeMoment.format("dddd");

        const filteredRides = rides.filter(r => r.occurrence.day.day !== currentDayOfWeek);

        return filteredRides.map(r => (
            <SingleRide ride={r} mode="restWeek" timeMoment={timeMoment}/>
        ));
    };

    const ridesInNextHour = filterNextHour();
    const ridesInRestOfDay = filterRestOfDay();
    const ridesInRestOfWeek = filterRestOfWeek();

    return (
        <div className="container-fluid">
            <Header selectedTab={HeaderTab.RidesChecking} onTimeChange={setTime}/>

            <RideCheckingForm selectedRide={rideToCheck} time={time} cancel={() => setRideToCheck(undefined)}/>

            <h1 className="mt-5">
                Today:
            </h1>
            <h2>
                In the next 1 hour:
            </h2>
            <div className="d-flex flex-wrap justify-content-around">
                {ridesInNextHour}
            </div>

            <h2>
                Rest of the day:
            </h2>
            <div className="d-flex flex-wrap justify-content-around">
                {ridesInRestOfDay}
            </div>

            <h1 className="mt-5">
                In the following days:
            </h1>
            <div className="d-flex flex-wrap justify-content-around">
                {ridesInRestOfWeek}
            </div>
        </div>
    )
}
