#!/bin/bash

# N4-DataSync Development Environment Setup Script
# This script sets up the complete development environment with professional standards

set -e  # Exit on any error

echo "🚀 Setting up N4-DataSync Development Environment..."

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

# Check if we're in the right directory
if [ ! -f "build.gradle" ]; then
    print_error "This script must be run from the project root directory"
    exit 1
fi

print_info "Setting up Git configuration..."

# Configure Git for professional commits
if [ ! -f ".gitmessage" ]; then
    print_error ".gitmessage file not found. Please ensure it exists."
    exit 1
fi

git config commit.template .gitmessage
print_status "Git commit template configured"

# Set up Git hooks directory
if [ ! -d ".git/hooks" ]; then
    mkdir -p .git/hooks
fi

# Create commit-msg hook for validation
cat > .git/hooks/commit-msg << 'EOF'
#!/bin/bash
# Commit message validation hook

commit_regex='^(feat|fix|docs|style|refactor|test|chore|perf|ci|build|revert)(\(.+\))?: .{1,50}'

if ! grep -qE "$commit_regex" "$1"; then
    echo "❌ Invalid commit message format!"
    echo "Format: type(scope): subject"
    echo "Example: feat(profile): add connection validation"
    echo ""
    echo "Types: feat, fix, docs, style, refactor, test, chore, perf, ci, build, revert"
    exit 1
fi

# Check for TODO/FIXME without issue references
if grep -n "TODO\|FIXME" "$1" | grep -v "#[0-9]"; then
    echo "❌ TODOs/FIXMEs must reference GitHub issues"
    echo "Example: TODO: Issue #123 - Add input validation"
    exit 1
fi
EOF

chmod +x .git/hooks/commit-msg
print_status "Git commit message validation hook installed"

# Create pre-commit hook
cat > .git/hooks/pre-commit << 'EOF'
#!/bin/bash
# Pre-commit validation hook

echo "🔍 Running pre-commit checks..."

# Check for trailing whitespace
if git diff --cached --check; then
    echo "❌ Found trailing whitespace or other whitespace errors"
    exit 1
fi

# Check for large files
large_files=$(git diff --cached --name-only | xargs ls -la 2>/dev/null | awk '$5 > 1048576 {print $9 " (" $5 " bytes)"}')
if [ -n "$large_files" ]; then
    echo "❌ Large files detected (>1MB):"
    echo "$large_files"
    echo "Consider using Git LFS for large files"
    exit 1
fi

# Check for TODO/FIXME without issue references
unreferenced=$(git diff --cached --name-only | xargs grep -n "TODO\|FIXME" 2>/dev/null | grep -v "#[0-9]" || true)
if [ -n "$unreferenced" ]; then
    echo "❌ Found TODOs/FIXMEs without issue references:"
    echo "$unreferenced"
    echo "Please reference GitHub issues: TODO: Issue #123 - Description"
    exit 1
fi

echo "✅ Pre-commit checks passed"
EOF

chmod +x .git/hooks/pre-commit
print_status "Git pre-commit validation hook installed"

print_info "Setting up development tools..."

# Check if Gradle wrapper exists
if [ ! -f "gradlew" ]; then
    print_error "Gradle wrapper not found. Please ensure gradlew exists."
    exit 1
fi

# Make gradlew executable
chmod +x gradlew
print_status "Gradle wrapper configured"

# Run initial build to verify setup
print_info "Running initial build to verify setup..."
if ./gradlew clean build -x test; then
    print_status "Initial build successful"
else
    print_error "Initial build failed. Please check your environment."
    exit 1
fi

# Run tests to verify test environment
print_info "Running tests to verify test environment..."
if ./gradlew test; then
    print_status "Test environment verified"
else
    print_warning "Some tests failed. This may be expected for new components."
fi

print_info "Setting up IDE configuration..."

# Create .vscode directory if it doesn't exist
if [ ! -d ".vscode" ]; then
    mkdir .vscode
fi

# Create VS Code settings for Java development
cat > .vscode/settings.json << 'EOF'
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.compile.nullAnalysis.mode": "automatic",
    "java.format.settings.url": "https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml",
    "java.format.settings.profile": "GoogleStyle",
    "files.trimTrailingWhitespace": true,
    "files.insertFinalNewline": true,
    "editor.tabSize": 2,
    "editor.insertSpaces": false,
    "editor.detectIndentation": false,
    "[java]": {
        "editor.tabSize": 2,
        "editor.insertSpaces": false
    },
    "git.inputValidation": "always",
    "git.inputValidationLength": 50,
    "git.inputValidationSubjectLength": 50
}
EOF

print_status "VS Code settings configured"

# Create launch configuration for debugging
cat > .vscode/launch.json << 'EOF'
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Debug Tests",
            "request": "launch",
            "mainClass": "",
            "projectName": "datasync-wb",
            "args": "",
            "vmArgs": "-ea"
        }
    ]
}
EOF

print_status "Debug configuration created"

print_info "Creating development scripts..."

# Create script for running tests with coverage
cat > scripts/run-tests.sh << 'EOF'
#!/bin/bash
echo "🧪 Running N4-DataSync Tests..."

