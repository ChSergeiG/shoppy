import type {IButtonBarItem} from "../src/components/ButtonBar";

export type IApplicationContext = {

    // button bar
    buttonBarItems?: IButtonBarItem[];
    setButtonBarItems?: (_: IButtonBarItem[]) => void;

};
