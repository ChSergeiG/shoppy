import {PureComponent} from "react";

interface Props {
    path: string,
    setActiveLink: (path: string) => void
}

class CustomRoute extends PureComponent<Props> {

    render() {
        const {} = this.props;
        return null;
    }

}

export default CustomRoute;