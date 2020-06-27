import React from "react";
import './Header.css';
import { RouterPaths } from "../RoutedApp/RoutedApp";
import { Centered } from "../Util/Centered";
import { Time } from "./Time";

export enum HeaderTab {
    None = "None",
    TicketsMarket = "TicketsMarket",
    TicketsWallet = "TicketsWallet"
}

interface HeaderProps {
    selectedTab: HeaderTab;
}

export const Header = (props: HeaderProps) => {
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
                {tab(HeaderTab.None, RouterPaths.ticketsMarket)}
                {tab(HeaderTab.TicketsMarket, RouterPaths.ticketsMarket)}
                {tab(HeaderTab.TicketsWallet, RouterPaths.ticketsWallet)}
            </header>
            <Time/>
        </div>
    )
}
