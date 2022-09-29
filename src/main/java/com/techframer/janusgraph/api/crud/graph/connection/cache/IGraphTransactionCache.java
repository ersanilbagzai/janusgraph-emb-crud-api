package com.techframer.janusgraph.api.crud.graph.connection.cache;

import org.janusgraph.core.JanusGraph;

/**
 * The Interface IGraphTransactionCache.
 *
 * @param <T>
 *            the generic type
 */
public interface IGraphTransactionCache<T extends JanusGraph> extends IGraphConnectionCache<T> {

}
