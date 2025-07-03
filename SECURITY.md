# Security Policy

## Supported Versions

We actively support the following versions of N4-DataSync with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |
| < 1.0   | :x:                |

## Reporting a Vulnerability

We take security vulnerabilities seriously. If you discover a security vulnerability in N4-DataSync, please report it responsibly.

### How to Report

**DO NOT** create a public GitHub issue for security vulnerabilities.

Instead, please:

1. **Email**: Send details to [your-security-email@domain.com]
2. **Subject**: Include "N4-DataSync Security Vulnerability" in the subject line
3. **Details**: Provide as much information as possible:
   - Description of the vulnerability
   - Steps to reproduce
   - Potential impact
   - Suggested fix (if known)

### What to Expect

- **Acknowledgment**: We'll acknowledge receipt within 48 hours
- **Initial Assessment**: We'll provide an initial assessment within 5 business days
- **Updates**: We'll keep you informed of our progress
- **Resolution**: We aim to resolve critical vulnerabilities within 30 days

### Responsible Disclosure

We follow responsible disclosure practices:

1. **Investigation**: We'll investigate and validate the report
2. **Fix Development**: We'll develop and test a fix
3. **Coordinated Release**: We'll coordinate the release with you
4. **Public Disclosure**: We'll publicly disclose after the fix is available

## Security Considerations

### Data Protection

N4-DataSync handles sensitive information:

- **Connection Credentials**: Niagara station passwords
- **Configuration Data**: System topology and settings
- **File Access**: Local Excel files and system paths

### Current Security Measures

- **Certificate-based Module Signing**: All modules are digitally signed
- **Input Validation**: User inputs are validated (ongoing improvement)
- **Secure Communication**: Uses Niagara's Fox protocol for station communication
- **Local Storage**: Profiles stored locally in user directory

### Known Security Limitations

- **Password Storage**: Currently stored encrypted in JSON (Issue #1)
- **File Permissions**: Relies on OS file system permissions
- **Network Security**: Inherits Niagara station security model

## Security Best Practices for Users

### Installation Security

1. **Verify Module Signature**: Ensure modules are properly signed
2. **Source Verification**: Only install modules from trusted sources
3. **Permission Review**: Review module permissions before installation

### Configuration Security

1. **Strong Passwords**: Use strong passwords for Niagara station connections
2. **Network Security**: Ensure secure network connections to stations
3. **File Protection**: Protect Excel files containing sensitive data
4. **Regular Updates**: Keep N4-DataSync and Niagara updated

### Operational Security

1. **Access Control**: Limit access to Workbench and configuration files
2. **Audit Logging**: Monitor synchronization activities
3. **Backup Security**: Secure backups of configuration profiles
4. **Regular Review**: Periodically review connection profiles and permissions

## Security Development Practices

### Code Security

- **Input Validation**: All user inputs are validated
- **Error Handling**: Secure error handling without information disclosure
- **Dependency Management**: Regular security updates for dependencies
- **Code Review**: Security-focused code reviews

### Build Security

- **Dependency Scanning**: Automated vulnerability scanning
- **Secure Build**: Secure build pipeline with signed artifacts
- **Supply Chain**: Verification of build dependencies

### Testing Security

- **Security Testing**: Automated security testing in CI/CD
- **Penetration Testing**: Regular security assessments
- **Vulnerability Management**: Systematic vulnerability tracking

## Compliance

N4-DataSync is designed to support compliance with:

- **Industry Standards**: Building automation security standards
- **Data Protection**: Local data protection requirements
- **Access Control**: Role-based access control principles

## Contact

For security-related questions or concerns:

- **Security Issues**: [your-security-email@domain.com]
- **General Questions**: Create a GitHub issue with the "security" label
- **Documentation**: See [CERTIFICATE_SETUP.md](docs/CERTIFICATE_SETUP.md) for security configuration

---

**Note**: This security policy will be updated as the project evolves. Please check back regularly for updates.
