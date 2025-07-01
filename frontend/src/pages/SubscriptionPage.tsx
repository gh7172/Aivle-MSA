import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../api';
import styles from './SubscriptionPage.module.css';

const SubscriptionPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubscribe = async () => {
    setLoading(true);
    try {
      // 백엔드의 구독 처리 API 호출
      await apiClient.post('/subscription/subscribe');
      alert('구독이 완료되었습니다! 모든 콘텐츠를 무제한으로 즐겨보세요.');
      navigate('/'); // 성공 시 홈으로 이동
    } catch (error) {
      alert('구독 처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.planBox}>
        <div className={styles.header}>
          <h1 className={styles.title}>프리미엄 구독</h1>
          <p className={styles.subtitle}>모든 이야기를 제한 없이 만나보세요.</p>
        </div>
        <div className={styles.priceSection}>
          <span className={styles.price}>월 9,900원</span>
          <span className={styles.vat}>VAT 포함</span>
        </div>
        <ul className={styles.features}>
          <li>✔️ 모든 전자책 무제한 열람</li>
          <li>✔️ 오디오북 콘텐츠 이용 가능</li>
          <li>✔️ 챗북 시리즈 독점 제공</li>
          <li>✔️ 광고 없이 쾌적한 독서</li>
        </ul>
        <button className={styles.subscribeButton} onClick={handleSubscribe} disabled={loading}>
          {loading ? '처리 중...' : '9,900원으로 구독 시작하기'}
        </button>
      </div>
    </div>
  );
};

export default SubscriptionPage;