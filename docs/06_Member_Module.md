# 06_Member_Module.md

# Gym Management System
## Member Module

Develop the complete Member Module.

Backend is already completed.

Do NOT modify backend APIs.

Follow backend contracts exactly.

Only authenticated users with ROLE_MEMBER can access these pages.

---

# Member Navigation

Dashboard

Profile

Membership

Payments

Events

Logout

---

# Routes

/member/dashboard

/member/profile

/member/membership

/member/payments

/member/events

All routes are protected.

Require JWT Token.

---

# Member Dashboard

Purpose

Provide a quick overview of the member account.

Dashboard should display

• Current Membership

• Membership Status

• Expiry Date

• Days Left

• Pending Membership Count

• Latest Payment

• Upcoming Events Count

Use cards similar to Trainer Dashboard.

---

# Profile Page

Route

/member/profile

---

# Get Profile API

GET

`/api/member/profile`

Authorization

Bearer Token

---

# Response

{
    "success": true,
    "data": {

        "memberId":1,

        "fullName":"Rahul",

        "email":"rahul@gmail.com",

        "phoneNumber":"9876543210",

        "gender":"MALE",

        "dateOfBirth":"2002-04-10",

        "address":"Hyderabad",

        "profileImage":""

    }

}

---

# Profile Layout

Profile Image

Full Name

Email

Phone Number

Gender

Date Of Birth

Address

Edit Profile Button

---

# Edit Profile

PUT

`/api/member/profile`

Authorization

Bearer Token

---

# Request

Use backend DTO exactly.

Never send additional fields.

Display validation errors returned by backend.

---

# Upload Profile Picture

If backend supports profile image upload,

provide

Upload Image

Preview Image

Replace Image

Remove Image (if backend API exists)

Display uploaded image immediately after successful upload.

---

# Membership Page

Route

/member/membership

---

# API

The backend does not expose a single `/api/member/details` endpoint. Use the following endpoints to assemble membership details on the frontend:

- GET `/api/member/profile` — member profile and basic info
- GET `/api/member/plans` — active membership plans
- GET `/api/member/payments/history` — member payment history

Authorization

Bearer Token

---

# Response

Backend returns

Current Membership

Pending Memberships

Payment History

Use backend response directly.

---

# Current Membership Card

Display

Current Plan

Membership Status

Start Date

Expiry Date

Days Left

---

# Membership Status

ACTIVE

Green

EXPIRED

Red

PENDING

Orange

---

# Pending Membership Table

Columns

Plan Name

Status

Created Date

---

# Payment History

Display

Plan

Amount

Payment Method

Payment Status

Payment Date

---

# Payment Status Colors

SUCCESS

Green

FAILED

Red

PENDING

Orange

---

# Payment Details

Clicking a payment row opens a modal.

Display

Plan

Amount

Payment Method

Payment Status

Payment Date

Failure Reason (only if FAILED)

Transaction Reference

---

# Events Page

Route

/member/events

---

# API

GET

`/api/member/events`

Authorization

Bearer Token

---

# Response

{

"success":true,

"data":[

{

"eventId":1,

"title":"Morning Yoga",

"description":"Yoga Session",

"eventDate":"2026-07-20",

"eventTime":"07:00",

"location":"Main Hall",

"daysRemaining":6

}

]

}

---

# Events Layout

Card Grid

Each Card

Title

Description

Date

Time

Location

Days Remaining

View Details Button

---

# Event Details

GET

`/api/member/events/{eventId}`

Display

Title

Description

Date

Time

Location

Days Remaining

---

# Empty States

No Membership

Display

"No Active Membership"

No Payments

Display

"No Payment History"

No Events

Display

"No Upcoming Events"

---

# Loading

Every page should display a Loader while API executes.

---

# Error Handling

401

Redirect Login

404

Display Resource Not Found

500

Show Toast

---

# Responsive Design

Desktop

Cards + Tables

Tablet

Responsive Grid

Mobile

Single Column

---

# UI Theme

Professional

Clean

Minimal

Gym Dashboard Design

---

# Important Rules

Never hardcode values.

Always use backend response.

Do not modify backend APIs.

Frontend must completely adapt to backend.