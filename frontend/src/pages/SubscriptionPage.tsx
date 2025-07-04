import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import type { RootState, AppDispatch } from "../store/store";
// [수정] fetchUserProfile 대신 subscribeUser를 가져옵니다.
import { subscribeUser } from "../store/slices/userSlice";
import PaymentModal from "../components/PaymentModal";
import styles from "./SubscriptionPage.module.css";

const SubscriptionPage: React.FC = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();

  // [수정] Redux store에서 필요한 상태들을 한 번에 가져옵니다.
  const { status, points: userPoints, userInfo } = useSelector((state: RootState) => state.user);
  const userId = userInfo?.id;
  const isLoading = status === 'loading'; // 로딩 상태를 Redux status와 연동

  const requiredPrice = 9900;
  const productName = "프리미엄 구독";

  const handleOpenModal = () => {
    if (userPoints < requiredPrice) {
      alert(
        `포인트가 부족합니다. (필요: ${requiredPrice.toLocaleString()}P, 보유: ${userPoints.toLocaleString()}P)`
      );
      return;
    }
    setIsModalOpen(true);
  };

  const handleConfirmPayment = async () => {
    // [수정] userId가 없을 경우를 대비한 방어 코드
    if (!userId) {
      alert("사용자 정보가 없어 구독할 수 없습니다. 다시 로그인해주세요.");
      return;
    }

    try {
      // [수정] 직접 API를 호출하는 대신, Redux Thunk를 dispatch합니다.
      // 이 Thunk는 내부적으로 구독 API 호출과 프로필 업데이트를 모두 처리합니다.
      await dispatch(subscribeUser(userId)).unwrap();
      
      alert("구독이 완료되었습니다! 모든 콘텐츠를 무제한으로 즐겨보세요.");
      navigate("/");
    } catch (error) {
      console.error("구독 처리 중 오류:", error);
      alert("구독 처리 중 오류가 발생했습니다.");
    } finally {
      // 로딩 상태는 Redux status와 연동되므로 수동으로 제어할 필요가 없습니다.
      setIsModalOpen(false);
    }
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.planBox}>
          <div className={styles.header}>
            <h1 className={styles.title}>프리미엄 구독</h1>
            <p className={styles.subtitle}>
              모든 이야기를 제한 없이 만나보세요.
            </p>
          </div>
          <div className={styles.priceSection}>
            <span className={styles.price}>9,900 P</span>
          </div>
          <ul className={styles.features}>
            <li>✔️ 모든 전자책 무제한 열람</li>
            <li>✔️ 오디오북 콘텐츠 이용 가능</li>
            <li>✔️ 챗북 시리즈 독점 제공</li>
            <li>✔️ 광고 없이 쾌적한 독서</li>
          </ul>
          <button className={styles.subscribeButton} onClick={handleOpenModal} disabled={isLoading}>
            {`${requiredPrice.toLocaleString()}P로 구독 시작하기`}
          </button>
        </div>
      </div>
      <PaymentModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onConfirm={handleConfirmPayment}
        productName={productName}
        price={requiredPrice}
        loading={isLoading}
      />
    </>
  );
};

export default SubscriptionPage;
