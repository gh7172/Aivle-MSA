import React from 'react';
import styles from './SkeletonCard.module.css';

const SkeletonCard: React.FC = () => {
  return (
    <div className={styles.card}>
      <div className={`${styles.skeleton} ${styles.coverImage}`}></div>
      <div className={styles.info}>
        <div className={`${styles.skeleton} ${styles.title}`}></div>
        <div className={`${styles.skeleton} ${styles.author}`}></div>
      </div>
    </div>
  );
};

export default SkeletonCard;