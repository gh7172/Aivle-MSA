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
}

export const fetchBooks = createAsyncThunk('books/fetchBooks', async () => {
  const response = await apiClient.get('/books');
  return response.data;
});

const initialState: BookState = {
  books: [
    // 👇 테스트를 위한 임시 책 데이터
    {
      id: 'mock-book-1',
      title: 'AIVLE',
      authorName: '김철수',
      summary: '한여름, 시인 ‘하루’가 인간의 감정을 배우며 써내려가는 가슴 시린 이야기.',
      coverImageUrl: 'https://images.unsplash.com/photo-1595864057944-bec21293d35c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
      viewCount: 120,
      requiredPoints: 500,
    }
  ],
  bestsellers: [], // 베스트셀러는 동적으로 계산되므로 비워둡니다.
  status: 'succeeded', // 데이터를 이미 가지고 있으므로 'succeeded'로 설정
};

const bookSlice = createSlice({
  name: 'books',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    // ... 기존 extraReducers 로직 ...
  },
});

export default bookSlice.reducer;