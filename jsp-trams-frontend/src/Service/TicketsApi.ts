import axios, { AxiosResponse } from "axios";
import { TicketPlan } from "../Models/TicketPlan";

const instance = axios.create({
    baseURL: "http://localhost:8081",
    headers: {
        "Access-Control-Allow-Origin": "*"
    },
});

interface TicketId {
    id: string
}

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

    static async getInfo(customerName?: string, ticketId?: string): Promise<any[]> {
        if (customerName) {
            return await instance.get(`/?name=${customerName}`)
        }

        if (ticketId) {
            return await instance.get(`/?id=${ticketId}`)
        }

        return Promise.resolve([]);
    }
}
