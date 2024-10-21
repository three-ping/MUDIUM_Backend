<template>
	<Modal :isModalOpen="isSignupModalVisible" @close="closeSignupModal">
		<template #modalSection>
			<div class="signup-form">
				<form @submit.prevent="handleSignup">
					<div class="form-group">
						<div class="input-button-wrapper">
							<input type="email" v-model="email" placeholder="이메일" required :disabled="isEmailVerified">
							<button type="button" class="verify-button" @click="sendVerification"
								:disabled="isEmailSent || isEmailVerified">
								{{ isEmailSent ? '전송됨' : '인증번호 발송' }}
							</button>
						</div>
						<div v-if="emailError" class="error-message">{{ emailError }}</div>
					</div>
					<div v-if="isEmailSent" class="form-group">
						<div class="input-button-wrapper">
							<input type="text" v-model="verificationCode" placeholder="인증번호를 입력해주세요" required
								:disabled="isEmailVerified">
							<button type="button" class="verify-button" @click="verifyCode" :disabled="isEmailVerified">
								{{ isEmailVerified ? '확인됨' : '확인' }}
							</button>
						</div>
					</div>
					<div v-if="isEmailVerified" class="form-group">
						<div class="input-button-wrapper">
							<input type="text" v-model="nickname" placeholder="닉네임을 입력해주세요" required
								:disabled="isNicknameVerified">
							<button type="button" class="verify-button" @click="verifyNickname"
								:disabled="isNicknameVerified">
								{{ isNicknameVerified ? '확인됨' : '중복 확인' }}
							</button>
						</div>
					</div>
					<div v-if="isNicknameVerified" class="form-group">
						<input type="text" v-model="username" placeholder="이름" required>
					</div>
					<div v-if="isNicknameVerified" class="form-group">
						<input type="password" v-model="password" placeholder="비밀번호를 입력해주세요" required>
					</div>
					<div v-if="isNicknameVerified" class="form-group">
						<input type="password" v-model="confirmPassword" placeholder="비밀번호를 확인해주세요" required>
						<div v-if="password && confirmPassword" class="password-validation">
							{{ passwordValidationMessage }}
						</div>
					</div>
					<button type="submit" class="signup-button" :disabled="!isFormValid">
						회원가입 하기
					</button>
				</form>
			</div>
		</template>
		<template #modalFooter>
			<div class="auth-links">
				<span>이미 계정이 있으신가요?</span>
				<a href="#" @click.prevent="switchToLogin">로그인</a>
			</div>
		</template>
	</Modal>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import axios from 'axios';
import Modal from '@/components/layout/Modal.vue';

const props = defineProps({
	isSignupModalVisible: Boolean,
});

const emit = defineEmits(['close', 'signup-success', 'switch-to-login']);

const userStore = useUserStore();
const router = useRouter();

const email = ref('');
const verificationCode = ref('');
const nickname = ref('');
const username = ref('');
const password = ref('');
const confirmPassword = ref('');

const isEmailSent = ref(false);
const isEmailVerified = ref(false);
const isNicknameVerified = ref(false);
const emailError = ref('');

const passwordValidationMessage = computed(() => {
	if (!password.value || !confirmPassword.value) return '';
	return password.value === confirmPassword.value
		? '비밀번호가 일치합니다.'
		: '비밀번호가 일치하지 않습니다.';
});

const isFormValid = computed(() =>
	isEmailVerified.value &&
	isNicknameVerified.value &&
	username.value &&
	password.value &&
	confirmPassword.value &&
	password.value === confirmPassword.value
);

const closeSignupModal = () => emit('close');

const sendVerification = async () => {
	try {
		emailError.value = '';
		const response = await axios.post('/api/users/send-verification', null, { params: { email: email.value } });
		if (response.data.success) {
			isEmailSent.value = true;
			alert('이메일 인증 코드가 전송되었습니다.');
		} else {
			const errorData = response.data.error;
			emailError.value = errorData.message;
			alert(errorData.message);
			isEmailSent.value = false;
		}
	} catch (error) {
		handleError(error, '이메일 인증 코드 전송 실패');
		isEmailSent.value = false;
	}
};

