import { Md5 } from "ts-md5";
export function calcMd5(str: string): string {
  return Md5.hashStr(str);
}
