import type {IButtonBarItem} from "../src/components/ButtonBar";

export type IApplicationContext = {

    // admin filtering
    adminFilter?: string;
    setAdminFilter?: (filter: string) => void;

    // button bar
    buttonBarItems?: IButtonBarItem[];
    setButtonBarItems?: (_: IButtonBarItem[]) => void;

};
