import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { Link } from 'react-router-dom'; // Link 컴포넌트 추가
import { login, fetchUserProfile } from '../store/slices/userSlice';
import type { AppDispatch } from '../store/store';
import styles from './LoginPage.module.css'; // CSS 모듈 임포트

const LoginPage: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(''); // 에러 메시지 상태 추가
  const dispatch = useDispatch<AppDispatch>();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(''); // 요청 시작 시 에러 메시지 초기화
    try {
      await dispatch(login({ email, password })).unwrap();
      await dispatch(fetchUserProfile());
      // 성공 시 라우터가 자동으로 홈으로 이동시킴
    } catch (err) {
      setError('이메일 또는 비밀번호가 올바르지 않습니다.'); // 실패 시 에러 메시지 설정
      setLoading(false);
    }
  };

  return (
    <div className={styles.loginPage}>
      <div className={styles.loginContainer}>
        <h1 className={styles.title}>걷다가 서재</h1>
        <p className={styles.subtitle}>당신의 이야기를 작품으로, 지금 시작하세요.</p>

        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.inputGroup}>
            <label htmlFor="email">이메일</label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="email@example.com"
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="password">비밀번호</label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="비밀번호"
              required
            />
          </div>

          {error && <p className={styles.errorMessage}>{error}</p>}

          <button type="submit" className={styles.loginButton} disabled={loading}>
            {loading ? '로그인 중...' : '로그인'}
          </button>
        </form>
        
        <div className={styles.links}>
          <Link to="/signup">회원가입</Link>
          <span>|</span>
          <Link to="/forgot-password">비밀번호 찾기</Link>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;