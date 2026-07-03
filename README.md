# Secure DevSecOps CI/CD Pipeline

## Project Overview

This project is a hands-on **DevSecOps learning project** focused on building a secure CI/CD pipeline for a Java application. The objective is to integrate security and quality assurance tools throughout the Software Development Lifecycle (SDLC) and automate their execution using Jenkins.

The repository is organized to separate infrastructure components from the application source code, making it easier to manage, extend, and maintain as additional DevSecOps tools are introduced.

---

## Repository Structure

```text
.
├── infra/
│   ├── docker-compose.yml
│   └── .env
│
├── src/
│   ├── main/
│   └── test/
│
├── Jenkinsfile
├── pom.xml
└── README.md
```

### `infra/`

Contains the infrastructure required to run the CI/CD environment.

Current contents:

- `docker-compose.yml` – Starts the Jenkins environment.
- `.env` – Stores environment variables used by Docker Compose.

### `src/`

Contains the Java Spring Boot application and its unit tests.

### `Jenkinsfile`

Defines the CI pipeline executed by Jenkins, including build, testing, code coverage, mutation testing, and artifact publishing.

---

# Project Goal

The goal of this project is to build a complete **DevSecOps pipeline** by integrating security, testing, and code quality tools into every stage of the software development lifecycle.

The pipeline will evolve incrementally, with each tool added as a dedicated stage in Jenkins.

## Current Progress

- ✅ Jenkins infrastructure
- ✅ Java Spring Boot application
- ✅ Git secret scanning with Talisman
- ✅ Automated build with Maven
- ✅ Unit testing with JUnit
- ✅ Code coverage with JaCoCo
- ✅ Mutation testing with PIT

## Planned Integrations

- Static Application Security Testing (SAST)
- Dependency vulnerability scanning
- Software Composition Analysis (SCA)
- Infrastructure as Code (IaC) scanning
- Container image scanning
- Dynamic Application Security Testing (DAST)
- Docker image build and publishing
- Deployment automation

---

# Jenkins Pipeline

The current Jenkins pipeline performs the following stages:

1. Checkout source code
2. Compile the application
3. Execute JUnit tests
4. Generate JaCoCo code coverage reports
5. Execute PIT mutation testing
6. Package the application
7. Archive build artifacts and reports

---

# Secret Scanning with Talisman

The first security tool integrated into this project is **Talisman**.

Talisman is a Git hook that helps prevent developers from accidentally committing sensitive information such as:

- API keys
- Passwords
- Tokens
- Certificates
- Private keys
- Credentials
- Cloud secrets

By scanning files before commits and pushes, Talisman helps reduce the risk of exposing sensitive information in source control.

## Git Hook Configuration

Talisman has been configured as:

- ✅ Pre-Commit Hook
- ✅ Pre-Push Hook

This ensures secrets are scanned both before creating a commit and before pushing code to a remote repository.

## Installation

### Download the installer

```bash
curl https://thoughtworks.github.io/talisman/install.sh -o ~/install-talisman.sh
chmod +x ~/install-talisman.sh
```

### Install as a Pre-Push hook

```bash
~/install-talisman.sh
```

### Install as a Pre-Commit hook

```bash
~/install-talisman.sh pre-commit
```

---

# Unit Testing with JUnit

Unit tests are executed automatically during the Jenkins pipeline using Maven.

JUnit verifies the correctness of the application's business logic and REST endpoints before the build proceeds to later stages.

The generated test reports are published by Jenkins, allowing easy inspection of passed and failed tests.

---

# Code Coverage with JaCoCo

JaCoCo measures how much of the application code is exercised by the unit tests.

The pipeline automatically generates HTML coverage reports after successful test execution.

Coverage reports help identify:

- Untested code
- Dead code
- Areas requiring additional unit tests

---

# Mutation Testing with PIT

PIT (Pitest) evaluates the quality of the unit tests rather than just measuring code coverage.

Instead of only reporting which lines of code were executed, PIT intentionally introduces small changes (mutations) into the application code and reruns the test suite.

If a test fails, the mutation is considered **killed**, indicating the tests successfully detected the change.

If the tests continue to pass, the mutation **survives**, revealing weaknesses in the test suite.

This provides a much stronger indication of test effectiveness than code coverage alone.

The Jenkins pipeline automatically generates PIT HTML reports after every execution.

---

# Current Status

| Component | Status |
|-----------|--------|
| Docker Compose Infrastructure | ✅ |
| Jenkins | ✅ |
| Java Spring Boot Application | ✅ |
| Maven Build | ✅ |
| Talisman (Pre-Commit) | ✅ |
| Talisman (Pre-Push) | ✅ |
| JUnit Tests | ✅ |
| JaCoCo Code Coverage | ✅ |
| PIT Mutation Testing | ✅ |

