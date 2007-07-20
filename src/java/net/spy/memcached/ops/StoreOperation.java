package net.spy.memcached.ops;

/**
 * Operation that represents object storage.
 */
public interface StoreOperation extends Operation {

	/**
	 * The type of storage operation to perform.
	 */
	public enum StoreType {
		/**
		 * Unconditionally store a value in the cache.
		 */
		set,
		/**
		 * Store a value in the cache iff there is not already something stored
		 * for the given key.
		 */
		add,
		/**
		 * Store a value in the cache iff there is already something stored for
		 * the given key.
		 */
		replace
	}

}