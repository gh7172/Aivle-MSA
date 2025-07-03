FROM node:20-alpine AS builder 

WORKDIR /app
COPY package*.json ./
RUN npm install
RUN npm install @rollup/rollup-linux-x64-musl --save-optional || true
COPY . .
RUN npm run build

FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist /usr/share/nginx/html 
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/timezone
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]