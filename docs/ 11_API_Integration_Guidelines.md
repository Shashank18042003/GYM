# 11_API_Integration_Guidelines.md

# Gym Management System
## API Integration Guidelines

This document defines how the React frontend should communicate with the Spring Boot backend.

Backend APIs are already finalized.

Do NOT modify backend APIs.

Frontend must adapt to backend.

---

# Backend URL

Development

http://localhost:8080

Production

Use environment variables.

Example

VITE_API_BASE_URL=http://localhost:8080

Never hardcode URLs inside components.

---

# Axios

Create a single Axios instance.

Example

src/api/axios.js

Responsibilities

• Base URL

• JSON Content Type

• Authorization Header

• Response Interceptor

• Error Handling

Never call axios directly inside React pages.

Always use Service Classes.

---

# Service Layer

Create

authService.js

dashboardService.js

memberService.js

membershipPlanService.js

membershipService.js

paymentService.js

eventService.js

Each service should only communicate with its own backend module.

Never mix APIs.

Example

eventService

should NEVER call

payment APIs.

---

# Authorization

Every protected API requires

Authorization

Bearer <JWT Token>

Read token from localStorage.

Automatically attach token using Axios Request Interceptor.

Never manually add Authorization headers inside components.

---

# Response Format

Every backend API returns

{

"success":true,

"message":"...",

"data":{},

"timestamp":"..."

}

Always check

success

before rendering.

Never assume API succeeded.

---

# Success Response

Display

message

using React Toastify.

Example

Membership created successfully.

Payment verified successfully.

Event deleted successfully.

---

# Error Response

{

"success":false,

"message":"Event not found.",

"data":null,

"timestamp":"..."

}

Display

message

inside Toast.

Do NOT display backend stack traces.

---

# Validation Errors

If backend returns validation errors,

display them below the corresponding input field.

Example

Email Required

Password Required

Price must be greater than zero.

---

# Loading

Every API call should have

Loading State

Disable Buttons

Show Loader

Example

Login

↓

Disable Login Button

↓

Show Spinner

↓

Enable after response.

---

# Refresh

After successful

Create

Update

Delete

Refresh the list automatically.

Do not ask user to refresh manually.

---

# Authentication

Login

↓

Receive JWT

↓

Store

token

role

user

↓

Update AuthContext

↓

Navigate

Dashboard

Logout

↓

Clear localStorage

↓

Clear Context

↓

Navigate Login

---

# Axios Request Interceptor

Automatically attach

Authorization

Bearer Token

If token does not exist,

send request without Authorization.

---

# Axios Response Interceptor

If

401

Unauthorized

Automatically

Clear

token

role

user

Redirect

/login

---

# Service Return Type

Service should return

response.data

instead of entire Axios response.

Example

Incorrect

return axios.get(...)

Correct

return response.data

This keeps components clean.

---

# Components

Components should NEVER know backend URLs.

Components should only call Service Methods.

Example

dashboardService.getDashboard()

memberService.getMembers()

paymentService.getPayments()

---

# API Calls

Call APIs inside

useEffect

when page loads.

Avoid duplicate requests.

Use dependency arrays correctly.

---

# Search

If backend Search API is unavailable,

perform search on frontend only.

Do NOT repeatedly call backend while typing.

---

# Pagination

If backend pagination is unavailable,

implement frontend pagination.

If backend pagination exists later,

replace frontend implementation.

---

# Date Formatting

Backend returns

2026-07-15

Display

15 Jul 2026

Use date formatting utilities.

Never modify backend date format.

---

# Currency

Backend returns

2500

Display

₹2,500

Use frontend formatting only.

---

# Badges

Membership Status

ACTIVE

Green

PENDING

Orange

EXPIRED

Red

Payment Status

SUCCESS

Green

FAILED

Red

PENDING

Orange

---

# Retry

Never automatically retry failed POST requests.

Retry GET requests only when user clicks Refresh.

---

# Empty States

Display meaningful messages.

No Members Found

No Events Found

No Payments Found

No Membership Plans Available

Never leave empty tables.

---

# Network Errors

Display

Unable to connect to server.

Please try again later.

---

# File Upload

When uploading profile images,

use multipart/form-data

Show upload progress.

Preview selected image before upload.

---

# Delete

Always ask confirmation before Delete.

Never delete immediately.

---

# Protected Routes

Trainer

/dashboard

/members

/plans

/payments

/events

Member

/member/dashboard

/member/profile

/member/membership

/member/payments

/member/events

---

# Route Protection

Unauthenticated

↓

Redirect

/login

Wrong Role

↓

Access Denied

---

# API Standards

One API call

↓

One Service Method

↓

One Component

Keep responsibilities separate.

---

# Performance

Avoid unnecessary re-renders.

Use memoization only where beneficial.

Avoid duplicate API calls.

Do not fetch the same data repeatedly.

---

# Logging

Do not use console.log() in production components.

Handle errors gracefully.

---

# Backend is Source of Truth

Frontend should NEVER

Calculate Membership Queue

Activate Membership

Expire Membership

Verify Payments

Generate Dashboard Statistics

Calculate Days Left

Delete Expired Events

Backend already performs all business logic.

Frontend only displays backend responses.

---

# Final Rule

The Spring Boot backend is finalized.

React must consume backend APIs exactly as implemented.

Do not redesign backend contracts.

Do not rename request fields.

Do not rename response fields.

Follow backend API specifications exactly.