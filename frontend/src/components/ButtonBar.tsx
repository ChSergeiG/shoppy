import React, {PropsWithChildren, useContext, useState} from "react";
import {ApplicationContext} from "../applicationContext";
import {
    AppBarProps,
    Box,
    CssBaseline,
    CSSObject,
    Divider,
    Drawer,
    IconButton,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    styled,
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
import {LOCAL_STORAGE_JWT_KEY} from "../pages/admin/components/AuthorizationOverlay";

type ButtonBarState = {
    open: boolean;
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
    const context = useContext(ApplicationContext);

    const [state, setState] = useState<ButtonBarState>({
        open: false,
    });

    const theme = useTheme();

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

    const logout = (e: any) => {
        handleDrawerClose();
        context.setAuthorized?.(false);
        context.setToken?.("");
        localStorage.setItem(LOCAL_STORAGE_JWT_KEY, "");
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
                        context.buttonBarItems
                            ?.filter(i => !i.adminButton && i.index < 1000)
                            .sort((bbi1, bbi2) => bbi1.index - bbi2.index)
                            .map(i =>
                                <Link
                                    {...i.routerLinkProps}
                                    style={{textDecoration: "inherit", color: "inherit"}}
                                >
                                    <ListItem
                                        button
                                        key={`button-${context.buttonBarItems?.indexOf(i)}`}
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
                        context.buttonBarItems
                            ?.filter(i => i.adminButton && i.index < 1000)
                            .sort((bbi1, bbi2) => bbi1.index - bbi2.index)
                            .map(i =>
                                <Link
                                    {...i.routerLinkProps}
                                    style={{textDecoration: "inherit", color: "inherit"}}
                                >
                                    <ListItem
                                        button
                                        key={`admin-button-${context.buttonBarItems?.indexOf(i)}`}
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
                        context.buttonBarItems
                            ?.filter(i =>   i.index >= 1000)
                            .sort((bbi1, bbi2) => bbi1.index - bbi2.index)
                            .map(i =>
                                <Link
                                    {...i.routerLinkProps}
                                    style={{textDecoration: "inherit", color: "inherit"}}
                                >
                                    <ListItem
                                        button
                                        key={`admin-button-${context.buttonBarItems?.indexOf(i)}`}
                                        onClick={logout}
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

