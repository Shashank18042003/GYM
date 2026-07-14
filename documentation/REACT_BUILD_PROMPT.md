# Gym Management System - React Application Build Prompt

## PROJECT OVERVIEW

You are an expert React developer responsible for building a **complete, production-ready React frontend** for a **Gym Management System**. The backend is fully developed using Spring Boot 3.5.15 with Spring Security, JWT Authentication, and Razorpay payment integration.

### Key Principles
- **DO NOT** modify or suggest changes to backend APIs
- **ALWAYS** follow exact request/response structures
- **MUST** implement JWT-based authentication
- **IMPLEMENT** role-based access control (TRAINER & MEMBER)
- **BUILD** fully functional, user-friendly UI/UX

---

## TECHNOLOGY STACK (MANDATORY)

### Frontend Requirements
- **React** 19.x (latest)
- **Vite** (build tool)
- **React Router DOM** v6 (routing)
- **Axios** (HTTP client)
- **Context API** (state management)
- **Tailwind CSS** v3 (styling)
- **React Hook Form** (form management)
- **React Icons** (icon library)
- **React Toastify** (notifications)

### Development Tools
- **Node.js** 18+ / 20+
- **npm** or **yarn**
- **ESLint** (code quality)
- **Prettier** (code formatting)

### Backend Services
- **Spring Boot** 3.5.15
- **Spring Security**
- **JWT** (JJWT v0.12.7)
- **Razorpay** Payment Gateway
- **MySQL** Database
- **CORS** configured for http://localhost:5173

---

## USER AUTHENTICATION & AUTHORIZATION

### Authentication Flow
1. **Registration**: User creates account with email, username, phone, password, fullname
2. **Login**: User logs in with email + password
3. **JWT Token**: Backend returns JWT token valid for 24 hours (86400000ms)
4. **Token Storage**: Store in localStorage as `authToken`
5. **Token Usage**: Include in Authorization header for protected routes

### User Roles
1. **ROLE_MEMBER**: Can view plans, make payments, update profile, view events
2. **ROLE_TRAINER**: Can manage plans, members, payments, create events, view dashboard

### Access Control
- Public routes: `/login`, `/register`
- Member routes: `/member/*` (requires ROLE_MEMBER)
- Trainer routes: `/trainer/*` (requires ROLE_TRAINER)
- Protected route example: Redirect to login if token missing/invalid

### JWT Token Structure
```json
{
  "userId": 1,
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "user@example.com",
  "role": "ROLE_MEMBER"
}
```

---

## API ENDPOINTS SPECIFICATION

### Base URL
- **Production**: To be determined
- **Development**: `http://localhost:8081`
- **CORS Allowed**: `http://localhost:5173`

### Authentication Endpoints

#### 1. Register New User
```
POST /api/auth/register
Content-Type: application/json

Request Body:
{
  "username": "john_doe",
  "fullname": "John Doe",
  "email": "john@example.com",
  "phone": "9876543210",
  "password": "password123"
}

Response (201 Created):
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "userId": 1,
    "token": "jwt_token_here",
    "email": "john@example.com",
    "role": "ROLE_MEMBER"
  },
  "timestamp": "2026-07-14T10:30:00"
}

Validation Rules:
- Username: 4-20 characters
- Email: Valid email format
- Phone: 10 digits, starts with 6-9
- Password: Minimum 6 characters
- Fullname: Required, non-blank
```

#### 2. Login User
```
POST /api/auth/login
Content-Type: application/json

Request Body:
{
  "email": "john@example.com",
  "password": "password123"
}

Response (200 OK):
{
  "success": true,
  "message": "Login successful",
  "data": {
    "userId": 1,
    "token": "jwt_token_here",
    "email": "john@example.com",
    "role": "ROLE_MEMBER"
  },
  "timestamp": "2026-07-14T10:30:00"
}

Error (401 Unauthorized):
{
  "success": false,
  "message": "Invalid email or password",
  "data": null,
  "timestamp": "2026-07-14T10:30:00"
}
```

---

### Member Endpoints (Require JWT Token)

