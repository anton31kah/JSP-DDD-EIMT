import React from "react";
import { Conductor } from "Models/Conductor";
import { Ticket } from "Models/Ticket";
import { Ride } from "Models/Ride";
import { CompletedRide } from "Models/CompletedRide";
import { isJVMException, JVMException } from "Models/JVMException";
import { RidesApi } from "Services/RidesApi";
import { TicketsApi } from "Services/TicketsApi";
import { Centered } from "Components/Util/Centered";
import moment from "moment";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import { AllHtmlEntities } from "html-entities";

interface RideCheckingSafeFormProps {
    selectedRide: Ride;
    time: string;
    cancel: () => void;
}

const bulletEntity = AllHtmlEntities.decode("&bull;");
const plusEntity = AllHtmlEntities.decode("&plus;");

const RideCheckingSafeForm = (props: RideCheckingSafeFormProps) => {
    const [conductors, setConductors] = React.useState<Conductor[]>([]);
    const [selectedConductor, setSelectedConductor] = React.useState<Conductor | undefined>(undefined);

    const [ticketOwnerName, setTicketOwnerName] = React.useState<string>("");
    const [tickets, setTickets] = React.useState<Ticket[]>([]);
    const [selectedTickets, setSelectedTickets] = React.useState<Ticket[]>([]);
    const [selectedOwnersTicket, setSelectedOwnersTicket] = React.useState<Ticket | undefined>(undefined);

    const [completedRide, setCompletedRide] = React.useState<CompletedRide | JVMException | undefined>(undefined);

    React.useEffect(() => {
        const fetchConductors = async () => {
            let response = await RidesApi.getConductors();
            setConductors(response.data);
        };

        setSelectedConductor(undefined);

        setTicketOwnerName("");
        setTickets([]);
        setSelectedTickets([]);
        setSelectedOwnersTicket(undefined);

        setCompletedRide(undefined);

        fetchConductors();
    }, [props.selectedRide.id]);

    const selectConductorOption = (event: React.ChangeEvent<HTMLSelectElement>) => {
        let selected = conductors.find(c => c.id.id === event.target.value);
        setSelectedConductor(selected);
    };

    const conductorsDropDownItems = () => {
        let conductorsOptions = conductors.map(c => (
            <option value={c.id.id} key={c.id.id}
                    title={`Employee since ${moment.utc(c.employeeSince).from(moment.utc(props.time))}`}
                    selected={selectedConductor !== undefined && selectedConductor.id.id === c.id.id}>
                {c.name}
            </option>
        ));

        return [
            <option value={-1} key={-1}
                    title="Select a conductor"
                    selected={selectedConductor === undefined}>
                Select a conductor
            </option>,

            ...conductorsOptions
        ]
    };

    const cancelChecking = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        event.stopPropagation();

        confirmAlert({
            title: "Confirm checking cancel",
            message: "Are you sure you want to cancel this ride checking?",
            buttons: [
                {
                    label: "Yes, cancel it",
                    onClick: props.cancel
                },
                {
                    label: "No, leave it",
                    onClick: () => {
                    }
                }
            ]
        });
    };

    const removeSelectedTicket = (event: React.MouseEvent<HTMLButtonElement>, ticketId: string) => {
        event.preventDefault();
        event.stopPropagation();

        setSelectedTickets(selectedTickets.filter(t => t.id.id !== ticketId));
    };

    const selectedTicketsView = () => {
        return selectedTickets.map(t => (
            <li key={t.id.id} title={`Bought on ${moment.utc(t.boughtOn).format()}`}>
                {t.customer.name}
                <span> {bulletEntity} </span>
                {t.id.id}
                <span> {bulletEntity} </span>
                {t.usages.left}/{t.usages.initial}
                <span> {bulletEntity} </span>
                {moment(t.expiryDate).format("MMMM Do YYYY")}
                <span> {bulletEntity} {bulletEntity} {bulletEntity} </span>
                <button type="button" className="close" onClick={e => removeSelectedTicket(e, t.id.id)}
                        title="Remove this ticket.">
                    <span aria-hidden="true">&times;</span>
                </button>
            </li>
        ))
    };

    const updateTicketOwnerName = (event: React.ChangeEvent<HTMLInputElement>) => {
        setTickets([]);
        setSelectedOwnersTicket(undefined);
        setTicketOwnerName(event.target.value);
    };

    const listOwnersTickets = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        event.stopPropagation();

        setTickets([]);
        setSelectedOwnersTicket(undefined);
        let response = await TicketsApi.getInfo(ticketOwnerName);
        setTickets(response.data);
    };

    const selectTicketOption = (event: React.ChangeEvent<HTMLSelectElement>) => {
        let selected = tickets.find(t => t.id.id === event.target.value);
        setSelectedOwnersTicket(selected);
    };

    const ticketsDropDownItems = () => {
        const ticketsComparer = (t1: Ticket, t2: Ticket): number => {
            let momentT1 = moment(t1.expiryDate);
            let momentT2 = moment(t2.expiryDate);
            if (momentT1.isBefore(momentT2)) {
                return -1;
            } else if (momentT1.isAfter(momentT2)) {
                return 1;
            } else {
                return t1.usages.left - t2.usages.left;
            }
        }

        let sortedTickets = [...tickets].sort(ticketsComparer);
        let ticketsOptions = sortedTickets.map(t => (
            <option value={t.id.id} key={t.id.id}
                    title={`Bought on ${moment.utc(t.boughtOn).format()}`}
                    selected={selectedOwnersTicket !== undefined && selectedOwnersTicket.id.id === t.id.id}>
                {t.id.id} {bulletEntity} {t.usages.left}/{t.usages.initial} {bulletEntity} {moment(t.expiryDate).format("MMMM Do YYYY")}
            </option>
        ));

        return [
            <option value={-1} key={-1}
                    title="Select a ticket"
                    selected={selectedOwnersTicket === undefined}>
                Select a ticket
            </option>,

            ...ticketsOptions
        ]
    };

    const ticketAdd = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        event.stopPropagation();

        if (selectedOwnersTicket !== undefined) {
            setSelectedTickets([...selectedTickets, selectedOwnersTicket]);

            setTicketOwnerName("");
            setTickets([]);
            setSelectedOwnersTicket(undefined);
        }
    }

    const submitChecking = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        event.stopPropagation();

        if (selectedConductor !== undefined && selectedTickets.length > 0) {
            try {
                let response = await RidesApi.checkRide({
                    conductorId: selectedConductor.id.id,
                    rideId: props.selectedRide.id.id,
                    ticketsIds: selectedTickets.map(t => t.id.id)
                });
                setCompletedRide(response.data);
            } catch (e) {
                setCompletedRide(e as JVMException);
            }
        }
    }

    const completedRideView = () => {
        if (completedRide === undefined) {
            return undefined;
        } else if (isJVMException(completedRide)) {
            return <div>Error {completedRide.message}</div>;
        } else {
            return <div>Checked {moment.utc(completedRide.completedOn).from(props.time)}</div>
        }
    };

    return (
        <Centered>
            <h3>
                Ride Checking Device 2000
            </h3>
            <h5>
                Checking Ride {props.selectedRide.route.name} ({props.selectedRide.route.number})
                <button type="button" className="close" onClick={cancelChecking}
                        title="Cancel checking this ride.">
                    <span aria-hidden="true">&times;</span>
                </button>
            </h5>
            <select className="form-select" onChange={selectConductorOption}>
                {conductorsDropDownItems()}
            </select>
            <div>
                <h4>
                    Added Tickets
                </h4>
                <ul>
                    {selectedTicketsView()}
                </ul>
                <h3>
                    Add a new ticket
                </h3>
                <div className="input-group">
                    <span className="input-group-text">Name & Ticket</span>
                    <input type="text" className="form-control" placeholder="Name"
                           value={ticketOwnerName} onChange={updateTicketOwnerName}/>
                    <button className="btn btn-outline-secondary" type="button"
                            disabled={ticketOwnerName.length === 0} onClick={listOwnersTickets}>
                        List Tickets
                    </button>
                    <select className="form-control form-select" onChange={selectTicketOption}>
                        {ticketsDropDownItems()}
                    </select>
                    <button className="btn btn-outline-secondary" type="button"
                            disabled={selectedOwnersTicket === undefined} onClick={ticketAdd}>
                        {plusEntity}
                    </button>
                </div>
            </div>
            <button className="btn btn-primary btn-lg" type="button"
                    disabled={selectedConductor === undefined || selectedTickets.length === 0 || completedRide !== undefined}
                    onClick={submitChecking}>
                Check Ride
            </button>
            <span> {completedRideView()}</span>
        </Centered>
    )
}

interface RideCheckingFormProps {
    selectedRide?: Ride;
    time: string;
    cancel: () => void;
}

export const RideCheckingForm = (props: RideCheckingFormProps) => {
    if (props.selectedRide !== undefined) {
        return <RideCheckingSafeForm selectedRide={props.selectedRide}
                                     time={props.time}
                                     cancel={props.cancel}/>
    } else {
        return (
            <Centered>
                <p>You can select a ride to check here!</p>
            </Centered>
        )
    }
}
