import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import App from "../App/App";
import { TicketsMarket } from "../Tickets/TicketsMarket";
import { TicketsWallet } from "../Tickets/TicketsWallet";

export const RouterPaths = {
    app: "/",
    ticketsMarket: "/tickets-market",
    ticketsWallet: "/tickets-wallet"
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
                <Route path={RouterPaths.app}>
                    <App/>
                </Route>
            </Switch>
        </BrowserRouter>
    )
}
