package com.philiprehberger.deepmerge

/** Strategy for resolving conflicts between non-map, non-list values. */
public enum class MergeStrategy { LAST_WINS, FIRST_WINS, THROW }
