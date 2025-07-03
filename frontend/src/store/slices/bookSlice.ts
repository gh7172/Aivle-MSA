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
  try {
    const response = await apiClient.get('/books');
    if (response.data && response.data._embedded && Array.isArray(response.data._embedded.books)) {
      return response.data._embedded.books as Book[];
    }
    return []; 
  } catch (error) {
    console.error("Failed to fetch books:", error);
    return [];
  }
});

export const createDraftBook = createAsyncThunk(
  'books/createDraft',
  async (draftData: { title: string; text: string }) => {
    const response = await apiClient.post('/write', draftData);
    return response.data;
  }
);

export const requestPublication = createAsyncThunk(
  'books/requestPublication',
  async (bookId: number) => {
    await apiClient.post(`/write/${bookId}/requestPublication`, {});
    return bookId;
  }
);

const initialState: BookState = {
  books: [],
  bestsellers: [],
  status: 'idle',
  error: null,
};

const bookSlice = createSlice({
  name: 'books',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      // fetchBooks 상태 처리
      .addCase(fetchBooks.pending, (state) => { state.status = 'loading'; })
      .addCase(fetchBooks.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.books = action.payload;
      })
      .addCase(fetchBooks.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })
      
      // createDraftBook 상태 처리
      .addCase(createDraftBook.pending, (state) => { state.status = 'loading'; })
      .addCase(createDraftBook.fulfilled, (state) => { state.status = 'succeeded'; })
      .addCase(createDraftBook.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      })

      // requestPublication 상태 처리
      .addCase(requestPublication.pending, (state) => { state.status = 'loading'; })
      .addCase(requestPublication.fulfilled, (state) => { state.status = 'succeeded'; })
      .addCase(requestPublication.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message || null;
      });
  },
});

export default bookSlice.reducer;