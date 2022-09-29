package com.techframer.janusgraph.api.crud.graph.connection.cache;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

/**
 * The Interface IGraphTraversalCache.
 *
 * @param <T>
 *            the generic type
 */
public interface IGraphTraversalCache<T extends GraphTraversalSource> extends IGraphConnectionCache<T> {

}
