# syntax=docker/dockerfile:1

### Build-Stage
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
# Vite Production Build
RUN npm run build

### Run-Stage (Nginx)
FROM nginx:alpine
# Nginx-Konfiguration für API-Proxy
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Statisches Bundle deployen
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
