import { useStore } from "@/stores";
import router from "@/router";
/**
 * 检查token是否存在，不存在则跳转到登录页面
 * @returns true:已登录 false:未登录
 */
export function CheckTokenOrRedirect() {
  const source = router.currentRoute.value.path;//当前位置
  let r = TryGetToken();
  if (!r.success) {
    router.push("/login/goto");
    useStore().source = source;
    return false;
  }
  return true;
}
export function GetSource():
  | { exists: true; source: string }
  | { exists: false; source: undefined } {
  const s = useStore().source;
  if (s) {
    return { exists: true, source: s };
  } else {
    return { exists: false, source: undefined };
  }
}
/**
 * 获取token
 */
export function TryGetToken():
  | { success: true; token: string }
  | { success: false; token: undefined } {
  const tk = useStore().token;
  if (tk) {
    return { success: true, token: tk };
  } else {
    return { success: false, token: undefined };
  }
}
export function GetToken(): string | undefined {
  const tk = useStore().token;
  if (tk) {
    return tk;
  } else {
    return undefined;
  }
}
export function SetToken(value: string) {
  useStore().SetToken(value);
}
