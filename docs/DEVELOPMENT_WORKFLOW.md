# N4-DataSync Development Workflow

## **Quick Start for New Features**

### 1. Create or Assign Issue
```bash
# Create issue on GitHub or get assigned to existing issue
# Example: Issue #123 "Add connection validation to BConnectionProfile"
```

### 2. Create Feature Branch
```bash
git checkout main
git pull origin main
git checkout -b feature/123-add-connection-validation
```

### 3. Configure Git for Professional Commits
```bash
# Set up commit message template (one-time setup)
git config commit.template .gitmessage

# Configure your identity (if not already done)
git config user.name "Your Name"
git config user.email "your.email@example.com"
```

### 4. Develop with Professional Practices
```bash
# Make small, logical commits
git add src/com/mea/datasync/model/BConnectionProfile.java
git commit
# Use the template to write a proper commit message:
# feat(profile): add null validation to setSourcePath method
#
# - Add null/empty check before setting source path
# - Throw IllegalArgumentException for invalid inputs
# - Improve user experience with clear error messages
#
# Relates to #123

# Continue development with frequent commits
git add tests/
git commit -m "test(profile): add unit tests for source path validation

- Test null input handling
- Test empty string handling  
- Test valid path acceptance
- Verify exception messages

Relates to #123"
```

### 5. Push and Create Pull Request
```bash
git push origin feature/123-add-connection-validation

# Create PR on GitHub using the feature template
# Link to issue: "Closes #123"
```

## **Branch Naming Conventions**

### Format: `type/issue-number-short-description`

**Types:**
- `feature/` - New functionality
- `fix/` - Bug fixes
- `hotfix/` - Critical production fixes
- `chore/` - Maintenance tasks
- `docs/` - Documentation updates
- `test/` - Test improvements
- `refactor/` - Code restructuring

**Examples:**
```bash
feature/123-add-connection-validation
fix/456-resolve-memory-leak-in-profile-manager
hotfix/789-critical-security-patch
chore/101-update-dependencies
docs/202-improve-api-documentation
test/303-add-integration-tests
refactor/404-separate-connection-concerns
```

## **Commit Message Standards**

### Format
```
type(scope): subject

body

footer
```

### Examples
```bash
# Feature commit
feat(profile): add connection validation to BConnectionProfile

Add comprehensive validation for connection parameters including
null checks, format validation, and business rule enforcement.

- Add null/empty validation to all setters
- Add format validation for paths and URLs
- Throw IllegalArgumentException for invalid inputs
- Provide meaningful error messages

Closes #123

# Bug fix commit
fix(persistence): resolve JSON serialization issue with BAbsTime

The ProfileManager was failing to serialize BAbsTime objects correctly,
causing profile save operations to fail silently.

- Convert BAbsTime to ISO string format for JSON
- Add proper error handling for time conversion
- Update tests to verify time serialization

Fixes #456

# Documentation commit
docs(readme): update installation instructions for N4.13

- Add specific steps for Niagara 4.13.3.48
- Include troubleshooting for common certificate issues
- Update dependency requirements

# Chore commit
chore(deps): update Gson to version 2.10.1

- Update Gson dependency for security patches
- Verify compatibility with existing serialization
- Update tests for new Gson behavior
```

## **Pull Request Process**

### 1. Pre-PR Checklist
- [ ] All tests pass locally
- [ ] Code follows project style guidelines
- [ ] Commit messages follow conventions
- [ ] Branch name follows conventions
- [ ] Documentation updated if needed

### 2. Create Pull Request
- Use appropriate PR template (feature/bugfix)
- Fill out all sections completely
- Link to related issues
- Add reviewers
- Add appropriate labels

### 3. Code Review Process
- Address all review comments
- Make requested changes in new commits
- Don't force-push during review
- Respond to comments professionally

### 4. Merge Requirements
- [ ] All CI checks pass
- [ ] At least one approval from code owner
- [ ] No merge conflicts
- [ ] All conversations resolved

## **Issue Management Workflow**

### 1. Issue Creation
```bash
# Use GitHub issue templates
# Provide clear description and acceptance criteria
# Add appropriate labels and milestone
# Estimate effort (story points)
```

### 2. Issue Lifecycle
```
New → Triaged → In Progress → Review → Done → Closed
```

### 3. Issue Labels
- **Type**: `bug`, `enhancement`, `tech-debt`, `security`
- **Priority**: `priority/critical`, `priority/high`, `priority/medium`, `priority/low`
- **Status**: `status/needs-triage`, `status/in-progress`, `status/blocked`
- **Component**: `component/ui`, `component/persistence`, `component/model`

## **Testing Workflow**

### 1. Test-Driven Development
```bash
# Write failing test first
./gradlew test
# Expected: Test fails

# Implement feature
# Run tests again
./gradlew test
# Expected: Test passes

# Refactor if needed
./gradlew test
# Expected: All tests still pass
```

### 2. Test Categories
```bash
# Unit tests (fast, isolated)
./gradlew test --tests "*Unit*"

# Integration tests (slower, with dependencies)
./gradlew test --tests "*Integration*"

# All tests
./gradlew test

# With coverage
./gradlew test jacocoTestReport
```

### 3. Quality Gates
- Minimum 85% code coverage
- All tests must pass
- No security vulnerabilities
- No critical code quality issues

## **Release Workflow**

### 1. Prepare Release
```bash
# Create release branch
git checkout main
git pull origin main
git checkout -b release/v1.0.0

# Update version numbers
# Update CHANGELOG.md
# Run full test suite
./gradlew clean build test
```

### 2. Release Process
```bash
# Create release PR to main
# Get approval and merge
# Tag the release
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0

# GitHub Actions will automatically create release
```

### 3. Post-Release
```bash
# Merge back to develop
git checkout develop
git merge main
git push origin develop
```

## **Hotfix Workflow**

### 1. Critical Issues
```bash
# Create hotfix branch from main
git checkout main
git pull origin main
git checkout -b hotfix/789-critical-security-patch

# Fix the issue
# Test thoroughly
# Create PR to main with "hotfix" label
```

### 2. Emergency Deployment
```bash
# After PR approval and merge
# Tag hotfix release
git tag -a v1.0.1 -m "Hotfix version 1.0.1"
git push origin v1.0.1

# Deploy immediately
# Monitor for issues
```

## **Daily Development Routine**

### Morning Routine
```bash
# Check for updates
git checkout main
git pull origin main

# Review assigned issues
# Check PR reviews needed
# Plan day's work
```

### Development Session
```bash
# Start work on issue
git checkout -b feature/issue-number-description

# Make frequent, small commits
# Push regularly for backup
git push origin feature/issue-number-description

# Create PR when feature is complete
```

### End of Day
```bash
# Ensure work is pushed
git push origin current-branch

# Update issue status
# Review any pending PRs
# Plan next day's work
```

## **Collaboration Best Practices**

### Code Reviews
- Review code within 24 hours
- Provide constructive feedback
- Focus on logic, security, maintainability
- Approve only when confident

### Communication
- Use issue comments for technical discussion
- Keep PR descriptions clear and detailed
- Document decisions in code comments
- Share knowledge in team meetings

### Continuous Improvement
- Regular retrospectives
- Process refinement
- Tool evaluation
- Knowledge sharing sessions
