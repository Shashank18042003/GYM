# 05_Trainer_Members_Module.md

# Gym Management System
## Trainer Members Module

Develop the complete Trainer Members module.

This module allows the trainer to manage gym members.

Backend is already completed.

Do NOT modify backend APIs.

Follow backend contracts exactly.

---

# Route

/members

Protected Route

Trainer Only

---

# Purpose

Trainer should be able to

• View all members

• Filter members

• View member details

Members without any membership should NOT appear.

---

# Members List API

GET

`/api/trainer/members`

Authorization

Bearer Token

---

# Filter APIs

GET

`/api/trainer/members?filter=ALL`

GET

`/api/trainer/members?filter=ACTIVE`

GET

`/api/trainer/members?filter=EXPIRED`

GET

`/api/trainer/members?filter=RENEWAL_DUE&days=3`

---

# Member List Response

{
    "success": true,
    "message": "Members fetched successfully.",
    "data": [

        {

            "memberId": 1,

            "fullName": "Rahul",

            "email": "rahul@gmail.com",

            "phoneNumber": "9876543210",

            "currentPlan": "Elite",

            "membershipStatus": "ACTIVE",

            "expiryDate": "2026-08-15",

            "daysLeft": 33

        }

    ]

}

---

# Members Page Layout

Top Bar

--------------------------------

Members

Search Box

Refresh Button

--------------------------------

Filter Buttons

ALL

ACTIVE

EXPIRED

RENEWAL DUE

--------------------------------

Members Table

--------------------------------

Name

Email

Phone

Current Plan

Membership Status

Expiry Date

Days Left

Action

--------------------------------

---

# Membership Status Badge

ACTIVE

Green

EXPIRED

Red

RENEWAL DUE

Orange

---

# Days Left

If

membershipStatus == ACTIVE

Show

33 Days

If

Expired

Show

Expired

---

# Search

Frontend Search

Search by

Full Name

Email

Phone Number

Search should filter table without making backend request.

---

# Sorting

Allow sorting

Member Name

Expiry Date

Days Left

---

# Refresh

Refresh Button

Calls

GET

`/api/trainer/members`

again.

---

# Empty State

Display

No Members Found

---

# Clicking View Button

Navigate

/members/{memberId}

---

# Member Details API

GET

`/api/trainer/members/{memberId}`

Authorization

Bearer Token

---

# Member Details Response

{

"success": true,

"data":{

"memberId":1,

"fullName":"Rahul",

"email":"rahul@gmail.com",

"phoneNumber":"9876543210",

"gender":"MALE",

"dateOfBirth":"2002-04-10",

"address":"Hyderabad",

"profileImage":"",

"currentMembership":{

"planName":"Elite",

"status":"ACTIVE",

"startDate":"2026-07-01",

"expiryDate":"2026-08-01",

"daysLeft":18

},

"pendingMemberships":[

{

"planName":"Premium",

"status":"PENDING",

"createdAt":"..."

}

],

"paymentHistory":[

{

"paymentId":1,

"planName":"Elite",

"amount":2500,

"paymentMethod":"RAZORPAY",

"paymentStatus":"SUCCESS",

"paymentDate":"..."

}

]

}

}

---

# Member Details Layout

Profile Card

Profile Image

Full Name

Email

Phone

Gender

Date Of Birth

Address

Current Membership Card

Current Plan

Membership Status

Start Date

Expiry Date

Days Left

Pending Membership Table

Plan

Status

Created Date

Payment History Table

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

# UI Components

Card

Table

Status Badge

Avatar

Tabs

Loader

Empty State

Back Button

---

# Loading

Display Loader while API executes.

---

# Error Handling

404

Member Not Found

401

Redirect Login

500

Display Toast

---

# Responsive Design

Desktop

Two-column layout

Mobile

Single-column layout

---

# Navigation

Trainer Dashboard

↓

Members

↓

Member Details

↓

Back to Members

---

## Trainer Cash Payment

Trainers can create a cash payment for a member using the following endpoint:

POST

`/api/trainer/members/{memberId}/cash-payment`

Authorization

Bearer Token

Provide the backend `CashPaymentRequest` DTO exactly as implemented by the backend. This endpoint creates a cash payment record for the specified member.

---

# Important Rules

Never hardcode values.

Always use backend response.

Never modify backend APIs.

Frontend should completely adapt to backend.