# deep-merge

[![CI](https://github.com/philiprehberger/kt-deep-merge/actions/workflows/publish.yml/badge.svg)](https://github.com/philiprehberger/kt-deep-merge/actions/workflows/publish.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.philiprehberger/deep-merge)](https://central.sonatype.com/artifact/com.philiprehberger/deep-merge)
[![License](https://img.shields.io/github/license/philiprehberger/kt-deep-merge)](LICENSE)

Deep merge maps with configurable conflict resolution.

## Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("com.philiprehberger:deep-merge:0.1.2")
}
```

### Maven

```xml
<dependency>
    <groupId>com.philiprehberger</groupId>
    <artifactId>deep-merge</artifactId>
    <version>0.1.2</version>
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

## License

MIT
