# 07_Membership_Plans_Module.md

# Gym Management System
## Membership Plans Module

Develop the complete Membership Plans module.

Only Trainers can access this module.

Backend is already completed.

Do NOT modify backend APIs.

Always follow backend request and response structures.

---

# Route

/plans

Protected Route

Trainer Only

---

# Purpose

Trainer should be able to

• View all plans

• Create a plan

• Update a plan

• Activate/Deactivate a plan

---

# APIs

## Get All Plans

GET

`/api/trainer/plans`

Authorization

Bearer Token

---

## Response

{
    "success": true,
    "message": "Plans fetched successfully.",
    "data": [

        {

            "planId":1,

            "planName":"Elite",

            "description":"Premium Gym Membership",

            "price":2500,

            "durationInDays":30

        }

    ]

}

---

## Create Plan

POST

`/api/trainer/plans`

Authorization

Bearer Token

---

## Request

{

"planName":"Elite",

"description":"Premium Gym Membership",

"price":2500,

"durationInDays":30

}

---

## Validation

Plan Name

Required

Maximum 100 characters

Description

Required

Maximum 500 characters

Price

Required

Greater than Zero

Duration

Required

Greater than Zero

---

## Success Response

{

"success":true,

"message":"Membership Plan created successfully.",

"data":{

"planId":1,

"planName":"Elite",

"description":"Premium Gym Membership",

"price":2500,

"durationInDays":30

}

}

---

## Update Plan

PUT

`/api/trainer/plans/{id}`

Authorization

Bearer Token

---

## Request

{

"planName":"Elite",

"description":"Updated Description",

"price":3000,

"durationInDays":45

}

---

## Change Plan Status (Activate/Deactivate)

PATCH

`/api/trainer/plans/{id}/status`

Authorization

Bearer Token

### Request

{

"active": true

}

### Purpose

Use this endpoint to activate or deactivate a membership plan instead of deleting it. When a plan is deactivated, it will not be available to members for purchase.

---

# Business Rules

A deactivated membership plan should no longer appear in the Plans page.

If the backend prevents status changes because the plan is already used by memberships, display the backend error message returned in the "message" field.

Do not assume status change will always succeed.

---

# Membership Plans Page Layout

Top Section

------------------------------------------------

Membership Plans

Create Plan Button

Refresh Button

------------------------------------------------

Plans Table

------------------------------------------------

Plan Name

Description

Price

Duration

Status

Actions

------------------------------------------------

Edit

Deactivate/Activate

---

# Create Plan

Click

Create Plan

↓

Open Modal

Fields

Plan Name

Description

Price

Duration

Buttons

Create

Cancel

---

# Edit Plan

Click

Edit

↓

Open Modal

Pre-fill all fields

Buttons

Update

Cancel

---

# Deactivate/Activate Plan

Click

Deactivate or Activate

↓

Confirmation Dialog

Title

Deactivate Membership Plan? or Activate Membership Plan?

Message

Are you sure?

Buttons

Confirm

Cancel

---

# Table Features

Search

Sort

Pagination

Responsive

---

# Search

Frontend Search

Search by

Plan Name

Description

---

# Sorting

Sort by

Plan Name

Price

Duration

---

# Price

Display

₹2500

instead of

2500

---

# Duration

Display

30 Days

instead of

30

---

# Status

Display

Active or Inactive badge

GREEN for Active

GRAY for Inactive

---

# Loading

Display Loader while API executes.

Disable buttons during API execution.

---

# Empty State

No Membership Plans Available

Display

Create your first membership plan.

---

# Toast Messages

Create Success

Membership Plan created successfully.

Update Success

Membership Plan updated successfully.

Status Change Success

Membership Plan status changed successfully.

Error

Display backend message.

---

# Error Handling

401

Redirect Login

403

Access Denied

404

Plan Not Found

500

Display generic error toast.

---

# Responsive Design

Desktop

Table Layout

Tablet

Responsive Table

Mobile

Cards instead of table rows

---

# Important Rules

Never hardcode data.

Always use backend responses.

Never modify backend APIs.

Frontend must completely adapt to backend.

Always refresh the plans list after Create, Update or Status Change.