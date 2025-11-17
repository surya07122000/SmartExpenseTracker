
# Smart Expense Tracker

A full-stack application to manage expenses, income, borrowed money, and categories with a dashboard summary.

---

## 📂 Project Structure
Backend: 
com.monexel.expensetracker
├── ExpensetrackerApplication.java
├── config
│   ├── SecurityConfig.java
│   ├── SwaggerConfig.java
│   └── WebConfig.java
├── controller
│   ├── BorrowedMoneyController.java
│   ├── CategoryController.java
│   ├── DashboardController.java
│   ├── ExpenseController.java
│   ├── IncomeController.java
│   └── UserController.java
├── entity
│   ├── Income
    ├── User
    ├── Expense
    ├── BorrowedMoney
    └── Category
├── exception
│   ├── APIException.java
│   ├── APIResponse.java
│   ├── InsufficientFundsException.java
│   ├── MyGlobalExceptionHandler.java
│   ├── ResourceAlreadyExistsException.java
│   ├── ResourceNotFoundException.java
│   └── UserNotFoundException.java
├── jwtutils
│   ├── AuthEntryPointJwt.java
│   ├── JwtAuthEntryPointJwt.java
│   └── JwtUtils.java
└── repository
    ├── BorrowedMoneyRepository.java
    ├── CategoryRepository.java
    ├── ExpenseRepository.java
    ├── IncomeRepository.java
    └── UserRepository.java
