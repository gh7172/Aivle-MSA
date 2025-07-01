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
  onClick: () => void;
}

const BookCard: React.FC<BookCardProps> = ({ book, isBestseller, onClick }) => {
  return (
    <div className={styles.card} onClick={onClick}>
      <div className={styles.imageContainer}>
        <img src={book.coverImageUrl || 'https://via.placeholder.com/200x280'} alt={book.title} className={styles.coverImage} />
        {isBestseller && <span className={styles.bestsellerBadge}>BEST</span>}
      </div>
      <div className={styles.info}>
        <h3 className={styles.title}>{book.title}</h3>
        <p className={styles.author}>{book.authorName}</p>
      </div>
    </div>
  );
};

export default BookCard;