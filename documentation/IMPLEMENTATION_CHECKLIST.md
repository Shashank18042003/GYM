# React Implementation Checklist & Testing Guide

## Pre-Development Checklist

### Project Setup
- [ ] Node.js 18+ / 20+ installed
- [ ] npm or yarn available
- [ ] Git initialized for version control
- [ ] Backend running on http://localhost:8081
- [ ] MySQL database configured and populated

### Environment Setup
- [ ] Create React app with Vite: `npm create vite@latest gym-frontend -- --template react`
- [ ] Install all dependencies from technology stack
- [ ] Configure .env files for development and production
- [ ] Setup Tailwind CSS
- [ ] Configure axios base URL to http://localhost:8081
- [ ] Setup ESLint and Prettier (optional but recommended)

---

## Component Development Checklist

### Authentication Components
#### Login Form
- [ ] Email input field with validation
- [ ] Password input field with validation
- [ ] Remember me checkbox (optional)
- [ ] Submit button with loading state
- [ ] Error message display
- [ ] Link to registration
- [ ] Form validation before submission
- [ ] API call to /api/auth/login
- [ ] Store JWT token in localStorage
- [ ] Redirect to appropriate dashboard
- [ ] Handle 401 errors

#### Registration Form
- [ ] Username field (4-20 chars)
- [ ] Full name field
- [ ] Email field with validation
- [ ] Phone field (10 digits validation)
- [ ] Password field (min 6 chars)
- [ ] Confirm password field
- [ ] Terms & conditions checkbox
- [ ] Form validation
- [ ] API call to /api/auth/register
- [ ] Success message
- [ ] Redirect to login
- [ ] Handle duplicate email/username errors

### Common Components
- [ ] Navbar with logo and user menu
- [ ] Sidebar/navigation for different sections
- [ ] Loading spinner
- [ ] Error boundary
- [ ] Toast notification system
- [ ] Protected route wrapper
- [ ] Not found (404) page
- [ ] Unauthorized (403) page

### Member Components
#### Dashboard
- [ ] Welcome message with member name
- [ ] Current membership card display
- [ ] Quick stats section
- [ ] Recent payments list
- [ ] Upcoming events section
- [ ] Navigation buttons
- [ ] Responsive layout
- [ ] Loading states
- [ ] Error handling

#### Profile Page
- [ ] Display current profile information
- [ ] Edit profile form with all fields
- [ ] Profile picture display
- [ ] Profile picture upload with preview
- [ ] File size validation (5MB max)
- [ ] Success/error messages
- [ ] Save and cancel buttons
- [ ] Responsive design

#### Plans Page
- [ ] Display all active plans
- [ ] Plan details in cards/table
- [ ] Plan name, duration, price
- [ ] Subscribe button for each plan
- [ ] Filter/search functionality
- [ ] Responsive grid layout
- [ ] Loading state

#### Payment Integration
- [ ] Display selected plan details
- [ ] Razorpay script integration
- [ ] Create order before payment
- [ ] Open Razorpay checkout
- [ ] Handle payment success
- [ ] Handle payment failure
- [ ] Verify payment with backend
- [ ] Update membership on success
- [ ] Show transaction details
- [ ] Error handling and retry

#### Payment History
- [ ] Display all payments in table
- [ ] Columns: date, plan, amount, method, status
- [ ] Filter by payment method/status
- [ ] Sort by date
- [ ] Pagination (optional)
- [ ] Responsive table design
- [ ] Format amount with currency

#### Events Page
- [ ] Display all events
- [ ] Event card with title, date, time, location
- [ ] Event details modal/page
- [ ] Search functionality
- [ ] Filter by date (optional)
- [ ] Responsive layout
- [ ] Loading states

### Trainer Components
#### Dashboard
- [ ] Total members count
- [ ] Active members count
- [ ] Inactive members count
- [ ] Expiring memberships count
- [ ] Total revenue display
- [ ] Monthly revenue display
- [ ] Charts for revenue trend
- [ ] Member growth chart
- [ ] Recent payments list
- [ ] Top performing plans
- [ ] Responsive grid layout

#### Plans Management
- [ ] Display all plans in table
- [ ] Create new plan button
- [ ] Edit plan functionality
- [ ] Toggle plan status (active/inactive)
- [ ] Delete plan functionality
- [ ] Create plan form/modal
- [ ] Plan form validation
- [ ] Search and filter plans
- [ ] Success/error messages
- [ ] Confirmation dialogs for destructive actions

