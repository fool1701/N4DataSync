# Contributing to N4-DataSync

## Welcome Contributors!

Thank you for your interest in contributing to N4-DataSync! This guide will help you get started with contributing to the project.

## Getting Started

### Prerequisites for Contributors

- **Niagara 4.11+** with development license
- **Java 8 JDK** (Zulu JDK 8 recommended)
- **IntelliJ IDEA** (recommended IDE)
- **Git** for version control
- **Basic understanding** of Niagara framework

### Development Setup

1. **Clone the repository**
2. **Follow the [Certificate Setup Guide](CERTIFICATE_SETUP.md)**
3. **Run the test suite**: `gradlew :datasync-wb:niagaraTest`
4. **Build the module**: `gradlew :datasync-wb:jar`

## How to Contribute

### Types of Contributions Welcome

- 🐛 **Bug fixes** - Fix issues in existing functionality
- ✨ **New features** - Add capabilities within project scope
- 📚 **Documentation** - Improve guides, examples, comments
- 🧪 **Tests** - Add test coverage for existing code
- 🔧 **Build improvements** - Enhance development workflow
- 🎨 **UI improvements** - Better user experience

### Before You Start

1. **Check existing issues** - See if someone is already working on it
2. **Create an issue** - Discuss your idea before implementing
3. **Review the roadmap** - Ensure alignment with project direction
4. **Start small** - Begin with documentation or small bug fixes

## Development Guidelines

### Code Style

#### Java Code Standards
```java
// Use clear, descriptive names
public class BConnectionProfile extends BComponent {
    
    // Document public methods with Javadoc
    /**
     * Saves the connection profile to persistent storage.
     * @param profileName The name to save the profile under
     * @return true if save was successful, false otherwise
     */
    public boolean saveProfile(String profileName) {
        // Implementation
    }
    
    // Use meaningful variable names
    private String sourceFilePath;
    private String targetStationHost;
}
```

#### Niagara Conventions
- **Extend appropriate base classes** (`BComponent`, `BTestNg`, etc.)
- **Use @NiagaraType annotation** for all Niagara types
- **Follow slot-o-matic patterns** for component properties
- **Use proper agent registration** for UI components

#### General Guidelines
- **Write self-documenting code** with clear names
- **Add comments for complex logic** (explain WHY, not WHAT)
- **Keep methods focused** - single responsibility
- **Use consistent formatting** - follow existing style

### Testing Requirements

#### All Contributions Must Include Tests
```java
@Test(groups = {"datasync", "unit"})
public void testProfileSerialization() {
    // Given
    BConnectionProfile profile = createTestProfile();
    
    // When
    String json = profileManager.serialize(profile);
    BConnectionProfile loaded = profileManager.deserialize(json);
    
    // Then
    Assert.assertEquals(loaded.getSourcePath(), profile.getSourcePath());
}
```

#### Test Guidelines
- **Use descriptive test names** that explain what is being tested
- **Follow Given-When-Then structure**
- **Test both success and failure cases**
- **Use appropriate test groups** (`unit`, `integration`, `ui`)
- **Clean up resources** in `@AfterClass` methods

### Documentation Requirements

#### Code Documentation
- **Javadoc for all public APIs**
- **Inline comments for complex logic**
- **Update relevant documentation** when changing functionality

#### User Documentation
- **Update guides** when adding user-facing features
- **Add examples** for new functionality
- **Update troubleshooting** for new error conditions

## Submission Process

### 1. Prepare Your Changes

```bash
# Create a feature branch
git checkout -b feature/your-feature-name

# Make your changes
# ... edit files ...

# Run tests
gradlew :datasync-wb:niagaraTest

# Build module
gradlew :datasync-wb:jar
```

### 2. Test Your Changes

- **Run full test suite**: `gradlew :datasync-wb:niagaraTest`
- **Test manually** in Niagara Workbench
- **Verify documentation** is accurate
- **Check for regressions** in existing functionality

### 3. Submit Your Contribution

1. **Commit your changes** with clear commit messages
2. **Push to your branch**
3. **Create a pull request** with:
   - Clear description of changes
   - Reference to related issues
   - Test results
   - Screenshots for UI changes

### Commit Message Format

```
type(scope): brief description

Longer description if needed, explaining:
- What changed
- Why it changed
- Any breaking changes

Fixes #123
```

**Types**: `feat`, `fix`, `docs`, `test`, `refactor`, `style`, `chore`

**Examples**:
```
feat(ui): add profile validation in DataSync Manager

- Add real-time validation for connection profiles
- Show error messages for invalid configurations
- Prevent saving of incomplete profiles

Fixes #45

fix(persistence): handle file permission errors gracefully

- Catch IOException when creating profile directory
- Show user-friendly error message
- Fallback to temporary directory if needed

Fixes #67
```

## Project Structure

### Understanding the Codebase

```
datasync/datasync-wb/
├── src/com/mea/datasync/
│   ├── model/          # Data models (BConnectionProfile)
│   ├── persistence/    # Data storage (ProfileManager)
│   ├── ui/            # User interface components
│   └── core/          # Core synchronization logic
├── srcTest/           # Test code
└── module-include.xml # Niagara module configuration
```

### Key Components

- **BConnectionProfile** - Connection profile data model
- **ProfileManager** - Profile persistence management
- **BDataSyncTool** - Main Workbench tool entry point
- **BDataSyncManagerView** - Profile management UI

## Review Process

### What Reviewers Look For

1. **Code Quality** - Clear, maintainable code
2. **Test Coverage** - Adequate tests for new functionality
3. **Documentation** - Updated guides and comments
4. **Niagara Compliance** - Follows Niagara patterns
5. **User Experience** - Intuitive and helpful features

### Review Timeline

- **Initial review** within 1-2 weeks
- **Follow-up reviews** within a few days
- **Merge** after approval and passing tests

## Getting Help

### Resources

- **[Development Patterns](NiagaraPatterns.md)** - Niagara development guidance
- **[Testing Guide](TESTING.md)** - How to run and write tests
- **[Niagara Documentation](Niagara/)** - Official framework docs
- **[Project Specification](../N4-DataSync%20Full%20Feature%20Specification%20&%20Roadmap.md)** - Complete project vision

### External Examples

For additional Niagara development examples:
- **Official Examples**: `{niagara_home}/examples/` in your Niagara installation
- **Module Examples**: componentLinks, envCtrlDriver, typeExtensionDemo
- **Authentication Examples**: ScramSha256 HTTP client implementations
- **Community Examples**: Niagara developer forums and GitHub repositories

### Communication

- **Create issues** for questions or discussions
- **Reference documentation** when asking questions
- **Be patient and respectful** in all interactions

## Recognition

Contributors will be recognized in:
- **Project README** - Contributor list
- **Release notes** - Feature attribution
- **Documentation** - Author credits where appropriate

Thank you for contributing to N4-DataSync! 🚀
