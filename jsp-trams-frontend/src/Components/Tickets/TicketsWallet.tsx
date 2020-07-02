import React from "react";
import { Header, HeaderTab } from "Components/Header/Header";
import { Centered } from "Components/Util/Centered";
import { TicketsApi } from "Services/TicketsApi";
import { useLocation } from "react-router-dom";
import { Ticket } from "Models/Ticket";
import moment from "moment";

export const TicketsWallet = (props: any) => {
    const query = new URLSearchParams(useLocation().search)

    const [customerName, setCustomerName] = React.useState<string>(query.get("customerName") ?? "");
    const [tickets, setTickets] = React.useState<Ticket[]>([]);

    React.useEffect(() => {
        const fetchTickets = async () => {
            if (customerName.length !== 0) {
                let response = await TicketsApi.getInfo(customerName);
                setTickets(response.data);
            }
        };

        fetchTickets();
    }, [customerName]);

    const ticketView = (ticket: Ticket) => {
        return (
            <div key={ticket.id.id} className="m-1 d-flex flex-column align-items-center justify-content-center"
                 style={{
                     width: "20vw",
                     border: "dashed 3px darkgray"
                 }}>
                <h4><b>Owner: {ticket.customer.name}</b></h4>
                <h5><b>{ticket.usages.left} Remaining Rides</b></h5>
                <p>out of {ticket.usages.initial}</p>
                <p>to use until</p>
                <b><p>{moment(ticket.expiryDate).format("MMMM Do, YYYY")}</p></b>
                <p>bought on {moment.utc(ticket.boughtOn).format("MMMM Do YYYY, h:mm:ss a")}</p>
                <p>which was {moment.utc(ticket.boughtOn).fromNow()}</p>
            </div>
        )
    }

    const updateName = (event: React.ChangeEvent<HTMLInputElement>) => {
        setCustomerName(event.target.value);
    };

    const nameAndSearchForm = () => {
        return (
            <div className="input-group mb-3">
                <span className="input-group-text">Your Name</span>
                <input className="form-control" type="text" placeholder="Name" value={customerName}
                       onChange={updateName}/>
            </div>
        );
    };

    return (
        <div className="container-fluid">
            <Header selectedTab={HeaderTab.TicketsWallet}/>
            <Centered className="my-4">
                <h2>Enter the name on your ticket</h2>
            </Centered>
            <Centered className="my-4">
                {nameAndSearchForm()}
            </Centered>
            <div className="d-flex flex-wrap justify-content-around">
                {tickets.map(ticketView)}
            </div>
        </div>
    );
}
