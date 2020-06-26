import { BrowserRouter, Route, Switch } from "react-router-dom";
import { TicketsMarket } from "../Tickets/TicketsMarket";
import App from "../App/App";
import React from "react";

export const RouterPaths = {
    app: "/",
    tickets: "/tickets-market"
}

export const RoutedApp = () => {
    return (
        <BrowserRouter>
            <Switch>
                <Route exact path={RouterPaths.tickets}>
                    <TicketsMarket/>
                </Route>
                <Route path={RouterPaths.app}>
                    <App/>
                </Route>
            </Switch>
        </BrowserRouter>
    )
}
