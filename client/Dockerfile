FROM node:16-alpine

WORKDIR /app
COPY . .

RUN npm install -g http-server

EXPOSE 3000
ENTRYPOINT ["http-server", "-c-1", "-p", "3000"]
