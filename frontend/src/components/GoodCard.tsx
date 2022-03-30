import React from "react";
import type {IGood} from "../../types/AdminTypes";
import {Button, Card, CardActions, CardContent, Typography} from "@mui/material";
import {ShoppingBasket} from "@mui/icons-material";
import type {CardProps} from "@mui/material/Card/Card";


const GoodCard: React.FC<CardProps & { good: IGood }> = (props) => {

    return <Card
        {...props}
    >
        <CardContent>
            <Typography variant="body1">{props.good.name}</Typography>
            <Typography variant="body2">{props.good.article}</Typography>
        </CardContent>
        <CardActions>
            <Button
                key="add-to-card"
            >
                <ShoppingBasket/>
            </Button>
            <Typography variant="body1">{props.good.price}{' RUB'}</Typography>
        </CardActions>
    </Card>
};

export default GoodCard;