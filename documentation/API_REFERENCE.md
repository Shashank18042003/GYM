# API Endpoints Quick Reference

## Authentication Endpoints

| Method | Endpoint | Authentication | Purpose |
|--------|----------|-----------------|---------|
| POST | `/api/auth/register` | No | Register new user |
| POST | `/api/auth/login` | No | Login user and get JWT token |
| GET | `/api/auth/hello` | No | Test endpoint |

---

## Member Endpoints

| Method | Endpoint | Auth Required | Purpose |
|--------|----------|---------------|---------|
| GET | `/api/member/profile` | Yes (JWT) | Get member profile |
| PUT | `/api/member/profile` | Yes (JWT) | Update member profile |
| POST | `/api/member/profile-picture` | Yes (JWT) | Upload profile picture |
| GET | `/api/member/plans` | Yes (JWT) | Get active membership plans |
| GET | `/api/member/events` | Yes (JWT) | Get all events |
| GET | `/api/member/events/{eventId}` | Yes (JWT) | Get event details |
| POST | `/api/member/payments/create-order` | Yes (JWT) | Create Razorpay order |
| POST | `/api/member/payments/verify` | No | Verify Razorpay payment |
| GET | `/api/member/payments/history` | Yes (JWT) | Get payment history |

---

## Trainer Endpoints

| Method | Endpoint | Auth Required | Role | Purpose |
|--------|----------|---------------|------|---------|
| POST | `/api/trainer/plans` | Yes (JWT) | TRAINER | Create membership plan |
| GET | `/api/trainer/plans` | Yes (JWT) | TRAINER | Get all plans |
| PUT | `/api/trainer/plans/{id}` | Yes (JWT) | TRAINER | Update plan |
| PATCH | `/api/trainer/plans/{id}/status` | Yes (JWT) | TRAINER | Change plan status |
| GET | `/api/trainer/members` | Yes (JWT) | TRAINER | Get all members (with filters) |
| GET | `/api/trainer/members/{memberId}` | Yes (JWT) | TRAINER | Get member details |
| POST | `/api/trainer/members/{memberId}/cash-payment` | Yes (JWT) | TRAINER | Record cash payment |
| GET | `/api/trainer/payments` | Yes (JWT) | TRAINER | Get all successful payments |
| POST | `/api/trainer/events` | Yes (JWT) | TRAINER | Create event |
| GET | `/api/trainer/events` | Yes (JWT) | TRAINER | Get all events |
| GET | `/api/trainer/events/{eventId}` | Yes (JWT) | TRAINER | Get event details |
| PUT | `/api/trainer/events/{eventId}` | Yes (JWT) | TRAINER | Update event |
| DELETE | `/api/trainer/events/{eventId}` | Yes (JWT) | TRAINER | Delete event |
| GET | `/api/trainer/dashboard` | Yes (JWT) | TRAINER | Get dashboard statistics |

---

## Header Requirements

### For Protected Endpoints (Member & Trainer)
```
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

### For File Upload
```
Authorization: Bearer <JWT_TOKEN>
Content-Type: multipart/form-data
```

### For Payment Verification (no auth required)
```
Content-Type: application/json
```

---

## Query Parameters

### Get Members Filter
```
GET /api/trainer/members?filter=ALL&days=30
```

- `filter`: ALL, ACTIVE, INACTIVE, EXPIRING_SOON (default: ALL)
- `days`: Number of days for EXPIRING_SOON filter (optional)

---

## Status Codes Reference

| Code | Meaning | When Used |
|------|---------|-----------|
| 200 | OK | Successful GET, PUT, POST requests |
| 201 | Created | Successful POST creating new resource |
| 400 | Bad Request | Validation error or invalid input |
| 401 | Unauthorized | Missing or invalid JWT token |
| 403 | Forbidden | User doesn't have permission (wrong role) |
| 404 | Not Found | Resource not found |
| 500 | Internal Server Error | Server error |

---

## Error Response Format
```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2026-07-14T10:30:00"
}
```

---

## Success Response Format
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { /* Response data */ },
  "timestamp": "2026-07-14T10:30:00"
}
```

---

## API Testing Examples

### Using cURL

#### Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"member@test.com","password":"password123"}'
```

#### Get Member Profile
```bash
curl -X GET http://localhost:8081/api/member/profile \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

#### Create Plan (Trainer)
```bash
curl -X POST http://localhost:8081/api/trainer/plans \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "planName":"Premium - 6 Months",
    "durationInDays":180,
    "price":4999.00,
    "description":"Premium membership"
  }'
```

---

## Axios Configuration Example

```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add JWT token to every request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Handle response errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Clear token and redirect to login
      localStorage.removeItem('authToken');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
```

---

## Common Integration Points

### Payment Flow
1. POST `/api/member/payments/create-order` → Get Razorpay Order ID
2. User completes payment in Razorpay Checkout
3. POST `/api/member/payments/verify` → Verify and activate membership

### Member Registration & Profile
1. POST `/api/auth/register` → Create account
2. GET `/api/member/profile` → Fetch initial profile
3. PUT `/api/member/profile` → Update profile details
4. POST `/api/member/profile-picture` → Upload profile image

### Trainer Member Management
1. GET `/api/trainer/members` → List all members
2. GET `/api/trainer/members/{memberId}` → Get member details
3. POST `/api/trainer/members/{memberId}/cash-payment` → Record payment

### Trainer Plan Management
1. POST `/api/trainer/plans` → Create new plan
2. GET `/api/trainer/plans` → List all plans
3. PUT `/api/trainer/plans/{id}` → Update plan details
4. PATCH `/api/trainer/plans/{id}/status` → Toggle active/inactive
