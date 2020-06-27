interface Customer {
    name: string
}

interface Usages {
    initial: number,
    left: number
}

export interface TicketId {
    id: string
}

export interface Ticket {
    customer: Customer,
    boughtOn: string,
    expiryDate: string,
    usages: Usages,
    id: TicketId
}
