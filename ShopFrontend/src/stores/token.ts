import { useCounterStore } from "@/stores";
const store = useCounterStore();
export function TryGetToken(): { success: true, token: string }
    | { success: false, token: undefined } {
    const tk = store.token
    if (tk) {
        return { success: true, token: tk }
    } else {
        return { success: false, token: undefined }
    }
}
export function SetToken(value: string) {
    store.SetToken(value)
}
