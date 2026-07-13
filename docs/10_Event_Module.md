# 10_Event_Module.md

# Gym Management System
## Event Module

Develop the complete Event Module.

Backend is already implemented in Spring Boot.

Do NOT modify backend APIs.

Always follow backend contracts exactly.

There are two roles.

Trainer

Member

---

# Purpose

Trainer

• Create Event

• View Events

• View Event Details

• Update Event

• Delete Event

Member

• View Events

• View Event Details

Members CANNOT

Create

Update

Delete

Events.

---

# Routes

Trainer

/events

Member

/member/events

---

# Trainer APIs

## Create Event

POST

/api/trainer/events

Authorization

Bearer Token

---

Request

{

"title":"Morning Yoga Session",

"description":"Yoga for Beginners",

"eventDate":"2026-08-15",

"eventTime":"07:00:00",

"location":"Main Hall"

}

---

Validation

Title

Required

Description

Required

Event Date

Required

Cannot be before today's date.

Event Time

Required

Location

Required

---

Success Response

{

"success":true,

"message":"Event created successfully.",

"data":{

"eventId":1,

"title":"Morning Yoga Session",

"description":"Yoga for Beginners",

"eventDate":"2026-08-15",

"eventTime":"07:00:00",

"location":"Main Hall",

"daysRemaining":32

}

}

---

# Get All Events

GET

/api/trainer/events

Authorization

Bearer Token

---

Response

{

"success":true,

"data":[

{

"eventId":1,

"title":"Morning Yoga",

"description":"Yoga",

"eventDate":"2026-08-15",

"eventTime":"07:00:00",

"location":"Main Hall",

"daysRemaining":32

}

]

}

---

# Event Details

GET

/api/trainer/events/{eventId}

Authorization

Bearer Token

---

# Update Event

PUT

/api/trainer/events/{eventId}

Authorization

Bearer Token

Use the same request body as Create Event.

---

# Delete Event

DELETE

/api/trainer/events/{eventId}

Authorization

Bearer Token

Display confirmation dialog before deleting.

Backend permanently deletes the event.

---

# Important Backend Rule

Expired events are automatically removed every midnight by the backend scheduler.

Frontend should NEVER display expired events.

Frontend should NEVER try to expire events manually.

---

# Trainer Event Page

Top Bar

--------------------------------

Events

Create Event

Refresh

--------------------------------

Search

--------------------------------

Events Table

--------------------------------

Title

Date

Time

Location

Days Remaining

Actions

--------------------------------

View

Edit

Delete

--------------------------------

---

# Search

Frontend Search

Search by

Title

Location

---

# Sorting

Title

Event Date

Days Remaining

---

# Create Event

Click

Create Event

↓

Open Modal

Fields

Title

Description

Date

Time

Location

Buttons

Create

Cancel

---

# Edit Event

Click

Edit

↓

Open Modal

Pre-fill all fields

Buttons

Update

Cancel

---

# Delete Event

Click

Delete

↓

Confirmation Dialog

Title

Delete Event?

Message

Are you sure you want to delete this event?

Buttons

Delete

Cancel

---

# Event Details

Click

View

↓

Open Dialog

Display

Title

Description

Date

Time

Location

Days Remaining

---

# Member Events

Route

/member/events

---

API

GET

/api/member/events

Authorization

Bearer Token

Backend returns only upcoming events.

---

Member Layout

Display Event Cards.

Each Card

Title

Description

Date

Time

Location

Days Remaining

View Details

---

Member Event Details

GET

/api/member/events/{eventId}

Display

Title

Description

Date

Time

Location

Days Remaining

---

# Empty State

Trainer

No Events Created

Member

No Upcoming Events

---

# Loading

Display Skeleton Loader.

Disable buttons while API executes.

---

# Toast Messages

Create Success

Update Success

Delete Success

Display backend message.

---

# Error Handling

401

Redirect Login

403

Access Denied

404

Event Not Found

500

Generic Error Toast

---

# Responsive Design

Desktop

Responsive Table

Mobile

Event Cards

---

# Important Rules

Frontend should NEVER delete expired events automatically.

Frontend should NEVER calculate daysRemaining.

Frontend should ALWAYS display values returned by backend.

Backend is the source of truth.

After Create, Update or Delete,

refresh the Events list automatically.