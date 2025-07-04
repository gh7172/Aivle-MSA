import React, { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { useNavigate } from 'react-router-dom';
import apiClient from '../api';
import styles from './EditorPage.module.css';

const modules = {
  toolbar: [
    [{ 'header': [1, 2, 3, false] }],
    ['bold', 'italic', 'underline', 'strike'],
    ['blockquote', 'code-block'],
    [{ 'list': 'ordered'}, { 'list': 'bullet' }],
    [{ 'align': [] }],
    ['link', 'image'],
    ['clean']
  ],
};

const EditorPage: React.FC = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const [bookId, setBookId] = useState<number | null>(null);

  const handlePublish = async () => {
    setLoading(true);
    try {
      await apiClient.post(`/write/${bookId}/requestPublication`, {});
      alert('출간 요청이 완료되었습니다. AI가 작업을 시작합니다.');
      navigate('/');
    } catch (error: any) {
      const errorMessage = error.response?.data?.message || '출간 요청 중 오류가 발생했습니다.';
      alert(errorMessage);
    } finally {
      setLoading(false);
    }
  };
  const handleDraft = async () => {
    if (!title.trim()) {
      alert('제목을 입력해주세요.');
      return;
    }
    if (!content.trim() || content === '<p><br></p>') {
      alert('내용을 입력해주세요.');
      return;
    }

    setLoading(true);
    try {
      const payload = {
        title,
        text: content,
      };

      if (bookId) {
        // bookId가 있으면 기존 글을 업데이트합니다 (PUT).
        await apiClient.put(`/write/${bookId}`, payload);
        alert('임시 저장되었습니다.');
      } else {
        // bookId가 없으면 새 글을 생성합니다 (POST).
        const response = await apiClient.post('/write', payload);
        const newBookIdUrl = response.data._links.self.href;
        const newBookId = Number(newBookIdUrl.split("/").pop());
        setBookId(newBookId);
        alert('초고가 저장되었습니다. 계속해서 작업할 수 있습니다.');
      }
    } catch (error: any) {
      const errorMessage = error.response?.data?.message || '저장 중 오류가 발생했습니다.';
      alert(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.editorWrapper}>
      <div className={styles.editorContainer}>
        <div className={styles.titleArea}>
          <input
            type="text"
            className={styles.titleInput}
            placeholder="제목을 작성해주세요"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>

        <ReactQuill
          theme="snow"
          value={content}
          onChange={setContent}
          modules={modules}
          placeholder="내용을 작성해주세요"
        />
      </div>

      {/* 👇 출간 요청 버튼을 화면 하단으로 이동 */}
      <footer className={styles.editorFooter}>
        <button onClick={handleDraft} className={styles.publishButton} disabled={loading}>
          {loading ? '처리 중...' : '저장'}
        </button>
        <button disabled={!bookId} onClick={handlePublish} className={styles.publishButton}>
          {!bookId ? '저장해주세요' : '출간 요청'}
        </button>
      </footer>
    </div>
  );
};

export default EditorPage;