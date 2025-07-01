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

  const handlePublish = async () => {
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
      await apiClient.post('/publish/request', { title, content });
      alert('출간 요청이 완료되었습니다. AI가 작업을 시작합니다.');
      navigate('/');
    } catch (error) {
      alert('출간 요청 중 오류가 발생했습니다.');
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
        <button onClick={handlePublish} className={styles.publishButton} disabled={loading}>
          {loading ? '처리 중...' : '출간 요청'}
        </button>
      </footer>
    </div>
  );
};

export default EditorPage;