import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import apiClient from "../api";
import type { RootState, AppDispatch } from "../store/store";
import { fetchUserProfile } from "../store/slices/userSlice";
import PaymentModal from "../components/PaymentModal";
import styles from "./SubscriptionPage.module.css";

const SubscriptionPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const userPoints = useSelector((state: RootState) => state.user.points);
  const userId = useSelector((state: RootState) => state.user.userInfo?.id);

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
    setLoading(true);
    try {
      await apiClient.post(`/subscribe?userId=${userId}`);
      alert("구독이 완료되었습니다! 모든 콘텐츠를 무제한으로 즐겨보세요.");
      dispatch(fetchUserProfile());
      navigate("/");
    } catch (error) {
      alert("구독 처리 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
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
          <button className={styles.subscribeButton} onClick={handleOpenModal}>
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
        loading={loading}
      />
    </>
  );
};

export default SubscriptionPage;
