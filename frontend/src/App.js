import './App.css';
import React from "react";
import API from './utils/API'
import {MenuItem, Select, Table, TableBody, TableCell, TableHead, TableRow} from "@material-ui/core";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            userResponse: {},
            states: null
        };

    }

    render() {
        const {isLoading, userResponse, states} = this.state;
        let element = isLoading ?
            <div>LOADING</div> :
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Pass</TableCell>
                        <TableCell>Status</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {userResponse.data.map(row => (
                        <TableRow>
                            <TableCell>{row.id}</TableCell>
                            <TableCell>{row.name}</TableCell>
                            <TableCell>{row.password}</TableCell>
                            <TableCell>
                                <Select>
                                    {
                                        states.data.map(item => (
                                            <MenuItem
                                                value={item}
                                                selected={item === row.state}
                                            >
                                                {item}
                                            </MenuItem>
                                        ))
                                    }
                                </Select>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>;
        return element;
    }

    async componentDidMount() {
        let userResponse = await API.get("/admin/users/get_all");
        let states = await API.get("statuses");

        this.setState({
            ...this.state,
            isLoading: false,
            userResponse: userResponse,
            states: states
        });
    }
}


export default App;
