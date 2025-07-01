import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import apiClient from '../api';
import type { RootState, AppDispatch } from '../store/store';
import { fetchUserProfile } from '../store/slices/userSlice';
import styles from './SubscriptionPage.module.css';

type PaymentMethod = 'CASH' | 'POINTS';

const SubscriptionPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [paymentMethod, setPaymentMethod] = useState<PaymentMethod>('CASH');
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const userPoints = useSelector((state: RootState) => state.user.points);

  const handleSubscribe = async () => {
    setLoading(true);

    // 포인트 결제 로직
    if (paymentMethod === 'POINTS') {
      const requiredPoints = 9900;
      if (userPoints < requiredPoints) {
        alert(`포인트가 부족합니다. (필요: ${requiredPoints.toLocaleString()}P, 보유: ${userPoints.toLocaleString()}P)`);
        setLoading(false);
        return;
      }
      if (!window.confirm(`${requiredPoints.toLocaleString()} 포인트로 구독하시겠습니까?`)) {
        setLoading(false);
        return;
      }
    }

    // 현금 결제는 외부 결제 모듈 연동이 필요하므로, 여기서는 성공했다고 가정합니다.
    
    try {
      // 백엔드에 선택한 결제 수단 정보 전송
      await apiClient.post('/subscription/subscribe', { paymentMethod });
      
      alert('구독이 완료되었습니다! 모든 콘텐츠를 무제한으로 즐겨보세요.');
      dispatch(fetchUserProfile()); // 포인트 변동이 있을 수 있으므로 유저 정보 갱신
      navigate('/');
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

        {/* 결제 수단 선택 탭 */}
        <div className={styles.paymentTabs}>
          <button
            className={`${styles.tab} ${paymentMethod === 'CASH' ? styles.active : ''}`}
            onClick={() => setPaymentMethod('CASH')}
          >
            현금 결제
          </button>
          <button
            className={`${styles.tab} ${paymentMethod === 'POINTS' ? styles.active : ''}`}
            onClick={() => setPaymentMethod('POINTS')}
          >
            포인트 결제
          </button>
        </div>

        <div className={styles.priceSection}>
          <span className={styles.price}>
            {paymentMethod === 'CASH' ? '월 9,900원' : '9,900 P'}
          </span>
          {paymentMethod === 'CASH' && <span className={styles.vat}>VAT 포함</span>}
        </div>
        
        <ul className={styles.features}>
          <li>✔️ 모든 전자책 무제한 열람</li>
          <li>✔️ 오디오북 콘텐츠 이용 가능</li>
          <li>✔️ 챗북 시리즈 독점 제공</li>
          <li>✔️ 광고 없이 쾌적한 독서</li>
        </ul>

        <button className={styles.subscribeButton} onClick={handleSubscribe} disabled={loading}>
          {loading ? '처리 중...' : `${paymentMethod === 'CASH' ? '9,900원으로' : '9,900P로'} 구독 시작하기`}
        </button>
      </div>
    </div>
  );
};

export default SubscriptionPage;