SOLID Principles

SRP: Each class has a single responsibility.
OCP: New book types can be added without modifying existing logic.
LSP: Subclasses are interchangeable with the base type.
ISP: Interfaces are small and focused.
DIP: Service layer depends on repository interfaces.

Advanced OOP Features

Generics: Generic CRUD repository interface.
Lambdas: Sorting and filtering via Comparator and Predicate.
Reflection: Runtime class inspection using ReflectionUtils.
Interface default/static methods: Used in Validatable.

Database Design

books (base entity) 
ebooks AND printed_books (inheritance tables) 
category (composition)

Architecture
Controller (Main) - Service - Repository (JDBC) - DB(PostgreSQL)

This project helped reinforce the importance of SOLID principles and clean architecture in real-world Java applications.

<img width="248" height="813" alt="image" src="https://github.com/user-attachments/assets/c8b8e2f2-dc59-48ef-9843-680ed74ddaa0" />
