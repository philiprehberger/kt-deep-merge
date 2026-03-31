# deep-merge

[![Tests](https://github.com/philiprehberger/kt-deep-merge/actions/workflows/publish.yml/badge.svg)](https://github.com/philiprehberger/kt-deep-merge/actions/workflows/publish.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.philiprehberger/deep-merge.svg)](https://central.sonatype.com/artifact/com.philiprehberger/deep-merge)
[![Last updated](https://img.shields.io/github/last-commit/philiprehberger/kt-deep-merge)](https://github.com/philiprehberger/kt-deep-merge/commits/main)

Deep merge maps with configurable conflict resolution.

## Installation

### Gradle (Kotlin DSL)

```kotlin
implementation("com.philiprehberger:deep-merge:0.1.3")
```

### Maven

```xml
<dependency>
    <groupId>com.philiprehberger</groupId>
    <artifactId>deep-merge</artifactId>
    <version>0.1.3</version>
</dependency>
```

## Usage

```kotlin
import com.philiprehberger.deepmerge.*

val defaults = mapOf("db" to mapOf("host" to "localhost", "port" to 5432))
val overrides = mapOf("db" to mapOf("host" to "db.prod.example.com"))

val config = deepMerge(defaults, overrides)
// {"db": {"host": "db.prod.example.com", "port": 5432}}

// With configuration
deepMerge(a, b) {
    onConflict = MergeStrategy.FIRST_WINS
    onListConflict = ListMerge.APPEND
    onNull = NullHandling.SKIP
}
```

## API

| Function / Class | Description |
|------------------|-------------|
| `deepMerge(vararg maps, config)` | Deep merge maps with configurable strategies |
| `MergeStrategy` | Conflict resolution: LAST_WINS, FIRST_WINS, THROW |
| `ListMerge` | List merging: REPLACE, APPEND, UNION |
| `NullHandling` | Null values: KEEP or SKIP |
| `MergeConflictException` | Thrown when THROW strategy encounters a conflict |

## Development

```bash
./gradlew test       # Run tests
./gradlew build      # Build JAR
```

## Support

If you find this project useful:

⭐ [Star the repo](https://github.com/philiprehberger/kt-deep-merge)

🐛 [Report issues](https://github.com/philiprehberger/kt-deep-merge/issues?q=is%3Aissue+is%3Aopen+label%3Abug)

💡 [Suggest features](https://github.com/philiprehberger/kt-deep-merge/issues?q=is%3Aissue+is%3Aopen+label%3Aenhancement)

❤️ [Sponsor development](https://github.com/sponsors/philiprehberger)

🌐 [All Open Source Projects](https://philiprehberger.com/open-source-packages)

💻 [GitHub Profile](https://github.com/philiprehberger)

🔗 [LinkedIn Profile](https://www.linkedin.com/in/philiprehberger)

## License

[MIT](LICENSE)