#### 3. Get Member Profile
```
GET /api/member/profile
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Profile fetched successfully",
  "data": {
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
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 4. Update Member Profile
```
PUT /api/member/profile
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request Body:
{
  "fullName": "John Doe",
  "age": 28,
  "gender": "MALE",
  "dob": "1998-05-15",
  "height": 5.9,
  "weight": 75.5,
  "address": "123 Street, City"
}

Response (200 OK):
{
  "success": true,
  "message": "Profile updated successfully",
  "data": { /* Updated member object */ },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 5. Upload Profile Picture
```
POST /api/member/profile-picture
Authorization: Bearer <JWT_TOKEN>
Content-Type: multipart/form-data

Form Data:
- file: <Image File (max 5MB)>

Response (200 OK):
{
  "success": true,
  "message": "Profile picture uploaded successfully",
  "data": {
    "filePath": "uploads/profile/uuid.png",
    "fileName": "uuid.png"
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 6. Get Active Membership Plans
```
GET /api/member/plans
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Plans fetched successfully",
  "data": [
    {
      "id": 1,
      "planName": "Basic - 1 Month",
      "durationInDays": 30,
      "price": 999.00,
      "description": "Basic gym membership for 1 month",
      "active": true
    },
    {
      "id": 2,
      "planName": "Premium - 3 Months",
      "durationInDays": 90,
      "price": 2499.00,
      "description": "Premium gym membership with trainer access",
      "active": true
    }
  ],
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 7. Create Razorpay Order
```
POST /api/member/payments/create-order
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request Body:
{
  "planId": 1
}

Response (200 OK):
{
  "success": true,
  "message": "Order created successfully",
  "data": {
    "orderId": "order_12345abc",
    "razorpayOrderId": "order_12345abc",
    "amount": 99900,
    "currency": "INR",
    "keyId": "rzp_test_TB6ZbqdkGwaXAa"
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 8. Verify Razorpay Payment
```
POST /api/member/payments/verify
Content-Type: application/json

Request Body:
{
  "razorpayOrderId": "order_12345abc",
  "razorpayPaymentId": "pay_12345xyz",
  "razorpaySignature": "signature_here"
}

Response (200 OK):
{
  "success": true,
  "message": "Payment verified and membership activated",
  "data": {
    "paymentId": 1,
    "memberId": 1,
    "planId": 1,
    "planName": "Basic - 1 Month",
    "amount": 999.00,
    "paymentMethod": "RAZORPAY",
    "paymentStatus": "SUCCESS",
    "paymentReference": "order_12345abc",
    "membership": {
      "id": 1,
      "startDate": "2026-07-14",
      "expiryDate": "2026-08-14",
      "status": "ACTIVE"
    }
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 9. Get Payment History
```
GET /api/member/payments/history
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Payment history fetched successfully",
  "data": [
    {
      "id": 1,
      "memberId": 1,
      "planId": 1,
      "planName": "Basic - 1 Month",
      "amount": 999.00,
      "paymentMethod": "RAZORPAY",
      "paymentStatus": "SUCCESS",
      "paymentReference": "order_12345abc",
      "createdAt": "2026-07-14T10:30:00"
    }
  ],
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 10. Get All Events (Member View)
```
GET /api/member/events
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Events fetched successfully",
  "data": [
    {
      "id": 1,
      "title": "Summer Fitness Challenge",
      "description": "Join our 30-day fitness challenge",
      "eventDate": "2026-08-01",
      "eventTime": "06:00:00",
      "location": "Gym Main Hall"
    }
  ],
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 11. Get Event Details
```
GET /api/member/events/{eventId}
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Event fetched successfully",
  "data": {
    "id": 1,
    "title": "Summer Fitness Challenge",
    "description": "Join our 30-day fitness challenge with expert trainers",
    "eventDate": "2026-08-01",
    "eventTime": "06:00:00",
    "location": "Gym Main Hall"
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

---

### Trainer Endpoints (Require JWT Token & ROLE_TRAINER)

#### 12. Create Membership Plan
```
POST /api/trainer/plans
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request Body:
{
  "planName": "Premium - 6 Months",
  "durationInDays": 180,
  "price": 4999.00,
  "description": "Premium membership with personal trainer"
}

Response (201 Created):
{
  "success": true,
  "message": "Plan created successfully",
  "data": {
    "id": 3,
    "planName": "Premium - 6 Months",
    "durationInDays": 180,
    "price": 4999.00,
    "description": "Premium membership with personal trainer",
    "active": true
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 13. Get All Membership Plans
```
GET /api/trainer/plans
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Plans fetched successfully",
  "data": [
    {
      "id": 1,
      "planName": "Basic - 1 Month",
      "durationInDays": 30,
      "price": 999.00,
      "description": "Basic gym membership",
      "active": true
    }
  ],
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 14. Update Membership Plan
```
PUT /api/trainer/plans/{planId}
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request Body:
{
  "planName": "Premium - 6 Months Updated",
  "durationInDays": 180,
  "price": 4499.00,
  "description": "Updated premium membership"
}

Response (200 OK):
{
  "success": true,
  "message": "Plan updated successfully",
  "data": { /* Updated plan */ },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 15. Change Plan Status (Activate/Deactivate)
```
PATCH /api/trainer/plans/{planId}/status
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request Body:
{
  "active": false
}

Response (200 OK):
{
  "success": true,
  "message": "Plan status updated successfully",
  "data": {
    "id": 1,
    "planName": "Basic - 1 Month",
    "active": false
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 16. Get All Members (with filters)
```
GET /api/trainer/members?filter=ALL&days=30
Authorization: Bearer <JWT_TOKEN>

Query Parameters:
- filter: ALL, ACTIVE, INACTIVE, EXPIRING_SOON (default: ALL)
- days: Number of days for EXPIRING_SOON filter (optional)

Response (200 OK):
{
  "success": true,
  "message": "Members fetched successfully",
  "data": [
    {
      "id": 1,
      "fullName": "John Doe",
      "email": "john@example.com",
      "phone": "9876543210",
      "status": "ACTIVE",
      "currentMembership": {
        "planName": "Basic - 1 Month",
        "startDate": "2026-07-14",
        "expiryDate": "2026-08-14",
        "status": "ACTIVE"
      },
      "createdAt": "2026-07-01T08:00:00"
    }
  ],
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 17. Get Member Details
```
GET /api/trainer/members/{memberId}
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Member details fetched successfully",
  "data": {
    "memberId": 1,
    "fullName": "John Doe",
    "email": "john@example.com",
    "phone": "9876543210",
    "age": 28,
    "gender": "MALE",
    "address": "123 Street, City",
    "profileImage": "uploads/profile/uuid.png",
    "status": "ACTIVE",
    "joinDate": "2026-07-01",
    "currentMembership": {
      "id": 1,
      "planName": "Basic - 1 Month",
      "startDate": "2026-07-14",
      "expiryDate": "2026-08-14",
      "status": "ACTIVE"
    },
    "membershipHistory": [
      {
        "id": 1,
        "planName": "Basic - 1 Month",
        "startDate": "2026-07-14",
        "expiryDate": "2026-08-14",
        "status": "ACTIVE"
      }
    ],
    "paymentHistory": [
      {
        "id": 1,
        "amount": 999.00,
        "paymentStatus": "SUCCESS",
        "createdAt": "2026-07-14T10:30:00"
      }
    ]
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 18. Create Cash Payment
```
POST /api/trainer/members/{memberId}/cash-payment
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request Body:
{
  "planId": 1,
  "amount": 999.00
}

Response (201 Created):
{
  "success": true,
  "message": "Cash payment recorded and membership activated",
  "data": {
    "paymentId": 2,
    "memberId": 1,
    "planId": 1,
    "planName": "Basic - 1 Month",
    "amount": 999.00,
    "paymentMethod": "CASH",
    "paymentStatus": "SUCCESS",
    "paymentReference": "CASH_REF_12345",
    "membership": {
      "id": 2,
      "startDate": "2026-07-14",
      "expiryDate": "2026-08-14",
      "status": "ACTIVE"
    }
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 19. Get All Successful Payments
```
GET /api/trainer/payments
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Payments fetched successfully",
  "data": [
    {
      "id": 1,
      "memberId": 1,
      "memberName": "John Doe",
      "planId": 1,
      "planName": "Basic - 1 Month",
      "amount": 999.00,
      "paymentMethod": "RAZORPAY",
      "paymentStatus": "SUCCESS",
      "createdAt": "2026-07-14T10:30:00"
    }
  ],
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 20. Create Event
```
POST /api/trainer/events
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request Body:
{
  "title": "Yoga Session",
  "description": "Morning yoga with our expert instructor",
  "eventDate": "2026-07-20",
  "eventTime": "06:00:00",
  "location": "Yoga Studio"
}

Response (201 Created):
{
  "success": true,
  "message": "Event created successfully",
  "data": {
    "id": 1,
    "title": "Yoga Session",
    "description": "Morning yoga with our expert instructor",
    "eventDate": "2026-07-20",
    "eventTime": "06:00:00",
    "location": "Yoga Studio",
    "createdAt": "2026-07-14T10:30:00"
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 21. Get All Events (Trainer View)
```
GET /api/trainer/events
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Events fetched successfully",
  "data": [
    {
      "id": 1,
      "title": "Yoga Session",
      "description": "Morning yoga",
      "eventDate": "2026-07-20",
      "eventTime": "06:00:00",
      "location": "Yoga Studio"
    }
  ],
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 22. Get Event Details (Trainer View)
```
GET /api/trainer/events/{eventId}
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Event fetched successfully",
  "data": {
    "id": 1,
    "title": "Yoga Session",
    "description": "Morning yoga with expert",
    "eventDate": "2026-07-20",
    "eventTime": "06:00:00",
    "location": "Yoga Studio"
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 23. Update Event
```
PUT /api/trainer/events/{eventId}
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

Request Body:
{
  "title": "Advanced Yoga Session",
  "description": "Advanced yoga for experienced members",
  "eventDate": "2026-07-20",
  "eventTime": "07:00:00",
  "location": "Yoga Studio - Hall A"
}

Response (200 OK):
{
  "success": true,
  "message": "Event updated successfully",
  "data": { /* Updated event */ },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 24. Delete Event
```
DELETE /api/trainer/events/{eventId}
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Event deleted successfully",
  "data": null,
  "timestamp": "2026-07-14T10:30:00"
}
```

#### 25. Get Trainer Dashboard
```
GET /api/trainer/dashboard
Authorization: Bearer <JWT_TOKEN>

Response (200 OK):
{
  "success": true,
  "message": "Dashboard data fetched successfully",
  "data": {
    "totalMembers": 50,
    "activeMembers": 40,
    "inactiveMembers": 10,
    "expiringMemberships": 5,
    "totalRevenue": 25000.00,
    "thisMonthRevenue": 3500.00,
    "totalPlans": 4,
    "activePlans": 3,
    "recentPayments": [
      {
        "id": 1,
        "memberName": "John Doe",
        "planName": "Basic - 1 Month",
        "amount": 999.00,
        "paymentStatus": "SUCCESS",
        "createdAt": "2026-07-14T10:30:00"
      }
    ],
    "topPerformingPlans": [
      {
        "id": 1,
        "planName": "Basic - 1 Month",
        "purchaseCount": 30
      }
    ]
  },
  "timestamp": "2026-07-14T10:30:00"
}
```

---

## DATA MODELS & ENUMS

### Enums

#### Role
```javascript
ROLE_MEMBER
ROLE_TRAINER
```

#### Gender
```javascript
MALE
FEMALE
TRANS
```

#### MemberStatus
```javascript
ACTIVE
INACTIVE
BLOCKED
```

#### MembershipStatus
```javascript
ACTIVE
EXPIRED
CANCELLED
PENDING
```

#### PaymentStatus
```javascript
PENDING
SUCCESS
FAILED
REFUNDED
```

#### PaymentMethod
```javascript
CASH
RAZORPAY
```

#### EventStatus (if used)
```javascript
SCHEDULED
COMPLETED
CANCELLED
```

#### MemberFilter (for trainer members list)
```javascript
ALL
ACTIVE
INACTIVE
EXPIRING_SOON
```

---

## REQUIRED PAGES & FEATURES

### Public Pages (No Authentication)

#### 1. Login Page
- Email input with validation
- Password input
- "Remember me" checkbox (optional)
- "Forgot password" link (future feature)
- Sign-up link
- Error message display
- Loading state during submission
- Submit button with loading state
- Redirect to appropriate dashboard after login

#### 2. Registration Page
- Username field (4-20 chars validation)
- Full Name field
- Email field with validation
- Phone field (10 digits, starts with 6-9)
- Password field (min 6 chars)
- Confirm password field (must match)
- Terms & conditions checkbox
- Back to login link
- Form validation with error messages
- Error handling and display
- Redirect to login after successful registration

---

### Member Pages (Require ROLE_MEMBER)

#### 3. Member Dashboard
- Welcome message with member name
- Current membership status card:
  - Plan name
  - Days remaining
  - Expiry date
  - Status (ACTIVE/EXPIRED/PENDING)
- Quick stats:
  - Active membership count
  - Total spent
  - Next expiry date
- Recent payments section
- Upcoming events section
- Action buttons: View Plans, Payment History, Browse Events

#### 4. Member Profile Page
- Display current profile information:
  - Full Name, Email, Phone
  - Age, Gender, Date of Birth
  - Height, Weight
  - Address
  - Profile Picture
- Edit profile functionality:
  - All fields editable
  - Save and cancel buttons
  - Success/error messages
- Profile picture upload:
  - Show current picture
  - Upload new picture with preview
  - Max 5MB validation
  - Image type validation (jpg, png, etc.)

#### 5. Membership Plans Page
- Display all active plans in card/table format:
  - Plan name
  - Duration (days)
  - Price
  - Description
  - Action button: "Subscribe Now"
- Filter/sort options (optional)
- Plan comparison feature (if applicable)
- "Subscribe Now" button opens payment flow

#### 6. Payment Page (Razorpay Integration)
- Show selected plan details
- Plan price display
- Payment method selection (optional, if multiple methods)
- Razorpay payment widget/checkout
- Order creation before payment
- Payment verification after completion
- Success/failure handling
- Order ID and transaction details display
- Redirect to dashboard on success

#### 7. Payment History Page
- Display all payments in table/list format:
  - Date
  - Plan name
  - Amount paid
  - Payment method
  - Status
  - Transaction reference
- Filter by status/date (optional)
- Export to PDF (optional)
- Sorting capabilities

#### 8. Events Page (Member)
- Display all upcoming events:
  - Event title
  - Date and time
  - Location
  - Short description
- Event details popup/page with full description
- Search and filter functionality
- Calendar view (optional)
- Event reminder notification (optional)

#### 9. Member Settings Page (Optional)
- Change password
- Logout button
- Account deactivation (if supported)
- Notification preferences

---

### Trainer Pages (Require ROLE_TRAINER)

#### 10. Trainer Dashboard
- Key metrics:
  - Total members
  - Active members
  - Inactive members
  - Memberships expiring soon (next 30 days)
  - Total revenue
  - This month revenue
  - Active plans count
- Charts/visualizations:
  - Revenue trend (monthly)
  - Member growth chart
  - Plan popularity chart
  - Payment method distribution
- Recent payments list
- Top performing plans
- Recent member registrations
- Navigation to management sections

#### 11. Membership Plans Management
- Display all plans (active and inactive) in table:
  - Plan name
  - Duration
  - Price
  - Status (Active/Inactive)
  - Actions: Edit, Toggle Status, Delete
- Create new plan button:
  - Modal/form with fields:
    - Plan name
    - Duration in days
    - Price
    - Description
  - Validation and error handling
  - Success message
- Edit plan functionality:
  - Prefilled form with current data
  - Update button
  - Success/error messages
- Toggle plan status (Active/Inactive):
  - Confirmation dialog
  - Immediate status update
- Search and filter plans

#### 12. Members Management
- Display all members in table with:
  - Member name
  - Email
  - Phone
  - Status (ACTIVE/INACTIVE/BLOCKED)
  - Current membership plan
  - Membership expiry date
  - Join date
  - Actions: View Details, Record Payment
- Filter by status:
  - ALL
  - ACTIVE
  - INACTIVE
  - EXPIRING_SOON (with days parameter)
- Search members by name/email/phone
- View member details page with:
  - Full profile information
  - Current membership details
  - Membership history
  - Payment history
  - Action button: Record Cash Payment
- Record cash payment modal:
  - Select plan from dropdown
  - Amount field (auto-filled from plan price, editable)
  - Submit button
  - Success/error messages
- Sorting and pagination

#### 13. Payments Management
- Display all successful payments in table:
  - Date
  - Member name
  - Plan name
  - Amount
  - Payment method (CASH/RAZORPAY)
  - Status
  - Reference ID
- Filter by:
  - Payment method
  - Date range
  - Status
- Search by member name
- Export functionality (optional)
- Sorting and pagination

#### 14. Events Management
- Display all events in table:
  - Title
  - Date and time
  - Location
  - Status
  - Actions: Edit, Delete, View Details
- Create event button:
  - Modal/form with fields:
    - Title
    - Description
    - Event date (date picker)
    - Event time (time picker)
    - Location
  - Validation
  - Success message
- Edit event:
  - Prefilled form
  - Update button
  - Success/error messages
- Delete event:
  - Confirmation dialog
  - Soft delete or permanent (as per backend)
- View event details
- Calendar view (optional)
- Search events by title/date

#### 15. Trainer Settings Page (Optional)
- Change password
- Logout button
- Profile management
- Notification preferences

---

## STATE MANAGEMENT REQUIREMENTS

### Context Providers to Create

#### 1. AuthContext
```javascript
{
  isAuthenticated: boolean,
  user: {
    userId: number,
    email: string,
    role: string,
    token: string
  },
  login(email, password): Promise,
  register(userData): Promise,
  logout(): void,
  isLoading: boolean,
  error: string | null
}
```

#### 2. MemberContext (for member role)
```javascript
{
  profile: memberData,
  currentMembership: membershipData,
  paymentHistory: payments[],
  plans: plans[],
  events: events[],
  getProfile(): Promise,
  updateProfile(data): Promise,
  uploadProfilePicture(file): Promise,
  getPlans(): Promise,
  getPaymentHistory(): Promise,
  getEvents(): Promise,
  isLoading: boolean,
  error: string | null
}
```

#### 3. TrainerContext (for trainer role)
```javascript
{
  dashboard: dashboardData,
  members: members[],
  plans: plans[],
  payments: payments[],
  events: events[],
  getMembers(filter, days): Promise,
  getMemberDetails(id): Promise,
  createPlan(data): Promise,
  updatePlan(id, data): Promise,
  changePlanStatus(id, status): Promise,
  createEvent(data): Promise,
  updateEvent(id, data): Promise,
  deleteEvent(id): Promise,
  recordCashPayment(memberId, data): Promise,
  getDashboard(): Promise,
  isLoading: boolean,
  error: string | null
}
```

---

## FORM REQUIREMENTS

### Validation Rules

#### Login Form
- Email: Required, valid email format
- Password: Required, minimum 6 characters

#### Registration Form
- Username: Required, 4-20 characters, alphanumeric
- Full Name: Required, non-blank
- Email: Required, valid email format
- Phone: Required, 10 digits, starts with 6-9
- Password: Required, minimum 6 characters
- Confirm Password: Required, must match password
- All fields must match server-side validation

#### Profile Update Form
- Full Name: Required
- Age: Optional, number
- Gender: Optional, enum (MALE, FEMALE, TRANS)
- DOB: Optional, date format
- Height: Optional, decimal number
- Weight: Optional, decimal number
- Address: Optional, text

#### Create/Edit Plan Form (Trainer)
- Plan Name: Required, string, unique
- Duration: Required, positive integer (days)
- Price: Required, positive decimal (BigDecimal in backend)
- Description: Optional, max 500 characters

#### Create/Edit Event Form (Trainer)
- Title: Required, max 100 characters
- Description: Required, max 1000 characters
- Event Date: Required, date picker (must be future date)
- Event Time: Required, time picker (format HH:mm:ss)
- Location: Required, max 255 characters

#### Cash Payment Form (Trainer)
- Plan ID: Required, select from dropdown
- Amount: Required, auto-fill from plan, but editable for discounts
- Validation: Amount > 0

---

## ERROR HANDLING & USER FEEDBACK

### Error Messages
- Display clear, user-friendly error messages
- Show validation errors inline on forms
- Handle network errors gracefully
- Session expiration handling (redirect to login)
- Display error toast/notification for API failures

### Success Messages
- Show success toast for operations:
  - Profile update
  - File upload
  - Payment processing
  - Plan creation/update
  - Event creation/update
  - Cash payment recording

### Loading States
- Show loading spinner during:
  - Form submission
  - Data fetching
  - File upload
  - Payment processing

### API Error Response Handling
```javascript
{
  success: false,
  message: "Error description",
  data: null,
  timestamp: "2026-07-14T10:30:00"
}
```

---

## PAYMENT INTEGRATION (RAZORPAY)

### Razorpay Configuration
- **Key ID**: `rzp_test_TB6ZbqdkGwaXAa` (test mode)
- **Currency**: INR
- **Amount**: In paise (multiply by 100)

### Payment Flow
1. Member selects a plan and clicks "Subscribe"
2. Create Razorpay order via POST `/api/member/payments/create-order`
3. Open Razorpay checkout with order details
4. After successful payment, get:
   - razorpayOrderId
   - razorpayPaymentId
   - razorpaySignature
5. Verify payment via POST `/api/member/payments/verify`
6. On success:
   - Show success message
   - Update membership status
   - Redirect to dashboard
7. On failure:
   - Show error message
   - Allow retry

### Frontend Implementation
- Use Razorpay script: `https://checkout.razorpay.com/v1/checkout.js`
- Or use React Razorpay SDK
- Handle payment success and failure callbacks
- Store transaction details locally for reference

---

## SECURITY REQUIREMENTS

### JWT Authentication
- Store token in localStorage as `authToken`
- Include token in Authorization header: `Bearer <token>`
- Token valid for 24 hours (86400000ms)
- Implement token refresh (optional, if backend supports)
- Clear token on logout
- Validate token on app initialization

### Password Security
- Never send password in plain text in localStorage
- Implement password confirmation on registration
- Show password strength indicator (optional)
- Implement "forgot password" flow (future)

### Input Validation
- Validate all user inputs on frontend before submission
- Sanitize inputs to prevent XSS
- Validate file uploads (size, type)

### Protected Routes
- Implement route guards
- Redirect unauthenticated users to login
- Redirect users to appropriate role-based pages
- Check token validity before rendering protected content

### CORS
- Backend configured for `http://localhost:5173`
- Frontend configured to hit `http://localhost:8081`
- Credentials included in requests

---

## ADDITIONAL FEATURES

### File Upload
- Profile picture upload to `/api/member/profile-picture`
- Max file size: 5MB
- Allowed types: jpg, jpeg, png, gif
- Show progress during upload
- Show preview after upload
- Handle upload errors gracefully

### Notifications/Toasts
- Use React Toastify for notifications
- Success messages (green)
- Error messages (red)
- Info messages (blue)
- Auto-dismiss after 3-5 seconds
- Allow manual dismiss

### Responsive Design
- Mobile-first approach
- Responsive layout using Tailwind CSS
- Mobile navigation (hamburger menu for smaller screens)
- Touch-friendly buttons and inputs
- Readable font sizes on all devices

### Accessibility
- Semantic HTML structure
- Proper label associations
- Keyboard navigation support
- ARIA labels where needed
- Color contrast compliance
- Focus indicators on interactive elements

---

## PROJECT STRUCTURE (RECOMMENDED)

```
gym-management-frontend/
├── public/
├── src/
│   ├── components/
│   │   ├── common/
│   │   │   ├── Navbar.jsx
│   │   │   ├── Sidebar.jsx
│   │   │   ├── ProtectedRoute.jsx
│   │   │   └── Loading.jsx
│   │   ├── auth/
│   │   │   ├── LoginForm.jsx
│   │   │   └── RegisterForm.jsx
│   │   ├── member/
│   │   │   ├── Dashboard.jsx
│   │   │   ├── Profile.jsx
│   │   │   ├── Plans.jsx
│   │   │   ├── Payment.jsx
│   │   │   ├── PaymentHistory.jsx
│   │   │   └── Events.jsx
│   │   └── trainer/
│   │       ├── Dashboard.jsx
│   │       ├── Members.jsx
│   │       ├── Plans.jsx
│   │       ├── Payments.jsx
│   │       └── Events.jsx
│   ├── context/
│   │   ├── AuthContext.jsx
│   │   ├── MemberContext.jsx
│   │   └── TrainerContext.jsx
│   ├── hooks/
│   │   ├── useAuth.js
│   │   ├── useMember.js
│   │   └── useTrainer.js
│   ├── services/
│   │   ├── api.js
│   │   ├── authService.js
│   │   ├── memberService.js
│   │   └── trainerService.js
│   ├── utils/
│   │   ├── constants.js
│   │   ├── helpers.js
│   │   └── validators.js
│   ├── pages/
│   │   ├── LoginPage.jsx
│   │   ├── RegisterPage.jsx
│   │   └── NotFoundPage.jsx
│   ├── styles/
│   │   └── globals.css
│   ├── App.jsx
│   └── main.jsx
├── .env.example
├── .gitignore
├── vite.config.js
├── tailwind.config.js
├── package.json
└── README.md
```

---

## DEVELOPMENT WORKFLOW

### Setup Instructions
1. Create Vite project: `npm create vite@latest gym-frontend -- --template react`
2. Install dependencies:
   ```bash
   npm install
   npm install react-router-dom axios react-hook-form react-toastify react-icons
   npm install -D tailwindcss postcss autoprefixer
   npx tailwindcss init -p
   ```
3. Configure Tailwind CSS
4. Create .env file with:
   ```
   VITE_API_BASE_URL=http://localhost:8081
   ```
5. Start development server: `npm run dev`

### Development Best Practices
- Use functional components with hooks
- Create custom hooks for reusable logic
- Use Context API for state management
- Implement error boundaries (optional)
- Log important events for debugging
- Use environment variables for configuration
- Follow naming conventions consistently
- Write comments for complex logic
- Keep components small and focused
- Reuse components across pages

### Testing Checklist
- Test all CRUD operations
- Test authentication and authorization
- Test form validation
- Test payment flow (test mode)
- Test file upload
- Test responsive design on mobile
- Test error handling
- Test loading states
- Test session expiration
- Test navigation between pages

### Before Production
- Build production bundle: `npm run build`
- Test production build locally
- Update backend URL in .env
- Verify all API endpoints work
- Test with real Razorpay credentials
- Implement error tracking (optional)
- Setup analytics (optional)
- Documentation and README

---

## ENVIRONMENT CONFIGURATION

### .env.development
```
VITE_API_BASE_URL=http://localhost:8081
VITE_RAZORPAY_KEY=rzp_test_TB6ZbqdkGwaXAa
VITE_APP_NAME=Gym Management
```

### .env.production
```
VITE_API_BASE_URL=https://api.yourdomain.com
VITE_RAZORPAY_KEY=rzp_live_XXXXXXXXX
VITE_APP_NAME=Gym Management
```

---

## CRITICAL REMINDERS

1. **Do NOT modify backend APIs** - Use them exactly as specified
2. **Token Management**: Store JWT securely, include in all protected requests
3. **Request/Response Format**: Follow exact structures shown in API docs
4. **Error Handling**: Implement comprehensive error handling for all API calls
5. **Loading States**: Show loading indicators during async operations
6. **Form Validation**: Validate on client before submission
7. **Authentication**: Check token validity on app load
8. **Role-Based Access**: Enforce role restrictions on routes and features
9. **File Upload**: Validate file size and type before upload
10. **Payment Flow**: Follow exact Razorpay flow shown in API docs

---

## TESTING CREDENTIALS

### Test User (Member)
- Email: `member@test.com`
- Password: `password123`

### Test User (Trainer)
- Email: `trainer@test.com`
- Password: `password123`

---

## SUCCESS CRITERIA

Your React application should:
✅ Allow users to register and login with validation
✅ Differentiate between MEMBER and TRAINER roles
✅ Members can view plans, make payments, manage profile, view events
✅ Trainers can manage plans, members, events, view payments and dashboard
✅ Integrate Razorpay payment gateway successfully
✅ Handle all API responses correctly
✅ Show appropriate success and error messages
✅ Be responsive on desktop, tablet, and mobile
✅ Have smooth navigation and user experience
✅ Handle network errors gracefully
✅ Implement JWT authentication completely
✅ Follow all specified API contracts exactly

---

**END OF PROMPT**

This document contains EVERYTHING needed to build a complete React application. Every API endpoint, data model, feature requirement, and technical specification is documented. Refer to this prompt for complete clarity on what to build.
