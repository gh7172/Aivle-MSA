import React, { useEffect } from 'react';
import { Outlet, Link, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { logout, fetchUserProfile } from '../store/slices/userSlice';
import type { RootState, AppDispatch } from '../store/store';
import styles from './MainLayout.module.css';

const MainLayout: React.FC = () => {
    const dispatch = useDispatch<AppDispatch>();
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user.userInfo);
    const roles = useSelector((state: RootState) => state.user.roles);

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
                    <Link to="/subscribe" className={styles.subscribeLink}>구독하기</Link>
                    
                    {roles.includes('WRITER') && <Link to="/write" className={styles.navLink}>글쓰기</Link>}

                    {roles.includes('ADMIN') && <Link to="/admin" className={styles.navLink}>관리자 페이지</Link>}
                    
                    <div className={styles.userMenu}>
                        {/* 👇 관리자(ADMIN)가 아닐 경우에만 '마이페이지' 메뉴를 보여줌 */}
                        {!roles.includes('ADMIN') && <Link to="/mypage" className={styles.navLink}>마이페이지</Link>}
                        
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