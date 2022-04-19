import React from "react";
import type {IGood} from "../../types/AdminTypes";
import {Badge, Box, Button, Card, CardContent, Typography} from "@mui/material";
import type {CardProps} from "@mui/material/Card/Card";
import {useStore} from "effector-react";
import {addGoodToBasket, removeGoodFromBasket, selectedGoods} from "../store/UserBucketStore";
import {Add, Remove, ShoppingBasket} from "@mui/icons-material";

const cardWidth = 250;
const cardHeight = 180;

const evaluateCount = (store: IGood[], good: IGood): number | undefined => {
    const count = store.filter(g => g.article === good.article).length;
    return count > 0 ? count : undefined;
}

const GoodCard: React.FC<CardProps & { good: IGood }> = (props) => {
    const store = useStore(selectedGoods);
    return <Box
        sx={{
            display: "flex"
        }}
    >
        <Badge
            sx={{
                top: "15px",
                left: `${cardWidth + 6}px`,
            }}
            color="primary"
            badgeContent={evaluateCount(store, props.good)}
        />
        <Card
            {...props}
        >
            <CardContent>
                <Box
                    sx={{
                        display: "flex",
                        flexDirection: "row",
                        justifyContent: "space-between",
                    }}
                >
                    <Typography variant="body1">{props.good.name}</Typography>
                </Box>
                <Typography variant="caption" color="text.secondary">{props.good.article}</Typography>
                <hr/>
                <Box
                    sx={{
                        display: "flex",
                        flexDirection: "row",
                        justifyContent: "space-between",
                    }}
                >
                    <Button
                        size="small"
                        onClick={() => removeGoodFromBasket(props.good)}
                    >
                        <Remove/>
                    </Button>

                    <Typography variant="body1">{props.good.price.toFixed(2)}{' RUB'}</Typography>
                    <Button
                        size="small"
                        onClick={() => addGoodToBasket(props.good)}
                    >
                        <ShoppingBasket/>
                    </Button>
                </Box>
            </CardContent>
        </Card>
    </Box>

};

export default GoodCard;