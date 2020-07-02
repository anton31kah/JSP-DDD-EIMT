import React from "react";
import "react-datepicker/dist/react-datepicker.css";
import { Centered } from "Components/Util/Centered";
import ReactDatePicker from "react-datepicker";
import { TimeApi } from "Services/TimeApi";
import moment from "moment";

export interface TimeProps {
    onTimeChange?: (time: string) => void;
}

export const Time = (props: TimeProps) => {
    const [backendTime, setBackendTime] = React.useState<string>("");
    const [frontendTime, setFrontendTime] = React.useState<Date | null>(new Date());

    const fetchTime = async () => {
        let response = await TimeApi.getTime();
        setBackendTime(response.data);
        setFrontendTime(new Date(response.data));
    };

    React.useEffect(() => {
        fetchTime();
    }, []);

    React.useEffect(() => {
        props.onTimeChange?.(backendTime);
    }, [backendTime, props.onTimeChange]);

    const setTimeClick = async (event: React.MouseEvent) => {
        event.preventDefault();
        event.stopPropagation();

        let response = await TimeApi.setTime(moment(frontendTime).format());

        setBackendTime(response.data.after);
        setFrontendTime(new Date(response.data.after));
    };

    const resetTimeClick = async (event: React.MouseEvent) => {
        event.preventDefault();
        event.stopPropagation();

        let response = await TimeApi.resetTime();

        setBackendTime(response.data.after);
        setFrontendTime(new Date(response.data.after));
    };

    const refreshTime = async (event: React.MouseEvent) => {
        event.preventDefault();
        event.stopPropagation();

        await fetchTime();
    }

    return (
        <Centered className="mt-1" style={{
            border: "lightgray solid 2px"
        }}>
            <div className="d-flex justify-content-center m-2">
                <div className="mr-1">
                    <p className="link-primary m-0" onClick={refreshTime} title="Click to refresh time.">
                        {moment(backendTime).format("MMMM Do YYYY, h:mm:ss a")}
                    </p>
                </div>
                <div className="mx-1">
                    <ReactDatePicker
                        selected={frontendTime}
                        todayButton="Today"
                        onChange={date => setFrontendTime(date)}
                        showTimeInput={true}
                        timeFormat="h:mm aa"
                        timeIntervals={1}
                        timeCaption="time"
                        dateFormat="MMMM d, yyyy h:mm aa"
                    />
                </div>
                <div className="mx-1">
                    <button type="button" className="btn btn-primary btn-sm" onClick={setTimeClick}>Set Time</button>
                </div>
                <div className="ml-1">
                    <button type="button" className="btn btn-warning btn-sm" onClick={resetTimeClick}>
                        Reset Time <b>to actual current time</b>
                    </button>
                </div>
            </div>
        </Centered>
    );
}
