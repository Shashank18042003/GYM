# Gym Management System - Documentation Hub

Welcome to the comprehensive documentation for building the React frontend of the Gym Management System.

## 📚 Documentation Files Overview

### 1. **REACT_BUILD_PROMPT.md** ⭐ START HERE
**The most important file** - Contains everything an AI assistant needs to build the complete React application from scratch.

**Contents**:
- Complete project overview
- Technology stack requirements
- User authentication & authorization details
- ALL 25 API endpoints with exact request/response formats
- Data models and enums
- Required pages and features for both Member and Trainer roles
- State management architecture (Context API structure)
- Form validation requirements
- Error handling strategies
- Razorpay payment integration details
- Security requirements
- Project structure recommendations
- Development workflow
- Environment configuration
- Critical reminders and success criteria

**Use this file when**: You need to instruct an AI to build the React application, or as the master reference document.

---

### 2. **API_REFERENCE.md**
Quick reference guide for all API endpoints.

**Contents**:
- Endpoints organized by category (Auth, Member, Trainer)
- Request/response format examples
- Query parameters and header requirements
- Status codes reference
- Error response format
- cURL examples for testing
- Axios configuration example
- Common integration points

**Use this file when**: You need to quickly look up an endpoint, test with Postman/curl, or integrate a specific API call.

---

### 3. **DATA_MODELS.md**
Complete reference for all data entities and database schema.

**Contents**:
- User entity structure
- Member, Trainer, MembershipPlan entities
- Membership, Payment, Event entities
- All enums (Role, Gender, MemberStatus, etc.)
- Entity relationships and constraints
- Example data structures in JSON format
- Unique and not-null constraints
- Database indexes for performance
- Data validation rules

**Use this file when**: You need to understand the data structure, field names, or validation rules.

---

### 4. **BACKEND_ARCHITECTURE.md**
Deep dive into backend architecture and integration details.

**Contents**:
- Technology stack explanation
- Security architecture (JWT flow)
- CORS configuration
- Request/response pattern
- Business logic flows (registration, payment, membership)
- Razorpay integration details
- File upload feature explanation
- Database schema and initialization
- Error handling strategy
- Performance considerations
- Scheduled tasks and automation
- Deployment configuration
- Logging and debugging
- Security best practices
- Troubleshooting guide

**Use this file when**: You need to understand how the backend works, integrate complex features, or debug issues.

---

### 5. **IMPLEMENTATION_CHECKLIST.md**
Comprehensive checklist for implementing the React application.

**Contents**:
- Pre-development setup checklist
- Component development checklist (auth, member, trainer)
- State management checklist
- API integration checklist
- Form validation checklist
- File upload checklist
- Razorpay integration checklist
- UI/UX checklist
- Security checklist
- Testing checklist (manual and API)
- Performance optimization checklist
- Production deployment checklist
- Debugging guide
- Documentation requirements
- Final sign-off checklist

**Use this file when**: Planning implementation, tracking progress, or ensuring nothing is missed.

---

### 6. **README.md** (This File)
Overview and navigation guide for the documentation.

---

## 🚀 Quick Start Guide

### For AI Assistants:
1. **Start with**: `REACT_BUILD_PROMPT.md`
2. **Reference for details**: `API_REFERENCE.md`, `DATA_MODELS.md`
3. **For debugging**: `BACKEND_ARCHITECTURE.md`
4. **For tracking**: `IMPLEMENTATION_CHECKLIST.md`

### For Developers:
1. **First time?** Read `REACT_BUILD_PROMPT.md` fully
2. **Setting up?** Follow `IMPLEMENTATION_CHECKLIST.md`
3. **Need API details?** Use `API_REFERENCE.md`
4. **Understanding data?** Check `DATA_MODELS.md`
5. **Debugging backend issues?** Consult `BACKEND_ARCHITECTURE.md`

---

## 📋 Project Context

### Backend Details
- **Framework**: Spring Boot 3.5.15
- **Database**: MySQL
- **Authentication**: JWT (24-hour expiration)
- **Payment Gateway**: Razorpay
- **Server Port**: 8081
- **CORS Allowed**: http://localhost:5173

### Frontend Requirements
- **React** 19.x
- **Vite** (build tool)
- **Tailwind CSS** (styling)
- **React Router** (routing)
- **Axios** (HTTP client)
- **Context API** (state management)
- **Dev Port**: 5173

