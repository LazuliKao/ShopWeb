import { useCounterStore } from "@/stores";
const getStore = () => useCounterStore();
export function TryGetToken(): { success: true, token: string }
    | { success: false, token: undefined } {
    const tk = getStore().token
    if (tk) {
        return { success: true, token: tk }
    } else {
        return { success: false, token: undefined }
    }
}
export function SetToken(value: string) {
    getStore().SetToken(value)
}
