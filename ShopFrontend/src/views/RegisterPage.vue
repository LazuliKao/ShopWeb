<script lang="ts" setup>
enum UsernameError {
  None,
  TooShort,
  InvalidChar,
}
</script>
<script lang="ts">
import { TryGetToken, SetToken } from "@/stores/token";
export default {
  data() {
    return {
      user: "",
      password: "",
      repeatPassword: "",
      result: "",
      success: false,
      showPasswordError: false,
      showUsernameError: UsernameError.None,
    };
  },
  watch: {
    password() {
      this.checkPassword();
    },
    repeatPassword() {
      this.checkPassword();
    },
    user() {
      this.checkUsername();
    },
  },
  methods: {
    checkUsername() {
      //长度大于3
      if (this.user.length < 3) {
        this.showUsernameError = UsernameError.TooShort;
      }
      //仅允许英文、数字、下划线
      else if (!/^[a-zA-Z0-9_]+$/.test(this.user)) {
        this.showUsernameError = UsernameError.InvalidChar;
      } else {
        this.showUsernameError = UsernameError.None;
      }
    },
    checkPassword() {
      if (this.password !== this.repeatPassword) {
        this.showPasswordError = true;
      } else {
        this.showPasswordError = false;
      }
    },
    Register: async function () {
      //check valid
      if (
        this.showPasswordError ||
        this.showUsernameError !== UsernameError.None
      ) {
        ElMessage.error("输入有误");
        return;
      }
      //send request
      try {
        let response = await this.axios.post("http://localhost:8080/register", {
          user: this.user,
          passwordMd5: this.password,
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
            this.$router.push("/");
            SetToken(token);
          }, 1000);
        } else {
          this.result = "注册失败！信息：" + message;
          ElMessage.error(this.result);
        }
      } catch (error) {
        let err = error as Error;
        this.result = "注册失败！错误：" + err.name + " : " + err.message;
        ElMessage.error(this.result);
      }
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
    <div class="input-container">
      <label>重复密码：</label>
      <el-input
        type="password"
        placeholder="再次输入密码"
        v-model="repeatPassword"
        show-password
        clearable
      />
    </div>
    <br />
    <div class="button-container">
      <el-button @click="Register" type="primary"> 注册 </el-button>
      <RouterLink to="/login">
        <el-button>返回登录</el-button>
      </RouterLink>
    </div>
    <br />
    <el-alert
      v-if="showPasswordError"
      title="两次输入的密码不一致"
      type="error"
      show-icon
    ></el-alert>
    <el-alert
      v-if="showUsernameError === UsernameError.TooShort"
      title="用户名长度必须大于3"
      type="error"
      show-icon
    ></el-alert>
    <el-alert
      v-if="showUsernameError === UsernameError.InvalidChar"
      title="用户名只能包含英文、数字、下划线"
      type="error"
      show-icon
    ></el-alert>
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
  width: 80px;
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
