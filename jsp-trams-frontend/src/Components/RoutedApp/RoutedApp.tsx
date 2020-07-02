import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import App from "Components/App/App";
import { TicketsMarket } from "Components/Tickets/TicketsMarket";
import { TicketsWallet } from "Components/Tickets/TicketsWallet";
import { RidesChecking } from "Components/Rides/RidesChecking";

export const RouterPaths = {
    app: "/",
    ticketsMarket: "/tickets-market",
    ticketsWallet: "/tickets-wallet",
    ridesChecking: "/rides-checking"
}

export const RoutedApp = () => {
    return (
        <BrowserRouter>
            <Switch>
                <Route exact path={RouterPaths.ticketsMarket}>
                    <TicketsMarket/>
                </Route>
                <Route exact path={RouterPaths.ticketsWallet}>
                    <TicketsWallet/>
                </Route>
                <Route exact path={RouterPaths.ridesChecking}>
                    <RidesChecking/>
                </Route>
                <Route path={RouterPaths.app}>
                    <App/>
                </Route>
            </Switch>
        </BrowserRouter>
    )
}
