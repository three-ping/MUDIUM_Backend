   <template>
    <div class="ticket-container">  
      <!-- 이미지 파일 업로드 -->
      <div class="file-upload">
        <input type="file" @change="handleImageUpload" />
      </div>

      <div class="controls">
        <label>
          홀로그램 색상 1
          <input type="color" v-model="hologramColor1" @input="applyHologramStyle" />
        </label>
        <label>
          홀로그램 색상 2
          <input type="color" v-model="hologramColor2" @input="applyHologramStyle" />
        </label>
      </div>
  
      <!-- 포스터 클릭 시 뒤집히는 컨테이너 -->
      <div v-if="uploadedImage" class="flip-container" @click="flipCard">
        <div class="flipper" :class="{ flipped: isFlipped }">
          <!-- 앞면 -->
          <div class="front">
            <div class="container" ref="container" @mousemove="handleMouseMove" @mouseout="handleMouseOut">
              <div class="overlay" ref="overlay"></div>
              <div class="card" :style="{ backgroundImage: 'url(' + uploadedImage + ')' }"></div>
            </div>
          </div>
          <!-- 뒷면 -->
          <div class="back">
            <div class="back-content">
              <p v-if="backMessage">{{ backMessage }}</p>
              <p v-else>코멘트가 없습니다. 코멘트를 추가해주세요.</p>
            </div>
          </div>
        </div>
      </div>
  
      <!-- 파일 업로드 후에만 코멘트 섹션 표시 -->
      <div v-if="uploadedImage" class="comments-section">
        <h3>Comments</h3>
        <textarea v-model="newComment" placeholder="Add your comment"></textarea>
        <button @click="saveCustomTicket">저장</button>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import { useRouter } from 'vue-router';

  import { useUserStore } from '@/stores/userStore';  // 사용자 정보를 저장한 Pinia store


  export default {
    setup() {
      const router = useRouter();
      const userStore = useUserStore();
      // const userId = userStore.userInfo.userId;  // 로그인된 사용자의 ID
      const userId = userStore.userInfo.userId || userStore.userInfo.user_id;
      console.log(userId);


      const container = ref(null);
      const overlay = ref(null);
      const isFlipped = ref(false);
  
      const hologramColor1 = ref(null);
      const hologramColor2 = ref(null);
      const uploadedImage = ref(null); // 업로드된 이미지 URL
  
      const newComment = ref('');
      const backMessage = ref(''); // 댓글 저장할 변수

      // 이미지 파일 업로드 핸들러
      const handleImageUpload = (event) => {
        const file = event.target.files[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = (e) => {
            uploadedImage.value = e.target.result;
            console.log("이미지 데이터 (Base64):", uploadedImage.value);
          };
          reader.readAsDataURL(file);
        }
      };
  
      // 포스터를 뒤집는 함수
      const flipCard = () => {
        isFlipped.value = !isFlipped.value;
      };
  
      // 전체 티켓 데이터를 로그로 출력하는 함수
      const logTicketData = () => {
        console.log("티켓 데이터:", {
          image: uploadedImage.value,
          hologramColor1: hologramColor1.value,
          hologramColor2: hologramColor2.value,
          comment: backMessage.value,
        });
      };
  
      // 마우스 이동 핸들러
      const handleMouseMove = (e) => {
        const bounds = container.value.getBoundingClientRect();
        const x = e.offsetX;
        const y = e.offsetY;
  
        if (x >= 0 && y >= 0 && x <= bounds.width && y <= bounds.height) {
          const backgroundX = (x / bounds.width) * 100;
          const backgroundY = (y / bounds.height) * 100;
  
          if (overlay.value && hologramColor1.value && hologramColor2.value) {
            overlay.value.style.backgroundPosition = `${backgroundX}% ${backgroundY}%`;
            overlay.value.style.filter = `opacity(${x / bounds.width}) brightness(1.2)`;
          }
  
          const rotateY = -1 / 5 * x + 20;
          const rotateX = 4 / 30 * y - 20;
  
          if (container.value) {
            container.value.style.transform = `perspective(350px) rotateX(${rotateX}deg) rotateY(${rotateY}deg)`;
          }
        }
      };
  
      const handleMouseOut = () => {
        if (overlay.value) {
          overlay.value.style.filter = 'opacity(0)';
        }
  
        if (container.value) {
          container.value.style.transform = 'perspective(350px) rotateY(0deg) rotateX(0deg)';
        }
      };
  
      const applyHologramStyle = () => {
        if (overlay.value && uploadedImage.value) {
          const backgroundPosition = overlay.value.style.backgroundPosition || '50% 50%';
          if (hologramColor1.value && hologramColor2.value) {
            overlay.value.style.background = `linear-gradient(105deg, transparent 40%, ${hologramColor1.value} 45%, ${hologramColor2.value} 50%, transparent 54%)`;
            overlay.value.style.backgroundSize = '200% 200%';
            overlay.value.style.backgroundPosition = backgroundPosition;
  
            logTicketData();
          } else {
            overlay.value.style.background = 'none';
          }
        }
      };
  
      // 저장 및 서버로 전송하는 함수
      const saveCustomTicket = () => {
        if (newComment.value.trim() !== '') {
          backMessage.value = newComment.value;
          console.log("입력된 댓글:", newComment.value);
          newComment.value = ''; // 댓글 입력란 초기화
        }
  
        const ticketData = {
          ticketImage: uploadedImage.value,
          hologramColor1: hologramColor1.value,
          hologramColor2: hologramColor2.value,
          comment: backMessage.value,
          userId: userId
        };
  
        console.log('저장할 티켓 데이터:', ticketData);
  
        // fetch를 사용하여 서버로 POST 요청 전송
        fetch('http://localhost:8080/api/customticket/create', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(ticketData),
        })
          .then(response => response.json())
          .then(data => {
            console.log('서버 응답:', data);


          if (data.success) {
      // 성공적으로 저장되었다는 알림
      alert('티켓이 성공적으로 저장되었습니다.');


      // 홀로그램 색상과 파일 업로드 초기화
      hologramColor1.value = '';  // 홀로그램 색상 1 초기화
      hologramColor2.value = '';  // 홀로그램 색상 2 초기화
      uploadedImage.value = null; // 업로드된 이미지 초기화

      // 백 메시지 초기화
      backMessage.value = '';

      // selectedItem.value = 'tickets';
      router.push('/mypage');

      // 파일 선택창과 홀로그램만 보이게 다시 설정
      // 필요한 로직이 여기에 추가될 수 있음 (예: UI를 다시 리셋하는 로직)
    } else {
      alert('티켓 저장에 실패했습니다.');
    }
  })

          .catch(error => {
            console.error('티켓 저장 중 오류 발생:', error);
          });
      };
  
      onMounted(() => {
        applyHologramStyle();
      });
  
      return {
        container,
        overlay,
        hologramColor1,
        hologramColor2,
        handleMouseMove,
        handleMouseOut,
        applyHologramStyle,
        isFlipped,
        flipCard,
        newComment,
        // addComment,
        uploadedImage,
        handleImageUpload,
        backMessage,
        saveCustomTicket,
        // userId
      };
    },
  };
  </script>
  
  <style>
  .ticket-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    /* justify-content: center; */
    width: 100%;
    height: 100vh;
    margin-top: 2%;

  }

  .file-upload input{
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  margin-left: 20%;
}
  .container {
    width: 220px;
    height: 310px;
    transition: all 0.1s;
    position: relative;
    /* text-align: center; */
  }

  .controls {
    width: 30%;
    margin-top: 3%;
    font-size: 2rem;
    margin-bottom: 3%;
  }

  .controls input {
    width: 100%;
    margin-bottom: 5%;
  }
  
  .overlay {
    position: absolute;
    width: 220px;
    height: 310px;
    filter: brightness(1.1) opacity(0.8);
    mix-blend-mode: color-dodge;
    background-size: 200% 200%;
    transition: all 0.1s;
  }
  
  .card {
    width: 220px; 
    height: 310px; 
    background-size: cover;
  }
  
  .flip-container {
    perspective: 1000px;
    cursor: pointer;
  }
  
  .flipper {
    position: relative;
    width: 220px;
    height: 310px;
    transition: 0.6s;
    transform-style: preserve-3d;
  }
  
  .flipped {
    transform: rotateY(180deg);
  }
  
  .front,
  .back {
    position: absolute;
    width: 100%;
    height: 100%;
    backface-visibility: hidden;
  }
  
  .back {
    background-color: #f4f4f4;
    transform: rotateY(180deg);
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .back-content {
    text-align: center;
  }
  
  .comments-section {
    margin-top: 30px;
  width: 100%; /* Make sure the comment section is responsive */
  display: flex;
  flex-direction: column;
  align-items: center;
  }

  .comments-section h3 {
    font-size: 1.6rem;
    margin-bottom: 1%;
  }
  
  textarea {
    width: 30%;
    height: 80px;
    margin-bottom: 10px;
  }
  
  button {
    padding: 12px 18px;
  font-size: 1rem;
  background-color: #6a0dad;
  color: white;
  border: none;
  cursor: pointer;
  }
  </style>
  