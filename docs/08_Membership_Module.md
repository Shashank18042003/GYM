# 08_Membership_Module.md

# Gym Management System
## Membership Module

Develop the complete Membership Module.

Backend is already implemented in Spring Boot.

Do NOT modify backend APIs.

Follow backend contracts exactly.

This module allows Members to purchase memberships and manage their membership queue.

---

# Route

/member/membership

Protected Route

Member Only

---

# Purpose

A Member should be able to

• View current membership

• View pending memberships

• Purchase a new membership

• Pay using Razorpay

• View payment history

---

# Membership Flow

A member can have

Only ONE ACTIVE membership.

If the member purchases another plan while an ACTIVE membership exists,

the backend creates

PENDING membership.

Example

Current Membership

Elite

↓

Purchase Premium

↓

Premium becomes

PENDING

↓

When Elite expires

↓

Scheduler automatically activates Premium

Frontend should clearly visualize this queue.

---

# APIs

## View Membership Details

The backend does not expose a single `/api/member/details` endpoint. Instead membership-related information is available via the following member endpoints. The frontend should combine these responses to build the Membership page.

- GET `/api/member/profile`  — member profile and basic info
- GET `/api/member/plans`    — active membership plans (available for purchase)
- GET `/api/member/payments/history` — member payment history

Authorization

Bearer Token

---

# Response

{

"success":true,

"data":{

"currentMembership":{

"planName":"Elite",

"status":"ACTIVE",

"startDate":"2026-07-01",

"expiryDate":"2026-07-31",

"daysLeft":18

},

"pendingMemberships":[

{

"planName":"Premium",

"status":"PENDING",

"createdAt":"2026-07-10"

},

{

"planName":"Diamond",

"status":"PENDING",

"createdAt":"2026-07-11"

}

],

"paymentHistory":[...]

}

}

---

# Membership Page Layout

Current Membership Card

↓

Pending Membership Queue

↓

Payment History

↓

Purchase Membership Button

---

# Current Membership Card

Display

Current Plan

Membership Status

Start Date

Expiry Date

Days Left

---

# Pending Membership Queue

Display as vertical timeline

Elite ACTIVE

↓

Premium PENDING

↓

Diamond PENDING

Do NOT display queue as plain text.

Visual timeline preferred.

---

# Purchase Membership

Click

Purchase Membership

↓

Open Dialog

---

# Load Plans

GET

`/api/member/plans`

Authorization

Bearer Token

Display

Plan Name

Description

Price

Duration

---

# Select Plan

User selects plan

↓

Click Continue

↓

Choose Payment Method

---

# Payment Methods

Display

Razorpay

Cash

Cash should only be displayed if your backend allows members to use it.

Otherwise display Razorpay only.

---

# Razorpay Payment

Step 1

Create Order

POST

/api/member/payments/create-order

Request

{

"planId":1

}

---

# Response

{

"key":"rzp_test_xxxxx",

"orderId":"order_xxxxx",

"amount":250000,

"currency":"INR"

}

---

# Frontend Flow

Receive

key

orderId

amount

↓

Open Razorpay Checkout

↓

User Pays

↓

Receive

razorpay_payment_id

razorpay_order_id

razorpay_signature

↓

Call Verify API

---

# Verify Payment

POST

/api/member/payments/verify

Request

{

"razorpayOrderId":"",

"razorpayPaymentId":"",

"razorpaySignature":""

}

---

# Successful Payment

Display

Payment Successful

Refresh

Membership Details

Refresh

Payment History

---

# Failed Payment

Display backend message.

If backend returns failure reason,

show it to the member.

Example

Payment Failed

Reason

Payment cancelled by user.

or

Insufficient balance.

or

Invalid payment signature.

Never generate failure reasons on frontend.

Always use backend response.

---

# Cash Payment

If backend supports Cash

POST

/api/member/payments/cash

Use backend request DTO exactly.

Display

Cash Payment Successful

Refresh Membership Details.

---

# Payment History

Payment history is available from the member payments endpoint:

GET

`/api/member/payments/history`

Display

Plan Name

Amount

Payment Method

Payment Status

Payment Date

Failure Reason

(if FAILED)

---

# Payment Status Colors

SUCCESS

Green

FAILED

Red

PENDING

Orange

---

# Membership Status Colors

ACTIVE

Green

PENDING

Orange

EXPIRED

Red

---

# Queue Rules

Never allow frontend to activate memberships.

Never reorder queue.

Never remove pending memberships.

Queue is completely controlled by backend.

Frontend only displays it.

---

# Refresh

After

Successful Payment

Refresh

Membership Details

Payment History

Pending Membership Queue

Current Membership

---

# Empty States

No Active Membership

Display

Purchase your first membership.

No Pending Membership

Hide queue section.

No Payment History

Display

No Payments Yet.

---

# Loading

Display Loader while

Loading Membership

Loading Payment

Verifying Payment

---

# Error Handling

401

Redirect Login

403

Access Denied

404

Membership Not Found

500

Show generic error toast.

---

# Responsive Design

Desktop

Membership Card

Queue

Payment Table

Mobile

Single Column

Cards

---

# Important Rules

Never calculate membership activation on frontend.

Never calculate queue order.

Never calculate expiry.

Never change payment status manually.

Backend is the source of truth.

Frontend only displays backend responses.

Always refresh membership information after every successful payment.