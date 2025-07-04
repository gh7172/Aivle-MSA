import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import apiClient from '../../api';

// 클라이언트에서 사용하는 Book 모델
export interface Book { 
  id: string;
  title: string;
  authorName: string;
  coverImageUrl: string;
  summary: string;
  viewCount: number;
  requiredPoints: number;
}

// API 응답으로 받는 원시 Book 데이터 모델
interface RawBookFromAPI {
  bookId: number;
  coverImage: string;
  publishDate: string;
  state: string;
  summary: string;
  title: string;
  userId: number;
  viewCount: number;
}

interface BookState {
  books: Book[];
  bestsellers: Book[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
}

export const fetchBooks = createAsyncThunk<Book[], void, { rejectValue: string }>(
  'books/fetchBooks', 
  async (_, { rejectWithValue }) => {
  try {
    const response = await apiClient.get('http://localhost:8080/books');
    const rawBooks: RawBookFromAPI[] = response.data?._embedded?.bookList || response.data?.content || response.data || [];
    
    if (Array.isArray(rawBooks)) {
      // API 응답 데이터를 클라이언트 Book 모델로 매핑합니다.
      console.log(rawBooks)
      return rawBooks.map((book: RawBookFromAPI): Book => ({
        id: String(book.bookId),
        title: book.title,
        authorName: `작가 ${book.userId}`, // authorName은 userId 기반으로 생성, 추후 작가 정보 조회 로직 필요
        coverImageUrl: book.coverImage,
        summary: book.summary,
        viewCount: book.viewCount,
        requiredPoints: 500, // requiredPoints는 API에 없으므로 기본값 0으로 설정
      }));
    }
    return []; 
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || '책 목록을 불러오는데 실패했습니다.');
  }
});

// export const createDraftBook = createAsyncThunk(
//   'books/createDraft',
//   async (draftData: { title: string; text: string }) => {
//     const response = await apiClient.post('/write', draftData);
//     return response.data;
//   }
// );

// export const requestPublication = createAsyncThunk(
//   'books/requestPublication',
//   async (bookId: number) => {
//     await apiClient.post(`/write/${bookId}/requestPublication`, {});
//     return bookId;
//   }
// );

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
        state.error = action.payload || action.error.message || null;
      })
      
      // // createDraftBook 상태 처리
      // .addCase(createDraftBook.pending, (state) => { state.status = 'loading'; })
      // .addCase(createDraftBook.fulfilled, (state) => { state.status = 'succeeded'; })
      // .addCase(createDraftBook.rejected, (state, action) => {
      //   state.status = 'failed';
      //   state.error = action.error.message || null;
      // })

      // // requestPublication 상태 처리
      // .addCase(requestPublication.pending, (state) => { state.status = 'loading'; })
      // .addCase(requestPublication.fulfilled, (state) => { state.status = 'succeeded'; })
      // .addCase(requestPublication.rejected, (state, action) => {
      //   state.status = 'failed';
      //   state.error = action.error.message || null;
      // });
  },
});

export default bookSlice.reducer;