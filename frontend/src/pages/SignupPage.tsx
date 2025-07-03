import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import apiClient from '../api';
import styles from './LoginPage.module.css';

const SignupPage: React.FC = () => {
  const [loginId, setLoginId] = useState('');
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [isKtCustomer, setIsKtCustomer] = useState(false);
  const [isAuthor, setIsAuthor] = useState(false);
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
      const payload = {
        name: name,
        loginId: loginId,
        password: password,
        isKtCustomer: isKtCustomer,
        isAuthor: isAuthor,
      };

      await apiClient.post('/users', payload);
      alert('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.');
      navigate('/login');
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || err.response?.data || '회원가입 중 오류가 발생했습니다.';
      setError(errorMessage);
      console.error(err);
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
          <div className={styles.inputGroup}>
            <label htmlFor="name">이름</label>
            <input id="name" type="text" value={name} onChange={(e) => setName(e.target.value)} required />
          </div>
          
          <div className={styles.inputGroup}>
            <label htmlFor="loginId">로그인 ID (이메일)</label>
            <input id="loginId" type="email" value={loginId} onChange={(e) => setLoginId(e.target.value)} required />
          </div>

          <div className={styles.inputGroup}>
            <label htmlFor="password">비밀번호</label>
            <input id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
          </div>
          <div className={styles.inputGroup}>
            <label htmlFor="confirmPassword">비밀번호 확인</label>
            <input id="confirmPassword" type="password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
          </div>
          
          <div className={styles.checkboxGroup}>
            <input
              id="isAuthor"
              type="checkbox"
              checked={isAuthor}
              onChange={(e) => setIsAuthor(e.target.checked)}
            />
            <label htmlFor="isAuthor">작가로 가입하시겠습니까?</label>
          </div>

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
