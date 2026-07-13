# 09_Payment_Module.md

# Gym Management System
## Payment Module

Develop the complete Payment Module.

Backend is already implemented.

Do NOT modify backend APIs.

Follow backend contracts exactly.

This module provides payment history for both Members and Trainers.

Payment processing is completely controlled by the backend.

Frontend should only display backend responses.

---

# User Roles

Trainer

Member

---

# Member Payment History

Route

/member/payments

Protected Route

Member Only

---

# API

GET

`/api/member/payments/history`

Authorization

Bearer Token

---

# Response

{
    "success": true,
    "message": "Payment history fetched successfully.",
    "data": [

        {

            "paymentId":1,

            "planName":"Elite",

            "amount":2500,

            "paymentMethod":"RAZORPAY",

            "paymentStatus":"SUCCESS",

            "paymentDate":"2026-07-15",

            "failureReason":null

        }

    ]

}

---

# Member Payment Table

Columns

Payment ID

Plan Name

Amount

Payment Method

Payment Status

Payment Date

Action

---

# Payment Details

Click

View

↓

Open Dialog

Display

Payment ID

Membership Plan

Amount

Payment Method

Payment Status

Payment Date

Payment Reference

Failure Reason

Only display Failure Reason if

Payment Status == FAILED

---

# Payment Status

SUCCESS

Green Badge

FAILED

Red Badge

PENDING

Orange Badge

---

# Payment Method

RAZORPAY

Purple Badge

CASH

Blue Badge

---

# Search

Frontend Search

Search by

Plan Name

Payment Method

Payment Status

---

# Sorting

Amount

Payment Date

Status

---

# Empty State

No Payment History

---

# Trainer Payment History

Route

/payments

Protected Route

Trainer Only

---

# API

GET

`/api/trainer/payments`

Authorization

Bearer Token

---

# Business Rule

Trainer should ONLY see

SUCCESSFUL payments.

FAILED payments should NEVER appear.

PENDING payments should NEVER appear.

Cash payments should NOT appear if backend excludes them.

Display exactly what backend returns.

---

# Trainer Response

{

"success":true,

"data":[

{

"paymentId":15,

"memberName":"Rahul",

"planName":"Elite",

"amount":2500,

"paymentMethod":"RAZORPAY",

"paymentDate":"2026-07-15"

}

]

}

---

# Trainer Payment Table

Columns

Payment ID

Member Name

Membership Plan

Amount

Payment Method

Payment Date

View

---

# Trainer Payment Details

Open Dialog

Display

Member Name

Membership Plan

Amount

Payment Method

Payment Date

Payment Reference

---

# Revenue

Do NOT calculate revenue on frontend.

Revenue comes from

Trainer Dashboard API.

---

# Payment Colors

SUCCESS

Green

FAILED

Red

PENDING

Orange

---

# Currency

Always display

₹2500

Never

2500

---

# Date

Format

15 Jul 2026

instead of

2026-07-15

---

# Loading

Display Loader while

Payment History loads.

---

# Refresh

Refresh Button

Reload payment history.

---

# Error Handling

401

Redirect Login

403

Access Denied

404

No Payments Found

500

Display generic error toast.

---

# Responsive Design

Desktop

Responsive Table

Mobile

Payment Cards

---

# Future Scope

Do NOT implement

Export PDF

Export Excel

Refund

Invoice Download

These are future enhancements.

---

# Important Rules

Frontend should NEVER change payment status.

Frontend should NEVER calculate payment success.

Backend controls

Payment Status

Payment History

Payment Verification

Failure Reason

Frontend only displays backend responses.