const verifyCode = async () => {
	try {
		const response = await axios.post('/api/users/verify-code', null, {
			params: { email: email.value, code: verificationCode.value }
		});
		if (response.data.success) {
			isEmailVerified.value = true;
			alert('이메일 인증 성공');
		} else {
			alert('이메일 인증 실패');
		}
	} catch (error) {
		handleError(error, '이메일 인증 실패');
	}
};

const verifyNickname = async () => {
	try {
		const response = await axios.get(`/api/users/check-nickname/${nickname.value}`);
		if (response.data.success) {
			isNicknameVerified.value = true;
			alert('사용 가능한 닉네임입니다.');
		} else {
			alert('이미 사용 중인 닉네임입니다.');
		}
	} catch (error) {
		handleError(error, '닉네임 확인 실패');
	}
};

const handleSignup = async () => {
	if (!isFormValid.value) {
		alert('모든 필드를 올바르게 입력해주세요.');
		return;
	}

	try {
		const response = await axios.post('/api/users/signup', {
			user_name: username.value,
			password: password.value,
			nickname: nickname.value,
			email: email.value,
			signup_path: 'NORMAL'
		});

		if (response.data.success) {
			alert('회원가입 성공');
			await autoLogin();
		} else {
			alert('회원가입 실패');
		}
	} catch (error) {
		handleError(error, '회원가입 실패');
	}
};

const autoLogin = async () => {
	try {
		const response = await axios.post("/api/users/login", {
			email: email.value,
			password: password.value,
			signup_path: "NORMAL"
		});

		if (response.data.success) {
			userStore.updateLoginStatus(true);
			userStore.updateUserInfo(response.data.data);
			closeSignupModal();
			router.push('/'); // Redirect to home page after successful login
		} else {
			console.error('Auto-login failed');
			emit('signup-success');
			closeSignupModal();
		}
	} catch (error) {
		handleError(error, "Auto-login error");
		emit('signup-success');
		closeSignupModal();
	}
};

const switchToLogin = () => emit('switch-to-login');

const handleError = (error, defaultMessage) => {
	if (error.response && error.response.data && !error.response.data.success) {
		const errorData = error.response.data.error;
		alert(errorData.message || defaultMessage);
	} else {
		alert(defaultMessage);
	}
};
</script>

<style scoped>
.signup-form {
	max-height: 80vh;
	overflow-y: auto;
	padding: 2rem;
}

.form-group {
	margin-bottom: 2rem;
}

.input-button-wrapper {
	display: flex;
	align-items: stretch;
}

.input-button-wrapper>input,
.input-button-wrapper>button,
.form-group>input {
	height: 6rem;
	font-size: 2.4rem;
}

.input-button-wrapper>input {
	flex: 8;
	padding: 0 1.5rem;
	border: 1px solid #ccc;
	border-radius: 1rem 0 0 1rem;
}

.input-button-wrapper>button {
	flex: 2;
	padding: 0 1rem;
	border: 1px solid #ccc;
	border-left: none;
	border-radius: 0 1rem 1rem 0;
	background-color: var(--primary-color);
	color: white;
	cursor: pointer;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.form-group>input {
	width: 100%;
	padding: 0 1.5rem;
	border: 1px solid #ccc;
	border-radius: 1rem;
}

.password-validation {
	margin-top: 0.5rem;
	font-size: 2rem;
	color: var(--primary-color);
}

.error-message {
	color: red;
	font-size: 2rem;
	margin-top: 0.5rem;
}

.auth-links {
	text-align: center;
	margin-top: 1rem;
}

.auth-links span {
	margin-right: 0.5rem;
}

.auth-links a {
	color: var(--secondary-color);
	text-decoration: none;
}

.auth-links a:hover {
	text-decoration: underline;
}

.signup-button {
	width: 100%;
	height: 6rem;
	background-color: var(--primary-color);
	color: white;
	border: none;
	border-radius: 1rem;
	font-size: 2.4rem;
	cursor: pointer;
}

.signup-button:hover {
	background-color: var(--button-hover-color);
}

.signup-button:disabled {
	background-color: #ccc;
	cursor: not-allowed;
}

input:disabled {
	background-color: #f0f0f0;
	color: #888;
}
</style>