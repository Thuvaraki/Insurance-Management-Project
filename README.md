# Insurance-Management-Project

### Project Overview
This project is a backend service for an **Insurance Management System** built using **Spring Boot**. It provides functionalities to manage user authentication, insurance policy creation, enrollment, claims processing, and related operations. The project utilizes JWT for secure user authentication and role-based access control to ensure data security and restricted access.

### Features
1. **User Authentication and Authorization**
   - **Register User**: Allows new users to create an account.
   - **Login User**: Authenticates users and returns a JWT token for secure access.
   - **Role-based Access Control**: Only admins can create, update, and delete policies and approve/reject claims.

2. **Insurance Policy Management**
   - **Create Policy**: Admins can create new insurance policies.
   - **Retrieve Policy**: Users can retrieve details of a specific policy or all available policies.
   - **Update Policy**: Admins can update policy details.
   - **Delete Policy**: Admins can delete a specific policy.

3. **Claims Management**
   - **Create Claim**: Users can submit new claims for enrolled policies.
   - **Retrieve Claim**: Users can fetch a specific claim or all claims associated with their user ID.
   - **Update Claim**: Admins can approve or reject claims.
   - **Delete Claim**: Users can remove claims.

4. **Policy Enrollment**
   - **Enroll in Policy**: Users can enroll in available insurance policies.
   - **Retrieve Enrolled Policies**: Users can list all policies they are enrolled in.

### Technologies Used
- **Backend Framework**: Spring Boot
- **Database**: MySQL
- **Security**: Spring Security with JWT for authentication and authorization.
- **Exception Handling**: Custom exceptions for handling specific errors.
