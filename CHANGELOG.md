# Changelog

## 0.2.0 (2026-04-05)

- Add `diff()` function for deep comparison of two maps, returning only differing keys/values

## 0.1.4 (2026-03-31)

- Standardize README to 3-badge format with emoji Support section
- Update CI checkout action to v5 for Node.js 24 compatibility
- Add GitHub issue templates, dependabot config, and PR template

## 0.1.3 (2026-03-22)

- Fix README compliance (badge label, installation format), standardize CHANGELOG

## 0.1.2 (2026-03-20)

- Standardize README: fix title, badges, version sync, remove Requirements section

## 0.1.1 (2026-03-18)

- Upgrade to Kotlin 2.0.21 and Gradle 8.12
- Enable explicitApi() for stricter public API surface
- Add issueManagement to POM metadata

## 0.1.0 (2026-03-18)

### Added

- `deepMerge()` for recursive map merging

- Conflict strategies: LAST_WINS, FIRST_WINS, THROW

- List merge strategies: REPLACE, APPEND, UNION

- Null handling: KEEP or SKIP

- Variadic merge (multiple sources)
