import React, { useState, useEffect } from 'react';
import apiClient from '../api';
import styles from './AdminPage.module.css';

interface Application {
  applicationId: string;
  userId: string;
  userName: string;
  userEmail: string;
  bio: string;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
}

const AdminPage: React.FC = () => {
  const [applications, setApplications] = useState<Application[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchApplications = async () => {
      try {
        // 관리자용 작가 신청 목록 API 호출
        const response = await apiClient.get('/admin/applications');
        setApplications(response.data);
      } catch (error) {
        console.error("신청 목록을 불러오는데 실패했습니다.", error);
      } finally {
        setLoading(false);
      }
    };
    fetchApplications();
  }, []);

  const handleDecision = async (applicationId: string, decision: 'approve' | 'reject') => {
    try {
      // 백엔드의 승인/반려 API 호출
      await apiClient.post(`/admin/${decision}/${applicationId}`);
      // 처리된 신청을 목록에서 제거
      setApplications(apps => apps.filter(app => app.applicationId !== applicationId));
      alert(`신청이 ${decision === 'approve' ? '승인' : '반려'}되었습니다.`);
    } catch (error) {
      alert('처리 중 오류가 발생했습니다.');
    }
  };

  if (loading) return <div>신청 목록을 불러오는 중...</div>;

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>관리자 페이지: 작가 신청 관리</h1>
      <div className={styles.tableContainer}>
        {applications.length === 0 ? (
          <p>새로운 작가 신청이 없습니다.</p>
        ) : (
          applications.map(app => (
            <div key={app.applicationId} className={styles.applicationCard}>
              <div className={styles.cardHeader}>
                <strong>{app.userName}</strong> ({app.userEmail})
              </div>
              <div className={styles.cardBody}>
                <p>{app.bio}</p>
              </div>
              <div className={styles.cardFooter}>
                <button onClick={() => handleDecision(app.applicationId, 'reject')} className={`${styles.decisionButton} ${styles.reject}`}>반려</button>
                <button onClick={() => handleDecision(app.applicationId, 'approve')} className={`${styles.decisionButton} ${styles.approve}`}>승인</button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default AdminPage;