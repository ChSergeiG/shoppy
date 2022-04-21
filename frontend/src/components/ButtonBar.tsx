import React, {PropsWithChildren, useState} from "react";
import {
    AppBarProps,
    Badge,
    Box,
    Button,
    CssBaseline,
    CSSObject,
    DialogActions,
    DialogContent,
    DialogTitle,
    Divider,
    Drawer,
    IconButton,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    styled,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Theme,
    Toolbar,
    Typography,
    useTheme
} from "@mui/material";
import MuiAppBar from '@mui/material/AppBar';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import type {LinkProps} from "react-router-dom";
import {Link} from "react-router-dom";
import {ShoppingBag} from "@mui/icons-material";
import {getCreatedOrderInfo, getGoodsByIds, logoutRequest, postOrder} from "../utils/API";
import {removeAllFromBasket, selectedGoods} from "../store/UserBucketStore";
import {logout} from "../store/UserAuthorizationStore";
import {placeSnackBarAlert} from "../store/SnackBarStore";
import {useStore} from "effector-react";
import {buttonBarStore} from "../store/ButtonBarStore";
import {closeAlert, setAlertContent, showAlert} from "../store/AlertStore";
import {InlineSpinner} from "./Spinner";

type ButtonBarState = {
    open: boolean;

    orderInfoUrl: string;
    showOrderInfo: boolean;
};

type ButtonBarProps = AppBarProps & {
    open?: boolean;
};

export type IButtonBarItem = {
    routerLinkProps: LinkProps & React.RefAttributes<HTMLAnchorElement>;
    adminButton: boolean;
    isLogout?: boolean | undefined;
    index: number;
    text: string;
    icon: JSX.Element;
};

const drawerWidth = 240;

const openedMixin = (theme: Theme): CSSObject => ({
    width: drawerWidth,
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
});

const closedMixin = (theme: Theme): CSSObject => ({
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: `calc(${theme.spacing(7)} + 1px)`,
    [theme.breakpoints.up('sm')]: {
        width: `calc(${theme.spacing(8)} + 1px)`,
    },
});

