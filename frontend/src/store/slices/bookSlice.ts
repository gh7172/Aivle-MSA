import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import apiClient from '../../api';

// 타입 정의
export interface Book { 
  id: string;
  title: string;
  authorName: string;
  coverImageUrl: string;
  summary: string;
  viewCount: number;
  requiredPoints: number;
}

interface BookState {
  books: Book[];
  bestsellers: Book[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
}

export const fetchBooks = createAsyncThunk('books/fetchBooks', async () => {
  const response = await apiClient.get('/books');
  return response.data as Book[]; // 받아오는 데이터 타입을 명시해주는 것이 좋습니다.
});

const initialState: BookState = {
  books: [
    // 👇 '새로운 작품'으로 표시될 책
    {
      id: 'new-book-1',
      title: '달빛 아래 코딩',
      authorName: '박자바',
      summary: '밤하늘의 달을 보며 영감을 얻는 한 개발자의 성장 스토리. 그의 코드는 세상을 어떻게 바꿀까?',
      coverImageUrl: 'https://images.unsplash.com/photo-1532012197267-da84d127e765?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
      viewCount: 15,
      requiredPoints: 500,
    }
  ],
  bestsellers: [
    // 👇 '베스트셀러'로 표시될 책
    {
      id: 'best-book-1',
      title: 'AI가 써내려간 여름',
      authorName: '김지피티',
      summary: '한여름, 인공지능 시인 ‘하루’가 인간의 감정을 배우며 써내려가는 가슴 시린 이야기.',
      coverImageUrl: 'https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
      viewCount: 550,
      requiredPoints: 500,
    }
  ],
  status: 'idle', // 초기 상태는 'idle'
  error: null,
};

const bookSlice = createSlice({
  name: 'books',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      // API 요청이 시작되었을 때
      .addCase(fetchBooks.pending, (state) => {
        state.status = 'loading';
      })
      // API 요청이 성공적으로 끝났을 때
      .addCase(fetchBooks.fulfilled, (state, action) => {
        state.status = 'succeeded';
        // API로부터 받아온 책 목록으로 state를 업데이트
        state.books = action.payload;
      })
      // API 요청이 실패했을 때
      .addCase(fetchBooks.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || 'Something went wrong';
      });
  },
});

export default bookSlice.reducer;