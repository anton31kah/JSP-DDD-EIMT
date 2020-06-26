import React from 'react';
import './App.css';
import { Header, HeaderTab } from "../Header/Header";

const App = () => {
    return (
        <div className="container-fluid">
            <Header selectedTab={HeaderTab.None}/>
            <div className="row">
                <div className="col">
                    Please select a tab from above
                </div>
            </div>
        </div>
    );
};

export default App;
