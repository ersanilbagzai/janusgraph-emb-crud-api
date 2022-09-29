package com.techframer.janusgraph.api.crud.graph.connection.cache.impl;

import com.techframer.janusgraph.api.crud.graph.connection.cache.IGraphTransactionCache;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.janusgraph.core.JanusGraph;

import java.util.LinkedHashMap;
import java.util.Map;

public class GraphTransactionCache<T extends JanusGraph> implements IGraphTransactionCache<T> {

	private static Map<String, Graph> transactions = new LinkedHashMap<>();

	@Override
	public T add(String graphName, T graphTraversal) {
		return (T) transactions.put(graphName, (T) graphTraversal);
	}

	@Override
	public T set(String graphName, T graphTraversal) {
		return (T) transactions.put(graphName, (T) graphTraversal);
	}

	@Override
	public T get(String graphName) {
		return (T) transactions.get(graphName);
	}

	@Override
	public T remove(String graphName) {
		return (T) transactions.remove(graphName);
	}

	@Override
	public int size() {
		return transactions.size();
	}

	@Override
	public boolean isEmpty() {
		return transactions.isEmpty();
	}

	@Override
	public boolean contains(String graphName) {
		return transactions.containsKey(graphName);
	}

	@Override
	public void clear() {
		transactions.clear();
	}

}
