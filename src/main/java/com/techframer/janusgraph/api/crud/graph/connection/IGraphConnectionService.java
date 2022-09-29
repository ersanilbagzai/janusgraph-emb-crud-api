package com.techframer.janusgraph.api.crud.graph.connection;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;

/**
 * The Interface IGraphConnectionService.
 */
public interface IGraphConnectionService {

	/**
	 * Open.
	 *
	 * @param filePath
	 *            the file path
	 * @return the janus graph
	 */
	JanusGraph open(String filePath);

	/**
	 * Traversal.
	 *
	 * @param graph
	 *            the graph
	 * @return the graph traversal source
	 */
	GraphTraversalSource traversal(JanusGraph graph);

	/**
	 * Close.
	 *
	 * @param graph
	 *            the graph
	 */
	void close(JanusGraph graph);

	/**
	 * Checks if is closed.
	 *
	 * @param graph
	 *            the graph
	 * @return true, if is closed
	 */
	boolean isClosed(JanusGraph graph);
}