#### Members Management
- [ ] Display all members in table
- [ ] Filter by status (ALL, ACTIVE, INACTIVE, EXPIRING_SOON)
- [ ] Search by name/email/phone
- [ ] View member details button
- [ ] Record payment button
- [ ] Member details page with full info
- [ ] Membership history in details
- [ ] Payment history in details
- [ ] Record cash payment modal
- [ ] Plan selection dropdown
- [ ] Amount input field
- [ ] Pagination/sorting
- [ ] Responsive table design

#### Payments Management
- [ ] Display all payments in table
- [ ] Filter by payment method
- [ ] Filter by date range
- [ ] Filter by status
- [ ] Search by member name
- [ ] Export to PDF (optional)
- [ ] Pagination
- [ ] Sorting capabilities
- [ ] Format amounts with currency

#### Events Management
- [ ] Display all events in table
- [ ] Create event button
- [ ] Create event form/modal
- [ ] Edit event functionality
- [ ] Delete event with confirmation
- [ ] View event details
- [ ] Form validation
- [ ] Date and time picker
- [ ] Success/error messages
- [ ] Calendar view (optional)

---

## State Management Checklist

### Auth Context
- [ ] isAuthenticated state
- [ ] User object with userId, email, role, token
- [ ] Login function with API call
- [ ] Register function with API call
- [ ] Logout function with localStorage cleanup
- [ ] Error handling
- [ ] Loading state
- [ ] Token validation on app load
- [ ] Automatic redirect on session expiry

### Member Context
- [ ] Profile data state
- [ ] Current membership state
- [ ] Payment history state
- [ ] Plans list state
- [ ] Events list state
- [ ] getProfile function
- [ ] updateProfile function
- [ ] uploadProfilePicture function
- [ ] getPlans function
- [ ] getPaymentHistory function
- [ ] getEvents function
- [ ] Error handling
- [ ] Loading states

### Trainer Context
- [ ] Dashboard data state
- [ ] Members list state
- [ ] Plans list state
- [ ] Payments list state
- [ ] Events list state
- [ ] getMembers function with filters
- [ ] getMemberDetails function
- [ ] createPlan function
- [ ] updatePlan function
- [ ] changePlanStatus function
- [ ] createEvent function
- [ ] updateEvent function
- [ ] deleteEvent function
- [ ] recordCashPayment function
- [ ] getDashboard function
- [ ] Error handling
- [ ] Loading states

---

## API Integration Checklist

### Axios Configuration
- [ ] Create axios instance with base URL
- [ ] Configure request interceptor to add JWT token
- [ ] Configure response interceptor for error handling
- [ ] Handle 401 (unauthorized) errors
- [ ] Handle 403 (forbidden) errors
- [ ] Handle 400 (bad request) errors
- [ ] Handle network errors
- [ ] Console logging for debugging

### Authentication API
- [ ] POST /api/auth/register integration
- [ ] POST /api/auth/login integration
- [ ] JWT token storage and retrieval
- [ ] Token validation logic

### Member API
- [ ] GET /api/member/profile
- [ ] PUT /api/member/profile
- [ ] POST /api/member/profile-picture
- [ ] GET /api/member/plans
- [ ] GET /api/member/events
- [ ] GET /api/member/events/{eventId}
- [ ] POST /api/member/payments/create-order
- [ ] POST /api/member/payments/verify
- [ ] GET /api/member/payments/history

### Trainer API
- [ ] POST /api/trainer/plans
- [ ] GET /api/trainer/plans
- [ ] PUT /api/trainer/plans/{id}
- [ ] PATCH /api/trainer/plans/{id}/status
- [ ] GET /api/trainer/members
- [ ] GET /api/trainer/members/{memberId}
- [ ] POST /api/trainer/members/{memberId}/cash-payment
- [ ] GET /api/trainer/payments
- [ ] POST /api/trainer/events
- [ ] GET /api/trainer/events
- [ ] GET /api/trainer/events/{eventId}
- [ ] PUT /api/trainer/events/{eventId}
- [ ] DELETE /api/trainer/events/{eventId}
- [ ] GET /api/trainer/dashboard

---

## Form Validation Checklist

### Login Form Validation
- [ ] Email required validation
- [ ] Email format validation
- [ ] Password required validation
- [ ] Display errors inline

### Registration Form Validation
- [ ] Username required
- [ ] Username length (4-20) validation
- [ ] Full name required
- [ ] Email required and format
- [ ] Phone required and format (10 digits, starts with 6-9)
- [ ] Password required (min 6 chars)
- [ ] Confirm password matches
- [ ] Real-time validation feedback

### Profile Update Validation
- [ ] Full name required
- [ ] Age (if provided) must be positive number
- [ ] Height/Weight (if provided) must be positive decimal
- [ ] Phone format validation

