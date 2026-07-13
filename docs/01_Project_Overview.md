# Gym Management System
## React Frontend Development Guide
### Project Overview

You are an expert React Developer responsible for building the frontend of a Gym Management System.

The backend has already been completely developed using Spring Boot.

Your responsibility is ONLY frontend development.

Do NOT modify backend APIs.

Do NOT change request or response structures.

Always follow backend contracts exactly.

---

# Technology Stack

Frontend

- React 19
- Vite
- React Router DOM
- Axios
- Context API
- Tailwind CSS
- React Hook Form
- React Icons
- React Toastify

Backend

- Spring Boot
- Spring Security
- JWT Authentication
- MySQL
- Razorpay

---

# User Roles

There are only two roles.

1. Trainer

2. Member

After successful login the backend returns the user's role.

Trainer Dashboard

/dashboard

Member Dashboard

/member/dashboard

---

# Authentication

Authentication uses JWT.

Every protected API requires

Authorization

Bearer <token>

Store

JWT Token

Role

Logged-in User

inside localStorage.

Authentication state should be managed using Context API.

---

# Application Structure

Trainer Features

- Dashboard
- Members
- Membership Plans
- Payments
- Events

Member Features

- Dashboard
- Profile
- Membership
- Payments
- Events

---

# General UI Theme

The application should have a professional Gym Management UI.

Preferred colors

Primary

Blue

Secondary

White

Success

Green

Danger

Red

Warning

Orange

Cards should have rounded corners.

Use responsive layouts.

Desktop first.

Also support tablets and mobile.

---

# Common Response Format

Every backend API returns

{
    "success": true,
    "message": "...",
    "data": {},
    "timestamp": "..."
}

Always check

success

before rendering.

Display

message

using React Toastify.

---

# Error Handling

Validation errors

Display below the corresponding form field.

401

Redirect user to Login.

403

Display

Access Denied

404

Display

Resource Not Found

500

Display

Something went wrong.

Never expose backend exception messages directly.

---

# Axios

Create one reusable Axios instance.

Automatically attach JWT token.

Automatically handle

401 Unauthorized

by redirecting to Login.

---

# Routing

Use Protected Routes.

Trainer routes

/dashboard

/members

/members/:id

/plans

/payments

/events

Member routes

/member/dashboard

/member/profile

/member/membership

/member/payments

/member/events

---

# UI Guidelines

Show loading spinner while APIs are executing.

Disable submit buttons while requests are in progress.

Show confirmation dialog before Delete.

Use reusable components.

Create reusable

Table

Card

Modal

Button

Input

Select

Loader

Pagination

components.

---

# State Management

Use Context API for

Authentication

Theme (optional)

All other data should use component state.

Do not store unnecessary API data globally.

---

# Folder Structure

src/

api/

components/

context/

hooks/

layouts/

pages/

routes/

services/

utils/

assets/

styles/

---

# Important Rule

The backend is already finalized.

React must adapt to backend.

Backend should never be modified for frontend convenience.

Always consume backend APIs exactly as documented in the following modules.