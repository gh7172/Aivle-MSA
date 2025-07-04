import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { fetchBooks } from '../store/slices/bookSlice';
import { fetchUserProfile } from '../store/slices/userSlice';
import type { RootState, AppDispatch } from '../store/store';
import BookCard from '../components/BookCard';
import SkeletonCard from '../components/SkeletonCard';
import styles from './HomePage.module.css';

// 로딩 중에 보여줄 스켈레톤 UI 컴포넌트
const LoadingSkeleton = () => (
  <>
    <h2 className={styles.sectionTitle}> </h2>
    <div className={styles.bookListHorizontal}>
      {/* Array.from을 사용해 스켈레톤 카드를 5개 생성 */}
      {Array.from({ length: 5 }).map((_, index) => (
        <SkeletonCard key={index} />
      ))}
    </div>
    <h2 className={styles.sectionTitle}> </h2>
    <div className={styles.bookListGrid}>
      {Array.from({ length: 10 }).map((_, index) => (
        <SkeletonCard key={index} />
      ))}
    </div>
  </>
);


const HomePage: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();
  const { books, bestsellers, status: bookStatus } = useSelector((state: RootState) => state.books);
  const { status: userStatus } = useSelector((state: RootState) => state.user);

  useEffect(() => {
    if (bookStatus === 'idle') {
      dispatch(fetchBooks());
    }
    if (userStatus === 'idle') {
      const token = localStorage.getItem('accessToken');
      const userId = localStorage.getItem('userId');

      if (token && userId) {
        dispatch(fetchUserProfile(Number(userId)));
    }
    }
  }, [bookStatus, userStatus, dispatch]);

  // 👇 로딩 상태일 때 '로딩 중...' 텍스트 대신 LoadingSkeleton 컴포넌트를 보여줍니다.
  if (bookStatus === 'loading' || userStatus === 'loading' || userStatus === 'idle') {
    return (
      <div className={styles.homeContainer}>
        <LoadingSkeleton />
      </div>
    );
  }

  return (
    <div className={styles.homeContainer}>
      <h2 className={styles.sectionTitle}>🔥 베스트셀러</h2>
      <div className={styles.bookListHorizontal}>
        {bestsellers.map((book) => (
          <BookCard
            key={book.id}
            book={book}
            isBestseller={true}
            onClick={() => navigate(`/book/${book.id}`)}
          />
        ))}
      </div>

      <h2 className={styles.sectionTitle}>✨ 새로운 작품</h2>
      <div className={styles.bookListGrid}>
        {books.map((book) => (
          <BookCard
            key={book.id}
            book={book}
            isBestseller={book.viewCount >= 5}
            onClick={() => navigate(`/book/${book.id}`)}
          />
        ))}
      </div>
    </div>
  );
};

export default HomePage;