### Plan Creation Validation
- [ ] Plan name required
- [ ] Duration required and positive
- [ ] Price required and positive
- [ ] Description max 500 chars

### Event Creation Validation
- [ ] Title required, max 100 chars
- [ ] Description required, max 1000 chars
- [ ] Event date required and future date
- [ ] Event time required
- [ ] Location required, max 255 chars

---

## File Upload Checklist

### Profile Picture Upload
- [ ] File input element
- [ ] Show image preview before upload
- [ ] Validate file size (max 5MB)
- [ ] Validate file type (jpg, png, gif)
- [ ] Show upload progress
- [ ] Handle upload errors
- [ ] Display uploaded image
- [ ] Allow re-upload

---

## Razorpay Integration Checklist

### Payment Flow
- [ ] Import Razorpay script
- [ ] Create order API call
- [ ] Open Razorpay checkout
- [ ] Capture payment details from response
- [ ] Call verify endpoint
- [ ] Handle success response
- [ ] Handle failure response
- [ ] Show transaction reference

### Amount Handling
- [ ] Convert display amount (999.00) to paise (99900)
- [ ] Send correct amount to Razorpay
- [ ] Handle amounts from payment response correctly
- [ ] Display final amount in INR

---

## UI/UX Checklist

### Visual Design
- [ ] Consistent color scheme (brand colors)
- [ ] Typography hierarchy
- [ ] Proper spacing and padding
- [ ] Icons for actions and status
- [ ] Consistent button styles
- [ ] Form field styling
- [ ] Card/section layouts
- [ ] Proper contrast ratios

### Responsive Design
- [ ] Mobile-first approach
- [ ] Responsive navigation (hamburger menu)
- [ ] Readable font sizes on all devices
- [ ] Touch-friendly buttons (min 44px)
- [ ] Proper spacing for mobile
- [ ] Responsive tables (stack on mobile)
- [ ] Image responsiveness
- [ ] Test on multiple device sizes

### Accessibility
- [ ] Semantic HTML structure
- [ ] Proper label associations
- [ ] Keyboard navigation support
- [ ] ARIA labels for screen readers
- [ ] Color contrast (WCAG AA standards)
- [ ] Focus indicators on interactive elements
- [ ] Alt text for images

### User Feedback
- [ ] Success toast notifications
- [ ] Error toast notifications
- [ ] Loading spinners for async operations
- [ ] Disabled states for buttons
- [ ] Form submission feedback
- [ ] Confirmation dialogs for destructive actions
- [ ] Clear error messages
- [ ] Empty state messages

---

## Security Checklist

### Authentication
- [ ] Store JWT token securely (localStorage)
- [ ] Include token in all protected requests
- [ ] Clear token on logout
- [ ] Validate token on app initialization
- [ ] Handle token expiration (redirect to login)
- [ ] Validate user has correct role for pages

### Data Protection
- [ ] Never log sensitive data (passwords, tokens)
- [ ] Validate all user inputs
- [ ] Sanitize inputs for XSS prevention
- [ ] Use HTTPS for production
- [ ] Validate file uploads

### Authorization
- [ ] Check authentication before rendering protected content
- [ ] Enforce role-based access control
- [ ] Hide/disable UI elements based on permissions
- [ ] Redirect unauthorized users to login

---

## Testing Checklist

### Manual Testing

#### Authentication Flow
- [ ] Test registration with valid data
- [ ] Test registration with duplicate email
- [ ] Test registration validation errors
- [ ] Test login with valid credentials
- [ ] Test login with invalid credentials
- [ ] Test token storage and retrieval
- [ ] Test logout functionality
- [ ] Test session expiration redirect

#### Member Features
- [ ] View profile (GET)
- [ ] Update profile (PUT)
- [ ] Upload profile picture
- [ ] View membership plans
- [ ] View payment history
- [ ] Create payment order
- [ ] Verify payment (success)
- [ ] Verify payment (failure)
- [ ] View events
- [ ] View event details

#### Trainer Features
- [ ] Create membership plan
- [ ] View all plans
- [ ] Edit plan
- [ ] Toggle plan status
- [ ] View all members
- [ ] Filter members (ALL, ACTIVE, INACTIVE, EXPIRING_SOON)
- [ ] View member details
- [ ] Record cash payment
- [ ] View payment history
- [ ] Create event
- [ ] View events
- [ ] Edit event
- [ ] Delete event
- [ ] View dashboard

