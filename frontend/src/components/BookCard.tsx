import React from 'react';
import styles from './BookCard.module.css';

interface Book {
  id: string;
  title: string;
  authorName: string;
  coverImageUrl: string;
}

interface BookCardProps {
  book: Book;
  isBestseller: boolean;
  isNew?: boolean; // 👈 'isNew' 속성 추가 (선택 사항)
  onClick: () => void;
}

const BookCard: React.FC<BookCardProps> = ({ book, isBestseller, isNew, onClick }) => {
  return (
    <div className={styles.card} onClick={onClick}>
      <div className={styles.imageContainer}>
        <img src={book.coverImageUrl || 'https://via.placeholder.com/200x280'} alt={book.title} className={styles.coverImage} />
        {/* 👇 조건에 따라 다른 마크를 보여줍니다. */}
        {isBestseller && <span className={styles.bestsellerBadge}>BEST</span>}
        {isNew && <span className={`${styles.badgeBase} ${styles.newBadge}`}>NEW</span>}
      </div>
      <div className={styles.info}>
        <h3 className={styles.title}>{book.title}</h3>
        <p className={styles.author}>{book.authorName}</p>
      </div>
    </div>
  );
};

export default BookCard;