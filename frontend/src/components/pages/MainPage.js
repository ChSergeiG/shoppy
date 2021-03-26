import React, {Component} from "react";


class MainPage extends Component {

    render() {
        const {isLoading} = this.props;
        return (
            isLoading ?
                <div>LOADING</div> :
                <div>Main page</div>
        );
    }

}

export default MainPage;
