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
  const [bookId, setBookId] = useState<Number>()

  const handlePublish = async () => {
    // if (!title.trim()) {
    //   alert('제목을 입력해주세요.');
    //   return;
    // }
    // if (!content.trim() || content === '<p><br></p>') {
    //   alert('내용을 입력해주세요.');
    //   return;
    // }
    
    setLoading(true);
    try {
      await apiClient.post('http://localhost:8080/write/'+bookId+'/requestPublication',{});
      alert('출간 요청이 완료되었습니다. AI가 작업을 시작합니다.');
      navigate('/');
    } catch (error) {
      alert(error);
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
      const response = await apiClient.post('http://localhost:8080/write', {
      title,
      text: content,
    });
      alert('저장 완료');
      console.log("응답 데이터:", response.data);
      const newBookId = response.data._links.self.href;
      const num = Number(newBookId.split("/").pop())
      console.log(num);
      setBookId(num)
    } catch (error) {
      alert(error);
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