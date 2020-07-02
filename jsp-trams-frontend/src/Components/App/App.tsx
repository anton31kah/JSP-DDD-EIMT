import React from 'react';
import './App.css';
import { Header, HeaderTab } from "Components/Header/Header";
import { Centered } from "Components/Util/Centered";
import { Link } from "react-router-dom";
import { RouterPaths } from "Components/RoutedApp/RoutedApp";

const App = () => {
    return (
        <div className="container-fluid">
            <Header selectedTab={HeaderTab.None}/>
            <Centered className="mt-5 mb-3">
                <h1>
                    Welcome to the Public Transport Enterprise of Skopje for Trams!
                </h1>
            </Centered>
            <Centered className="mt-3 mb-4">
                <div className="row">
                    <div className="col-6">
                        <img src="tram.jpg" width="100%" alt="A Tram!"/>
                    </div>
                    <div className="col-6">
                        <div className="d-flex flex-column justify-content-evenly h-100">
                            <h4>
                                JSP is now using the latest trams and this website is one of the services it offers.
                            </h4>
                            <h4>
                                As a passenger you can
                                <Link to={RouterPaths.ticketsMarket}> buy tickets </Link>
                                and
                                <Link to={RouterPaths.ticketsWallet}> browse them </Link>
                                in the wallet.
                            </h4>
                            <h4>
                                As a conductor you can
                                <Link to={RouterPaths.ridesChecking}> check tickets </Link>
                                on tram boarding.
                            </h4>
                        </div>
                    </div>
                </div>
            </Centered>
        </div>
    );
};

export default App;
