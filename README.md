---

# **Trimble Cars**

## **Overview**  
Trimble Cars is a car leasing platform that allows owners to register their cars and users to lease available cars. The system ensures proper validation of ownership, prevents duplicate owners, and maintains a history of leasing activities.

---
 
TO RUN TESTS :: From the root directory use gradle test



## **Features**
### **1. Owner Management**
- Owners can register on the platform.  
- Each owner is uniquely identified by their **email** and **owner ID**.  
- **No two owners** can have the same email.  
- A single owner can **own multiple cars**.

### **2. Car Management**
Each car in the system has the following attributes:
- **Car ID**: A unique identifier for the car.  
- **Car Name**: The name or model of the car.  
- **Owner ID**: The unique identifier of the owner.  
- **Owner Email**: The email of the car owner.  
- **Status**: Indicates whether the car is **FREE** (available) or **LEASED** (currently booked by a user).  
- **User Email**: Refers to the email of the user who has currently leased the car (if any).  
- **Lease Start Date**: The start date of the current lease.  
- **Lease End Date**: The end date of the current lease.  
- **Car Lease History**: Keeps track of all past leases for the car, including bookings and cancellations.  

### **3. Car Leasing Rules**
- A **user can lease at most 2 cars at a time**.  
- A **leased car** will have its status updated to **LEASED** along with the user’s email and lease period.  

### **4. Lease History Management**
- The system maintains a detailed **lease history** for every car.  
- The lease history acts as a **record book** to track:
  - Which user booked the car.  
  - The status of the lease (BOOKED or CANCELLED).  
  - The start and end dates of each lease.  

---

## **Usage Workflow**
1. **Owner Registration**  
   - A new owner registers with a unique email.  
   - If an owner with the same email already exists, registration is **not allowed**.  
   
2. **Adding Cars**  
   - An owner can register multiple cars under their account.  

3. **Leasing a Car**  
   - A user can **lease a car** that is marked as **FREE**.  
   - The system updates the **status**, **user email**, and **lease dates**.  
   - A user **cannot lease more than 2 cars** at the same time.  

4. **Tracking Lease History**  
   - The lease history logs every **BOOKED** and **CANCELLED** transaction.  

---

