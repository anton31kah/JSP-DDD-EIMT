import axios, { AxiosResponse } from "axios";
import { TicketPlan } from "Models/TicketPlan";
import { Ticket, TicketId } from "Models/Ticket";

const instance = axios.create({
    baseURL: "http://localhost:8081",
    headers: {
        "Access-Control-Allow-Origin": "*"
    },
});

export class TicketsApi {
    static getPlans(): Promise<AxiosResponse<TicketPlan[]>> {
        return instance.get("/plans")
    }

    static async purchasePlans(customerName: string, purchasedPlans: TicketPlan[]): Promise<TicketId[]> {
        const ticketIds: TicketId[] = [];
        for (let plan of purchasedPlans) {
            const response: AxiosResponse<TicketId> = await instance.post("/", {
                customerName,
                ticketPlan: plan
            });

            ticketIds.push(response.data);
        }

        return Promise.resolve(ticketIds);
    }

    static getInfo(customerName: string): Promise<AxiosResponse<Ticket[]>> {
        return instance.get(`/?name=${customerName}`)
    }
}
