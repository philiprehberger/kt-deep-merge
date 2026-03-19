package com.philiprehberger.deepmerge

/** Thrown when [MergeStrategy.THROW] encounters a conflict. */
public class MergeConflictException(
    public val key: String, public val left: Any?, public val right: Any?,
) : RuntimeException("Merge conflict at key '$key': $left vs $right")