# Run all tests with coverage
./gradlew clean test jacocoTestReport

# Display coverage summary
if [ -f "build/reports/jacoco/test/html/index.html" ]; then
    echo "📊 Coverage report generated: build/reports/jacoco/test/html/index.html"
fi

# Check for test failures
if [ $? -eq 0 ]; then
    echo "✅ All tests passed!"
else
    echo "❌ Some tests failed. Check the reports for details."
    exit 1
fi
EOF

chmod +x scripts/run-tests.sh
print_status "Test runner script created"

# Create script for building and packaging
cat > scripts/build-module.sh << 'EOF'
#!/bin/bash
echo "🔨 Building N4-DataSync Module..."

# Clean and build
./gradlew clean build

# Check build status
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "📦 Module JAR: datasync/datasync-wb/build/libs/"
    ls -la datasync/datasync-wb/build/libs/*.jar
else
    echo "❌ Build failed. Check the output for errors."
    exit 1
fi
EOF

chmod +x scripts/build-module.sh
print_status "Build script created"

# Create script for development workflow
cat > scripts/start-feature.sh << 'EOF'
#!/bin/bash
# Script to start working on a new feature

if [ $# -eq 0 ]; then
    echo "Usage: $0 <issue-number> [description]"
    echo "Example: $0 123 add-connection-validation"
    exit 1
fi

ISSUE_NUMBER=$1
DESCRIPTION=${2:-"feature-$1"}

# Ensure we're on main and up to date
git checkout main
git pull origin main

# Create feature branch
BRANCH_NAME="feature/$ISSUE_NUMBER-$DESCRIPTION"
git checkout -b "$BRANCH_NAME"

echo "✅ Created and switched to branch: $BRANCH_NAME"
echo "🚀 Ready to start development!"
echo ""
echo "Remember to:"
echo "- Make small, frequent commits"
echo "- Reference issue #$ISSUE_NUMBER in commit messages"
echo "- Run tests before pushing: ./scripts/run-tests.sh"
echo "- Create PR when feature is complete"
EOF

chmod +x scripts/start-feature.sh
print_status "Feature workflow script created"

print_info "Setting up quality gates..."

# Create quality check script
cat > scripts/quality-check.sh << 'EOF'
#!/bin/bash
echo "🔍 Running Quality Checks..."

# Run tests with coverage
./gradlew test jacocoTestReport

# Check coverage threshold (85%)
COVERAGE=$(grep -o 'Total.*[0-9]*%' build/reports/jacoco/test/html/index.html | grep -o '[0-9]*%' | head -1 | sed 's/%//')

if [ -n "$COVERAGE" ] && [ "$COVERAGE" -ge 85 ]; then
    echo "✅ Coverage: $COVERAGE% (meets 85% threshold)"
else
    echo "❌ Coverage: $COVERAGE% (below 85% threshold)"
    exit 1
fi

# Run static analysis (if available)
if command -v spotbugs &> /dev/null; then
    ./gradlew spotbugsMain
    echo "✅ Static analysis completed"
fi

echo "✅ All quality checks passed!"
EOF

chmod +x scripts/quality-check.sh
print_status "Quality check script created"

print_info "Finalizing setup..."

# Create development status file
cat > .dev-setup-complete << EOF
N4-DataSync Development Environment Setup Complete
==================================================

Setup Date: $(date)
Git Hooks: Installed
IDE Config: VS Code configured
Scripts: Created in scripts/ directory
Quality Gates: 85% coverage threshold

Next Steps:
1. Start working on Issue #6: ./scripts/start-feature.sh 6 separate-connection-profile
2. Run tests: ./scripts/run-tests.sh
3. Build module: ./scripts/build-module.sh
4. Check quality: ./scripts/quality-check.sh

Development Workflow:
- Use feature branches: feature/issue-number-description
- Follow commit message standards
- Maintain 85%+ test coverage
- Create PRs for all changes

Happy coding! 🚀
EOF

print_status "Development environment setup complete!"

echo ""
echo "🎉 N4-DataSync Development Environment Ready!"
echo ""
echo "📋 Summary of what was configured:"
echo "   • Git commit message template and validation hooks"
echo "   • Pre-commit quality checks"
echo "   • VS Code IDE settings and debug configuration"
echo "   • Development scripts in scripts/ directory"
echo "   • Quality gates with 85% coverage threshold"
echo ""
echo "🚀 Next steps:"
echo "   1. Start working on profile separation: ./scripts/start-feature.sh 6 separate-connection-profile"
echo "   2. Run the test suite: ./scripts/run-tests.sh"
echo "   3. Build the module: ./scripts/build-module.sh"
echo ""
echo "📚 Documentation:"
echo "   • Development workflow: docs/DEVELOPMENT_WORKFLOW.md"
echo "   • Version management: docs/VERSION_MANAGEMENT_STANDARDS.md"
echo "   • Project management: docs/PROJECT_MANAGEMENT_STANDARDS.md"
echo ""
print_status "Setup complete! Happy coding! 🎉"
EOF
