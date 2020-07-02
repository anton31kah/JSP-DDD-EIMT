import axios, { AxiosResponse } from "axios";
import { Ride } from "Models/Ride";
import { CheckRide } from "Models/CheckRide";
import { CompletedRide } from "Models/CompletedRide";
import { Conductor } from "Models/Conductor";
import { JVMException } from "Models/JVMException";

const routesInstance = axios.create({
    baseURL: "http://localhost:8083",
    headers: {
        "Access-Control-Allow-Origin": "*"
    },
});

const ridesInstance = axios.create({
    baseURL: "http://localhost:8082",
    headers: {
        "Access-Control-Allow-Origin": "*"
    },
});

export class RidesApi {
    static getRides(): Promise<AxiosResponse<Ride[]>> {
        return routesInstance.get("/")
    }

    static checkRide(checkRide: CheckRide): Promise<AxiosResponse<CompletedRide | JVMException>> {
        return ridesInstance.post("/", checkRide)
    }

    static getConductors(): Promise<AxiosResponse<Conductor[]>> {
        return ridesInstance.get("/conductors")
    }
}
