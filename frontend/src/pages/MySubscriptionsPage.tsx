import React from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import type { RootState } from '../store/store';
import SubscribedBookActions from '../components/SubscribedBookActions'; // 액션 버튼 임포트
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

      {/* 👇 카드 그리드 형태에서 목록 형태로 UI 구조를 변경합니다. */}
      <div className={styles.listContainer}>
        {subscribedBooks.length > 0 ? (
          subscribedBooks.map(book => (
            <div key={book.id} className={styles.bookItem}>
              <img 
                src={book.coverImageUrl} 
                alt={book.title} 
                className={styles.coverImage}
                onClick={() => navigate(`/book/${book.id}`)} // 이미지 클릭 시 상세 페이지로 이동
              />
              <div className={styles.bookInfo}>
                <h2 className={styles.bookTitle} onClick={() => navigate(`/book/${book.id}`)}>
                  {book.title}
                </h2>
                <p className={styles.author}>{book.authorName}</p>
                {/* 👇 '바로 읽기'와 'PDF 다운로드' 버튼 컴포넌트를 추가합니다. */}
                <SubscribedBookActions bookId={book.id} bookTitle={book.title} />
              </div>
            </div>
          ))
        ) : (
          <p className={styles.emptyMessage}>아직 구독한 책이 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default MySubscriptionsPage;