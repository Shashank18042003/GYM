# Gym Management System
## React Architecture

You are building the frontend for an existing Spring Boot backend.

Backend APIs are already finalized.

Do NOT modify backend.

Frontend must adapt to backend.

Use production-level coding standards.

---

# Technology Stack

React 19

Vite

React Router DOM

Axios

Context API

Tailwind CSS

React Hook Form

React Toastify

React Icons

---

# Project Structure

src/

│

├── api/

│      axios.js

│

├── assets/

│

├── components/

│      Button.jsx

│      Card.jsx

│      Input.jsx

│      Loader.jsx

│      Modal.jsx

│      Pagination.jsx

│      Select.jsx

│      Table.jsx

│      EmptyState.jsx

│

├── context/

│      AuthContext.jsx

│

├── hooks/

│      useAuth.js

│

├── layouts/

│      TrainerLayout.jsx

│      MemberLayout.jsx

│

├── pages/

│

│      Login/

│

│      Trainer/

│           Dashboard/

│           Members/

│           MembershipPlans/

│           Payments/

│           Events/

│

│      Member/

│           Dashboard/

│           Profile/

│           Membership/

│           Payments/

│           Events/

│

├── routes/

│      ProtectedRoute.jsx

│      AppRoutes.jsx

│

├── services/

│      authService.js

│      dashboardService.js

│      memberService.js

│      membershipService.js

│      paymentService.js

│      planService.js

│      eventService.js

│

├── utils/

│

└── App.jsx

---

# Axios

Never call axios directly from pages.

Always create service classes.

Example

dashboardService.js

memberService.js

paymentService.js

---

# Components

Always create reusable components.

Button

Input

Card

Modal

Loader

Pagination

Select

Table

Never duplicate UI.

---

# Styling

Use Tailwind CSS.

Responsive Design.

Desktop First.

Support Mobile.

Rounded Cards.

Soft Shadows.

Professional Dashboard.

---

# State Management

Authentication

Context API

API Data

Component State

Avoid unnecessary global state.

---

# Forms

Use

React Hook Form

Display validation messages below fields.

Disable submit button while request is executing.

---

# Notifications

Use

React Toastify

Success

Error

Warning

Info

---

# Loading

Every page should display

Loader

while API is executing.

---

# Empty State

If API returns empty list

Display

"No Data Available"

instead of blank page.

---

# Delete

Before every Delete

Display confirmation dialog.

---

# Tables

All tables should support

Sorting

Searching

Pagination (Frontend if backend pagination is unavailable)

Responsive layout

---

# Error Handling

401

Redirect Login

403

Access Denied

404

Resource Not Found

500

Something went wrong

---

# Layout

Trainer Layout

Sidebar

Navbar

Content

Footer (optional)

Member Layout

Sidebar

Navbar

Content

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

# Code Quality

Use Functional Components.

Use Hooks.

Avoid Class Components.

Avoid duplicated logic.

Keep components reusable.

Follow clean architecture.
