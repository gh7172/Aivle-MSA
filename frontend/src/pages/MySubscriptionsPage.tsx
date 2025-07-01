import React from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import type { RootState } from '../store/store';
import BookCard from '../components/BookCard';
import styles from './MySubscriptionsPage.module.css';

const MySubscriptionsPage: React.FC = () => {
  const navigate = useNavigate();
  
  // 모든 책 목록과 내가 구독한 책 ID 목록을 가져옵니다.
  const allBooks = useSelector((state: RootState) => [...state.books.books, ...state.books.bestsellers]);
  const subscribedBookIds = useSelector((state: RootState) => state.user.subscribedBookIds);

  // ID를 기반으로 내가 구독한 책 객체들만 필터링합니다.
  const subscribedBooks = allBooks.filter(book => subscribedBookIds.includes(book.id));

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>내가 구독한 책</h1>
      {subscribedBooks.length > 0 ? (
        <div className={styles.bookListGrid}>
          {subscribedBooks.map(book => (
            <BookCard
              key={book.id}
              book={book}
              isBestseller={book.viewCount >= 500} // 베스트셀러 여부 판단
              onClick={() => navigate(`/book/${book.id}`)}
            />
          ))}
        </div>
      ) : (
        <p>아직 구독한 책이 없습니다.</p>
      )}
    </div>
  );
};

export default MySubscriptionsPage;