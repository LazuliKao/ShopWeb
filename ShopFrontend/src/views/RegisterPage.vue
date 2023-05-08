<script lang="ts" setup>
enum PasswordError {
  None,
  NotSame,
  AtLeast8Char,
  AtLeast1Number,
  AtLeast1Lowercase,
  AtLeast1Uppercase,
}
enum UsernameError {
  None,
  TooShort,
  InvalidChar,
}
</script>
<script lang="ts">
import { api } from "@/api";
import { SetToken, GetSource } from "@/stores/token";
import { calcMd5 } from "@/utils/md5";
export default {
  data() {
    return {
      user: "",
      password: "",
      repeatPassword: "",
      result: "",
      success: false,
      passwordError: PasswordError.None,
      usernameError: UsernameError.None,
      busy: false,
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
        this.usernameError = UsernameError.TooShort;
      }
      //仅允许英文、数字、下划线
      else if (!/^[a-zA-Z0-9_]+$/.test(this.user)) {
        this.usernameError = UsernameError.InvalidChar;
      } else {
        this.usernameError = UsernameError.None;
      }
    },
    checkPassword() {
      if (this.password) {
        if (this.password.length < 8) {
          this.passwordError = PasswordError.AtLeast8Char;
        } else if (!/[0-9]/.test(this.password)) {
          this.passwordError = PasswordError.AtLeast1Number;
        } else if (!/[a-z]/.test(this.password)) {
          this.passwordError = PasswordError.AtLeast1Lowercase;
        } else if (!/[A-Z]/.test(this.password)) {
          this.passwordError = PasswordError.AtLeast1Uppercase;
        } else if (this.password !== this.repeatPassword) {
          this.passwordError = PasswordError.NotSame;
        } else {
          this.passwordError = PasswordError.None;
        }
      } else {
        //密码为空
        this.passwordError = PasswordError.None;
        return;
      }
    },
    Register: async function () {
      //check valid
      if (
        this.passwordError !== PasswordError.None ||
        this.usernameError !== UsernameError.None
      ) {
        ElMessage.error("输入有误");
        //refresh all <el-alert/>
        this.checkPassword();
        this.checkUsername();
        return;
      }
      this.busy = true;
      //send request
      try {
        const { success, message, token } = await api.identity.register(
          this.user,
          this.password
        );
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
          this.result = "注册失败！信息：" + message;
          ElMessage.error(this.result);
        }
      } catch (error) {
        let err = error as Error;
        this.result = "注册失败！错误：" + err.name + " : " + err.message;
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
      <el-button @click="Register" type="primary" :loading="busy">
        注册
      </el-button>
      <RouterLink to="/login">
        <el-button>返回登录</el-button>
      </RouterLink>
    </div>
    <br />
    <el-alert
      v-if="usernameError === UsernameError.TooShort"
      title="用户名长度必须大于3"
      type="error"
      show-icon
    ></el-alert>
    <el-alert
      v-if="usernameError === UsernameError.InvalidChar"
      title="用户名只能包含英文、数字、下划线"
      type="error"
      show-icon
    ></el-alert>
    <el-alert
      v-if="passwordError === PasswordError.NotSame"
      title="两次输入的密码不一致"
      type="error"
      show-icon
    ></el-alert>
    <el-alert
      v-if="passwordError === PasswordError.AtLeast8Char"
      title="密码长度必须大于8"
      type="error"
      show-icon
    ></el-alert>
    <el-alert
      v-if="passwordError === PasswordError.AtLeast1Number"
      title="密码必须包含数字"
      type="error"
      show-icon
    ></el-alert>
    <el-alert
      v-if="passwordError === PasswordError.AtLeast1Lowercase"
      title="密码必须包含小写字母"
      type="error"
      show-icon
    ></el-alert>
    <el-alert
      v-if="passwordError === PasswordError.AtLeast1Uppercase"
      title="密码必须包含大写字母"
      type="error"
      show-icon
    ></el-alert>
    <el-alert
      v-if="success"
      title="注册成功！"
      type="success"
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
