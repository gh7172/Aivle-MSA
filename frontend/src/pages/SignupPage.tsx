import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import apiClient from '../api';
import styles from './LoginPage.module.css';

const SignupPage: React.FC = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [isKtCustomer, setIsKtCustomer] = useState(false); // 👈 KT 고객 여부 상태 추가
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      setError('비밀번호가 일치하지 않습니다.');
      return;
    }
    setLoading(true);
    setError('');

    try {
      // 👇 백엔드 API 명세에 맞게 주소와 전송 데이터를 수정
      await apiClient.post('/users', { 
        loginId: email, // 'email'을 'loginId'로 변경
        password: password,
        name: name,
        isAuthor: false, // 기본값으로 isAuthor 설정
        isKtCustomer: isKtCustomer 
      });
      alert('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.');
      navigate('/login');
    } catch (err: any) {
      if (err.response && err.response.data && err.response.data.message) {
        setError(err.response.data.message);
      } else {
        setError('회원가입 중 오류가 발생했습니다. 입력 정보를 확인하고 다시 시도해 주세요.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.loginPage}>
      <div className={styles.loginContainer}>
        <h1 className={styles.title}>회원가입</h1>
        <p className={styles.subtitle}>'걷다가 서재'의 작가 또는 독자가 되어보세요.</p>

        <form onSubmit={handleSubmit} className={styles.form}>
          {/* 이름, 이메일, 비밀번호 입력 필드는 기존과 동일 */}
          <div className={styles.inputGroup}>
            <label htmlFor="name">이름</label>
            <input id="name" type="text" value={name} onChange={(e) => setName(e.target.value)} required />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="email">이메일</label>
            <input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="password">비밀번호</label>
            <input id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="confirmPassword">비밀번호 확인</label>
            <input id="confirmPassword" type="password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
          </div>
          
          {/* 👇 KT 고객 여부 체크박스 추가 */}
          <div className={styles.checkboxGroup}>
            <input
              id="ktCustomer"
              type="checkbox"
              checked={isKtCustomer}
              onChange={(e) => setIsKtCustomer(e.target.checked)}
            />
            <label htmlFor="ktCustomer">KT 고객이신가요? (5,000P 추가 지급)</label>
          </div>

          {error && <p className={styles.errorMessage}>{error}</p>}

          <button type="submit" className={styles.loginButton} disabled={loading}>
            {loading ? '가입 진행 중...' : '가입하기'}
          </button>
        </form>
        
        <div className={styles.links}>
          <span>이미 계정이 있으신가요?</span>
          <Link to="/login">로그인</Link>
        </div>
      </div>
    </div>
  );
};

export default SignupPage;