import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import apiClient from "../api";
import type { RootState, AppDispatch } from "../store/store";
import { fetchUserProfile } from "../store/slices/userSlice";
import PaymentModal from "../components/PaymentModal"; // 👈 모달 컴포넌트 임포트
import styles from "./PointShopPage.module.css";
import { useSelector } from "react-redux";

// 타입 정의
interface PointPackage {
  id: string;
  points: number;
  price: number;
  bonus: number;
  color: string;
  best?: boolean;
}

// 포인트 상품 목록 정의
const pointPackages: PointPackage[] = [
  { id: "p1000", points: 1000, price: 1000, bonus: 0, color: "#90a4ae" },
  { id: "p5000", points: 5000, price: 5000, bonus: 200, color: "#ce93d8" },
  { id: "p10000", points: 10000, price: 10000, bonus: 500, color: "#ba68c8" },
  {
    id: "p30000",
    points: 30000,
    price: 30000,
    bonus: 2000,
    color: "#9c27b0",
    best: true,
  },
  { id: "p50000", points: 50000, price: 50000, bonus: 5000, color: "#7b1fa2" },
];

const PointShopPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  // 👇 모달을 제어하기 위해 선택된 상품 상태를 추가합니다.
  const [selectedPackage, setSelectedPackage] = useState<PointPackage | null>(
    null
  );
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const userId = useSelector((state: RootState) => state.user.userInfo?.id);

  // 이 함수는 모달 안의 최종 '결제하기' 버튼을 눌렀을 때 실행됩니다.
  const handleConfirmPayment = async () => {
    if (!selectedPackage) return;
    setLoading(true);

    try {
      // 백엔드에 포인트 구매 요청 API 호출
      const query = `?userId=${userId}&amount=${
        selectedPackage.points + selectedPackage.bonus
      }`;
      await apiClient.post(`/points/charge${query}`);

      alert("포인트 충전이 완료되었습니다.");
      // await dispatch(fetchUserProfile()); // 유저 정보(포인트) 갱신
      navigate("/mypage");
    } catch (error) {
      alert("결제 처리 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
      setSelectedPackage(null); // 모달 닫기
    }
  };

  return (
    <>
      <div className={styles.container}>
        <div className={styles.header}>
          <h1 className={styles.title}>포인트 상점</h1>
          <p className={styles.subtitle}>
            포인트를 충전하고 '걷다가 서재'의 모든 이야기를 만나보세요.
          </p>
        </div>
        <div className={styles.packageGrid}>
          {pointPackages.map((pkg) => (
            <div
              key={pkg.id}
              className={`${styles.card} ${pkg.best ? styles.best : ""}`}
            >
              {pkg.best && <div className={styles.bestBadge}>BEST</div>}
              <div className={styles.points} style={{ color: pkg.color }}>
                💎 {pkg.points.toLocaleString()} P
              </div>
              {pkg.bonus > 0 && (
                <div className={styles.bonus}>
                  + {pkg.bonus.toLocaleString()} P 보너스!
                </div>
              )}
              <div className={styles.price}>{pkg.price.toLocaleString()}원</div>
              <button
                className={styles.purchaseButton}
                // 👇 버튼 클릭 시 바로 결제 로직을 호출하는 대신, 모달을 열도록 상품을 선택합니다.
                onClick={() => setSelectedPackage(pkg)}
              >
                구매하기
              </button>
            </div>
          ))}
        </div>
      </div>

      {/* 👇 선택된 상품이 있을 때만 결제 모달을 렌더링합니다. */}
      <PaymentModal
        isOpen={!!selectedPackage}
        onClose={() => setSelectedPackage(null)}
        onConfirm={handleConfirmPayment}
        productName={`${selectedPackage?.points.toLocaleString()}P 충전`}
        price={selectedPackage?.price || 0}
        loading={loading}
      />
    </>
  );
};

export default PointShopPage;
