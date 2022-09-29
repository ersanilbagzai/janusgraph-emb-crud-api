package com.techframer.janusgraph.api.crud.graph.connection;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;

/**
 * The Interface IGraphInstanceManagerService.
 */
public interface IGraphInstanceManager {

	/**
	 * Gets the graph instance.
	 *
	 * @return the graph instance
	 */
	JanusGraph getGraphInstance();

	/**
	 * Gets the graph traversal.
	 *
	 * @return the graph traversal
	 */
	GraphTraversalSource getGraphTraversal();

	/**
	 * Refresh graph.
	 *
	 * @return true, if successful
	 */
	boolean refreshGraph();

	/**
	 * Inits the graph.
	 */
	void initGraph();

}
