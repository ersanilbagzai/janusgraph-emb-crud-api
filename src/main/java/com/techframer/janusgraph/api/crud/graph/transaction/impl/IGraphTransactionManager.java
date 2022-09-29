package com.techframer.janusgraph.api.crud.graph.transaction.impl;

import com.techframer.janusgraph.api.crud.graph.transaction.GraphTransaction;
import com.techframer.janusgraph.api.crud.graph.transaction.exceptions.TransactionException;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.schema.JanusGraphManagement;

public interface IGraphTransactionManager {

	GraphTransaction openTransaction() throws TransactionException;

	void commitTransaction(GraphTransaction transaction) throws TransactionException;
	void commitTransaction(Graph createThreadedTx) throws TransactionException;

	Graph getThreadedTransaction() throws TransactionException;

	Graph getThreadedTransaction(GraphTransaction transaction) throws TransactionException;

	void rollbackTransaction(GraphTransaction transaction) throws TransactionException;

	GraphTransaction getTransactionIfNull(GraphTransaction transaction) throws TransactionException;

	public JanusGraphManagement getManagementTransaction(JanusGraph graphInstance) throws TransactionException;
}
