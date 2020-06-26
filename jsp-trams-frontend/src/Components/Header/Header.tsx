import React from "react";
import './Header.css';
import { RouterPaths } from "../RoutedApp/RoutedApp";
import { Centered } from "../Util/Centered";

export enum HeaderTab {
    None = "None",
    Tickets = "Tickets"
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
        <header className="row">
            {tab(HeaderTab.None, RouterPaths.tickets)}
            {tab(HeaderTab.Tickets, RouterPaths.tickets)}
        </header>
    )
}
