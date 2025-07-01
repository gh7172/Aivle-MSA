import React, { useEffect } from 'react';
import { Outlet, Link, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { logout, fetchUserProfile } from '../store/slices/userSlice';
import type { RootState, AppDispatch } from '../store/store';
import styles from './MainLayout.module.css';

const MainLayout: React.FC = () => {
    const dispatch = useDispatch<AppDispatch>();
    const navigate = useNavigate();
    
    // 👇 points를 추가로 가져옵니다.
    const { userInfo: user, roles, isSubscribed, points } = useSelector((state: RootState) => state.user);

    useEffect(() => {
        if (localStorage.getItem('accessToken')) {
            dispatch(fetchUserProfile());
        }
    }, [dispatch]);

    const handleLogout = () => {
        dispatch(logout());
        navigate('/login');
    };

    return (
        <div className={styles.layout}>
            <header className={styles.header}>
                <Link to="/" className={styles.logo}>걷다가 서재</Link>
                <nav className={styles.nav}>
                    {/* 👇 구독하기 버튼 옆에 포인트 충전 버튼을 배치합니다. */}
                    {!isSubscribed && <Link to="/subscribe" className={styles.subscribeLink}>구독하기</Link>}
                    <Link to="/point-shop" className={styles.pointShopLink}>포인트 충전</Link>
                    
                    {roles.includes('WRITER') && <Link to="/write" className={styles.navLink}>글쓰기</Link>}
                    {roles.includes('ADMIN') && <Link to="/admin" className={styles.navLink}>관리자 페이지</Link>}
                    
                    <div className={styles.userMenu}>
                        {!roles.includes('ADMIN') && <Link to="/mypage" className={styles.navLink}>마이페이지</Link>}
                        
                        {/* 👇 현재 보유 포인트를 헤더에 표시합니다. */}
                        <div className={styles.pointDisplay}>
                          💎 {points.toLocaleString()} P
                        </div>

                        <span className={styles.welcome}>{user?.name}님</span>
                        <button onClick={handleLogout} className={styles.logoutButton}>로그아웃</button>
                    </div>
                </nav>
            </header>
            <main className={styles.mainContent}>
                <Outlet />
            </main>
        </div>
    );
};

export default MainLayout;