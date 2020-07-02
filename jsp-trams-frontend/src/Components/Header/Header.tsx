import React from "react";
import './Header.css';
import { RouterPaths } from "Components/RoutedApp/RoutedApp";
import { Centered } from "Components/Util/Centered";
import { Time, TimeProps } from "Components/Header/Time";

export enum HeaderTab {
    None = "Home Page",
    TicketsMarket = "Tickets Market",
    TicketsWallet = "Tickets Wallet",
    RidesChecking = "Rides Checking"
}

interface HeaderProps {
    selectedTab: HeaderTab;
}

export const Header = (props: HeaderProps & TimeProps) => {
    const tab = (tab: HeaderTab, path: string) => {
        return (
            <Centered className={`col tab ${props.selectedTab === tab ? "selected" : ""}`}>
                <a href={path}>
                    {tab}
                </a>
            </Centered>
        )
    }

    return (
        <div>
            <header className="row">
                {tab(HeaderTab.None, RouterPaths.app)}
                {tab(HeaderTab.TicketsMarket, RouterPaths.ticketsMarket)}
                {tab(HeaderTab.TicketsWallet, RouterPaths.ticketsWallet)}
                {tab(HeaderTab.RidesChecking, RouterPaths.ridesChecking)}
            </header>
            <Time onTimeChange={props.onTimeChange}/>
        </div>
    )
}
