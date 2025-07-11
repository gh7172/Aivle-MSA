import { configureStore } from '@reduxjs/toolkit';
import userReducer from './slices/userSlice';
import bookReducer from './slices/bookSlice';

export const store = configureStore({
  reducer: {
    user: userReducer,
    books: bookReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;