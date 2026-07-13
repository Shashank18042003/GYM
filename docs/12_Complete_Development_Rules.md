# 12_Complete_Development_Rules.md

# Gym Management System
## React Development Rules

You are a Senior React Developer.

You are developing the frontend for an already completed Spring Boot backend.

The backend is production-ready.

Your responsibility is ONLY frontend development.

Never modify backend APIs.

Never redesign backend architecture.

Frontend must adapt to backend.

Generate production-quality code.

---

# Primary Goal

Develop a modern Gym Management System using React.

Focus on

Maintainability

Scalability

Reusable Components

Responsive UI

Professional Design

Clean Code

---

# Technology Stack

React 19

Vite

React Router DOM

Axios

Context API

React Hook Form

Tailwind CSS

React Icons

React Toastify

---

# Coding Standards

Use Functional Components only.

Use Hooks only.

Do not use Class Components.

Prefer arrow functions.

Use ES6+ syntax.

---

# Folder Structure

Follow

src/

api/

assets/

components/

context/

hooks/

layouts/

pages/

routes/

services/

styles/

utils/

Never create unnecessary folders.

---

# Components

Always create reusable components.

Examples

Button

Input

Card

Modal

Table

Loader

Pagination

EmptyState

StatusBadge

Avatar

Breadcrumb

Do not duplicate component code.

---

# Naming Convention

Components

PascalCase

TrainerDashboard.jsx

MemberProfile.jsx

Services

camelCase

paymentService.js

eventService.js

Variables

camelCase

Functions

camelCase

Constants

UPPER_CASE

---

# React Principles

Keep components small.

Single Responsibility Principle.

Avoid long components.

Split reusable logic.

---

# State Management

Use Context API only for

Authentication

Theme (optional)

Everything else

Local Component State.

Avoid unnecessary global state.

---

# API Calls

Never call axios directly.

Always call Service Classes.

Example

dashboardService.getDashboard()

Never place backend URLs inside pages.

---

# Forms

Use React Hook Form.

Display validation errors below fields.

Disable submit buttons while submitting.

Show loading spinner.

---

# UI Design

Modern

Minimal

Professional

Gym Theme

Rounded Cards

Soft Shadows

Consistent Spacing

Good Typography

Responsive Layout

---

# Theme

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

Neutral

Gray

---

# Layout

Trainer

Sidebar

Navbar

Content

Member

Sidebar

Navbar

Content

---

# Sidebar Icons

Dashboard

Members

Membership Plans

Membership

Payments

Events

Profile

Logout

Use React Icons.

---

# Buttons

Primary

Blue

Secondary

Gray

Danger

Red

Success

Green

Disable while API executes.

---

# Tables

Responsive

Striped Rows

Hover Effects

Sorting

Search

Pagination

Status Badges

Actions Column

---

# Cards

Rounded Corners

Shadow

Icon

Title

Value

Consistent Padding

---

# Forms

Consistent Labels.

Placeholder Text.

Validation Messages.

Helper Text.

Required Field Indicator.

---

# Dialogs

Create

Edit

Delete

View Details

Always reusable.

---

# Delete

Always ask confirmation.

Never delete immediately.

---

# Notifications

Use React Toastify.

Success

Error

Warning

Info

Never use alert().

---

# Loading

Every API should show

Loader

Spinner

Skeleton

Disable Buttons

Prevent Duplicate Requests

---

# Empty States

Never display blank pages.

Examples

No Members Found

No Events Found

No Payments Found

No Membership Plans

No Upcoming Events

---

# Search

Implement frontend search unless backend provides search.

Search should be case-insensitive.

---

# Sorting

Allow sorting where appropriate.

Do not overcomplicate.

---

# Pagination

Use frontend pagination if backend pagination is unavailable.

---

# Date Format

Backend

2026-07-15

Frontend

15 Jul 2026

---

# Currency

Backend

2500

Frontend

₹2,500

---

# Accessibility

Buttons should have accessible labels.

Inputs should have labels.

Keyboard navigation should work.

Maintain sufficient color contrast.

---

# Responsive Design

Desktop

Tablet

Mobile

Every page must work on all screen sizes.

---

# Error Handling

401

Redirect Login

403

Access Denied

404

Resource Not Found

500

Something Went Wrong

Network Error

Unable to connect to server.

---

# Performance

Avoid unnecessary renders.

Use React.memo only when beneficial.

Use useMemo and useCallback only when necessary.

Lazy-load pages using React.lazy and Suspense.

---

# Code Quality

Avoid duplicated code.

Extract reusable logic.

Keep components readable.

Use descriptive variable names.

Use comments only where necessary.

---

# Security

Never store passwords.

Never expose JWT.

Never expose backend URLs inside components.

Never trust frontend validation alone.

Backend remains the source of truth.

---

# Backend Rules

Do NOT

Calculate Membership Queue

Calculate Dashboard Statistics

Verify Razorpay Payments

Expire Memberships

Delete Expired Events

Generate Business Logic

Backend already performs all business logic.

Frontend only displays backend responses.

---

# UI Behavior

After successful

Create

Update

Delete

Refresh corresponding list automatically.

Display backend success message.

Maintain current page if possible.

---

# Final Development Checklist

Every page should include

Responsive Layout

Loading State

Error State

Empty State

Toast Messages

Reusable Components

Clean Code

Professional UI

API Integration

Proper Validation

Protected Routes

Role-based Navigation

Consistent Styling

---

# Final Instruction

Always generate production-ready React code.

Never generate placeholder APIs.

Never invent backend endpoints.

Never change request DTOs.

Never change response DTOs.

Strictly follow the provided backend API documentation.

The React application should integrate seamlessly with the existing Spring Boot backend.