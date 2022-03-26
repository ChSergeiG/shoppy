import React from "react";
import type {IGood} from "../../types/AdminTypes";
import {Card, CardActions, CardContent} from "@mui/material";


const GoodCard: React.FC<{ good: IGood }> = (props) => {

    return <Card
        sx={{maxWidth: "140px"}}
    >
        <CardContent>

        </CardContent>
        <CardActions>

        </CardActions>
    </Card>
};

export default GoodCard;