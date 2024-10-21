import LoginModal from "@/views/user/components/LoginModal.vue";
export default [
  {
    path: "/mypage",
    component: () => import("@/views/user/MyPage.vue"),
    
  },
  {
    path: "/kakao-callback",
    name: "KakaoCallback",
    component: LoginModal,
  },
];
