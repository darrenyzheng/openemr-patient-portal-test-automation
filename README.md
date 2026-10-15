# Test-Automation-Patient-Portal-
test automation

## Features

- **Page Object Model (POM):** Organizes browser interactions into dedicated page classes for maintainability and reuse.
- **Authentication tests:** Cover valid and invalid credentials, required field validation, email format checks, password visibility, and logout behavior.
- **Profile tests:** Verify edit modal behavior, cancel and close controls, and modal dismissal after page refresh.
- **Appointment tests:** Cover page refresh behavior, appointment form controls, cancellation, and validation alerts when saving without a selected time.
- **Messaging tests:** Cover message composition and sending, compose window controls, message count updates, and archiving sent messages.
- **HTML reporting:** Uses Allure to generate reports available as downloadable GitHub Actions artifacts.
- **Headless execution:** Supports headless Chrome runs locally and in CI.
- **JUnit 5 test tags:** Organizes tests using `smoke`, `regression`, and numbered test-case IDs.

## Running tests

mvn clean test
mvn clean test -Dheadless=true

Run Specific Test Groups

mvn test -Dgroups=smoke 
mvn test -Dgroups=regression 

Run by Test ID

mvn test -Dgroups=tc45 

## Creating test report 

mvn allure:report 

The Allure HTML report is generated at target/site/allure-maven-plugin/index.html and can be opened in a browser.
