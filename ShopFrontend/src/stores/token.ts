import { useStore } from "@/stores";
const getStore = () => useStore();
export function TryGetToken(): { success: true, token: string }
    | { success: false, token: undefined } {
    const tk = getStore().token
    if (tk) {
        return { success: true, token: tk }
    } else {
        return { success: false, token: undefined }
    }
}
export function GetToken(): string | undefined {
    const tk = getStore().token
    if (tk) {
        return tk
    } else {
        return undefined
    }
}
export function SetToken(value: string) {
    getStore().SetToken(value)
}
