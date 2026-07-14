# Data Models & Database Schema Reference

## Core Entities

### User Entity
```
Table: users
- id (Long, PK)
- username (String, unique, not null, max 50)
- email (String, unique, not null)
- password (String, not null)
- role (Enum: ROLE_MEMBER, ROLE_TRAINER, not null)
- enabled (Boolean, default: true)
- created_at (LocalDateTime, not null)
- updated_at (LocalDateTime)

Relationships:
- One-to-One with Member (optional)
- One-to-One with Trainer (optional)
```

### Member Entity
```
Table: members
- id (Long, PK)
- user_id (Long, FK to users, unique, not null)
- fullName (String, not null)
- age (Integer)
- phone (String, unique, not null)
- gender (Enum: MALE, FEMALE, TRANS)
- dob (LocalDate)
- height (Double)
- weight (Double)
- address (String)
- profileImage (String) - file path like "uploads/profile/uuid.png"
- status (Enum: ACTIVE, INACTIVE, BLOCKED)
- created_at (LocalDateTime, not null)
- updated_at (LocalDateTime)

Relationships:
- Many-to-One with Membership
- Many-to-One with Payment
```

### Trainer Entity
```
Table: trainers
- id (Long, PK)
- user_id (Long, FK to users, unique, not null)
- fullName (String, not null)
- phone (String, unique, not null)
- experience (Integer) - years of experience
- specialization (String)
- profileImage (String)
- created_at (LocalDateTime, not null)
- updated_at (LocalDateTime)
```

### MembershipPlan Entity
```
Table: membership_plan
- id (Long, PK)
- planName (String, unique, not null)
- durationInDays (Integer, not null)
- price (BigDecimal, precision 10, scale 2)
- description (String, max 500)
- active (Boolean, not null)
- created_at (LocalDateTime, not null)
- updated_at (LocalDateTime)

Sample Data:
- Basic - 1 Month | 30 days | 999.00 | Active
- Standard - 3 Months | 90 days | 2499.00 | Active
- Premium - 6 Months | 180 days | 4999.00 | Active
- Diamond - 1 Year | 365 days | 8999.00 | Active
```

### Membership Entity
```
Table: memberships
- id (Long, PK)
- member_id (Long, FK to members, not null)
- plan_id (Long, FK to membership_plan, not null)
- payment_id (Long, FK to payments)
- startDate (LocalDate, not null)
- expiryDate (LocalDate, not null)
- status (Enum: ACTIVE, EXPIRED, CANCELLED, PENDING, not null)
- created_at (LocalDateTime, not null)
- updated_at (LocalDateTime)

Constraints:
- Each member can have multiple memberships (history)
- Only ONE membership should be ACTIVE at a time
```

### Payment Entity
```
Table: payments
- id (Long, PK)
- member_id (Long, FK to members, not null)
- membership_plan_id (Long, FK to membership_plan, not null)
- planName (String, not null)
- amount (BigDecimal, precision 10, scale 2)
- paymentMethod (Enum: CASH, RAZORPAY, not null)
- paymentStatus (Enum: PENDING, SUCCESS, FAILED, REFUNDED, not null)
- paymentReference (String, unique, not null) - CASH_REF_xxx or order_xxx
- razorpayOrderId (String, unique)
- razorpayPaymentId (String, unique)
- razorpaySignature (String)
- paymentMessage (String, max 500)
- created_at (LocalDateTime, not null)
- updated_at (LocalDateTime)

Payment Flow:
1. CASH: Trainer creates payment with CASH method, status SUCCESS immediately
2. RAZORPAY: 
   - Create order (status PENDING)
   - Verify payment after checkout
   - Update to SUCCESS with signature
```

### Event Entity
```
Table: events
- id (Long, PK)
- title (String, not null, max 100)
- description (String, not null, max 1000)
- eventDate (LocalDate, not null)
- eventTime (LocalTime, not null)
- location (String, not null, max 255)
- created_at (LocalDateTime, not null)
- updated_at (LocalDateTime)

Created By: Trainer
Visible To: All members and trainers
```

---

## Enums Reference

### Role Enum
```java
ROLE_MEMBER    // Member role
ROLE_TRAINER   // Trainer/Admin role
```

### Gender Enum
```java
MALE
FEMALE
TRANS
```

### MemberStatus Enum
```java
ACTIVE      // Member can use gym
INACTIVE    // Member inactive
BLOCKED     // Member blocked by trainer
```

### MembershipStatus Enum
```java
ACTIVE      // Current valid membership
EXPIRED     // Membership has expired
CANCELLED   // Membership cancelled
PENDING     // Awaiting payment verification
```

### PaymentStatus Enum
```java
PENDING     // Payment initiated, not verified
SUCCESS     // Payment successful
FAILED      // Payment failed
REFUNDED    // Payment refunded
```

### PaymentMethod Enum
```java
CASH        // Cash payment recorded by trainer
RAZORPAY    // Online payment via Razorpay
```

### MemberFilter Enum
```java
ALL              // All members
ACTIVE           // Members with active membership
INACTIVE         // Members without active membership
EXPIRING_SOON    // Members whose membership expires in X days
```

---

## Database Relationships

### Entity Relationship Diagram (Conceptual)

```
User (1)
  ├── Member (0..1)
  │   ├── Membership (*)
  │   │   ├── MembershipPlan (1)
  │   │   └── Payment (0..1)
  │   └── Payment (*)
  └── Trainer (0..1)

Event (*) - Created by Trainer, viewed by all
```

### Key Relationships Explained

1. **User → Member/Trainer**
   - One User can be either a Member or Trainer (not both)
   - OneToOne relationship

2. **Member → Membership**
   - One Member can have multiple Memberships (history)
   - ManyToOne relationship

