import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "../../api";

// --- 타입 정의 ---
// UserProfile에서 isWriter를 제거하고, roles 배열로 역할을 관리하는 것이 더 확장성 있습니다.
interface UserProfile {
  id: number;
  name: string;
}

interface UserState {
  userInfo: UserProfile | null;
  points: number;
  isKtCustomer: boolean;
  isAuthenticated: boolean;
  isSubscribed: boolean;
  roles: string[]; // 👈 사용자 역할을 저장할 배열 (예: ['SUBSCRIBER', 'WRITER'])
  status: "idle" | "loading" | "succeeded" | "failed";
  subscribedBookIds: string[];
}

// --- 비동기 Thunk ---
export const login = createAsyncThunk(
  "user/login",
  async (credentials: any, { rejectWithValue }) => {
    try {
      const response = await apiClient.post("/user/login", credentials);
      // 로그인 성공 시 토큰을 localStorage에 저장합니다.
      localStorage.setItem("accessToken", response.data.token);
      return response.data.user;
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);

export const fetchUserProfile = createAsyncThunk(
  "user/fetchProfile",
  async (_, { rejectWithValue }) => {
    try {
      // 이 API는 유저 정보, 포인트, KT 여부, 그리고 '역할(roles)'을 모두 반환해야 합니다.
      const response = await apiClient.get("/user/profile");
      return response.data;
    } catch (err: any) {
      return rejectWithValue(err.response.data);
    }
  }
);

// const initialState: UserState = {
//   userInfo: { id: 'subscriber-user', name: '김독자' },
//   points: 1000,
//   isKtCustomer: false,
//   isAuthenticated: true,
//   isSubscribed: true,
//   roles: ['SUBSCRIBER'], // 👈 역할을 SUBSCRIBER로 설정
//   subscribedBookIds: ['best-book-1'],
//   status: 'succeeded',
// };

// const initialState: UserState = {
//   userInfo: { id: 'new-user', name: '신규가입자' },
//   points: 1000,
//   isKtCustomer: false,
//   isAuthenticated: true,
//   subscribedBookIds: [], // 👈 이 부분을 빈 배열로 수정
//   isSubscribed: false,
//   roles: ['SUBSCRIBER'],
//   status: 'succeeded',
// };
// const initialState: UserState = {
//   userInfo: { id: 123, name: "김작가" },
//   points: 10000,
//   isKtCustomer: true,
//   isAuthenticated: true,
//   isSubscribed: false,
//   roles: ["SUBSCRIBER", "WRITER"], // 👈 여기에 'WRITER' 역할을 추가합니다.
//   subscribedBookIds: ["best-book-1"],
//   status: "succeeded",
// };
// const initialState: UserState = {
//   userInfo: { id: "writer-user", name: "김작가" },
//   points: 5000,
//   isKtCustomer: true,
//   isAuthenticated: true,
//   isSubscribed: true,
//   roles: ["SUBSCRIBER", "WRITER"], // 👈 여기에 'WRITER' 역할을 추가합니다.
//   subscribedBookIds: ["best-book-1"],
//   status: "succeeded",
// };

//👇 개발 편의를 위한 '강제 로그인' 상태 (필요시 주석을 풀고 위 initialState를 주석 처리)
const initialState: UserState = {
  userInfo: { id: 1, name: "관리자" },
  points: 99999,
  isKtCustomer: true,
  isAuthenticated: true,
  isSubscribed: false,
  roles: ["WRITER"], // 👈 여기에 'WRITER' 역할을 추가합니다.
  subscribedBookIds: [],
  status: "succeeded",
};

// --- 초기 상태 ---
// const initialState: UserState = {
//   userInfo: null,
//   points: 0,
//   isKtCustomer: false,
//   // 페이지 로드 시 토큰 존재 여부로 로그인 상태를 결정합니다.
//   isAuthenticated: !!localStorage.getItem("accessToken"),
//   isSubscribed: false,
//   roles: [],
//   status: "idle",
//   subscribedBookIds: [],
// };

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    logout: (state) => {
      localStorage.removeItem("accessToken");
      state.isAuthenticated = false;
      state.userInfo = null;
      state.points = 0;
      state.isKtCustomer = false;
      state.roles = []; // 로그아웃 시 역할도 초기화합니다.
      state.isSubscribed = false;
      state.status = "idle";
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(login.fulfilled, (state, action) => {
        state.isAuthenticated = true;
        state.userInfo = action.payload; // 로그인 직후에는 기본 정보만 설정될 수 있습니다.
      })
      .addCase(fetchUserProfile.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchUserProfile.fulfilled, (state, action) => {
        state.userInfo = action.payload.profile;
        state.points = action.payload.points;
        state.isKtCustomer = action.payload.isKtCustomer;
        state.isSubscribed = action.payload.isSubscribed || false;
        state.roles = action.payload.roles || []; // API 응답에서 roles를 가져와 저장합니다.
        state.status = "succeeded";
      })
      .addCase(fetchUserProfile.rejected, (state) => {
        // 프로필 가져오기 실패 시 로그아웃 처리(예: 토큰 만료)
        localStorage.removeItem("accessToken");
        state.isAuthenticated = false;
        state.userInfo = null;
        state.roles = [];
        state.status = "failed";
      });
  },
});

export const { logout } = userSlice.actions;
export default userSlice.reducer;
