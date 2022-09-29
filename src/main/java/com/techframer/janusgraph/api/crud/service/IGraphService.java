package com.techframer.janusgraph.api.crud.service;


import com.techframer.janusgraph.api.crud.graph.exceptions.*;
import com.techframer.janusgraph.api.crud.graph.transaction.GraphTransaction;
import com.techframer.janusgraph.api.crud.graph.transaction.exceptions.TransactionException;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;

public interface IGraphService {
	GraphVertex addV(GraphVertex vertex, GraphTransaction transaction) throws GraphObjectNotExistsException,
			GraphMarshallingException, TransactionException, GraphServiceException, GraphUnmarshallingException;

	GraphVertex replaceVByUUID(GraphVertex vertex, GraphTransaction transaction)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphMarshallingException;
	
	GraphVertex replaceVByVId(GraphVertex vertex, GraphTransaction transaction)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphMarshallingException;

	GraphVertex patchVByVId(GraphVertex vertex, GraphTransaction transaction) throws GraphObjectNotExistsException,
            TransactionException, GraphUnmarshallingException, GraphMarshallingException, GraphServiceException;
	
	GraphVertex patchVByUUID(GraphVertex vertex, GraphTransaction transaction) throws GraphObjectNotExistsException,
            TransactionException, GraphUnmarshallingException, GraphMarshallingException, GraphServiceException;

	boolean dropVById(Object vertexId, GraphTransaction transaction) throws GraphServiceException, GraphObjectNotExistsException,
            TransactionException, GraphUnmarshallingException;
	
	boolean dropVByUUID(Object vertexId, GraphTransaction transaction) throws GraphServiceException, GraphObjectNotExistsException,
            TransactionException, GraphUnmarshallingException;

	GraphEdge addE(GraphEdge edge, GraphTransaction transaction)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException,
			GraphEdgeNotExistsException, GraphMarshallingException, GraphServiceException;

	GraphEdge replaceEById(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;
	
	GraphEdge replaceEByUUID(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
	GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;

	GraphEdge patchEById(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;
	
	GraphEdge patchEByUUID(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
	GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;

	boolean dropEById(String edgeId, GraphTransaction transaction) throws GraphEdgeNotExistsException, TransactionException, GraphServiceException;
	
	boolean dropEByUUID(String edgeId, GraphTransaction transaction) throws GraphEdgeNotExistsException, TransactionException, GraphServiceException;

	GraphVertex getVById(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphServiceException;
	
	GraphVertex getVByUUID(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException, TransactionException,
	GraphUnmarshallingException, GraphServiceException;

	GraphEdge getEById(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException, GraphUnmarshallingException,
            TransactionException, GraphEdgeNotExistsException, GraphServiceException;
	
	GraphEdge getEByUUID(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException, GraphUnmarshallingException,
            TransactionException, GraphEdgeNotExistsException, GraphServiceException;

}
