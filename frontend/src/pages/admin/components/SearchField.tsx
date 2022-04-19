import React from "react";
import {debounce, TextField} from "@mui/material";
import {setAdminFilter} from "../../../store/AdminFilterStore";

const SearchField: React.FC<{ searchKey: string, }> = ({searchKey}) => {

    const handleUpdate = (e: { target: { value: string } }) => {
        console.log(searchKey)
        searchKey && setAdminFilter({key: searchKey, value: e.target.value});
    }

    return (
        <TextField
            placeholder="Search for:"
            style={{width: "50%"}}
            defaultValue=""
            onChange={debounce(handleUpdate, 1000)}
        />
    );
};

export default SearchField;