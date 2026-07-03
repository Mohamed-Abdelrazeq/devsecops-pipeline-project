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

- `docker-compose.yml` – Starts Jenkins and SonarQube.
- `.env` – Stores environment variables used by Docker Compose.

### `src/`

Contains the Java Spring Boot application and its unit tests.

### `Jenkinsfile`

Defines the CI pipeline executed by Jenkins, including build, testing, code coverage, mutation testing, static code analysis, quality gate validation, and artifact publishing.

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
- ✅ Static Code Analysis with SonarQube
- ✅ SonarQube Quality Gate

## Planned Integrations

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
6. Perform SonarQube static analysis
7. Wait for SonarQube Quality Gate
8. Package the application
9. Archive build artifacts and reports

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

# Static Analysis with SonarQube

SonarQube performs Static Application Security Testing (SAST) and code quality analysis as part of the Jenkins pipeline.

It analyzes the source code to identify:

- Bugs
- Security Vulnerabilities
- Security Hotspots
- Code Smells
- Duplicated Code
- Maintainability Issues

After the analysis completes, SonarQube evaluates the project against a predefined **Quality Gate**.

The pipeline waits for the Quality Gate result before continuing. If the project fails the configured quality criteria, the Jenkins build fails automatically.

This prevents code that does not meet the required quality standards from progressing further in the pipeline.

---

# SonarQube Integration Steps

## 1. Add SonarQube to Docker Compose

Run SonarQube alongside Jenkins.

```yaml
services:
  sonarqube:
    image: sonarqube:community
    ports:
      - "9000:9000"
```

---

## 2. Generate a SonarQube Token

Navigate to:

```
Administration
    Security
        Users
            Tokens
```

Generate a user token that Jenkins will use for authentication.

---

## 3. Store the Token in Jenkins

Create a Jenkins Secret Text credential.

- Kind: **Secret text**
- ID: `SONAR_TOKEN`

---

## 4. Configure the Maven Scan

Execute SonarQube analysis from the Jenkins pipeline.

```bash
mvn sonar:sonar \
  -Dsonar.projectKey=numeric \
  -Dsonar.host.url=http://sonarqube:9000 \
  -Dsonar.login=$SONAR_TOKEN
```

---

## 5. Configure SonarQube Server in Jenkins

Install the **SonarQube Scanner for Jenkins** plugin.

Then configure:

```
Manage Jenkins
    System
        SonarQube Servers
```

Provide:

- Server Name
- Server URL
- Authentication Token

---

## 6. Enable the Quality Gate Stage

After the analysis stage, Jenkins waits for SonarQube to finish processing the report.

```groovy
timeout(time: 5, unit: 'MINUTES') {
    waitForQualityGate abortPipeline: true
}
```

If the Quality Gate fails, the pipeline is aborted automatically.

---

# Current Status

| Component | Status |
|-----------|--------|
| Docker Compose Infrastructure | ✅ |
| Jenkins | ✅ |
| SonarQube | ✅ |
| Java Spring Boot Application | ✅ |
| Maven Build | ✅ |
| Talisman (Pre-Commit) | ✅ |
| Talisman (Pre-Push) | ✅ |
| JUnit Tests | ✅ |
| JaCoCo Code Coverage | ✅ |
| PIT Mutation Testing | ✅ |
| SonarQube Analysis | ✅ |
| Quality Gate | ✅ |

---

# Challenges Faced

This section documents issues encountered while building the pipeline and their solutions. It will continue to grow as more tools are integrated.

## SonarQube could not be reached from Jenkins

**Issue**

```
SonarQube server http://localhost:9000 can not be reached
```

**Cause**

Inside Docker, `localhost` refers to the Jenkins container itself, not the SonarQube container.

**Solution**

Use the Docker Compose service name instead.

```text
http://sonarqube:9000
```

---

## waitForQualityGate Failed

**Issue**

```
No previous SonarQube analysis found on this pipeline execution.
```

**Cause**

The Jenkins SonarQube plugin was not configured.

**Solution**

Configure the SonarQube server under:

```
Manage Jenkins
    System
        SonarQube Servers
```

---

## Jenkins Could Not Authenticate with SonarQube

**Issue**

The pipeline could not authenticate during analysis.

**Solution**

Create a **Secret Text** credential in Jenkins containing the SonarQube user token and reference it during the Maven scan.

---

## SonarQube Web UI Crashed During Startup

**Issue**

The SonarQube web interface was unavailable even though the container was running.

**Cause**

The latest SonarQube image introduced startup issues in the local Docker environment, preventing the web application from initializing correctly.

**Solution**

Switch to the **SonarQube LTS (Long-Term Support)** Docker image instead of the latest release.

```yaml
image: sonarqube:lts-community
```

Using the LTS version resolved the startup issue and provided a more stable environment for development.

---

## Docker Networking Confusion

One of the first issues encountered was understanding how Docker networking works.

Containers communicate using their **service names**, not `localhost`.

This is an important concept when integrating multiple DevSecOps tools inside the same Docker Compose environment.

---

# Future Work

The pipeline will continue evolving by integrating additional security tools, including:

- OWASP Dependency-Check
- Trivy
- Gitleaks
- Checkov
- Semgrep
- OWASP ZAP
- Docker Image Publishing
- Kubernetes Deployment
- GitOps
- Continuous Monitoring

Each integration will be documented along with any implementation challenges and lessons learned.