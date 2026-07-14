# Backend Architecture & Integration Guide

## Backend Technology Stack

### Framework & Libraries
- **Spring Boot** 3.5.15
- **Spring Data JPA** - ORM for database operations
- **Spring Security** - Authentication & Authorization
- **Spring Web** - REST API support
- **MySQL 8.0** - Database
- **JJWT 0.12.7** - JWT Token generation and validation
- **Razorpay Java SDK 1.4.8** - Payment processing
- **Lombok** - Code generation for getters/setters
- **Maven** - Build tool

### Security Architecture

#### JWT Authentication
```
User Login → Email + Password → Backend validates
            ↓
        Generate JWT Token (24-hour expiration)
            ↓
        Return Token to Frontend
            ↓
Frontend stores token in localStorage
            ↓
For every protected request, include:
Authorization: Bearer <TOKEN>
            ↓
Backend verifies token signature & expiration
            ↓
If valid: Allow request
If invalid/expired: Return 401 Unauthorized
```

#### JWT Token Details
- **Algorithm**: HS256 (HMAC with SHA-256)
- **Secret Key**: `ThisIsMyVerySecureSecretKeyForGymMembershipApplication123456789`
- **Expiration**: 24 hours (86400000ms)
- **Claims**: userId, email, role

#### Spring Security Flow
```
Request arrives
    ↓
JwtAuthenticationFilter intercepts
    ↓
Extract JWT token from header
    ↓
Validate token signature
    ↓
Extract user details and role
    ↓
Set SecurityContext with authentication
    ↓
Route to appropriate controller
    ↓
@PreAuthorize checks role (if needed)
```

### CORS Configuration
```
Allowed Origins: http://localhost:5173
Allowed Methods: GET, POST, PUT, DELETE, PATCH, OPTIONS
Allowed Headers: *
Credentials: true
```

---

## API Layer Architecture

### Request/Response Pattern
All API endpoints follow consistent request/response format:

#### Success Response
```json
{
  "success": true,
  "message": "Operation description",
  "data": { /* actual data */ },
  "timestamp": "2026-07-14T10:30:00"
}
```

#### Error Response
```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2026-07-14T10:30:00"
}
```

### Controller Layer
- **AuthController**: `/api/auth/*` - Registration and login
- **MemberController**: `/api/member/*` - Member operations
- **TrainerController**: `/api/trainer/*` - Trainer operations
- **PaymentController**: `/api/member/payments/*` - Payment operations

### Service Layer
- **AuthService**: User authentication and registration
- **MemberService**: Member profile and member management
- **MembershipPlanService**: Plan CRUD operations
- **MembershipService**: Membership tracking and scheduler
- **PaymentService**: Payment recording and Razorpay integration
- **EventService**: Event management
- **DashboardService**: Dashboard statistics
- **RazorpayService**: Razorpay API integration

### Repository Layer (Data Access)
- **UserRepository**: User data access
- **MemberRepository**: Member data access
- **TrainerRepository**: Trainer data access
- **MembershipPlanRepository**: Plan data access
- **MembershipRepository**: Membership data access
- **PaymentRepository**: Payment data access
- **EventRepository**: Event data access

---

## Business Logic Flow

### User Registration Flow
```
1. POST /api/auth/register
2. AuthService.register() called
3. Validate input (email uniqueness, phone format, etc.)
4. Hash password using Spring Security
5. Create User entity with ROLE_MEMBER
6. Create Member entity linked to User
7. Generate JWT token
8. Return token and user details
```

### User Login Flow
```
1. POST /api/auth/login
2. AuthService.login() called
3. Find user by email
4. Validate password against hash
5. Generate JWT token
6. Return token with user details (userId, email, role)
7. Frontend stores token in localStorage
```

### Membership Purchase Flow (Razorpay)
```
1. Member selects plan and clicks "Subscribe"
   
2. POST /api/member/payments/create-order
   - Create Payment entity with status PENDING
   - Create Razorpay order via RazorpayClient
   - Return order ID and amount
   
3. Frontend opens Razorpay Checkout
   - User enters payment details
   - Razorpay processes payment
   
4. POST /api/member/payments/verify
   - Receive razorpayPaymentId, razorpayOrderId, signature
   - Verify signature using Razorpay API
   - If valid:
     - Update Payment status to SUCCESS
     - Create Membership entity with status ACTIVE
     - Calculate expiryDate = startDate + durationInDays
   - If invalid: Return error
```

