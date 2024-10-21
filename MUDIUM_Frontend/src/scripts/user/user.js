import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {
  state: () => ({
    userInfo: { isLoggedIn: false },
  }),
  actions: {
    updateUserInfo(info) {
      console.log(`update userinfo: ${info}`);
      this.userInfo = { ...info };
      console.log(this.userInfo);
    },
    updateLoginStatus(status) {
      this.userInfo.isLoggedIn = status;
    },
    clearUserInfo() {
      console.log("logout");
      this.userInfo = { isLoggedIn: false };
      console.log(this.userInfo);
    },
  },
});
