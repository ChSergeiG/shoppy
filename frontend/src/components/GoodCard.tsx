import React from "react";
import type * as T from "../types";
import {Badge, Box, Button, Card, CardContent, Typography} from "@mui/material";
import type {CardProps} from "@mui/material/Card/Card";
import {useStore} from "effector-react";
import {addGoodToBasket, removeGoodFromBasket, selectedGoods} from "../store/UserBucketStore";
import {Remove, ShoppingBasket} from "@mui/icons-material";
import {evaluateSum} from "../utils/number-utils";

const evaluateCount = (store: T.CommonGoodDto[], good: T.CommonGoodDto): number | undefined => {
    const count = store.filter(g => g.article === good.article).length;
    return count > 0 ? count : undefined;
}

const GoodCard: React.FC<CardProps & { good: T.CommonGoodDto }> = (props) => {
    const store = useStore(selectedGoods);
    return <Box
        sx={{
            display: "flex"
        }}
    >
        <Badge
            sx={{
                top: "12px",
                right: "-12px",
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

                    <Typography
                        variant="body1">{evaluateSum<T.CommonGoodDto>([props.good], (good) => (good.price !== undefined ? good.price : 0))}{' RUB'}
                    </Typography>
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