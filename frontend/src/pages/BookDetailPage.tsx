import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import type { RootState, AppDispatch } from '../store/store';
import type { Book } from '../store/slices/bookSlice'; // Book 타입 임포트
import { fetchUserProfile } from '../store/slices/userSlice';
import apiClient from '../api';
import styles from './BookDetailPage.module.css';

const BookDetailPage: React.FC = () => {
  const { bookId } = useParams<{ bookId: string }>();
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();

  // Redux 스토어에서 모든 책 목록과 사용자 정보를 가져옵니다.
  const allBooks = useSelector((state: RootState) => state.books.books);
  const { points: userPoints } = useSelector((state: RootState) => state.user);

  // 현재 페이지에 맞는 책 정보를 찾습니다.
  const book = allBooks.find(b => b.id === bookId);

  const handleSubscribe = async () => {
    if (!book) return;

    // 1. 포인트 확인
    if (userPoints < book.requiredPoints) {
      alert(`포인트가 부족합니다. (필요: ${book.requiredPoints}P, 보유: ${userPoints}P)`);
      return;
    }

    // 2. 구독 여부 확인
    if (!window.confirm(`${book.requiredPoints} 포인트로 이 책을 구독하시겠습니까?`)) {
      return;
    }

    try {
      // 3. 백엔드에 구독(구매) 요청 API 호출
      await apiClient.post(`/read/${book.id}`); // 이 API는 백엔드에서 포인트 차감 로직을 처리합니다.

      alert('구독이 완료되었습니다. 이제 이 책을 자유롭게 열람할 수 있습니다.');

      // 4. 최신 포인트 정보를 반영하기 위해 유저 프로필을 다시 불러옵니다.
      dispatch(fetchUserProfile());

      // 5. 실제 뷰어 페이지로 이동 (지금은 홈으로 이동)
      navigate('/');

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
            <div className={styles.price}>{book.requiredPoints.toLocaleString()} P</div>
            <button onClick={handleSubscribe} className={styles.subscribeButton}>
              포인트로 구독하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookDetailPage;