# 02_Authentication_Module.md

# Gym Management System
## Authentication Module

You are developing ONLY the React frontend.

The backend is completely implemented in Spring Boot.

Do NOT modify backend APIs.

Use Axios.

Use React Router DOM.

Use Context API for authentication.

Store JWT in localStorage.

---

# Login Page

Create a professional login page.

Fields

- Email
- Password

Buttons

- Login

Links

- None

---

# Validation

Email

- Required
- Valid email format

Password

- Required

Disable Login button while API is executing.

---

# API

POST

/api/auth/login

Content-Type

application/json

---

# Request

{
    "email": "trainer@gmail.com",
    "password": "Password@123"
}

---

# Successful Response

{
    "success": true,
    "message": "Login Successful",
    "data": {

        "token": "<jwt-token>",

        "role": "TRAINER",

        "user": {

            "id": 1,

            "fullName": "John Doe",

            "email": "trainer@gmail.com"

        }

    },

    "timestamp": "2026-07-14T09:00:00"
}

---

# Store after Login

Store inside localStorage

token

role

user

Example

localStorage

token

role

user

---

# Navigation

If role == TRAINER

Navigate

/dashboard

If role == MEMBER

Navigate

/member/dashboard

---

# Failed Login

Example

{
    "success": false,
    "message": "Invalid email or password."
}

Display

Invalid email or password

using React Toastify.

Do not expose backend exception messages.

---

# Axios Configuration

Create

src/api/axios.js

Requirements

Base URL

http://localhost:8080

Automatically attach

Authorization

Bearer <token>

using Axios Interceptor.

If

401 Unauthorized

Automatically

Remove

token

role

user

from localStorage

Redirect

/login

---

# Auth Context

Create

AuthContext

Store

isAuthenticated

user

role

token

Provide

login()

logout()

methods.

---

# login()

Save

token

role

user

to localStorage.

Update Context.

Navigate according to role.

---

# logout()

Remove

token

role

user

Navigate

/login

---

# Protected Routes

Trainer Routes

/dashboard

/members

/members/:id

/plans

/payments

/events

Member Routes

/member/dashboard

/member/profile

/member/membership

/member/payments

/member/events

If user is not authenticated

Redirect

/login

---

# Navbar

Display

Logged-in User Name

Role

Logout Button

Example

John Doe

Trainer

Logout

---

# Sidebar

Trainer

Dashboard

Members

Membership Plans

Payments

Events

Logout

Member

Dashboard

Profile

Membership

Payments

Events

Logout

---

# Loading State

Disable Login button while request is executing.

Show Loader.

---

# Success Flow

User opens Login Page

↓

Enters

Email

Password

↓

Click Login

↓

POST

/api/auth/login

↓

Receive JWT

↓

Store in localStorage

↓

Update Auth Context

↓

Navigate according to Role

↓

Dashboard

---

# Folder Structure

src/

api/

context/

pages/

Login/

components/

ProtectedRoute.jsx

routes/

AppRoutes.jsx

---

# Important Rules

Never hardcode JWT.

Never hardcode Role.

Always use backend response.

Never modify backend API.

Frontend must completely adapt to backend.