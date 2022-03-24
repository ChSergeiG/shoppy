import React, {useContext} from "react";
import {debounce, TextField} from "@mui/material";
import {ApplicationContext} from "../../../applicationContext";

const SearchField: React.FC = () => {

    const context = useContext(ApplicationContext);

    const handleUpdate = (e: { target: { value: string } }) => {
        context.setAdminFilter?.(e.target.value);
    }

    return (
        <TextField
            placeholder="Search for:"
            style={{width: "50%"}}
            onChange={debounce(handleUpdate, 1000)}
        />
    );

};

export default SearchField;