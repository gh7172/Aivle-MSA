import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import apiClient from '../api';
import type { AppDispatch } from '../store/store';
import { fetchUserProfile } from '../store/slices/userSlice';
import styles from './PointShopPage.module.css';

// 포인트 상품 목록 정의
const pointPackages = [
  { id: 'p1000', points: 1000, price: 1000, bonus: 0, color: '#e0e0e0' },
  { id: 'p5000', points: 5000, price: 5000, bonus: 200, color: '#ce93d8' },
  { id: 'p10000', points: 10000, price: 10000, bonus: 500, color: '#ba68c8' },
  { id: 'p30000', points: 30000, price: 30000, bonus: 2000, color: '#9c27b0', best: true },
  { id: 'p50000', points: 50000, price: 50000, bonus: 5000, color: '#7b1fa2' },
];

const PointShopPage: React.FC = () => {
  const [loadingId, setLoadingId] = useState<string | null>(null);
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();

  const handlePurchase = async (packageId: string) => {
    setLoadingId(packageId);

    // 실제 환경에서는 외부 결제 모듈(PG사) 연동이 필요합니다.
    // 여기서는 성공했다고 가정하고 API 호출을 시뮬레이션합니다.
    try {
      if(window.confirm(`${pointPackages.find(p=>p.id === packageId)?.price.toLocaleString()}원을 결제하시겠습니까?`)){
        // 백엔드에 포인트 구매 요청 API 호출
        await apiClient.post('/purchase/points', { packageId });

        alert('포인트 충전이 완료되었습니다.');
        await dispatch(fetchUserProfile()); // 유저 정보(포인트) 갱신
        navigate('/mypage'); // 마이페이지로 이동하여 충전 결과 확인
      }
    } catch (error) {
      alert('결제 처리 중 오류가 발생했습니다.');
    } finally {
      setLoadingId(null);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1 className={styles.title}>포인트 상점</h1>
        <p className={styles.subtitle}>포인트를 충전하고 '걷다가 서재'의 모든 이야기를 만나보세요.</p>
      </div>
      <div className={styles.packageGrid}>
        {pointPackages.map(pkg => (
          <div key={pkg.id} className={`${styles.card} ${pkg.best ? styles.best : ''}`}>
            {pkg.best && <div className={styles.bestBadge}>BEST</div>}
            <div className={styles.points} style={{ color: pkg.color }}>
              {pkg.points.toLocaleString()} P
            </div>
            {pkg.bonus > 0 && (
              <div className={styles.bonus}>
                + {pkg.bonus.toLocaleString()} P 보너스!
              </div>
            )}
            <div className={styles.price}>
              {pkg.price.toLocaleString()}원
            </div>
            <button
              className={styles.purchaseButton}
              onClick={() => handlePurchase(pkg.id)}
              disabled={loadingId === pkg.id}
            >
              {loadingId === pkg.id ? '처리 중...' : '구매하기'}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default PointShopPage;