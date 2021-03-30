import React from 'react';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import UsersTable from "../admin/users/UsersTable";
import OrdersTable from "../admin/orders/OrdersTable";
import GoodsTable from "../admin/goods/GoodsTable";

interface TabPanelProps {
    children?: React.ReactNode;
    index: any;
    value: any;
}

function TabPanel(props: TabPanelProps) {
    const {children, value, index, ...other} = props;
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box p={3}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

function SimpleTabs() {
    const [value, setValue] = React.useState(0);

    return (
        <div>
            <Tabs
                value={value}
                onChange={(event: React.ChangeEvent<{}>, newValue: number) => setValue(newValue)}
                aria-label="simple tabs example"
            >
                <Tab label="Users" id="simple-tab-0"/>
                <Tab label="Goods" id="simple-tab-1"/>
                <Tab label="Orders" id="simple-tab-2"/>
            </Tabs>
            <TabPanel value={value} index={0}>
                <UsersTable/>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <GoodsTable/>
            </TabPanel>
            <TabPanel value={value} index={2}>
                <OrdersTable/>
            </TabPanel>
        </div>
    );
}

export default SimpleTabs;
