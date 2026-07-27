# Inkspire — Blog Management Platform Frontend

A responsive React 18 frontend for the Spring Boot Blog Management REST API. The application provides JWT authentication, role-aware navigation and routes, post discovery and creation, nested comments, user profiles, and administrator category management.

## Stack

React 18, Vite, Tailwind CSS, React Router, Axios, React Hook Form, Lucide icons, React Hot Toast, and Recharts-ready dashboard dependencies.

## Features

- JWT token and user state persisted in local storage; Axios attaches `Authorization: Bearer <token>`.
- Public paginated post feed with search, filtering, sorting, dark mode, responsive layout, and empty/error/loading states.
- Auth forms, protected routes, author/admin post editor, profile/password update, comments/replies, and admin category CRUD.
- `vercel.json` SPA rewrite configuration for Vercel.

## Screenshots

Add screenshots of the deployed home page, post editor, dashboard, and category manager here after deployment.

## Local setup

```bash
cp .env.example .env
npm install
npm run dev
```

Set `VITE_API_URL=http://localhost:8080/api` in `.env`. Ensure the Spring Boot backend is running on port 8080 and has CORS configured for the frontend origin (for Vite, `http://localhost:5173`).

## Build and deployment

```bash
npm run build
npm run preview
```

For Vercel: push this directory to GitHub, import the repository in Vercel, set `VITE_API_URL` to your deployed backend API URL (for example `https://api.example.com/api`), then deploy. Vercel detects Vite automatically; use build command `npm run build` and output directory `dist` if prompted.

For Netlify: set the same environment variable, build command `npm run build`, and publish directory `dist`. Add a `_redirects` rule for SPA routing if not using the included Vercel configuration.

## Backend integration note

The API must allow your frontend domain through CORS and return the request/response formats documented by the accompanying Spring Boot project. Role changes are managed by the backend; new registrations are `USER` accounts by default.