### Membership Purchase Flow (Cash)
```
1. Trainer selects member and clicks "Record Payment"

2. Trainer fills form:
   - Select plan
   - Enter amount (default = plan price)

3. POST /api/trainer/members/{memberId}/cash-payment
   - Create Payment entity:
     - paymentMethod = CASH
     - paymentStatus = SUCCESS (immediate)
     - paymentReference = CASH_REF_XXXXX
   - Create Membership entity with status ACTIVE
   - Calculate expiryDate
```

### Membership Expiry Flow
```
Scheduler runs daily (configured with @EnableScheduling)
    ↓
Check all ACTIVE memberships where expiryDate < today
    ↓
Update status to EXPIRED
    ↓
Member sees expired status on dashboard
    ↓
Member must purchase new plan to reactivate
```

---

## Razorpay Integration Details

### Razorpay Configuration
```
Key ID: rzp_test_TB6ZbqdkGwaXAa
Key Secret: 5yNrCzQsIy7762SQA8G6pEgM
Currency: INR
Mode: Test
```

### Razorpay Client Usage
```java
RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);

// Create order
JSONObject orderRequest = new JSONObject();
orderRequest.put("amount", amountInPaise); // amount * 100
orderRequest.put("currency", "INR");
Order order = razorpayClient.Orders.create(orderRequest);

// Verify signature
boolean isSignatureValid = Utils.verifySignature(
  paymentData,
  signature,
  keySecret
);
```

### Amount Conversion
```
Frontend displays: 999.00 (INR)
Backend calculation: 999.00 * 100 = 99900 (paise)
Razorpay expects: 99900 (paise)
After payment: 99900 / 100 = 999.00 (back to INR)
```

---

## File Upload Feature

### Configuration
```
Upload directory: uploads/profile/
Max file size: 5MB
Allowed types: jpg, jpeg, png, gif (configured in frontend)
```

### Upload Flow
```
1. POST /api/member/profile-picture
2. Receive MultipartFile from request
3. Generate unique filename (UUID)
4. Validate file:
   - Check file size (max 5MB)
   - Check file type
5. Save to uploads/profile/ directory
6. Store path in Member.profileImage field
7. Return file path to frontend
8. Frontend displays image using: 
   http://localhost:8081/uploads/profile/uuid.png
```

---

## Database Schema

### Tables Created
```
users - Authentication and role management
members - Member-specific details
trainers - Trainer-specific details
membership_plan - Available membership plans
memberships - Active/historical memberships
payments - Payment records
events - Gym events
```

### Auto-Generated Columns
All entities inherit from BaseEntity:
```
created_at: Timestamp when record created (auto-set)
updated_at: Timestamp when record last updated (auto-set)
```

### Database Initialization
- Spring Boot with `ddl-auto=update`
- Tables created automatically on first run
- No manual migration needed for development

---

## Error Handling Strategy

### Validation Errors
```
Input validation happens in:
1. Frontend: Basic validation before submission
2. Backend: Comprehensive validation in service layer
3. Response: Returns 400 Bad Request with detailed error message
```

### Authentication Errors
```
- 401 Unauthorized: Missing or invalid JWT token
- 403 Forbidden: User lacks required role
- Auto-redirect: Frontend checks response code and redirects to login
```

### Business Logic Errors
```
- 400 Bad Request: Invalid operation (e.g., update plan on deleted record)
- 404 Not Found: Resource doesn't exist
- Custom messages: Each error returns specific message
```

---

## Performance Considerations

### Lazy Loading
Relationships configured with `FetchType.LAZY` to prevent:
- N+1 query problems
- Unnecessary data fetching
- Large response payloads

### Indexing
Database indexes on frequently queried columns:
- memberships.member_id
- memberships.status
- payments.member_id
- events.eventDate

### Pagination
Trainer member list and payment list support pagination (optional frontend implementation).

---

## Scheduling & Automation

### Scheduled Tasks
```
@Scheduled(cron = "0 0 0 * * *") // Daily at midnight
- Check expired memberships
- Update membership status from ACTIVE to EXPIRED
- Send notifications (if implemented)
```

