import React from 'react';
import styles from './PaymentModal.module.css';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
  productName: string;
  price: number;
  paymentType?: 'CASH' | 'POINTS';
  loading: boolean;
}

const PaymentModal: React.FC<Props> = ({ isOpen, onClose, onConfirm, productName, price, paymentType = 'CASH', loading }) => {
  if (!isOpen) return null;

  const priceFormatted = paymentType === 'CASH' ? `${price.toLocaleString()}원` : `${price.toLocaleString()} P`;

  return (
    <div className={styles.modalOverlay} onClick={onClose}>
      <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
        <div className={styles.header}>
          <h2>결제하기</h2>
          <button onClick={onClose} className={styles.closeButton}>&times;</button>
        </div>
        <div className={styles.body}>
          <div className={styles.productInfo}>
            <span>주문상품</span>
            <span>{productName}</span>
          </div>
          <div className={styles.productInfo}>
            <strong>최종 결제 금액</strong>
            <strong className={styles.finalPrice}>{priceFormatted}</strong>
          </div>
        </div>
        <div className={styles.footer}>
          <button className={styles.confirmButton} onClick={onConfirm} disabled={loading}>
            {loading ? '결제 진행 중...' : `${priceFormatted} 결제하기`}
          </button>
        </div>
      </div>
    </div>
  );
};

export default PaymentModal;