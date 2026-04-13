## PetShop Inventory System

A simple Spring Boot application designed to manage pet store operations, including tracking pets, assigning caretakers, and managing food supplies and suppliers.

---

### **Features**
* **Pet Management:** Track pet details and status.
* **Employee Assignment:** Link specific staff members to pets for caretaking.
* **Inventory Tracking:** Assign food types and suppliers to specific pets.
* **Data Transfer:** Clean DTO (Data Transfer Object) conversion for secure API responses.

---

### **Tech Stack**
* **Java 17+**
* **Spring Boot** (Web, Data JPA)
* **H2 Database** (or MySQL/PostgreSQL)
* **Maven**

---

### **Project Structure**
* `entity/`: Database models (Pet, Employee, PetFood, Supplier).
* `repository/`: Spring Data JPA interfaces for database access.
* `service/`: Business logic layer (specifically `InventoryService`).
* `dto/`: Data transfer objects for API communication.

---

### **Getting Started**

1.  **Clone the repo:**
    ```bash
    git clone https://github.com/your-username/petshop-project.git
    ```
2.  **Configure Database:**
    Update `src/main/resources/application.properties` with your database credentials.
3.  **Run the app:**
    ```bash
    mvn spring-boot:run
    ```

---

### **API Usage Example**
To assign an employee to a pet, the service processes the request via:
`public String assignEmployee(int petId, int employeeId)`

Returns **"Employee assigned"** on success or **"Not found"** if IDs are invalid.
