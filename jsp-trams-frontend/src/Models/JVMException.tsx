export interface JVMException {
    message: string;
}

export function isJVMException<T>(object: JVMException | T): object is JVMException {
    return (object as JVMException).message !== undefined;
}