#### Error Handling
- [ ] Handle 401 errors (redirect to login)
- [ ] Handle 403 errors (show error page)
- [ ] Handle 404 errors (show not found)
- [ ] Handle network errors
- [ ] Handle form validation errors
- [ ] Handle file upload errors

#### Responsive Design
- [ ] Test on desktop (1920x1080)
- [ ] Test on tablet (768x1024)
- [ ] Test on mobile (375x667)
- [ ] Test on large screens (2560x1440)

#### Browser Compatibility
- [ ] Chrome/Chromium
- [ ] Firefox
- [ ] Safari
- [ ] Edge

### API Testing (Using Postman/curl)

#### Public Endpoints
- [ ] POST /api/auth/register
- [ ] POST /api/auth/login
- [ ] POST /api/member/payments/verify

#### Member Endpoints
- [ ] GET /api/member/profile (with token)
- [ ] PUT /api/member/profile (with token)
- [ ] POST /api/member/profile-picture (with token)
- [ ] GET /api/member/plans (with token)
- [ ] GET /api/member/events (with token)
- [ ] GET /api/member/events/{eventId} (with token)
- [ ] POST /api/member/payments/create-order (with token)
- [ ] GET /api/member/payments/history (with token)

#### Trainer Endpoints
- [ ] All trainer endpoints with valid token and ROLE_TRAINER

---

## Performance Optimization Checklist

### Bundle Size
- [ ] Check production bundle size
- [ ] Tree-shake unused dependencies
- [ ] Code splitting for routes (lazy loading)
- [ ] Minify CSS and JavaScript

### Loading Performance
- [ ] Implement skeleton loading screens
- [ ] Lazy load images
- [ ] Implement pagination for large lists
- [ ] Cache API responses (optional)

### Runtime Performance
- [ ] Prevent unnecessary re-renders
- [ ] Use React.memo for pure components
- [ ] Optimize context updates
- [ ] Use useCallback for memoized functions

---

## Production Deployment Checklist

### Pre-Deployment
- [ ] Environment variables updated for production
- [ ] Backend URL changed to production
- [ ] Razorpay credentials updated to production
- [ ] Build optimizations enabled
- [ ] Error tracking setup (optional)
- [ ] Analytics setup (optional)

### Build & Deployment
- [ ] Run `npm run build`
- [ ] Test production build locally
- [ ] Deploy to hosting platform
- [ ] Configure CDN (optional)
- [ ] Setup SSL/HTTPS
- [ ] Configure domain

### Post-Deployment
- [ ] Test all features in production
- [ ] Verify API endpoints work
- [ ] Test payment flow with real credentials
- [ ] Monitor for errors
- [ ] Check performance metrics
- [ ] Setup uptime monitoring

---

## Common Issues & Debugging

### Issue: Token not being sent
**Debug**:
- Check localStorage has `authToken`
- Verify axios interceptor is configured
- Check network tab in browser DevTools

### Issue: CORS errors
**Debug**:
- Verify backend is running on :8081
- Check frontend is on :5173
- Verify CORS configuration in backend

### Issue: 401 errors on protected routes
**Debug**:
- Check token is valid (not expired)
- Verify token format in localStorage
- Check Authorization header in network tab

### Issue: Payment fails
**Debug**:
- Verify Razorpay credentials
- Check amount is sent in paise
- Verify order ID exists before verification
- Check signature validation

### Issue: File upload fails
**Debug**:
- Check file size < 5MB
- Verify file type is allowed
- Check uploads/profile/ directory exists
- Check server write permissions

---

## Documentation Requirements

### README.md
- [ ] Project setup instructions
- [ ] Technology stack listed
- [ ] Environment variable documentation
- [ ] Running the development server
- [ ] Building for production
- [ ] Project folder structure explained

### Code Comments
- [ ] Document complex logic
- [ ] Comment API calls with expected response
- [ ] Document form validation rules
- [ ] Comment context providers
- [ ] Document custom hooks

### Git Commits
- [ ] Clear, descriptive commit messages
- [ ] Logical commit grouping
- [ ] Reference issue numbers (if applicable)

---

## Sign-Off Checklist

Final verification before marking as complete:
- [ ] All pages load correctly
- [ ] Authentication works (register, login, logout)
- [ ] All API endpoints called successfully
- [ ] Error handling works properly
- [ ] Responsive design confirmed
- [ ] Accessibility tested
- [ ] Payment integration verified (test mode)
- [ ] File uploads working
- [ ] Dashboard displays correct data
- [ ] All forms validate correctly
- [ ] No console errors or warnings
- [ ] Performance is acceptable
- [ ] Security measures implemented
- [ ] Documentation complete
- [ ] Ready for production deployment
