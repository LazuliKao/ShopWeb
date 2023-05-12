import axios from "axios";
import { calcMd5 } from "@/utils/md5";
import type { ShopItem } from "@/models/shop";
import { GetToken } from "@/stores/token";
import router from "@/router";
axios.defaults.baseURL = "http://192.168.16.233:8081";
axios.interceptors.request.use(
  function (config) {
    const token = GetToken();
    config.headers.Authorization = token;
    if (!config.data) config.data = {};
    config.data.token = token;
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);
axios.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    const msg = error.response?.data?.message;
    if (msg) {
      ElMessage.error(msg);
    } else if (error.response?.status == 401) {
      ElMessage.error("未登录");
      router.push("/login/goto");
    } else {
      ElMessage.error("未知错误" + error.response?.status);
    }
    return Promise.reject(error);
  }
);
export const api = {
  shop: {
    delcart: async (id: string) =>
      (
        await axios.post("shop/delcart", {
          id: id,
        })
      ).data as { success: boolean; message: string },
    all: async () =>
      (await axios.post("shop/all")).data as {
        success: boolean;
        message: string;
        items: Array<ShopItem>;
      },
    settocart: async (id: string, count: number | undefined) =>
      (
        await axios.post("shop/settocart", {
          id: id,
          count: count,
        })
      ).data as { success: boolean; message: string },
    addtocart: async (id: string, count: number | undefined) =>
      (
        await axios.post("shop/addtocart", {
          id: id,
          count: count,
        })
      ).data as { success: boolean; message: string },
    getcart: async () =>
      (await axios.post("shop/getcart")).data as {
        success: boolean;
        message: string;
        items: Array<ShopItem>;
      },
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
