import React from "react";
import {CircularProgress} from "@mui/material";

export const SpinnerOverlay: React.FC = () => {
    return (
        <div
            style={{
                width: "100%",
                height: "100%",
                position: "fixed",
                padding: 0,
                margin: 0,
                top: 0,
                left: 0,
                background: "rgba(255,255,255,0.7)"
            }}
        >
            <CircularProgress
                style={{
                    position: "absolute",
                    top: "50%",
                    left: "50%",
                    margin: "-20px 0 0 -20px"
                }}
            />
        </div>
    );
};

export const InlineSpinner: React.FC = () => {
    return (<CircularProgress/>);
}