### How It Works
```
1. MembershipService has scheduled method
2. Spring scheduler runs at configured time
3. Queries all memberships with:
   - status = ACTIVE
   - expiryDate <= today
4. Updates status to EXPIRED
5. Runs automatically, no manual trigger needed
```

---

## Deployment Configuration

### Current Development Setup
```
Database URL: jdbc:mysql://localhost:3306/GYM
Database User: root
Database Password: shashank4848 (TODO: Change for production)
Server Port: 8081
```

### For Production Deployment
1. Change database credentials
2. Update JWT secret key
3. Use production Razorpay credentials
4. Configure production CORS origins
5. Enable HTTPS
6. Setup database backups
7. Configure logging and monitoring

---

## Logging & Debugging

### Logging Configuration
```
spring.jpa.show-sql=true - Shows SQL queries in console
Log output includes:
- SQL statements
- Parameter bindings
- Execution time
```

### For Frontend Debugging
Monitor Network tab in browser:
- Request headers (Authorization)
- Response bodies (error messages)
- Status codes
- Response times

---

## Security Best Practices Implemented

1. **Password Security**
   - Passwords hashed using Spring Security
   - Never stored in plain text
   - Validated on registration

2. **JWT Security**
   - Token signed with secret key
   - Expiration time set to 24 hours
   - Validated on every protected request

3. **CORS Security**
   - Only localhost:5173 allowed in development
   - Change for production domains
   - Credentials included only with allowed origins

4. **Data Validation**
   - All inputs validated (email format, phone length, etc.)
   - SQL injection prevented via JPA parameterized queries
   - XSS prevention via proper response encoding

5. **Authorization**
   - Role-based access control
   - Methods require specific roles
   - Unauthorized access returns 403 Forbidden

---

## Endpoints Summary by Role

### Public Endpoints (No Auth Required)
```
POST /api/auth/register
POST /api/auth/login
POST /api/member/payments/verify
```

### Member-Only Endpoints
```
GET /api/member/profile
PUT /api/member/profile
POST /api/member/profile-picture
GET /api/member/plans
GET /api/member/events
GET /api/member/events/{eventId}
POST /api/member/payments/create-order
GET /api/member/payments/history
```

### Trainer-Only Endpoints
```
POST /api/trainer/plans
GET /api/trainer/plans
PUT /api/trainer/plans/{id}
PATCH /api/trainer/plans/{id}/status
GET /api/trainer/members
GET /api/trainer/members/{memberId}
POST /api/trainer/members/{memberId}/cash-payment
GET /api/trainer/payments
POST /api/trainer/events
GET /api/trainer/events
GET /api/trainer/events/{eventId}
PUT /api/trainer/events/{eventId}
DELETE /api/trainer/events/{eventId}
GET /api/trainer/dashboard
```

### Shared Endpoints
```
GET /api/member/events
GET /api/trainer/events
- Both member and trainer can view events
- Same endpoint for both roles
```

---

## Development Workflow

### Starting the Backend
```bash
cd D:\GYM_Management\explorer\GYM_Management
mvn spring-boot:run
```
Server starts on http://localhost:8081

### Building WAR/JAR
```bash
mvn clean package
java -jar target/GYM_Management-0.0.1-SNAPSHOT.jar
```

### Testing Endpoints
Use Postman or curl with provided examples.

---

## Common Issues & Solutions

### Issue: 401 Unauthorized
**Solution**: 
- Check token is included in header
- Verify token hasn't expired (24 hours)
- Clear localStorage and re-login

### Issue: 403 Forbidden
**Solution**:
- Check user has required role (ROLE_TRAINER for /trainer endpoints)
- Verify role in JWT token matches required role

### Issue: CORS Error
**Solution**:
- Frontend running on http://localhost:5173
- Backend running on http://localhost:8081
- CORS configured in CorsConfig.java

### Issue: File Upload Fails
**Solution**:
- Check file size < 5MB
- Create uploads/profile/ directory
- Verify write permissions on directory

### Issue: Payment Verification Fails
**Solution**:
- Check Razorpay credentials are correct
- Verify signature is calculated properly
- Check payment amount matches plan price
