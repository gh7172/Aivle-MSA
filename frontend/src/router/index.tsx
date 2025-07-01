import { createBrowserRouter, RouterProvider, Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import type { RootState } from '../store/store';

// 페이지 임포트
import HomePage from '../pages/HomePage';
import LoginPage from '../pages/LoginPage';
import SignupPage from '../pages/SignupPage';
import BookDetailPage from '../pages/BookDetailPage';
import EditorPage from '../pages/EditorPage';
import SubscriptionPage from '../pages/SubscriptionPage';
import MainLayout from '../layouts/MainLayout';
import MyPage from '../pages/MyPage'; // 👈 마이페이지 임포트
import AuthorApplicationPage from '../pages/AuthorApplicationPage'; // 👈 작가신청 임포트
import AdminPage from '../pages/AdminPage'; // 👈 관리자페이지 임포트

const AppRouter = () => {
  const { isAuthenticated, roles } = useSelector((state: RootState) => state.user);

  const router = createBrowserRouter([
    {
      path: '/',
      element: isAuthenticated ? <MainLayout /> : <Navigate to="/login" />,
      children: [
        { index: true, element: <HomePage /> },
        { path: 'book/:bookId', element: <BookDetailPage /> },
        { path: 'subscribe', element: <SubscriptionPage /> },
        { path: 'mypage', element: <MyPage /> },
        { path: 'author-apply', element: <AuthorApplicationPage /> },
        // 👇 ADMIN 역할이 있을 때만 접근 가능한 경로
        { path: 'admin', element: roles.includes('ADMIN') ? <AdminPage /> : <Navigate to="/" /> },
        // 👇 WRITER 역할이 있을 때만 접근 가능한 경로
        { path: 'write', element: roles.includes('WRITER') ? <EditorPage /> : <Navigate to="/" /> },
      ],
    },
    { path: '/login', element: !isAuthenticated ? <LoginPage /> : <Navigate to="/" /> },
    { path: '/signup', element: !isAuthenticated ? <SignupPage /> : <Navigate to="/" /> },
  ]);

  return <RouterProvider router={router} />;
};

export default AppRouter;