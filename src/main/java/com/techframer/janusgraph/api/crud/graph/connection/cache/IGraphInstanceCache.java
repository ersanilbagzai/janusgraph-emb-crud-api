package com.techframer.janusgraph.api.crud.graph.connection.cache;

import org.janusgraph.core.JanusGraph;

/**
 * The Interface IGraphInstanceCache.
 *
 * @param <T>
 *            the generic type
 */
public interface IGraphInstanceCache<T extends JanusGraph> extends IGraphConnectionCache<T> {

}
