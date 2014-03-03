package com.philiprehberger.deepmerge

/** Configuration for deep merge behavior. */
public class MergeConfig {
    public var onConflict: MergeStrategy = MergeStrategy.LAST_WINS
    public var onListConflict: ListMerge = ListMerge.REPLACE
    public var onNull: NullHandling = NullHandling.KEEP
}
