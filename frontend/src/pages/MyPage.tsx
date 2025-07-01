import React from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import type { RootState } from '../store/store';
import styles from './MyPage.module.css';

const MyPage: React.FC = () => {
  const user = useSelector((state: RootState) => state.user.userInfo);
  const roles = useSelector((state: RootState) => state.user.roles);
  const points = useSelector((state: RootState) => state.user.points);

  const isWriter = roles.includes('WRITER');
  // 실제로는 user.authorApplicationStatus 같은 값으로 '심사 중' 상태를 관리해야 합니다.

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>{user?.name}님의 마이페이지</h1>
      <div className={styles.card}>
        <Link to="/my-subscriptions" className={styles.menuLink}>
          <div className={styles.infoRow}>
            <strong>내가 구독한 책</strong>
            <span>&gt;</span>
          </div>
        </Link>
        <div className={styles.infoRow}>
          <strong>포인트</strong>
          <span>{points.toLocaleString()} P</span>
        </div>
        <div className={styles.infoRow}>
          <strong>현재 역할</strong>
          <span>{isWriter ? '작가' : '구독자'}</span>
        </div>
      </div>

      {!isWriter && (
        <div className={styles.card}>
          <h2 className={styles.cardTitle}>작가 되기</h2>
          <p>당신의 이야기를 세상에 선보이세요. 지금 바로 작가 신청을 하고 멋진 작품을 공유해보세요.</p>
          {/* '심사 중' 상태가 아닐 때만 버튼을 보여줍니다. */}
          <Link to="/author-apply" className={styles.applyButton}>작가 신청하기</Link>
        </div>
      )}
    </div>
  );
};

export default MyPage;