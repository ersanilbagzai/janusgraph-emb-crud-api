package com.techframer.janusgraph.api.crud.service.impl;

import com.techframer.janusgraph.api.crud.graph.exceptions.*;
import com.techframer.janusgraph.api.crud.repository.GraphRepository;
import com.techframer.janusgraph.api.crud.service.IGraphService;
import com.techframer.janusgraph.api.crud.graph.transaction.GraphTransaction;
import com.techframer.janusgraph.api.crud.graph.transaction.exceptions.TransactionException;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GraphServiceImpl")
public class GraphServiceImpl implements IGraphService {

	@Autowired
	GraphRepository graphRepository;

	@Override
	public GraphVertex addV(GraphVertex vertex, GraphTransaction transaction) throws GraphObjectNotExistsException,
			GraphMarshallingException, TransactionException, GraphServiceException, GraphUnmarshallingException {
		return graphRepository.addV(vertex, transaction);
	}

	@Override
	public GraphVertex replaceVByUUID(GraphVertex vertex, GraphTransaction transaction)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphMarshallingException {
		return graphRepository.replaceVByUUID(vertex, transaction);
	}
	
	@Override
	public GraphVertex replaceVByVId(GraphVertex vertex, GraphTransaction transaction)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphMarshallingException {
		return graphRepository.replaceVByVId(vertex, transaction);
	}

	@Override
	public GraphVertex patchVByVId(GraphVertex vertex, GraphTransaction transaction) throws GraphObjectNotExistsException,
			TransactionException, GraphUnmarshallingException, GraphMarshallingException, GraphServiceException {
		return graphRepository.patchVByVId(vertex, transaction);
	}
	
	@Override
	public GraphVertex patchVByUUID(GraphVertex vertex, GraphTransaction transaction) throws GraphObjectNotExistsException,
			TransactionException, GraphUnmarshallingException, GraphMarshallingException, GraphServiceException {
		return graphRepository.patchVByUUID(vertex, transaction);
	}

	@Override
	public boolean dropVById(Object vertexId, GraphTransaction transaction) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException {
		return graphRepository.dropVById(vertexId, transaction);
	}
	
	@Override
	public boolean dropVByUUID(Object vertexId, GraphTransaction transaction) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException {
		return graphRepository.dropVByUUID(vertexId, transaction);
	}

	@Override
	public GraphEdge addE(GraphEdge edge, GraphTransaction transaction)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException,
			GraphEdgeNotExistsException, GraphMarshallingException, GraphServiceException {
		return graphRepository.addE(edge, transaction);
	}

	@Override
	public GraphEdge replaceEById(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException {
		return graphRepository.replaceEById(edge, transaction);
	}
	
	@Override
	public GraphEdge replaceEByUUID(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException {
		return graphRepository.replaceEByUUID(edge, transaction);
	}

	@Override
	public GraphEdge patchEById(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException {
		return graphRepository.patchEById(edge, transaction);
	}
	
	@Override
	public GraphEdge patchEByUUID(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException {
		return graphRepository.patchEByUUID(edge, transaction);
	}

	@Override
	public boolean dropEById(String edgeId, GraphTransaction transaction)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException {
		return graphRepository.dropEById(edgeId, transaction);
	}
	
	@Override
	public boolean dropEByUUID(String edgeId, GraphTransaction transaction)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException {
		return graphRepository.dropEByUUID(edgeId, transaction);
	}

	@Override
	public GraphVertex getVById(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException,
			TransactionException, GraphUnmarshallingException, GraphServiceException {
		return graphRepository.getVById(id, null, transaction);
	}
	
	@Override
	public GraphVertex getVByUUID(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException,
			TransactionException, GraphUnmarshallingException, GraphServiceException {
		return graphRepository.getVByUUID(id, null, transaction);
	}

	@Override
	public GraphEdge getEById(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException, GraphServiceException {
		return graphRepository.getEById(id, transaction);
	}
	
	@Override
	public GraphEdge getEByUUID(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException, GraphServiceException {
		return graphRepository.getEByUUID(id, transaction);
	}

}