3. **Member → Payment**
   - One Member can have multiple Payments
   - ManyToOne relationship

4. **Membership → MembershipPlan**
   - One Membership belongs to one Plan
   - ManyToOne relationship

5. **Payment → MembershipPlan**
   - One Payment is for one Plan
   - ManyToOne relationship

6. **Membership → Payment**
   - One Membership can be linked to one Payment
   - OneToOne relationship

---

## Example Data Structures

### Member Profile Response
```json
{
  "id": 1,
  "fullName": "John Doe",
  "email": "john@example.com",
  "phone": "9876543210",
  "age": 28,
  "gender": "MALE",
  "dob": "1998-05-15",
  "height": 5.9,
  "weight": 75.5,
  "address": "123 Street, City",
  "profileImage": "uploads/profile/uuid.png",
  "status": "ACTIVE",
  "createdAt": "2026-07-01T08:00:00",
  "updatedAt": "2026-07-14T10:30:00"
}
```

### Membership Response
```json
{
  "id": 1,
  "memberId": 1,
  "planId": 1,
  "planName": "Basic - 1 Month",
  "startDate": "2026-07-14",
  "expiryDate": "2026-08-14",
  "status": "ACTIVE",
  "daysRemaining": 31
}
```

### Payment Response
```json
{
  "id": 1,
  "memberId": 1,
  "planId": 1,
  "planName": "Basic - 1 Month",
  "amount": 999.00,
  "paymentMethod": "RAZORPAY",
  "paymentStatus": "SUCCESS",
  "paymentReference": "order_12345abc",
  "razorpayOrderId": "order_12345abc",
  "razorpayPaymentId": "pay_12345xyz",
  "createdAt": "2026-07-14T10:30:00"
}
```

### Plan Response
```json
{
  "id": 1,
  "planName": "Basic - 1 Month",
  "durationInDays": 30,
  "price": 999.00,
  "description": "Basic gym membership for 1 month",
  "active": true,
  "createdAt": "2026-07-01T08:00:00",
  "updatedAt": "2026-07-14T10:30:00"
}
```

### Event Response
```json
{
  "id": 1,
  "title": "Summer Fitness Challenge",
  "description": "Join our 30-day fitness challenge with expert trainers",
  "eventDate": "2026-08-01",
  "eventTime": "06:00:00",
  "location": "Gym Main Hall",
  "createdAt": "2026-07-14T10:30:00",
  "updatedAt": "2026-07-14T10:30:00"
}
```

---

## Database Constraints

### Unique Constraints
- `users.username` - Unique username
- `users.email` - Unique email
- `members.phone` - Unique phone number
- `membership_plan.planName` - Unique plan name
- `payments.paymentReference` - Unique payment reference
- `payments.razorpayOrderId` - Unique Razorpay order ID (if not null)
- `payments.razorpayPaymentId` - Unique Razorpay payment ID (if not null)

### Not Null Constraints
- `users.username`, `email`, `password`, `role`
- `members.user_id`, `fullName`, `phone`, `status`
- `trainers.user_id`, `fullName`, `phone`
- `membership_plan.planName`, `durationInDays`, `price`, `active`
- `memberships.member_id`, `plan_id`, `startDate`, `expiryDate`, `status`
- `payments.member_id`, `membership_plan_id`, `planName`, `amount`, `paymentMethod`, `paymentStatus`, `paymentReference`
- `events.title`, `description`, `eventDate`, `eventTime`, `location`

---

## Database Query Examples (for frontend developer understanding)

### Get Current Membership
```
SELECT m.* FROM memberships m
WHERE m.member_id = ? AND m.status = 'ACTIVE'
```

### Get Payment History
```
SELECT p.* FROM payments p
WHERE p.member_id = ? AND p.paymentStatus = 'SUCCESS'
ORDER BY p.created_at DESC
```

### Get Members by Filter
```
-- ACTIVE: Members with active membership
SELECT DISTINCT m.* FROM members m
INNER JOIN memberships mbh ON m.id = mbh.member_id
WHERE mbh.status = 'ACTIVE'

-- EXPIRING_SOON: Memberships expiring in next X days
SELECT m.* FROM members m
INNER JOIN memberships mbh ON m.id = mbh.member_id
WHERE mbh.status = 'ACTIVE'
AND mbh.expiryDate BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY)
```

### Get Dashboard Metrics
```
SELECT 
  COUNT(*) as totalMembers,
  SUM(CASE WHEN memberships.status = 'ACTIVE' THEN 1 ELSE 0 END) as activeMembers,
  SUM(p.amount) as totalRevenue
FROM members m
LEFT JOIN memberships ON m.id = memberships.member_id
LEFT JOIN payments p ON m.id = p.member_id AND p.paymentStatus = 'SUCCESS'
```

---

## Indexes (Performance Optimization)

```
- Index on memberships.member_id
- Index on memberships.status
- Index on memberships.expiryDate
- Index on payments.member_id
- Index on payments.paymentStatus
- Index on payments.created_at
- Index on events.eventDate
- Index on users.email
- Index on users.username
```

---

## Data Validation Rules

### User Registration
- Email format: valid email
- Password: minimum 6 characters
- Username: 4-20 characters, alphanumeric

### Member Profile
- Phone: 10 digits, starts with 6-9
- Age: positive integer (if provided)
- Height/Weight: positive decimal numbers

### Membership Plan
- Plan name: non-empty string
- Duration: positive integer (days)
- Price: positive decimal (BigDecimal)
- Description: max 500 characters

### Event
- Title: max 100 characters
- Description: max 1000 characters
- Location: max 255 characters
- Event date: must be future date

### Payment
- Amount: must match plan price or be manually adjusted
- Payment reference: unique identifier
