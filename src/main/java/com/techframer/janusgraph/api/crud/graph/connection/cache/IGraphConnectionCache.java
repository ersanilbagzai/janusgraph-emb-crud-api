package com.techframer.janusgraph.api.crud.graph.connection.cache;

/**
 * The Interface IGraphConnectionCache.
 *
 * @param <T>
 *            the generic type
 */
public interface IGraphConnectionCache<T> {

	/**
	 * Adds the.
	 *
	 * @param graphName
	 *            the graph name
	 * @param object
	 *            the object
	 * @return the t
	 */
	T add(String graphName, T object);

	/**
	 * Sets the.
	 *
	 * @param graphName
	 *            the graph name
	 * @param graph
	 *            the graph
	 * @return the t
	 */
	T set(String graphName, T graph);

	/**
	 * Gets the.
	 *
	 * @param graphName
	 *            the graph name
	 * @return the t
	 */
	T get(String graphName);

	/**
	 * Removes the.
	 *
	 * @param graphName
	 *            the graph name
	 * @return the t
	 */
	T remove(String graphName);

	/**
	 * Size.
	 *
	 * @return the int
	 */
	int size();

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	boolean isEmpty();

	/**
	 * Contains.
	 *
	 * @param graphName
	 *            the graph name
	 * @return true, if successful
	 */
	boolean contains(String graphName);

	/**
	 * Clear.
	 */
	void clear();
}
