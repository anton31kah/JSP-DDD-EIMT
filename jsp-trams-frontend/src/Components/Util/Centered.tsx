import React from "react";

export const Centered = (props: React.PropsWithChildren<any>) => (
    <div className={`${props.className} d-flex align-items-center justify-content-center`}>
        <span>
            {props.children}
        </span>
    </div>
)
