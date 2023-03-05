<script lang="ts">
import { SetToken, GetSource } from "@/stores/token";
import { calcMd5 } from "@/utils/md5";
export default {
  data() {
    return {
      user: "admin",
      password: "admin",
      result: "",
      success: false,
      busy: false,
    };
  },
  methods: {
    Login: async function () {
      this.busy = true;
      try {
        let response = await this.axios.post("login", {
          user: this.user,
          passwordMd5: calcMd5(this.password),
        });
        const {
          success,
          message,
          token,
        }: {
          success: boolean;
          message: string;
          token: string;
        } = response.data;
        this.success = success;
        if (success) {
          this.result = message + "\n将在1秒后跳转";
          setTimeout(() => {
            SetToken(token);
            const { exists, source } = GetSource();
            if (exists) {
              this.$router.push(source);
            } else {
              this.$router.push("/");
            }
          }, 1000);
        } else {
          this.result = "登录失败！信息：" + message;
          ElMessage.error(this.result);
        }
      } catch (error) {
        let err = error as Error;
        this.result = "登录失败！错误：" + err.name + " : " + err.message;
        ElMessage.error(this.result);
      }
      this.busy = false;
    },
  },
};
</script>
<template>
  <div class="main">
    <div class="input-container">
      <label>帐号：</label>
      <el-input v-model="user" placeholder="输入帐号" clearable />
    </div>
    <br />
    <div class="input-container">
      <label>密码：</label>
      <el-input
        type="password"
        placeholder="输入密码"
        v-model="password"
        show-password
        clearable
      />
    </div>
    <br />
    <div class="button-container">
      <el-button @click="Login" type="primary" :loading="busy">
        登录
      </el-button>
      <RouterLink to="/register"> <el-button>前往注册 </el-button></RouterLink>
    </div>
    <h4>
      {{ result }}
      <a v-if="success" href="#/">主页</a>
    </h4>
  </div>
</template>

<style scoped>
.el-input {
  width: 200px;
}
.main {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.input-container {
  display: flex;
}

.input-container label {
  width: 60px;
  display: flex;
  align-items: center;
}
.button-container {
  display: flex;
  justify-content: space-between;
  width: 220px;
}
.el-button {
  width: 100px;
}
</style>
