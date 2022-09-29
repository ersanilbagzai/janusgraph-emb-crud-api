package com.techframer.janusgraph.api.crud.graph.connection.cache.impl;

import com.techframer.janusgraph.api.crud.graph.connection.cache.IGraphTraversalCache;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component("GraphTraversalCache")
public class GraphTraversalCache<T extends GraphTraversalSource> implements IGraphTraversalCache<T> {

	private static Map<String, GraphTraversalSource> traversals = new LinkedHashMap<>();

	@Override
	public T add(String graphName, T graphTraversal) {
		return (T) traversals.put(graphName, (T) graphTraversal);
	}

	@Override
	public T set(String graphName, T graphTraversal) {
		return (T) traversals.put(graphName, (T) graphTraversal);
	}

	@Override
	public T get(String graphName) {
		return (T) traversals.get(graphName);
	}

	@Override
	public T remove(String graphName) {
		return (T) traversals.remove(graphName);
	}

	@Override
	public int size() {
		return traversals.size();
	}

	@Override
	public boolean isEmpty() {
		return traversals.isEmpty();
	}

	@Override
	public boolean contains(String graphName) {
		return traversals.containsKey(graphName);
	}

	@Override
	public void clear() {
		traversals.clear();
	}
}
