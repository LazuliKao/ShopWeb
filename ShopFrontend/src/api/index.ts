import axios from "axios";
import { calcMd5 } from "@/utils/md5";
import type { ShopItem } from "@/models/shop";
export const api = {
  shop: {
    delcart: async (token: string | undefined, id: string) =>
      (
        await axios.post("shop/delcart", {
          token: token,
          id: id,
        })
      ).data as { success: boolean; message: string },
    all: async (token: string | undefined) =>
      (
        await axios.post("shop/all", {
          token: token,
        })
      ).data as { success: boolean; message: string; items: Array<ShopItem> },
    settocart: async (
      token: string | undefined,
      id: string,
      count: number | undefined
    ) =>
      (
        await axios.post("shop/delcart", {
          token: token,
          id: id,
          count: count,
        })
      ).data as { success: boolean; message: string },
    addtocart: async (
      token: string | undefined,
      id: string,
      count: number | undefined
    ) =>
      (
        await axios.post("shop/addtocart", {
          token: token,
          id: id,
          count: count,
        })
      ).data as { success: boolean; message: string },
    getcart: async (token: string | undefined) =>
      (
        await axios.post("shop/getcart", {
          token: token,
        })
      ).data as { success: boolean; message: string; items: Array<ShopItem> },
  },
  identity: {
    login: async (user: string, password: string) => {
      let response = await axios.post("identity/login", {
        user: user,
        passwordMd5: calcMd5(password),
      });
      return response.data as {
        success: boolean;
        message: string;
        token: string;
      };
    },
    register: async (user: string, password: string) => {
      let response = await axios.post("identity/register", {
        user: user,
        passwordMd5: calcMd5(password),
      });
      return response.data as {
        success: boolean;
        message: string;
        token: string;
      };
    },
  },
};
