# Secure DevSecOps CI/CD Pipeline

## Project Overview

This project is a hands-on **DevSecOps learning project** focused on building a secure CI/CD pipeline for a Java application. The objective is to integrate multiple security tools throughout the software development lifecycle (SDLC) and automate security checks using Jenkins.

The repository is organized to separate infrastructure components from the application source code, making it easier to manage and extend as additional tools are introduced.

---

## Repository Structure

```text
.
├── infra/
│   ├── docker-compose.yml
│   └── .env
│
├── src/
│   └── (Java application source code)
│
└── README.md
```

### `infra/`

Contains the infrastructure required to run the CI/CD environment.

Current contents:

- `docker-compose.yml` – Starts the Jenkins environment.
- `.env` – Stores environment variables used by Docker Compose.

### `src/`

Contains the Java application that will be used as the target application for the CI/CD pipeline.

---

# Project Goal

The goal of this project is to build a complete **DevSecOps pipeline** by integrating security tools into every stage of the development lifecycle.

As the project evolves, additional security tools will be incorporated into the Jenkins pipeline, allowing automated security scanning before code reaches production.

Current plan:

- ✅ Jenkins infrastructure
- ✅ Java application
- ✅ Git hooks with Talisman
- Static Application Security Testing (SAST)
- Dependency vulnerability scanning
- Secret scanning
- Container image scanning
- Infrastructure as Code (IaC) scanning
- Dynamic Application Security Testing (DAST)
- Software Composition Analysis (SCA)
- Deployment automation

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
- Other secrets

By scanning files before commits or pushes, Talisman reduces the risk of exposing sensitive information in the repository.

---

# Git Hook Configuration

Talisman has been configured as both:

- **Pre-Commit Hook**
  - Scans changes before a commit is created.

- **Pre-Push Hook**
  - Performs an additional scan before code is pushed to the remote repository.

Using both hooks provides an extra layer of protection against accidentally leaking secrets.

---

# Talisman Installation

The following steps were used to install Talisman.

## 1. Download the installer

```bash
curl https://thoughtworks.github.io/talisman/install.sh -o ~/install-talisman.sh
chmod +x ~/install-talisman.sh
```

## 2. Install as a Pre-Push hook (default)

```bash
~/install-talisman.sh
```

## 3. install as a Pre-Commit hook

```bash
~/install-talisman.sh pre-commit
```

---

# Current Status

| Component | Status |
|-----------|--------|
| Docker Compose | ✅ |
| Java Application | ✅ |
| Jenkins | ✅ |
| Talisman (Pre-Commit) | ✅ |
| Talisman (Pre-Push) | ✅ |

---

# Next Steps

The pipeline will continue to evolve by integrating additional DevSecOps and security tools into Jenkins, creating a comprehensive automated CI/CD workflow that enforces security throughout the software development lifecycle.