const DrawerHeader = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})<ButtonBarProps>(({theme, open}) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const ButtonBarDrawer = styled(Drawer, {shouldForwardProp: (prop) => prop !== 'open'})(
    ({theme, open}) => ({
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap',
        boxSizing: 'border-box',
        ...(open && {
            ...openedMixin(theme),
            '& .MuiDrawer-paper': openedMixin(theme),
        }),
        ...(!open && {
            ...closedMixin(theme),
            '& .MuiDrawer-paper': closedMixin(theme),
        }),
    }),
);

const ButtonBar: React.FC<PropsWithChildren<{}>> = (props) => {

    const goodsStore = useStore(selectedGoods);

    const buttonsStore = useStore(buttonBarStore);

    const [state, setState] = useState<ButtonBarState>({
        open: false,
        orderInfoUrl: "",
        showOrderInfo: false,
    });

    const theme = useTheme();

    const handlePlaceOrder = (e: any) => {
        if (goodsStore.length === 0) {
            return;
        }
        postOrder(goodsStore)
            .then((or) => {
                placeSnackBarAlert({
                    message: or.data,
                    color: "success"
                });
                removeAllFromBasket();
                const orderLocation = or?.headers?.location;
                if (orderLocation) {
                    showAlert()
                    setAlertContent(<InlineSpinner/>)
                    getCreatedOrderInfo(orderLocation)
                        .then((cor) => {
                            console.log(cor)
                            const order = cor.data
                            const goodIds: number[] = order.goods.map(g => g.good.id)
                                .filter(id => id !== undefined) as number[]
                            getGoodsByIds(goodIds)
                                .then((gbi) => {

                                    const total = order.goods.map((g) => g.count * g.good.price)
                                        .reduce((a, b) => a + b)

                                    setAlertContent(
                                        <form>
                                            <DialogTitle>
                                                Order successfully created
                                            </DialogTitle>
                                            <DialogContent>
                                                <Typography variant="body1">{`Number: ${order.id}`}</Typography>
                                                <Typography variant="body1">{`Info: ${order.info}`}</Typography>
                                                <hr/>
                                                <Typography variant="overline">{order.guid}</Typography>
                                                <hr/>
                                                <Table>
                                                    <TableHead>
                                                        <TableRow key="header-row">
                                                            <TableCell key="article-header-cell">Article</TableCell>
                                                            <TableCell key="info-header-cell">Info</TableCell>
                                                            <TableCell key="count-header-cell">Count</TableCell>
                                                        </TableRow>
                                                    </TableHead>
                                                    <TableBody>

                                                        {order.goods.map((g) => {
                                                            return (
                                                                <TableRow>
                                                                    <TableCell
                                                                        key={`article-cell-${g.good.id}`}
                                                                    >
                                                                        <Typography
                                                                            variant="overline"
                                                                        >
                                                                            {g.good.article}
                                                                        </Typography>
                                                                    </TableCell>
                                                                    <TableCell
                                                                        key={`name-cell-${g.good.id}`}
                                                                    >
                                                                        {g.good.name}
                                                                    </TableCell>
                                                                    <TableCell
                                                                        key={`count-cell-${g.good.id}`}
                                                                    >
                                                                        {g.count}
                                                                    </TableCell>
                                                                </TableRow>
                                                            );
                                                        })}
                                                    </TableBody>
                                                </Table>
                                                <hr/>
                                                <Typography variant="body1">{`Total: ${total} RUB`}</Typography>
                                            </DialogContent>
                                            <DialogActions>
                                                <Button
                                                    onClick={(e) => closeAlert()}
                                                >
                                                    Close
                                                </Button>
                                            </DialogActions>
                                        </form>
                                    );
                                });
                        })
                }
            })
            .catch((r) => {
                placeSnackBarAlert({
                    message: r.response.data,
                    color: r.response.status === 401 ? "warning" : "error"
                });
            })
    };

    const handleDrawerOpen = () => {
        setState(prevState => {
            return {...prevState, open: true};
        });
    };

    const handleDrawerClose = () => {
        setState(prevState => {
            return {...prevState, open: false};
        });
    };

    const doLogout = (e: any) => {
        handleDrawerClose();
        logout();
        logoutRequest().then(_ => {
        });
    };

    return (
        <Box
            sx={{display: 'flex'}}
        >
            <CssBaseline/>
            <AppBar
                position="fixed"
                open={state.open}
            >
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        sx={{
                            marginRight: 5,
                            ...(state.open && {display: 'none'}),
                        }}
                    >
                        <MenuIcon/>
                    </IconButton>
                    <Typography
                        variant="h6"
                        noWrap
                        component="div"
                    >
                        Well well well //
                    </Typography>
                    <Box sx={{
                        flexGrow: 1
                    }}/>
                    <Box
                        sx={{
                            display: "flex"
                        }}
                    >
                        <Badge
                            sx={{
                                top: "5px",
                                left: "35px",
                            }}
                            color="secondary"
                            badgeContent={goodsStore.length}
                        />
                        <IconButton
                            color="inherit"
                            onClick={handlePlaceOrder}
                            sx={{
                                ...(state.open && {display: 'none'}),
                            }}
                            disabled={goodsStore.length === 0}
                        >
                            <ShoppingBag/>
                        </IconButton>
                    </Box>
                </Toolbar>
            </AppBar>
            <ButtonBarDrawer
                variant="permanent"
                open={state.open}
            >
                <DrawerHeader>
                    <IconButton
                        onClick={handleDrawerClose}
                    >
                        {theme.direction === 'rtl' ? <ChevronRightIcon/> : <ChevronLeftIcon/>}
                    </IconButton>
                </DrawerHeader>
                <Divider/>
                <List>
                    {
                        buttonsStore
                            ?.filter(i => !i.adminButton && i.index < 1000)
                            .sort((bbi1, bbi2) => bbi1.index - bbi2.index)
                            .map(i =>
                                <Link
                                    {...i.routerLinkProps}
                                    style={{textDecoration: "inherit", color: "inherit"}}
                                    key={`button-${buttonsStore.indexOf(i)}`}
                                >
                                    <ListItem
                                        button
                                        key={`button-${buttonsStore.indexOf(i)}`}
                                        onClick={handleDrawerClose}
                                    >
                                        <ListItemIcon>
                                            {i.icon}
                                        </ListItemIcon>
                                        <ListItemText
                                            primary={i.text}
                                        />
                                    </ListItem>
                                </Link>
                            )
                    }
                    <Divider/>
                    {
                        buttonsStore
                            ?.filter(i => i.adminButton && i.index < 1000)
                            .sort((bbi1, bbi2) => bbi1.index - bbi2.index)
                            .map(i =>
                                <Link
                                    {...i.routerLinkProps}
                                    style={{textDecoration: "inherit", color: "inherit"}}
                                    key={`admin-button-${buttonsStore.indexOf(i)}`}
                                >
                                    <ListItem
                                        button
                                        key={`admin-button-${buttonsStore.indexOf(i)}`}
                                        onClick={handleDrawerClose}
                                    >
                                        <ListItemIcon>
                                            {i.icon}
                                        </ListItemIcon>
                                        <ListItemText
                                            primary={i.text}
                                        />
                                    </ListItem>
                                </Link>
                            )
                    }
                    <Divider/>
                    {
                        buttonsStore
                            ?.filter(i => i.index >= 1000)
                            .sort((bbi1, bbi2) => bbi1.index - bbi2.index)
                            .map(i =>
                                <Link
                                    {...i.routerLinkProps}
                                    style={{textDecoration: "inherit", color: "inherit"}}
                                    key={`admin-button-${buttonsStore.indexOf(i)}`}
                                >
                                    <ListItem
                                        button
                                        key={`admin-button-${buttonsStore.indexOf(i)}`}
                                        onClick={doLogout}
                                    >
                                        <ListItemIcon>
                                            {i.icon}
                                        </ListItemIcon>
                                        <ListItemText
                                            primary={i.text}
                                        />
                                    </ListItem>
                                </Link>
                            )
                    }
                </List>
            </ButtonBarDrawer>
            <Box
                sx={{
                    p: 2,
                }}
                style={{
                    width: "100%",
                    height: "100%",
                }}
            >
                <div
                    style={{
                        height: "72px"
                    }}
                />
                {props.children}
            </Box>
        </Box>
    );
}

export default ButtonBar;