### User Roles
1. **ROLE_MEMBER**: Can view plans, make payments, update profile, view events
2. **ROLE_TRAINER**: Can manage plans, members, events, payments, view analytics

---

## 🎯 Key Features to Implement

### Member Features
- ✅ User registration and login
- ✅ Profile management (view/edit/upload picture)
- ✅ View membership plans
- ✅ Subscribe to plans (Razorpay integration)
- ✅ Payment history
- ✅ View gym events

### Trainer Features
- ✅ Membership plan management (CRUD)
- ✅ Members management (view, filter, details)
- ✅ Payment recording (cash and online)
- ✅ Events management (CRUD)
- ✅ Analytics dashboard
- ✅ Payment history

---

## 🔗 API Endpoints Summary

### Authentication (2 endpoints)
- POST `/api/auth/register` - Register new user
- POST `/api/auth/login` - User login

### Member (9 endpoints)
- Profile management (GET/PUT)
- Profile picture upload
- Plans and events viewing
- Payment processing and history

### Trainer (13 endpoints)
- Plans management (CRUD)
- Members management (list, filter, details)
- Payments management
- Events management (CRUD)
- Dashboard analytics

**Total: 24 API endpoints** (Detailed specs in API_REFERENCE.md)

---

## 🔐 Security Highlights

1. **JWT Authentication**
   - Token-based authentication
   - 24-hour expiration
   - Stored in localStorage
   - Included in Authorization header

2. **Role-Based Access Control**
   - ROLE_MEMBER and ROLE_TRAINER
   - Route protection
   - Feature-level authorization

3. **Input Validation**
   - Frontend validation
   - Backend validation
   - Form validation rules documented

4. **CORS Security**
   - Only localhost:5173 allowed
   - Credentials included
   - Configurable for production

---

## 📊 Data Models (8 Entities)

1. **User** - Authentication and role management
2. **Member** - Member-specific details and relationships
3. **Trainer** - Trainer-specific details
4. **MembershipPlan** - Available subscription plans
5. **Membership** - Active/historical member subscriptions
6. **Payment** - Payment records and transaction history
7. **Event** - Gym events and activities
8. **BaseEntity** - Abstract entity with timestamps (created_at, updated_at)

---

## 🗂️ Recommended Project Structure

```
gym-management-frontend/
├── src/
│   ├── components/
│   │   ├── common/       (Navbar, Sidebar, ProtectedRoute)
│   │   ├── auth/         (Login, Register forms)
│   │   ├── member/       (Dashboard, Profile, Plans, etc.)
│   │   └── trainer/      (Dashboard, Members, Plans, etc.)
│   ├── context/          (Auth, Member, Trainer contexts)
│   ├── hooks/            (useAuth, useMember, useTrainer)
│   ├── services/         (API calls: authService, memberService, etc.)
│   ├── utils/            (Constants, helpers, validators)
│   ├── pages/            (Page layouts)
│   ├── styles/           (Global CSS and Tailwind config)
│   ├── App.jsx           (Main app component)
│   └── main.jsx          (Entry point)
├── .env.example          (Environment variables template)
├── vite.config.js        (Vite configuration)
├── tailwind.config.js    (Tailwind configuration)
└── package.json
```

---

## 🔄 Development Workflow

### Step 1: Setup
```bash
npm create vite@latest gym-frontend -- --template react
cd gym-frontend
npm install
```

### Step 2: Configure
- Setup .env with `VITE_API_BASE_URL=http://localhost:8081`
- Configure Tailwind CSS
- Setup Axios interceptors

### Step 3: Implement
- Build Auth context and components
- Build Member context and components
- Build Trainer context and components
- Integrate Razorpay

### Step 4: Test
- Manual testing of all features
- API integration testing
- Payment flow testing
- Responsive design testing

### Step 5: Deploy
- Build production bundle: `npm run build`
- Deploy to hosting platform
- Update environment variables for production

---

## ⚠️ Critical Requirements

1. **DO NOT modify backend APIs** - Use them exactly as specified
2. **Follow exact request/response formats** - No deviations
3. **Implement JWT authentication** - Required for all protected endpoints
4. **Handle all error cases** - Show user-friendly messages
5. **Support both user roles** - Different features for Member and Trainer
6. **Implement Razorpay correctly** - Follow payment flow precisely
7. **Validate all user inputs** - Frontend and backend validation
8. **Responsive design** - Works on mobile, tablet, desktop
9. **Security first** - Store tokens securely, validate permissions
10. **Complete error handling** - No broken experiences

