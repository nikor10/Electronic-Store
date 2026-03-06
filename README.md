 **Electronic Store Management System**
 
  A comprehensive desktop application built with JavaFX, designed to streamline the operations of an electronic retail store.

  This project was developed as a group assignment for the Object-Oriented Programming (OOP) course in Java during the second
  year of University. It focuses on implementing core OOP principles, such as inheritance, polymorphism, encapsulation, and
  robust data persistence.

  🚀 Features

  🛠 Role-Based Access Control (RBAC)
  The system supports three distinct user levels, each with tailored views and permissions:

   - Administrator: Full CRUD operations for managing employees (Cashiers, Managers), overseeing the product database, and
     managing supplier lists.
   - Manager: Authorized to add/update/delete inventory, view supplier contacts, and analyze business performance through
     dynamic revenue charts (Weekly/Monthly Bar Charts).
   - Cashier: Optimized for high-efficiency checkouts, real-time product availability searches, and automatic bill generation.

  💾 Data Persistence & Logic
   - Binary Storage: Employs Java Object Serialization to manage Users, Products, and Suppliers in .dat files.
   - Custom File Handling: Includes a HeaderlessObjectOutputStream to allow seamless appending of objects to existing data
     streams.
   - Transaction Records: Automatically generates formatted .txt invoices for every sale, stored locally in the resources
     directory.

  🛠 Technologies Used
   - Language: Java 17+
   - UI Framework: JavaFX (styled with CSS for a modern, responsive aesthetic)
   - Data Storage: Binary File I/O (Serialization)
   - Architecture: MVC-inspired (Model-View-Controller) design pattern.

  📂 Project Structure
   - src/models: Core data structures (User, Item, Bill, etc.) utilizing inheritance.
   - src/View: JavaFX UI layouts, custom components, and CSS styling.
   - src/Controller: Logic for handling user interactions and data flow.
   - src/FileHandler: Methods for reading/writing data to local storage.
   - src/resources: Project assets including icons and transaction logs.

  ⚙️ Getting Started

  Prerequisites
   - JDK 17 or higher.
   - JavaFX SDK (properly configured in your IDE environment).

  Running the Application
   1. Clone the repository:
   1    git clone https://github.com/your-username/ElectronicStore_Nikola_Rigo.git
   2. Open the project in your preferred IDE (IntelliJ IDEA or Eclipse).
   3. Ensure JavaFX libraries are included in the project's build path.
   4. Run Main/Main.java to access the login terminal.

  ---
  Developed as a Group Project for the University OOP Course.
