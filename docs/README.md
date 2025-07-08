# N4-DataSync Developer Documentation

## Getting Started

* **[Quick Start Guide](QUICK_START.md)** - Get up and running in 5 minutes
* **[Certificate Setup](CERTIFICATE_SETUP.md)** - How to set up module signing certificates for N4-DataSync development
* **[Testing Guide](TESTING.md)** - How to run tests and understand test results for N4-DataSync development
* **[Troubleshooting](TROUBLESHOOTING.md)** - Common issues and solutions
* **[Contributing Guide](CONTRIBUTING.md)** - How to contribute to the project

## Development Patterns

* **[Niagara Patterns](NiagaraPatterns.md)** - Essential patterns for Niagara module development
* **[Patterns by Use Case](PatternsByUseCase.md)** - Quick reference for implementing specific features
* **[Architecture](ARCHITECTURE.md)** - System design and component relationships

## Testing & Quality Assurance

### 🧪 **Enterprise-Grade Testing Framework**
* **[TESTING_STRATEGY.md](testing/TESTING_STRATEGY.md)** - Comprehensive testing strategy, standards & quality gates
* **[TESTING_IMPLEMENTATION_GUIDE.md](testing/TESTING_IMPLEMENTATION_GUIDE.md)** - Step-by-step implementation roadmap
* **[TEST_AUTOMATION_CONFIG.md](testing/TEST_AUTOMATION_CONFIG.md)** - CI/CD pipeline & automation configuration

### 📊 **Quality Standards**
* **Minimum 85% code coverage** for all production code
* **100% coverage** for critical business logic (ProfileService, persistence)
* **Zero tolerance** for flaky tests
* **Automated quality gates** at every commit
* **Performance benchmarks** for critical operations

## Technical Reference

* **[JSON Schemas](schemas/)** - Active schemas used by N4-DataSync
* **[Development Schemas](../schemas-dev/)** - Future development and reference schemas
* **[Utility Scripts](../scripts/)** - Development and deployment automation scripts

## Official Niagara Documentation

* **[Niagara Framework Documentation](Niagara/)** - Complete Niagara framework documentation
* **[Niagara Developer Guide](Niagara/Niagara%20Developer%20Guide%20Index.md)** - Official Niagara developer guide

## Reference Materials

* **[Niagara Source Code](../niagara_source_code/)** - Official Niagara 4.13.3.48 source code for development reference

## External Examples and References

For additional Niagara development examples and patterns:

* **Official Niagara Examples** - Available in your Niagara installation at `{niagara_home}/examples/`
* **Tridium Developer Documentation** - See [Niagara Documentation](Niagara/) for comprehensive guides
* **Community Resources** - Niagara developer community forums and repositories
* **Authentication Examples** - ScramSha256 HTTP client examples available from Tridium documentation

## Project Documentation

* **[Project README](../README.md)** - Main project overview, features, and installation guide
* **[Technical Specification](../N4-DataSync%20Full%20Feature%20Specification%20&%20Roadmap.md)** - Complete technical roadmap and architectural details
