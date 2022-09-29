package com.techframer.janusgraph.api.crud.repository;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.graph.exceptions.*;
import com.techframer.janusgraph.api.crud.graph.transaction.GraphTransaction;
import com.techframer.janusgraph.api.crud.graph.transaction.exceptions.TransactionException;

import java.util.List;

public interface GraphRepository {
	GraphVertex patchVByUUID(GraphVertex vertex, GraphTransaction transaction) throws GraphObjectNotExistsException,
            TransactionException, GraphUnmarshallingException, GraphMarshallingException, GraphServiceException;

	GraphVertex patchVByVId(GraphVertex vertex, GraphTransaction transaction) throws GraphObjectNotExistsException,
            TransactionException, GraphUnmarshallingException, GraphMarshallingException, GraphServiceException;

	GraphEdge addE(GraphEdge edge, GraphTransaction transaction) throws GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphEdgeNotExistsException, GraphMarshallingException, GraphServiceException;

	GraphVertex addV(GraphVertex vertex, GraphTransaction transaction)
			throws GraphObjectNotExistsException, GraphMarshallingException, TransactionException,
			GraphServiceException, GraphUnmarshallingException;
	GraphVertex replaceVByUUID(GraphVertex vertex, GraphTransaction transaction)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphMarshallingException;

	GraphVertex replaceVByVId(GraphVertex vertex, GraphTransaction transaction)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphMarshallingException;

	boolean dropVByUUID(Object vertexId, GraphTransaction transaction) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException;

	boolean dropVById(Object vertexId, GraphTransaction transaction) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException;

	GraphVertex getVById(Object id, List<String> fields, GraphTransaction transaction)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException,
			GraphServiceException;

	GraphVertex getVByUUID(Object id, List<String> fields, GraphTransaction transaction)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException,
			GraphServiceException;

	List<GraphEdge> getEdgesUsingIdMode(Object vertexId, List<String> labelList, List<String> targetVertexLabel,
			GraphTransaction transaction) throws GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphServiceException;

	List<GraphEdge> getEdgesUsingGIdMode(Object vertexId, List<String> labelList, List<String> targetVertexLabel,
			GraphTransaction transaction) throws GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphServiceException;

	GraphEdge getEByUUID(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException, GraphServiceException;

	GraphEdge getEById(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException, GraphServiceException;

	GraphEdge replaceEByUUID(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;

	GraphEdge replaceEById(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;

	GraphEdge patchEById(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;

	GraphEdge patchEByUUID(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;

	boolean dropEByUUID(String edgeId, GraphTransaction transaction)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException;

	boolean dropEById(String edgeId, GraphTransaction transaction)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException;

}
