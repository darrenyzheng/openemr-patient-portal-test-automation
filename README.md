# Test-Automation-Patient-Portal

A portfolio project demonstrating test automation through automated testing of the OpenEMR patient portal. Built with Java, Selenium WebDriver, JUnit 5, and Maven, the framework uses the Page Object Model (POM) to create maintainable, reusable tests.

The project showcases QA skills, including test design, browser automation, test organization with JUnit 5 tags, headless execution, Allure HTML reporting, and CI integration with GitHub Actions.

## Tech Stack

- **Language:** Java
- **Browser Automation:** Selenium WebDriver
- **Testing Framework:** JUnit 5
- **Build Tool:** Maven
- **Reporting:** Allure
- **CI/CD:** GitHub Actions
- **Design Pattern:** Page Object Model (POM)

## Prerequisites

Install the following before running the tests:

- **Java JDK** compatible with the project configuration
- **Apache Maven**
- **Google Chrome**
- **Git**

Verify your installations:

```bash
java -version
mvn -version
```

## Running Locally

Clone the repository and navigate to the project directory:

```bash
git clone https://github.com/darrenyzheng/openemr-patient-portal-test-automation.git
cd openemr-patient-portal-test-automation
```

**Run the full test suite with Chrome visible:**

```bash
mvn clean test
```

**Run the test suite in headless mode:**

```bash
mvn clean test -Dheadless=true
```

## Features

- **Page Object Model (POM):** Organizes browser interactions into dedicated page classes for maintainability and reuse.
- **Authentication tests:** Cover valid and invalid credentials, required field validation, email format checks, password visibility, and logout behavior.
- **Profile tests:** Verify edit modal behavior, cancel and close controls, and modal dismissal after page refresh.
- **Appointment tests:** Cover page refresh behavior, appointment form controls, cancellation, and validation alerts when saving without a selected time.
- **Messaging tests:** Cover message composition and sending, compose window controls, message count updates, and archiving sent messages.
- **HTML reporting:** Uses Allure to generate reports available as downloadable GitHub Actions artifacts.
- **Headless execution:** Supports headless Chrome runs locally and in CI.
- **JUnit 5 test tags:** Organizes tests using `smoke`, `regression`, and numbered test-case IDs.

## Project Structure

```text
openemr-patient-portal-test-automation/
├── .github/
│   └── workflows/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/
│   │           ├── AppointmentPage.java
│   │           ├── BillingSummaryPage.java
│   │           ├── DocumentsPage.java
│   │           ├── HelpPage.java
│   │           ├── ListsPage.java
│   │           ├── LoginPage.java
│   │           ├── MessagesPage.java
│   │           ├── ProfilePage.java
│   │           ├── ReportsHelpPage.java
│   │           ├── ReportsPage.java
│   │           └── SettingsPage.java
│   └── test/
│       └── java/
│           ├── AppointmentTests.java
│           ├── BillingSummaryTests.java
│           ├── DocumentsTests.java
│           ├── HelpTests.java
│           ├── ListsTests.java
│           ├── LoginTests.java
│           ├── MessagesTests.java
│           ├── ProfileTests.java
│           ├── ReportsHelpTests.java
│           ├── ReportsTests.java
│           ├── SettingsTests.java
│           └── BaseTest.java
├── .gitignore
├── pom.xml
├── README.md
└── Test Automation Patient Portal Selenium.iml
```

## Running Tests

**Run all tests:**

```bash
mvn clean test
```

Runs the tests with the browser visible, making it easier to debug and observe test execution.

**Run tests in headless mode:**

```bash
mvn clean test -Dheadless=true
```

Runs the tests without displaying a browser window. Recommended for CI environments such as GitHub Actions.

**Run specific test groups:**

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
```

**Run a specific test case by ID:**

```bash
mvn test -Dgroups=tc45
```

## Test Reports

Generate the Allure HTML report:

```bash
mvn allure:report
```

The Allure HTML report is generated at `target/site/allure-maven-plugin/index.html` and can be opened in a browser.

## Continuous Integration

**GitHub Actions** runs automated tests in a CI environment. The workflow can execute tests in headless Chrome and publish test reports as downloadable artifacts.

To view workflow results, navigate to the repository's **Actions** tab, select a workflow run, and download the `allure-html-report` ZIP file. Unzip it and open `index.html` to view the report.

## Test Plan

- **TC01–TC14:** Login
- **TC15–TC19:** Messages
- **TC20–TC23:** Appointments
- **TC24–TC26:** Profile
- **TC27–TC28:** Reports Help
- **TC29–TC31:** Reports
- **TC32–TC38:** Settings
- **TC39–TC48:** Billing Summary
- **TC49–TC50:** Documents
- **TC51–TC52:** Help
- **TC53–TC58:** Lists
