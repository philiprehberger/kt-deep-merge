package com.philiprehberger.deepmerge

/**
 * Deep merge multiple maps. Later maps override earlier maps.
 * Nested maps are merged recursively. Lists are merged per [MergeConfig.onListConflict].
 */
@Suppress("UNCHECKED_CAST")
public fun deepMerge(vararg maps: Map<String, Any?>, config: MergeConfig.() -> Unit = {}): Map<String, Any?> {
    val cfg = MergeConfig().apply(config)
    return maps.fold(emptyMap()) { acc, map -> mergeTwo(acc, map, cfg, "") }
}

/**
 * Returns only the keys/values in [modified] that differ from [original] (deep comparison).
 * Nested maps are compared recursively — the result preserves structure for nested diffs.
 * Keys present in [original] but absent in [modified] are not included.
 */
@Suppress("UNCHECKED_CAST")
public fun diff(original: Map<String, Any?>, modified: Map<String, Any?>): Map<String, Any?> {
    val result = mutableMapOf<String, Any?>()
    for ((key, modifiedVal) in modified) {
        if (!original.containsKey(key)) {
            result[key] = modifiedVal
            continue
        }
        val originalVal = original[key]
        if (originalVal is Map<*, *> && modifiedVal is Map<*, *>) {
            val nested = diff(originalVal as Map<String, Any?>, modifiedVal as Map<String, Any?>)
            if (nested.isNotEmpty()) {
                result[key] = nested
            }
        } else if (originalVal != modifiedVal) {
            result[key] = modifiedVal
        }
    }
    return result
}

@Suppress("UNCHECKED_CAST")
private fun mergeTwo(left: Map<String, Any?>, right: Map<String, Any?>, cfg: MergeConfig, path: String): Map<String, Any?> {
    val result = left.toMutableMap()
    for ((key, rightVal) in right) {
        val fullPath = if (path.isEmpty()) key else "$path.$key"
        if (rightVal == null && cfg.onNull == NullHandling.SKIP) continue
        val leftVal = result[key]
        result[key] = when {
            leftVal is Map<*, *> && rightVal is Map<*, *> ->
                mergeTwo(leftVal as Map<String, Any?>, rightVal as Map<String, Any?>, cfg, fullPath)
            leftVal is List<*> && rightVal is List<*> -> when (cfg.onListConflict) {
                ListMerge.REPLACE -> rightVal
                ListMerge.APPEND -> leftVal + rightVal
                ListMerge.UNION -> (leftVal + rightVal).distinct()
            }
            !result.containsKey(key) -> rightVal
            else -> when (cfg.onConflict) {
                MergeStrategy.LAST_WINS -> rightVal
                MergeStrategy.FIRST_WINS -> leftVal
                MergeStrategy.THROW -> throw MergeConflictException(fullPath, leftVal, rightVal)
            }
        }
    }
    return result
}
