---
applyTo: "**/*.java, **/*.yml, **/*.properties, **/pom.xml, **/build.gradle"
description: "Java Spring Boot enterprise coding standards and architectural rules"
---

# Spring Boot Development Guidelines

You are an expert Java developer specializing in Spring Boot enterprise architecture. Follow these strict patterns, conventions, and constraints when writing or modifying code in this workspace.

## 1. Core Architecture & Layered Responsibilities
*   **Package by Feature:** Group code logically by feature domain rather than purely by technical layer (e.g., prefer `com.company.project.appointment` over generic layer packages).
*   **Thin Controllers:** Keep `@RestController` classes lean. Their only responsibilities are request validation, routing, HTTP response status wrapping, and calling services. Do not leak business or data validation logic into the controller layer.
*   **Stateless Services:** Business logic belongs exclusively inside `@Service` annotated classes. Services must be stateless, highly testable, and handle cross-domain integration using DTOs rather than exposing raw database entities.
*   **Declarative Transactions:** Use `@Transactional` explicitly in the service layer where mutating operations or multi-query workflows happen. Prefer read-only scoping (`@Transactional(readOnly = true)`) for retrieval methods.

## 2. Dependency Injection & State
*   **Constructor Injection:** Use constructor-based injection for all required dependencies.
*   **Immutability:** Mark all injected dependencies as `private final`. Do not use `@Autowired` field injection.
*   **Sample Structure:**
    ```java
    @Service
    public class AppointmentService {
        private final AppointmentRepository appointmentRepository;
        private final PatientClient patientClient;

        public AppointmentService(AppointmentRepository appointmentRepository, PatientClient patientClient) {
            this.appointmentRepository = appointmentRepository;
            this.patientClient = patientClient;
        }
    }
    ```

## 3. Configuration Management
*   **Format:** Prefer `application.yml` over `application.properties` for complex, nested configurations.
*   **Type-Safe Properties:** Group related application settings into custom classes using `@ConfigurationProperties` and `@Validated`. Avoid raw `@Value("${some.property}")` injections scattered across components.
*   **Environment Isolation:** Leverage Spring Profiles (`dev`, `test`, `prod`) to externalize environments. Never hardcode credentials, tokens, or infrastructure secrets inside the repository; assume they will be injected dynamically via external environment variables or vault systems.

## 4. Data Layer & Validation
*   **Spring Data JPA:** Write clean repository interfaces extending `JpaRepository` or `CrudRepository`. Use derived query methods where applicable, or explicitly define `@Query` mappings.
*   **DTO Projections:** Do not return database entities directly to the client layer. Always map to and from clean Data Transfer Objects (DTOs) or Records at the service edge.
*   **Input Validation:** Use JSR-380 / Jakarta Bean Validation annotations (e.g., `@NotNull`, `@Size`, `@Email`) on incoming DTOs in the controller endpoints. Always activate validation using `@Valid` or `@Validated`.

## 5. Exception Handling & Logging
*   **Global Exception Handling:** Use a centralized `@RestControllerAdvice` class coupled with `@ExceptionHandler` methods to intercept exceptions globally and return predictable, standardized JSON error models (such as RFC 7807 Problem Details).
*   **SLF4J Parameterized Logging:** Use standard SLF4J loggers. Always use parameterized brackets `{}` to prevent premature string concatenation overhead.
*   *Do Not Use:* `System.out.println()` or `e.printStackTrace()`.

## 6. Testing Strategy
*   **Testing Framework:** Use JUnit 5 and AssertJ for assertions.
*   **Unit Isolation:** Isolate `@Service` logic using Mockito (`@ExtendWith(MockitoExtension.class)`) instead of spinning up the entire Spring context.
*   **Slice Tests:** Utilize targeted slice annotations like `@WebMvcTest` for controller tests and `@DataJpaTest` for database persistence assertions.
*   **Integration Testing:** Use `@SpringBootTest` alongside Testcontainers for reliable, real-database validation during containerized integration test cycles.
