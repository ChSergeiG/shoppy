export const evaluateSum = <T extends any>(items: T[], priceExtractor: (item: T) => number) => {
    const total = items.reduce((pv, cv, ci, a) => pv + priceExtractor(cv), 0);
    return total.toFixed(Math.abs(total % 1.00) >= 0.01 ? 2 : 0);
};