---

## 🧪 Testing Credentials (Test Accounts)

Use these for manual testing:

### Member Account
- Email: `member@test.com`
- Password: `password123`
- Role: ROLE_MEMBER

### Trainer Account
- Email: `trainer@test.com`
- Password: `password123`
- Role: ROLE_TRAINER

---

## 🚨 Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| CORS Error | Verify frontend is on :5173, backend on :8081 |
| 401 Unauthorized | Check token in localStorage, verify it hasn't expired |
| 403 Forbidden | Verify user has required role for the route |
| Payment fails | Check Razorpay credentials, verify amount is in paise |
| File upload fails | Check file size < 5MB, verify directory exists |

---

## 📞 Backend Information

### Database
- **Type**: MySQL
- **URL**: jdbc:mysql://localhost:3306/GYM
- **User**: root
- **Password**: shashank4848 (change for production)

### Razorpay (Test Credentials)
- **Key ID**: rzp_test_TB6ZbqdkGwaXAa
- **Key Secret**: 5yNrCzQsIy7762SQA8G6pEgM
- **Currency**: INR
- **Mode**: Test

### Server
- **Host**: localhost
- **Port**: 8081
- **Base URL**: http://localhost:8081/api

---

## 📖 Documentation Standards

All documentation follows these principles:
- **Complete**: Every detail is documented
- **Clear**: Easy to understand for developers and AI
- **Organized**: Structured for quick reference
- **Practical**: Includes examples and code snippets
- **Accurate**: Reflects actual backend implementation
- **Maintainable**: Can be updated as system evolves

---

## ✅ Success Criteria

Your React implementation should:
- ✅ Allow user registration and login with validation
- ✅ Differentiate between MEMBER and TRAINER roles
- ✅ Members can view plans, make Razorpay payments, manage profile
- ✅ Trainers can manage plans, members, events, view analytics
- ✅ Implement complete Razorpay payment flow
- ✅ Handle all API responses correctly
- ✅ Show appropriate success and error messages
- ✅ Be responsive on all device sizes
- ✅ Implement JWT authentication fully
- ✅ Follow all specified API contracts exactly
- ✅ Have smooth, intuitive user experience
- ✅ Handle network errors gracefully

---

## 📞 Support & Questions

If building with AI assistance:
1. Provide the `REACT_BUILD_PROMPT.md` as context
2. Reference specific documentation files when needed
3. Use checklists to verify completeness
4. Test against specifications

If implementing manually:
1. Read `REACT_BUILD_PROMPT.md` completely first
2. Use checklists to track progress
3. Test each feature thoroughly
4. Refer to API_REFERENCE.md for exact endpoints
5. Check IMPLEMENTATION_CHECKLIST.md regularly

---

## 🎓 Learning Resources

### For React & Frontend
- React Documentation: https://react.dev
- Vite Guide: https://vitejs.dev
- Tailwind CSS: https://tailwindcss.com
- React Router: https://reactrouter.com
- Axios Docs: https://axios-http.com

### For Backend Integration
- Spring Boot: https://spring.io/projects/spring-boot
- JWT.io: https://jwt.io
- Razorpay Integration: https://razorpay.com/docs
- MySQL: https://www.mysql.com/products/community/

---

## 📝 Document Versioning

- **Created**: July 14, 2026
- **Backend Version**: Spring Boot 3.5.15
- **Frontend Stack**: React 19.x, Vite, Tailwind CSS
- **Last Updated**: July 14, 2026
- **Status**: Complete and Ready for Implementation

---

## 🏁 Next Steps

1. **If you're an AI assistant**: Start with `REACT_BUILD_PROMPT.md`
2. **If you're a developer**: 
   - Read `REACT_BUILD_PROMPT.md` (understand the vision)
   - Follow `IMPLEMENTATION_CHECKLIST.md` (track progress)
   - Reference other documents as needed
3. **If you're reviewing**: Check against success criteria and checklists

---

**This documentation is complete and contains everything needed to build a production-ready React Gym Management System. Happy coding! 🚀**
