# Gym Management System
## Trainer Dashboard Module

Create the Trainer Dashboard page.

This page is the landing page after Trainer login.

Route

/dashboard

Protected Route

Trainer only

---

# API

GET

/api/trainer/dashboard

Authorization

Bearer Token Required

---

# Response

{
    "success": true,
    "message": "Dashboard fetched successfully.",
    "data": {

        "totalMembers": 120,

        "activeMembers": 95,

        "expiredMembers": 20,

        "renewalDueMembers": 5,

        "todayRevenue": 15000,

        "monthlyRevenue": 220000,

        "successfulPayments": 310,

        "upcomingEvents": 4

    }

}

---

# Dashboard Cards

Display

Total Members

Active Members

Expired Members

Renewal Due

Today's Revenue

Monthly Revenue

Successful Payments

Upcoming Events

Each card should have

Icon

Title

Value

Hover Effect

Rounded Corners

---

# Layout

Responsive Grid

Desktop

4 cards per row

Tablet

2 cards

Mobile

1 card

---

# Loading

Show Skeleton Loader.

---

# Error

Show Toast.

---

# Refresh

Reload dashboard when page loads.

---

# Navigation

Cards should be clickable.

Total Members

↓

Navigate

/members

Upcoming Events

↓

Navigate

/events

Payments

↓

Navigate

/payments

Renewal Due

↓

Open Members page with

Filter

RENEWAL_DUE

days=3

---

# UI Theme

Modern Dashboard.

Minimal.

Professional.
