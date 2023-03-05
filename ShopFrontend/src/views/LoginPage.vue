<script lang="ts" >
import { TryGetToken, SetToken } from "@/stores/token";
export default {
    data() {
        return {
            user: "admin",
            password: "admin",
            result: "",
            success: false
        }
    },
    methods: {
        Submit: async function () {
            try {
                let response = await this.axios.post('http://localhost:8080/login', {
                    user: this.user,
                    passwordMd5: this.password
                });
                const {
                    success,
                    message,
                    token
                }: {
                    success: boolean,
                    message: string,
                    token: string
                } = response.data
                this.success = success;
                if (success) {
                    this.result = message + "\n将在1秒后跳转";
                    setTimeout(() => {
                        this.$router.push("/")
                        SetToken(token)
                    }, 1000);
                } else {
                    this.result = "登录失败！信息：" + message;
                    ElMessage.error(this.result);
                }
            } catch (error) {
                let err = (error as Error);
                this.result = "登录失败！错误：" + err.name + " : " + err.message;
                ElMessage.error(this.result);
            }
        }
    }
}
</script>
<template>
    <div class="main">
        帐号：<el-input v-model="user" placeholder="输入帐号" clearable />
        <br>
        密码：<el-input type="password" placeholder="输入密码" v-model="password" show-password clearable />
        <br>
        <h4>
            {{ result }}
            <a v-if="success" href="#/">主页</a>
        </h4>
        <el-button @click="Submit" class="loginbutton">
            登录
        </el-button>
        <el-button @click="Submit" class="loginbutton">
            <RouterLink to="/register">前往注册</RouterLink>
        </el-button>    
    </div>
</template>
<style scoped>
</style>
