import React from 'react';
import { useParams } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import type { RootState, AppDispatch } from '../store/store';
import { fetchUserProfile } from '../store/slices/userSlice';
import apiClient from '../api';
import SubscribedBookActions from '../components/SubscribedBookActions'; // 액션 버튼 임포트
import styles from './BookDetailPage.module.css';

const BookDetailPage: React.FC = () => {
  const { bookId } = useParams<{ bookId: string }>();
  const dispatch = useDispatch<AppDispatch>();

  // Redux 스토어에서 필요한 모든 정보를 가져옵니다.
  const allBooks = useSelector((state: RootState) => [...state.books.books, ...state.books.bestsellers]);
  const { points: userPoints, isSubscribed, subscribedBookIds } = useSelector((state: RootState) => state.user);

  // 현재 페이지에 맞는 책 정보를 찾습니다.
  const book = allBooks.find(b => b.id === bookId);

  // 👇 이 책에 대한 접근 권한이 있는지 확인합니다.
  // 월정액 구독자이거나, 포인트로 이 책을 구독한 경우 true가 됩니다.
  const hasAccess = isSubscribed || subscribedBookIds.includes(book?.id || '');

  // '포인트로 구독하기' 버튼 클릭 시 실행될 함수
  const handlePointSubscribe = async () => {
    if (!book) return;

    if (userPoints < book.requiredPoints) {
      alert(`포인트가 부족합니다. (필요: ${book.requiredPoints.toLocaleString()}P, 보유: ${userPoints.toLocaleString()}P)`);
      return;
    }

    if (!window.confirm(`${book.requiredPoints.toLocaleString()} 포인트로 이 책을 구독하시겠습니까?`)) {
      return;
    }

    try {
      await apiClient.post(`/read/${book.id}`);
      alert('구독이 완료되었습니다! 이제 이 책을 자유롭게 열람할 수 있습니다.');
      // 유저 정보를 새로고침하여 포인트 및 구독 목록을 갱신합니다.
      const token = localStorage.getItem('accessToken');
      const userId = localStorage.getItem('userId');

      if (token && userId) {
        dispatch(fetchUserProfile(Number(userId)));
    }
    } catch (error) {
      alert('구독 처리 중 오류가 발생했습니다.');
    }
  };

  if (!book) {
    return <div className={styles.container}><p>해당 책을 찾을 수 없습니다.</p></div>;
  }

  return (
    <div className={styles.container}>
      <div className={styles.bookDetail}>
        <img src={book.coverImageUrl} alt={book.title} className={styles.coverImage} />
        <div className={styles.info}>
          <h1 className={styles.title}>{book.title}</h1>
          <h2 className={styles.author}>{book.authorName}</h2>
          <p className={styles.summary}>{book.summary}</p>
          <div className={styles.meta}>
            <span>조회수: {book.viewCount}</span>
          </div>
          <div className={styles.subscribeBox}>
            {hasAccess ? (
              // --- 접근 권한이 있는 경우 (구독자 또는 포인트 구매자) ---
              <SubscribedBookActions bookId={book.id} bookTitle={book.title} />
            ) : (
              // --- 접근 권한이 없는 경우 ---
              <>
                <div className={styles.price}>{book.requiredPoints.toLocaleString()} P</div>
                <button onClick={handlePointSubscribe} className={styles.subscribeButton}>
                  포인트로 구독하기
                </button>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookDetailPage;