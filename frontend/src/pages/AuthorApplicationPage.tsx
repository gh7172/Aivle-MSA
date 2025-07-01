import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../api';
import styles from './AuthorApplicationPage.module.css';

const AuthorApplicationPage: React.FC = () => {
  const [bio, setBio] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!bio.trim()) {
      alert('자기소개 및 포트폴리오를 입력해주세요.');
      return;
    }
    setLoading(true);
    try {
      // 백엔드에 작가 신청 API 호출
      await apiClient.post('/author/apply', { bio });
      alert('작가 신청이 완료되었습니다. 관리자 승인 후 활동할 수 있습니다.');
      navigate('/mypage');
    } catch (error) {
      alert('신청 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.formBox}>
        <h1 className={styles.title}>작가 신청하기</h1>
        <p className={styles.subtitle}>자신을 소개하고, 대표 작품이나 포트폴리오 링크를 자유롭게 적어주세요.</p>
        <form onSubmit={handleSubmit}>
          <textarea
            className={styles.textarea}
            value={bio}
            onChange={(e) => setBio(e.target.value)}
            placeholder="자신에 대한 소개, 작품 활동 계획, 포트폴리오 링크 등..."
            rows={10}
          />
          <button type="submit" className={styles.submitButton} disabled={loading}>
            {loading ? '제출 중...' : '신청서 제출하기'}
          </button>
        </form>
      </div>
    </div>
  );
};

export default AuthorApplicationPage;