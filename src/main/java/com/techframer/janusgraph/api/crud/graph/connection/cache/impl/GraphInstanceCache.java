package com.techframer.janusgraph.api.crud.graph.connection.cache.impl;

import com.techframer.janusgraph.api.crud.graph.connection.cache.IGraphInstanceCache;
import org.janusgraph.core.JanusGraph;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component("GraphInstanceCache")
public class GraphInstanceCache<T extends JanusGraph> implements IGraphInstanceCache<T> {

	/** The graphs. */
	private static Map<String, JanusGraph> graphs = new LinkedHashMap<>();

	@Override
	public T add(String graphName, T graphTraversal) {
		return (T) graphs.put(graphName, (T) graphTraversal);
	}

	@Override
	public T set(String graphName, T graphTraversal) {
		return (T) graphs.put(graphName, (T) graphTraversal);
	}

	@Override
	public T get(String graphName) {
		return (T) graphs.get(graphName);
	}

	@Override
	public T remove(String graphName) {
		return (T) graphs.remove(graphName);
	}

	@Override
	public int size() {
		return graphs.size();
	}

	@Override
	public boolean isEmpty() {
		return graphs.isEmpty();
	}

	@Override
	public boolean contains(String graphName) {
		return graphs.containsKey(graphName);
	}

	@Override
	public void clear() {
		graphs.clear();
	}

}
