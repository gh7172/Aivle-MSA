import React from 'react';
import apiClient from '../api';
import styles from './SubscribedBookActions.module.css';

interface Props {
  bookId: string;
  bookTitle: string;
}

const SubscribedBookActions: React.FC<Props> = ({ bookId, bookTitle }) => {
  const handleRead = () => {
    // 실제 뷰어 페이지가 있다면 해당 경로로 이동합니다.
    alert(`'${bookTitle}' 책을 열람합니다.`);
    // navigate(`/viewer/${bookId}`);
  };

  const handleDownload = async () => {
    if (!window.confirm(`'${bookTitle}' PDF 파일을 다운로드하시겠습니까?`)) {
      return;
    }
    try {
      // 1. 백엔드에 PDF 파일 요청 (응답 타입은 'blob'으로 설정)
      const response = await apiClient.get(`/books/${bookId}/pdf`, {
        responseType: 'blob',
      });

      // 2. 응답받은 blob 데이터로 다운로드 링크 생성 및 실행
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `${bookTitle}.pdf`); // 파일 이름 설정
      document.body.appendChild(link);
      link.click();

      // 3. 생성한 링크와 URL 정리
      link.parentNode?.removeChild(link);
      window.URL.revokeObjectURL(url);

    } catch (error) {
      console.error('PDF 다운로드 실패:', error);
      alert('PDF 파일을 다운로드하는 중 오류가 발생했습니다.');
    }
  };

  return (
    <div className={styles.actionsContainer}>
      <button onClick={handleDownload} className={`${styles.actionButton} ${styles.download}`}>
        PDF 다운로드
      </button>
      <button onClick={handleRead} className={`${styles.actionButton} ${styles.read}`}>
        바로 읽기
      </button>
    </div>
  );
};

export default SubscribedBookActions;