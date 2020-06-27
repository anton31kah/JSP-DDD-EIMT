import React from "react";
import { Header, HeaderTab } from "../Header/Header";
import { Centered } from "../Util/Centered";
import { TicketPlan } from "../../Models/TicketPlan";
import { TicketsApi } from "../../Service/TicketsApi";
import { Link } from "react-router-dom";
import { RouterPaths } from "../RoutedApp/RoutedApp";

export const TicketsMarket = (props: any) => {
    const [plans, setPlans] = React.useState<TicketPlan[]>([]);

    const [purchasedPlans, setPurchasedPlans] = React.useState<TicketPlan[]>([]);
    const [customerName, setCustomerName] = React.useState<string>("");

    const [isPurchaseSuccessful, setIsPurchaseSuccessful] = React.useState<boolean | undefined>(undefined);

    React.useEffect(() => {
        const fetchPlans = async () => {
            const response = await TicketsApi.getPlans()
            setPlans(response.data);
        };

        fetchPlans();
    }, []);

    const periodToHuman = (period: string) => {
        let weeksRegex = /P(\d+)D/;
        let monthsRegex = /P(\d+)M/;

        if (weeksRegex.test(period)) {
            const weeks = parseInt(weeksRegex.exec(period)![1]) / 7;
            return `${weeks} Week(s)`;
        }

        if (monthsRegex.test(period)) {
            const months = parseInt(monthsRegex.exec(period)![1]);
            return `${months} Month(s)`;
        }

        return "Error reading validity period!";
    }

    const addPlanToCart = (event: React.ChangeEvent<HTMLInputElement>, plan: TicketPlan) => {
        if (event.target.checked) {
            setPurchasedPlans([...purchasedPlans, plan]);
        } else {
            setPurchasedPlans(purchasedPlans.filter(p => p.usages !== plan.usages));
        }
    };

    const ticketPlanView = (plan: TicketPlan) => {
        return (
            <div key={plan.usages} className="d-flex flex-column align-items-center justify-content-center" style={{
                width: "20vw",
                border: "dashed 3px darkgray"
            }}>
                <h5><b>{plan.usages} Rides</b></h5>
                <p>over</p>
                <b><p>{periodToHuman(plan.validity)}</p></b>
                <p>for only</p>
                <b><p>{plan.price.amount} {plan.price.currency}</p></b>

                <p className="my-3">
                    <div className="input-group">
                        <div className="input-group-text">
                            <label htmlFor={`purchase-${plan.usages}`}>
                                Get One!
                                <span> </span>
                                <input id={`purchase-${plan.usages}`} onChange={event => addPlanToCart(event, plan)}
                                       className="form-check-input" type="checkbox" value=""/>
                            </label>
                        </div>
                    </div>
                </p>
            </div>
        )
    }

    const updateName = (event: React.ChangeEvent<HTMLInputElement>) => {
        setCustomerName(event.target.value);
    };

    const purchasePlans = async (event: React.MouseEvent) => {
        event.preventDefault();
        event.stopPropagation();

        setIsPurchaseSuccessful(undefined);

        await TicketsApi.purchasePlans(customerName, purchasedPlans);

        setIsPurchaseSuccessful(true);
    }

    const nameAndPurchaseForm = () => {
        let isDisabled = purchasedPlans.length === 0 || customerName.length === 0;
        return (
            <div className="input-group mb-3">
                <span className="input-group-text">Your Name</span>
                <input className="form-control" type="text" placeholder="Name" onChange={updateName}/>
                <button className="btn btn-primary" disabled={isDisabled} onClick={purchasePlans}>Purchase</button>
            </div>
        );
    };

    const purchaseResultAlert = () => {
        if (isPurchaseSuccessful !== undefined) {
            if (isPurchaseSuccessful) {
                return (
                    <div className="alert alert-success">
                        Successful purchase, visit
                        <span> </span>
                        <Link to={`${RouterPaths.ticketsWallet}?customerName=${customerName}`}>
                            your wallet
                        </Link>
                        <span> </span>
                        to check your tickets.
                    </div>
                );
            } else {
                return (
                    <div className="alert alert-danger">
                        Sorry! Your purchase was unsuccessful.
                    </div>
                );
            }
        }
    }

    return (
        <div className="container-fluid">
            <Header selectedTab={HeaderTab.TicketsMarket}/>
            <Centered className="my-4">
                <h2>Select a ticket plan to purchase</h2>
            </Centered>
            <div className="d-flex justify-content-around">
                {plans.map(ticketPlanView)}
            </div>
            <Centered className="my-4">
                {nameAndPurchaseForm()}
            </Centered>
            <Centered>
                {purchaseResultAlert()}
            </Centered>
        </div>
    );
}
