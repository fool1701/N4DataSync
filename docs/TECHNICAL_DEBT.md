# Technical Debt Register

## 🔴 Critical (Must Fix Before MVP)

| Issue | Component | Impact | Effort | GitHub Issue | Status |
|-------|-----------|--------|--------|--------------|--------|
| Plain text password storage | ProfileManager | Security | Medium | [#1](https://github.com/fool1701/N4DataSync/issues/1) | Open |
| No input validation | BConnectionProfile | Data integrity | Small | [#2](https://github.com/fool1701/N4DataSync/issues/2) | Open |

## 🟡 Important (Fix Before Production)

| Issue | Component | Impact | Effort | GitHub Issue | Status |
|-------|-----------|--------|--------|--------------|--------|
| Generic exception handling | ProfileManager | Debugging | Medium | [#3](https://github.com/fool1701/N4DataSync/issues/3) | Open |
| String-based status instead of enum | BConnectionProfile | Type safety | Small | [#4](https://github.com/fool1701/N4DataSync/issues/4) | Open |

## 🟢 Nice to Have (Future Improvements)

| Issue | Component | Impact | Effort | GitHub Issue | Status |
|-------|-----------|--------|--------|--------------|--------|
| Missing Factory pattern | Model layer | Architecture | Large | TBD | Backlog |
| Code duplication in getters/setters | BConnectionProfile | Maintainability | Small | [#5](https://github.com/fool1701/N4DataSync/issues/5) | Open |
| No comprehensive validation framework | All components | Robustness | Large | TBD | Backlog |

## Legend
- 🔴 **Critical**: Blocks MVP or creates security risks
- 🟡 **Important**: Should be fixed before production deployment
- 🟢 **Nice to Have**: Quality improvements for future iterations

## Review Schedule
- **Weekly**: Update status and reprioritize
- **Before MVP**: Address all Critical items
- **Before Production**: Address all Critical and Important items

## Notes
- All issues should have corresponding GitHub issues for detailed tracking
- Effort estimates: Small (< 2h), Medium (2-8h), Large (> 8h)
- Priority can change based on development needs
