import axios, { AxiosResponse } from "axios";
import { TimeChange } from "../Models/TimeChange";

const instance = axios.create({
    baseURL: "http://localhost:8084",
    headers: {
        "Access-Control-Allow-Origin": "*"
    },
});

export class TimeApi {
    static getTime(): Promise<AxiosResponse<string>> {
        return instance.get("/");
    }

    static setTime(time: string): Promise<AxiosResponse<TimeChange>> {
        return instance.post("/", {
            newTime: time
        });
    }

    static resetTime(): Promise<AxiosResponse<TimeChange>> {
        return instance.delete("/");
    }
}
