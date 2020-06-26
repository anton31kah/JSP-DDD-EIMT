interface Price {
    amount: number,
    currency: string
}

export interface TicketPlan {
    usages: number,
    price: Price,
    validity: string
}
