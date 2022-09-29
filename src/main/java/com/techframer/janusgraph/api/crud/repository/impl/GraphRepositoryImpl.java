package com.techframer.janusgraph.api.crud.repository.impl;

import com.techframer.janusgraph.api.crud.repository.GraphRepository;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.graph.exceptions.*;
import com.techframer.janusgraph.api.crud.graph.connection.constants.GraphConstants;
import com.techframer.janusgraph.api.crud.graph.transaction.GraphTransaction;
import com.techframer.janusgraph.api.crud.graph.transaction.exceptions.TransactionException;
import com.techframer.janusgraph.api.crud.graph.transaction.impl.IGraphTransactionManager;
import com.techframer.janusgraph.api.crud.utils.GraphDaoValidator;
import com.techframer.janusgraph.api.crud.utils.GraphErrorMsg;
import com.techframer.janusgraph.api.crud.utils.UUIDUtils;
import com.techframer.janusgraph.api.crud.utils.enums.ApplicationMessages;
import com.techframer.janusgraph.api.crud.utils.former.impl.TinkerpopGraphformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository("GraphRepositoryImpl")
public class GraphRepositoryImpl implements GraphRepository {

	private static final Logger logger = LogManager.getLogger(GraphRepositoryImpl.class);

	private static final TinkerpopGraphformer graphFormer = new TinkerpopGraphformer();

	@Autowired
	IGraphTransactionManager graphTransactionManager;

	@Override
	public GraphVertex addV(GraphVertex vertex, GraphTransaction transaction)
			throws GraphObjectNotExistsException, TransactionException,
			GraphServiceException, GraphUnmarshallingException {

		logger.debug("Going to add vertex using given data : {}", vertex);

		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in addV:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validateCreateVertexArguments(vertex);

			String newUUID = UUIDUtils.get();
			
			Vertex createdVertex = doCreateVertex(vertex, createThreadedTx, newUUID);
			vertex = graphFormer.unmarshallObject(createdVertex);

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}

		} catch (GraphObjectNotExistsException e) {
			logger.error("GraphObjectNotExistsException in addV : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in addV : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in addV : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}catch (GraphUnmarshallingException e) {
			logger.error("GraphUnmarshallingException in addV : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in addV : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
		return vertex;
	}
	
	@Override
	public GraphVertex replaceVByUUID(GraphVertex vertex, GraphTransaction transaction)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException
			 {
		logger.debug("Going to replaceVertex using given data : {}", vertex);

		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in replaceVByUUID:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try { 
			GraphDaoValidator.validateReplaceVertexArguments(vertex, GraphConstants.GRAPH_METHOD_MODE_GID);

			if (StringUtils.isEmpty(vertex.getgId())) {
				throw new GraphServiceException("Vertex can't be updated as given ID is null", HttpStatus.BAD_REQUEST);
			}

			Vertex updatedVertex = doReplaceVertex(vertex, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_GID);

			GraphVertex graphVertex = GraphVertex.create().from(vertex).withKey(updatedVertex.id()).build();
			
			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}

			return graphVertex;
		} catch (GraphObjectNotExistsException e) {
			logger.error("GraphObjectNotExistsException in replaceVByUUID : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in replaceVByUUID : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in replaceVByUUID : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in replaceVByUUID : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	@Override
	public GraphVertex replaceVByVId(GraphVertex vertex, GraphTransaction transaction)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphMarshallingException {
		logger.debug("Going to replaceVertex using given data : {}", vertex);

		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in replaceVByVId:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validateReplaceVertexArguments(vertex,GraphConstants.GRAPH_METHOD_MODE_ID);

			if (!vertex.getId().isPresent()) {
				throw new GraphServiceException("Vertex can't be updated as given ID is null", HttpStatus.BAD_REQUEST);
			}

			Vertex updatedVertex = doReplaceVertex(vertex, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_ID);

			GraphVertex graphVertex = GraphVertex.create().from(vertex).withKey(updatedVertex.id()).build();

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}
			return graphVertex;
		} catch (GraphObjectNotExistsException e) {
			logger.error("GraphObjectNotExistsException in replaceVByVId : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in replaceVByVId : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in replaceVByVId : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in replaceVByVId : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	@Override
	public GraphVertex patchVByUUID(GraphVertex vertex, GraphTransaction transaction)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException,
			GraphMarshallingException, GraphServiceException {
		logger.debug("Going to patchVertex using given data : {}", vertex);
		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in patchVByUUID:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validatePatchVertexArguments(vertex, GraphConstants.GRAPH_METHOD_MODE_GID);
			Vertex updatedVertex = doPatchVertex(vertex, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_GID);
			
			
			GraphVertex graphVertex = GraphVertex.create().from(vertex).withKey(updatedVertex.id()).build();

			logger.info("graphVertex :: {}",graphVertex);
			
			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}
			return graphVertex;
		} catch (GraphObjectNotExistsException e) {
			logger.error("GraphObjectNotExistsException in patchVByUUID : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in patchVByUUID : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in patchVByUUID : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in patchVByUUID : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}
	
	@Override
	public GraphVertex patchVByVId(GraphVertex vertex, GraphTransaction transaction)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException,
			GraphMarshallingException, GraphServiceException {
		logger.debug("Going to patchVertex using given data : {}", vertex);
		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in patchVByVId:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validatePatchVertexArguments(vertex, GraphConstants.GRAPH_METHOD_MODE_ID);
			Vertex updatedVertex = doPatchVertex(vertex, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_ID);
			
			
			GraphVertex graphVertex = GraphVertex.create().from(vertex).withKey(updatedVertex.id()).build();
			logger.info("graphVertex :: {}",graphVertex);
			
		
			
			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}
			return graphVertex;
		} catch (GraphObjectNotExistsException e) {
			logger.error("GraphObjectNotExistsException in patchVByVId : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in patchVByVId : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in patchVByVId : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in patchVByVId : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	@Override
	public boolean dropVByUUID(Object vertexId, GraphTransaction transaction) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException {
		logger.debug("Going to dropVertex of ID : {}", vertexId);
		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in dropVByUUID:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validateDropVertexArguments(vertexId);
			GraphVertex graphVertex = getVByUUID(vertexId,null, transaction);

			logger.debug("Going to check if the vertex with id : {} contains edges connected before dropping it.",
					vertexId);
			Stream<GraphEdge> relationships = retrieveEdges(graphVertex, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_GID);

			if (relationships.count() > 0) {
				logger.info("vertex with id : {} contains edges, thus can't drop this vertex", vertexId);
				throw new GraphServiceException(
						"Attempt to delete vertex with id " + vertexId + " which has incident edges.",
						HttpStatus.BAD_REQUEST);
			}

			logger.debug("All required checks checked to delete vertex of ID : {}", vertexId);
			boolean doDropVertex = doDropVertex(vertexId, GraphConstants.GRAPH_METHOD_MODE_GID, createThreadedTx);

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}

			return doDropVertex;
		} catch (GraphObjectNotExistsException | GraphUnmarshallingException e) {
			logger.error("GraphObjectNotExistsException/GraphUnmarshallingException in dropVByUUID : {} ",
					ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in dropVByUUID : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in dropVByUUID : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in dropVByUUID : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	@Override
	public boolean dropVById(Object vertexId, GraphTransaction transaction) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException {
		logger.debug("Going to dropVertex of ID : {}", vertexId);
		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in dropVById:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validateDropVertexArguments(vertexId);
			GraphVertex graphVertex = getVById(vertexId,null, transaction);

			logger.debug("Going to check if the vertex with id : {} contains edges connected before droping it.",
					vertexId);
			Stream<GraphEdge> relationships = retrieveEdges(graphVertex, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_ID);

			if (relationships.count() > 0) {
				logger.info("vertex with id : {} contains edges, thus can't drop this vertex", vertexId);
				throw new GraphServiceException(
						"Attempt to delete vertex with id " + vertexId + " which has incident edges.",
						HttpStatus.BAD_REQUEST);
			}

			logger.debug("All required checkes checked to delete vertex of ID : {}", vertexId);
			boolean doDropVertex = doDropVertex(vertexId, GraphConstants.GRAPH_METHOD_MODE_ID, createThreadedTx);

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}

			return doDropVertex;
		} catch (GraphObjectNotExistsException | GraphUnmarshallingException e) {
			logger.error("GraphObjectNotExistsException/GraphUnmarshallingException in dropVById : {} ",
					ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in dropVById : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in dropVById : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in dropVById : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}
	
	@Override
	public GraphEdge addE(GraphEdge edge, GraphTransaction transaction)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException,
			GraphEdgeNotExistsException, GraphMarshallingException, GraphServiceException {
		logger.debug("Going to addE for given data : {}", edge);
		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in addE:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			//GraphDaoValidator.validateCreateEdgeArguments(edge);
			String newUUID = UUID.randomUUID().toString();
			Edge writeEdge = doCreateEdge(edge, createThreadedTx, newUUID);

			GraphEdge unmarshallRelationship = graphFormer.unmarshallRelationship(writeEdge);

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}

			return unmarshallRelationship;

		} catch (GraphObjectNotExistsException | GraphEdgeNotExistsException | GraphUnmarshallingException e) {
			logger.error(
					"GraphObjectNotExistsException/GraphEdgeNotExistsException/GraphUnmarshallingException in createEdge : {} ",
					ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in addE : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in addE : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in addE : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	private void removeGIdFromGraphVertex(GraphVertex vertex) throws GraphServiceException {
		if (vertex == null || vertex.getProperties() == null
				|| vertex.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) == null) {
			logger.error("GraphServiceException : Given Vertex does not have gId");
			throw new GraphServiceException(GraphErrorMsg.MESSAGE_GIVEN_VERTEX_DOES_NOT_HAVE_GID);
		}
		vertex.getProperties().remove(GraphConstants.GRAPH_UUID_COLUMN_NAME);
	}

	private void replaceUUIDInGraphEdge(GraphEdge edge) throws GraphServiceException {
		if (edge.getSource() == null || edge.getSource().getProperties() == null
				|| edge.getSource().getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) == null) {
			logger.error("GraphServiceException : Given Vertex does not have gId");
			throw new GraphServiceException(GraphErrorMsg.MESSAGE_GIVEN_VERTEX_DOES_NOT_HAVE_GID);
		}
		edge.getSource()
				.setStringId(edge.getSource().getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString());

		if (edge.getTarget() == null || edge.getTarget().getProperties() == null
				|| edge.getTarget().getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) == null) {
			logger.error("GraphServiceException : Given Vertex does not have gId");
			throw new GraphServiceException(GraphErrorMsg.MESSAGE_GIVEN_VERTEX_DOES_NOT_HAVE_GID);
		}
		edge.getTarget()
				.setStringId(edge.getTarget().getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString());

		if (edge.getProperties() == null || edge.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) == null) {
			logger.error("GraphServiceException : Given edge does not have gId");
			throw new GraphServiceException(GraphErrorMsg.MESSAGE_GIVEN_EDGE_DOES_NOT_HAVE_GID);
		}
		edge.setStringId(edge.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString());
	}
	
	@Override
	public GraphEdge replaceEById(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			 GraphUnmarshallingException, TransactionException, GraphServiceException {
		logger.debug("Going to replaceEdge using given data : {}", edge);

		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in replaceEById:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validateReplaceEdgeArguments(edge, GraphConstants.GRAPH_METHOD_MODE_ID);
			Edge doReplaceEdge = doReplaceEdge(edge, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_ID);

			GraphEdge unmarshallRelationship = graphFormer.unmarshallRelationship(doReplaceEdge);

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}

			return unmarshallRelationship;
		} catch (GraphEdgeNotExistsException e) {
			logger.error("GraphEdgeNotExistsException in replaceEById : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in replaceEById : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in replaceEById : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in replaceEById : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}

	}

	@Override
	public GraphEdge replaceEByUUID(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphServiceException {
		logger.debug("Going to replaceEdge using given data : {}", edge);

		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in replaceEByUUID:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validateReplaceEdgeArguments(edge, GraphConstants.GRAPH_METHOD_MODE_GID);
			Edge doReplaceEdge = doReplaceEdge(edge, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_GID);

			GraphEdge unmarshallRelationship = graphFormer.unmarshallRelationship(doReplaceEdge);

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}

			return unmarshallRelationship;
		} catch (GraphEdgeNotExistsException e) {
			logger.error("GraphEdgeNotExistsException in replaceEByUUID : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in replaceEByUUID : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in replaceEByUUID : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in replaceEByUUID : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}

	}
	
	@Override
	public GraphEdge patchEById(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			 GraphUnmarshallingException, TransactionException, GraphServiceException {
		logger.debug("Going to patchEdge using given data : {}", edge);
		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in patchEById:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validatePatchEdgeArguments(edge, GraphConstants.GRAPH_METHOD_MODE_ID);
			Edge doPatchEdge = doPatchEdge(edge, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_ID);

			GraphEdge unmarshallRelationship = graphFormer.unmarshallRelationship(doPatchEdge);
			
			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}

			return unmarshallRelationship;
		} catch (GraphEdgeNotExistsException e) {
			logger.error("GraphEdgeNotExistsException in patchEById : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in patchEById : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphServiceException in patchEById : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in patchEById : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	@Override
	public GraphEdge patchEByUUID(GraphEdge edge, GraphTransaction transaction) throws GraphEdgeNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphServiceException {
		logger.debug("Going to patchEdge using given data : {}", edge);
		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in patchEByUUID:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			GraphDaoValidator.validatePatchEdgeArguments(edge, GraphConstants.GRAPH_METHOD_MODE_GID);
			Edge doPatchEdge = doPatchEdge(edge, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_GID);

			GraphEdge unmarshallRelationship = graphFormer.unmarshallRelationship(doPatchEdge);

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}

			return unmarshallRelationship;
		} catch (GraphEdgeNotExistsException e) {
			logger.error("GraphEdgeNotExistsException in patchEByUUID : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in patchEByUUID : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphServiceException in patchEByUUID : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in patchEByUUID : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}
	
	@Override
	public boolean dropEByUUID(String edgeId, GraphTransaction transaction)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException {
		logger.debug("Going to dropEdge of ID : {}", edgeId);

		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in createVertex:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}
		try {
			GraphDaoValidator.validateDropEdgeArguments(edgeId);

			doRemoveEdge(edgeId, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_GID);

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}
			return true;
		} catch (GraphEdgeNotExistsException e) {
			logger.error("GraphEdgeNotExistsException in createVertex : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in createVertex : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in createVertex : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in createVertex : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}
	
	@Override
	public boolean dropEById(String edgeId, GraphTransaction transaction)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException {
		logger.debug("Going to dropEdge of ID : {}", edgeId);

		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in createVertex:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}
		try {
			GraphDaoValidator.validateDropEdgeArguments(edgeId);

			doRemoveEdge(edgeId, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_ID);

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}
			return true;
		} catch (GraphEdgeNotExistsException e) {
			logger.error("GraphEdgeNotExistsException in createVertex : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in createVertex : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in createVertex : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in createVertex : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	/**
	 * Do remove edge.
	 *
	 * @param edgeId           the edge id
	 * @param createThreadedTx the create threaded tx
	 * @throws GraphEdgeNotExistsException the graph edge not exists exception
	 * @throws GraphServiceException 
	 */
	private void doRemoveEdge(String edgeId, Graph createThreadedTx, String mode)
			throws GraphEdgeNotExistsException, GraphServiceException {
		Iterator<Edge> edgeToDelete = null;
		if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
			edgeToDelete = createThreadedTx.edges(edgeId);
		} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
			edgeToDelete = createThreadedTx.traversal().E().has(GraphConstants.GRAPH_UUID_COLUMN_NAME, edgeId);
		} else {
			logger.debug("Given mode of ID is not supported : mode : {}", mode);
			throw new GraphServiceException("Given mode of ID is not supported");
		}

		if (!edgeToDelete.hasNext()) {
			throw new GraphEdgeNotExistsException();
		}

		edgeToDelete.next().remove();
	}

	@Override
	public GraphVertex getVById(Object id, List<String> fields, GraphTransaction transaction) throws GraphObjectNotExistsException,
            TransactionException, GraphUnmarshallingException, GraphServiceException {
		logger.debug("Going to getVertex of ID : {}", id);
		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in getVertex:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			if (id == null) {
				logger.error("GraphServiceException : Given Vertex ID is null");
				throw new GraphServiceException(GraphErrorMsg.VERTEX_ID_IS_NULL);
			}

			Optional<GraphVertex> retrieveObject = retrieveVertex(id,fields, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_ID);

			if (retrieveObject.isPresent()) {
				GraphVertex graphVertex = retrieveObject.get();
				
				if (transaction == null) {
					graphTransactionManager.commitTransaction(createThreadedTx);
				}

				return graphVertex;
			} else {
				logger.debug("Vertex with given ID : {} does not exist .", id);
				throw new GraphObjectNotExistsException();
			}

		} catch (GraphObjectNotExistsException e) {
			createThreadedTx.tx().rollback();
			logger.error("GraphObjectNotExistsException in getVertex : {}", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in createVertex : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in getVertex : {}", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			createThreadedTx.tx().rollback();
			logger.error("Exception in getVertex Exception : {}", ExceptionUtils.getStackTrace(ex));
			throw ex;
		}
	}

	@Override
	public GraphVertex getVByUUID(Object id, List<String> fields, GraphTransaction transaction) throws GraphObjectNotExistsException,
            TransactionException, GraphUnmarshallingException, GraphServiceException {
		logger.debug("Going to getVertex of ID : {}", id);
		Graph createThreadedTx = null;
		try {
			createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);
		} catch (TransactionException e) {
			logger.error("Exception in getVertex:getThreadedTransaction : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}

		try {
			if (id == null) {
				logger.error("GraphServiceException : Given Vertex ID is null");
				throw new GraphServiceException(GraphErrorMsg.VERTEX_ID_IS_NULL);
			}

			Optional<GraphVertex> retrieveObject = retrieveVertex(id,fields, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_GID);

			if (retrieveObject.isPresent()) {
				GraphVertex graphVertex = retrieveObject.get();
				
				if (transaction == null) {
					graphTransactionManager.commitTransaction(createThreadedTx);
				}

				return graphVertex;
			} else {
				logger.debug("Vertex with given ID : {} does not exist .", id);
				throw new GraphObjectNotExistsException();
			}

		} catch (GraphObjectNotExistsException e) {
			createThreadedTx.tx().rollback();
			logger.error("GraphObjectNotExistsException in getVertex : {}", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in createVertex : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in getVertex : {}", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			createThreadedTx.tx().rollback();
			logger.error("Exception in getVertex Exception : {}", ExceptionUtils.getStackTrace(ex));
			throw ex;
		}
	}
	
	@Override
	public GraphEdge getEByUUID(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException, GraphServiceException {
		Graph createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);

		try {
			Optional<GraphEdge> retrievedRelationship = retrieveEdge(id, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_GID);

			if (retrievedRelationship.isPresent()) {
				if (transaction == null) {
					graphTransactionManager.commitTransaction(createThreadedTx);
				}
				GraphEdge graphEdge = retrievedRelationship.get();
				
				return graphEdge;
			} else {
				throw new GraphEdgeNotExistsException();
			}

		} catch (GraphEdgeNotExistsException e) {
			logger.error("GraphEdgeNotExistsException in getEdge Exception : {}", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphUnmarshallingException e) {
			logger.error("GraphUnmarshallingException in getEdge Exception : {}", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in getEdge : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in getEdge Exception : {}", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in getEdge Exception : {}", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}
	
	@Override
	public GraphEdge getEById(Object id, GraphTransaction transaction) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException, GraphServiceException {
		Graph createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);

		try {
			Optional<GraphEdge> retrievedRelationship = retrieveEdge(id, createThreadedTx, GraphConstants.GRAPH_METHOD_MODE_ID);

			if (retrievedRelationship.isPresent()) {
				if (transaction == null) {
					graphTransactionManager.commitTransaction(createThreadedTx);
				}
				GraphEdge graphEdge = retrievedRelationship.get();
				
				return graphEdge;
			} else {
				throw new GraphEdgeNotExistsException();
			}

		} catch (GraphEdgeNotExistsException e) {
			logger.error("GraphEdgeNotExistsException in getEdge Exception : {}", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphUnmarshallingException e) {
			logger.error("GraphUnmarshallingException in getEdge Exception : {}", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (GraphServiceException e) {
			logger.error("GraphServiceException in getEdge : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in getEdge Exception : {}", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in getEdge Exception : {}", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	/**
	 * Do create vertex.
	 *
	 * @param vertexToCreate the vertex to create
	 * @param graphInstance  the graph instance
	 * @return the vertex
	 * @throws GraphObjectNotExistsException the graph object not exists exception
	 * @throws GraphServiceException 
	 */
	private Vertex doCreateVertex(GraphVertex vertexToCreate, Graph graphInstance, String newUUID)
			throws GraphObjectNotExistsException, GraphServiceException {
		final Vertex createdVertex;

		Optional<Object> id = vertexToCreate.getId();
		if(id.isPresent() && !org.apache.commons.lang.StringUtils.isBlank(vertexToCreate.getgId())) {
			Iterator<Vertex> vertexIter = graphInstance.traversal().V(id.get()).has(GraphConstants.GRAPH_UUID_COLUMN_NAME, vertexToCreate.getgId());
			if (vertexIter!=null && vertexIter.hasNext()) {
				logger.debug("Vertex with given ID : {} and gid:{} is available", id, vertexToCreate.getgId());
				createdVertex = vertexIter.next();
			} else {
				logger.debug("Vertex with given id : {} and gid:{} does not exist", id, vertexToCreate.getgId());
				throw new GraphObjectNotExistsException();
			}
		}else if(id.isPresent()) {
			createdVertex = getVertexFromGraphById(id.get(), graphInstance);
		}else if(!org.apache.commons.lang.StringUtils.isBlank(vertexToCreate.getgId())) {
			createdVertex =  getVertexFromGraphByGId(vertexToCreate.getgId(), graphInstance);
		}else {
			createdVertex = graphInstance.addVertex(vertexToCreate.getLabel());
			updateUUIDInVertex(createdVertex, newUUID);
			logger.debug("Vertex created with ID : {}", createdVertex.id());
		}
		updateVertexProperties(vertexToCreate, createdVertex);
		return createdVertex;
	}

	/**
	 * Update UUID in vertex.
	 *
	 * @param createdVertex
	 *            the created vertex
	 * @param newUUID 
	 */
	private void updateUUIDInVertex(Vertex createdVertex, String newUUID) {
		createdVertex.property(GraphConstants.GRAPH_UUID_COLUMN_NAME, newUUID);
	}

	/**
	 * Update UUID in edge.
	 *
	 * @param createdEdge
	 *            the created edge
	 * @param newUUID
	 *            the new UUID
	 */
	private void updateUUIDInEdge(Edge createdEdge, String newUUID) {
		createdEdge.property(GraphConstants.GRAPH_UUID_COLUMN_NAME, newUUID);
	}
	
	/**
	 * Update vertex properties.
	 *
	 * @param vertexToCreate the vertex to create
	 * @param createdVertex  the created vertex
	 * @throws GraphServiceException 
	 */
	private void updateVertexProperties(GraphVertex vertexToCreate, final Vertex createdVertex)
			throws GraphServiceException {
		if (vertexToCreate.getProperties() != null) {
			doUpdateVertexProperties(vertexToCreate, createdVertex);
			logger.debug("properties for vertex ID : {}, properties : {} , updated/replaced/patched successfully",
					createdVertex.id(), vertexToCreate.getProperties());
		} else {
			logger.debug("properties not provided for vertex ID : {}, properties : null", createdVertex.id());
		}
	}

	/**
	 * Do update vertex properties.
	 *
	 * @param vertexToCreate the vertex to create
	 * @param createdVertex  the created vertex
	 * @throws GraphServiceException 
	 */
	private void doUpdateVertexProperties(GraphVertex vertexToCreate, final Vertex createdVertex)
			throws GraphServiceException {
		for (Map.Entry<String, Object> property : vertexToCreate.getProperties().entrySet()) {

			if (!GraphConstants.GRAPH_UUID_COLUMN_NAME.equalsIgnoreCase(property.getKey())) {
				doUpdateVProperties(createdVertex, property);
			}

		}
	}

	/**
	 * Do update V properties.
	 *
	 * @param createdVertex the created vertex
	 * @param property the property
	 * @throws GraphServiceException 
	 */
	private void doUpdateVProperties(final Vertex createdVertex, Map.Entry<String, Object> property)
			throws GraphServiceException {
		if (property.getValue() == null) {
			removePropertyInVertex(property.getKey(), createdVertex);
		} else {
			if (property.getValue() instanceof List) {
				for (Object subPropertyValue : (List<?>) property.getValue()) {
					createdVertex.property(VertexProperty.Cardinality.list, property.getKey(), subPropertyValue);
				}
			} else if (property.getValue() instanceof Set) {
				for (Object subPropertyValue : (Set<?>) property.getValue()) {
					createdVertex.property(VertexProperty.Cardinality.set, property.getKey(), subPropertyValue);
				}
			} else {
				createdVertex.property(property.getKey(), property.getValue());
			}
		}
	}

	private void removePropertyInVertex(String key, Vertex createdVertex)
			throws GraphServiceException {
		VertexProperty<Object> property = null;
		try {
			property = createdVertex.properties(key).next();
			property.remove();
		} catch (Exception e) {
			logger.error("Exception in removePropertyInVertex : {}", ExceptionUtils.getStackTrace(e));
			throw new GraphServiceException(
					"can not update property key : " + key + " for verterx : {} " + createdVertex.id());
		}
	}

	/**
	 * Checks for label.
	 *
	 * @param query the query
	 * @param type  the type
	 * @return the graph traversal
	 */
	public GraphTraversal<?, ?> hasLabel(GraphTraversal<?, ?> query, Object type) {
		return query.hasLabel(type.toString());
	}

	/**
	 * Retrieve edges.
	 *
	 * @param source        the source
	 * @param graphInstance the graph instance
	 * @return the stream
	 * @throws GraphObjectNotExistsException the graph object not exists exception
	 * @throws GraphServiceException 
	 */
	public Stream<GraphEdge> retrieveEdges(GraphVertex source, Graph graphInstance, String mode)
			throws GraphObjectNotExistsException, GraphServiceException {
		logger.debug("Going to retrieveEdges for given vertex data : {}", source);

		final Vertex sourceVertex;
		Optional<Object> id = source.getId();

		try {

			if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
				if (id.isPresent()) {
					sourceVertex = graphInstance.vertices(id.get()).next();
				} else {
					logger.debug("Vertex with given ID : null does not exist.");
					throw new GraphObjectNotExistsException();
				}
			} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
				if (source.getgId()!=null) {
					sourceVertex = graphInstance.traversal().V().has(GraphConstants.GRAPH_UUID_COLUMN_NAME, source.getgId())
							.next();
				} else {
					logger.debug("Vertex with given ID : null does not exist.");
					throw new GraphObjectNotExistsException();
				}
			}else{
				logger.debug("Given mode of ID is not supported : mode : {}", mode);
				throw new GraphServiceException("Given mode of ID is not supported");
			}
			
			
		} catch (NoSuchElementException e) {
			if (id.isPresent()) {
				logger.debug("Vertex with given ID : {} does not exist ", id.get());
			}
			throw new GraphObjectNotExistsException();
		}

		final Iterator<Edge> edges = sourceVertex.edges(Direction.BOTH);
		final Iterator<GraphEdge> relIter = new Iterator<GraphEdge>() {

			private GraphEdge next;

			@Override
			public boolean hasNext() {
				while (edges.hasNext()) {
					try {
						next = graphFormer.unmarshallRelationship(edges.next());
						return true;
					} catch (GraphUnmarshallingException e) {
						logger.warn(String.format(GraphConstants.GRAPH_UNMARSHALLING_EXCEPTION_STRING_FORMATER,
								ApplicationMessages.GRAPH_ABSTRACT_TINKERPOP_CHAMP_GRAPH_WARN,
								"Failed to unmarshall tinkerpop edge during query, returning partial results : {}"),
								ExceptionUtils.getStackTrace(e));
					}
				}

				next = null;
				return false;
			}

			@Override
			public GraphEdge next() {
				if (next == null) {
					throw new NoSuchElementException();
				}

				return next;
			}
		};

		return StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(relIter, Spliterator.ORDERED | Spliterator.NONNULL), false);
	}

	/**
	 * Retrieve edges.
	 *
	 * @param source        the source
	 * @param graphInstance the graph instance
	 * @param edgeLabel     the edge label
	 * @return the stream
	 * @throws GraphObjectNotExistsException the graph object not exists exception
	 * @throws GraphServiceException 
	 */
	public Stream<GraphEdge> retrieveEdges(GraphVertex source, Graph graphInstance, String mode, String... edgeLabel)
			throws GraphObjectNotExistsException, GraphServiceException {
		logger.debug(
				"Going to retrieveEdges(GraphVertex source,Graph graphInstance, String... edgeLabel) for given vertex data : {}",
				source);
		final Vertex sourceVertex;

		Optional<Object> id = source.getId();

		try {
			if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
				if (id.isPresent()) {
					sourceVertex = graphInstance.vertices(id.get()).next();
					logger.debug("Vertex with given ID : {} was successfully retrieved", id.get());
				} else {
					logger.debug("Vertex with given ID : null does not exist ");
					throw new GraphObjectNotExistsException();
				}
			} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
				if (source.getgId()!=null) {
					sourceVertex = graphInstance.traversal().V().has(GraphConstants.GRAPH_UUID_COLUMN_NAME, source.getgId())
							.next();
					logger.debug("Vertex with given ID : {} was successfully retrieved", source.getgId());
				} else {
					logger.debug("Vertex with given ID : null does not exist ");
					throw new GraphObjectNotExistsException();
				}
			}else{
				logger.debug("Given mode of ID is not supported : mode : {}", mode);
				throw new GraphServiceException("Given mode of ID is not supported");
			}

		} catch (NoSuchElementException e) {
			if (id.isPresent()) {
				logger.debug("Vertex with given ID : {} does not exist. ", id.get());
			}
			throw new GraphObjectNotExistsException();
		}

		final Iterator<Edge> edges = sourceVertex.edges(Direction.BOTH, edgeLabel);
		final Iterator<GraphEdge> relIter = new Iterator<GraphEdge>() {

			private GraphEdge next;

			@Override
			public boolean hasNext() {
				while (edges.hasNext()) {
					try {
						next = graphFormer.unmarshallRelationship(edges.next());
						return true;
					} catch (GraphUnmarshallingException e) {
						logger.warn(String.format(GraphConstants.GRAPH_UNMARSHALLING_EXCEPTION_STRING_FORMATER,
								ApplicationMessages.GRAPH_ABSTRACT_TINKERPOP_CHAMP_GRAPH_WARN,
								"Failed to unmarshall tinkerpop edge during query, returning partial results : {}"),
								ExceptionUtils.getStackTrace(e));
					}
				}

				next = null;
				return false;
			}

			@Override
			public GraphEdge next() {
				if (next == null) {
					throw new NoSuchElementException();
				}

				return next;
			}
		};

		return StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(relIter, Spliterator.ORDERED | Spliterator.NONNULL), false);
	}

	/**
	 * Retrieve vertex.
	 *
	 * @param key           the key
	 * @param graphInstance the graph instance
	 * @return the optional
	 * @throws GraphUnmarshallingException the graph unmarshalling exception
	 */
	public Optional<GraphVertex> retrieveVertex(Object key,List<String> fields, Graph graphInstance, String mode) throws GraphUnmarshallingException {
		logger.debug("Going to retrieveEdges(Object key, Graph graphInstance) for given vertex id : {}", key);

		Iterator<Vertex> vertices = null;
		
		if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
			vertices = graphInstance.vertices(key);
		} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
			vertices = graphInstance.traversal().V().has(GraphConstants.GRAPH_UUID_COLUMN_NAME, key);
		}
		
		Optional<GraphVertex> optionalObject;

		if(vertices == null){
			optionalObject = Optional.empty();
			return optionalObject;
		}
		
		if (!vertices.hasNext()) {
			optionalObject = Optional.empty();
		} else {
			if (fields!=null && !fields.isEmpty()) {
			optionalObject = Optional.of(graphFormer.unmarshallObject(vertices.next(),fields));
			}else {
				optionalObject = Optional.of(graphFormer.unmarshallObject(vertices.next()));
			}
		}
		
		return optionalObject;
	}


	/**
	 * Do replace vertex.
	 *
	 * @param object        the object
	 * @param graphInstance the graph instance
	 * @return the vertex
	 * @throws GraphObjectNotExistsException the graph object not exists exception
	 * @throws GraphServiceException 
	 */
	private Vertex doReplaceVertex(GraphVertex object, Graph graphInstance, String mode) throws GraphObjectNotExistsException, GraphServiceException {
		logger.debug("Going to doReplaceVertex for given vertex data : {}", object);
		Vertex vertex = null;

		if(GraphConstants.GRAPH_METHOD_MODE_ID.equalsIgnoreCase(mode)) {
			Optional<Object> id = object.getId();
			if (id.isPresent()) {
				vertex = getVertexFromGraphById(id.get(), graphInstance);
			}else {
				logger.debug("Vertex with given ID : null does not exist");
				throw new GraphObjectNotExistsException();
			}
		}else if(GraphConstants.GRAPH_METHOD_MODE_GID.equalsIgnoreCase(mode)) {
			if (!org.apache.commons.lang.StringUtils.isBlank(object.getgId())) {
				vertex =  getVertexFromGraphByGId(object.getgId(), graphInstance);
			}else {
				logger.debug("Vertex with given ID : null does not exist");
				throw new GraphObjectNotExistsException();
			}
		}else {
			throw new GraphServiceException("Mode must be available");
		}
		
		// clear all the existing properties
		Iterator<VertexProperty<Object>> it = vertex.properties();
		while (it.hasNext()) {
			it.next().remove();
		}

		updateUUIDInVertex(vertex, object.getgId());
		updateVertexProperties(object, vertex);

		return vertex;
	}

	private Vertex getVertexFromGraphById(Object id, Graph graphInstance) throws GraphObjectNotExistsException {
		Iterator<Vertex> vertexIter = graphInstance.vertices(id);
		if (vertexIter!=null && vertexIter.hasNext()) {
			logger.debug("Vertex with given ID : {} is available", id);
			return vertexIter.next();
		} else {
			logger.debug("Vertex with given id : {} does not exist", id);
			throw new GraphObjectNotExistsException();
		}
	}
	
	private Vertex getVertexFromGraphByGId(String gId, Graph graphInstance) throws GraphObjectNotExistsException {
		Iterator<Vertex> vertexIter = graphInstance.traversal().V().has(GraphConstants.GRAPH_UUID_COLUMN_NAME, gId);
		if (vertexIter!=null && vertexIter.hasNext()) {
			logger.debug("Vertex with given ID : {} is available", gId);
			return vertexIter.next();
		} else {
			logger.debug("Vertex with given id : {} does not exist", gId);
			throw new GraphObjectNotExistsException();
		}
	}
	
	/**
	 * Do patch vertex.
	 *
	 * @param object        the object
	 * @param graphInstance the graph instance
	 * @return the vertex
	 * @throws GraphObjectNotExistsException the graph object not exists exception
	 * @throws GraphServiceException 
	 */
	private Vertex doPatchVertex(GraphVertex object, Graph graphInstance, String mode) throws GraphObjectNotExistsException, GraphServiceException {
		logger.debug("Going to doPatchVertex for given vertex data : {}", object);
		Vertex vertex = null;


		if(GraphConstants.GRAPH_METHOD_MODE_ID.equalsIgnoreCase(mode)) {
			Optional<Object> id = object.getId();
			if (id.isPresent()) {
				vertex = getVertexFromGraphById(id.get(), graphInstance);
			}else {
				logger.debug("Vertex with given ID : null does not exist");
				throw new GraphObjectNotExistsException();
			}
		}else if(GraphConstants.GRAPH_METHOD_MODE_GID.equalsIgnoreCase(mode)) {
			if (!org.apache.commons.lang.StringUtils.isBlank(object.getgId())) {
				vertex =  getVertexFromGraphByGId(object.getgId(), graphInstance);
			}else {
				logger.debug("Vertex with given ID : null does not exist");
				throw new GraphObjectNotExistsException();
			}
		}else {
			throw new GraphServiceException("Mode must be available");
		}

		updateVertexProperties(object, vertex);

		return vertex;
	}

	/**
	 * Do drop vertex.
	 *
	 * @param id the id
	 * @return true, if successful
	 * @throws GraphObjectNotExistsException the graph object not exists exception
	 * @throws TransactionException     the graph transaction exception
	 */
	private boolean doDropVertex(Object id, String mode, Graph createThreadedTx)
			throws GraphObjectNotExistsException, TransactionException, GraphServiceException {
		logger.debug("Going to doDropVertex for given vertex ID : {}", id);
		//Graph createThreadedTx = graphTransactionManager.getThreadedTransaction();

		try {

			Iterator<Vertex> vertex = null;

			if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
				vertex = createThreadedTx.vertices(id);
			} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
				vertex = createThreadedTx.traversal().V().has(GraphConstants.GRAPH_UUID_COLUMN_NAME, id);
			} else {
				logger.debug("Given mode of ID is not supported : mode : {}", mode);
				throw new GraphServiceException("Given mode of ID is not supported");
			}

			if (vertex == null) {
				throw new GraphObjectNotExistsException();
			}

			if (!vertex.hasNext()) {

				//createThreadedTx.tx().rollback();

				logger.debug("Vertex with given ID : {} does not exist", id);

				throw new GraphObjectNotExistsException();
			}

			vertex.next().remove();

			//graphTransactionManager.commitTransaction(createThreadedTx);
			return true;
		} catch (GraphObjectNotExistsException e) {
			logger.error("GraphObjectNotExistsException in doDropVertex : {} ", ExceptionUtils.getStackTrace(e));
			//createThreadedTx.tx().rollback();
			throw e;
		}/* catch (TransactionException e) {
			logger.error("GraphTransactionException in doDropVertex : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		}*/ catch (Exception ex) {
			logger.error("Exception in doDropVertex : {} ", ExceptionUtils.getStackTrace(ex));
			//createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	/**
	 * Do create edge.
	 *
	 * @param relationship  the relationship
	 * @param graphInstance the graph instance
	 * @return the edge
	 * @throws GraphObjectNotExistsException the graph object not exists exception
	 * @throws GraphEdgeNotExistsException   the graph edge not exists exception
	 * @throws GraphServiceException 
	 */
	private Edge doCreateEdge(GraphEdge relationship, Graph graphInstance, String newUUID)
			throws GraphObjectNotExistsException, GraphEdgeNotExistsException, GraphServiceException {

		String sourceUUID = relationship.getSource().getgId();
		String destUUID = relationship.getTarget().getgId();
		
		final Vertex source = doCreateVertex(relationship.getSource(), graphInstance, sourceUUID);
		final Vertex target = doCreateVertex(relationship.getTarget(), graphInstance, destUUID);

		final Edge edge;
		Iterator<Edge> edgeIter; 
		
		Optional<Object> id = relationship.getId();
		if(id.isPresent() && org.apache.commons.lang.StringUtils.isNotBlank(relationship.getgId())) {
			edgeIter = graphInstance.traversal().E(id.get()).has(GraphConstants.GRAPH_UUID_COLUMN_NAME, relationship.getgId());
			if (edgeIter.hasNext()) {
				edge = edgeIter.next();
				logger.debug("Edge with given ID : {} and gid:{} is already available. Going to update it", id.get(), relationship.getgId());
			} else {
				logger.debug("Edge with given ID : {} does not exist.  ", id.get());
				throw new GraphEdgeNotExistsException();
			}
		}else if (id.isPresent()) {
			edgeIter = graphInstance.edges(id.get());

			if (edgeIter.hasNext()) {
				edge = edgeIter.next();
				logger.debug("Edge with given ID : {} was already available. Going to update it", id.get());
			} else {
				logger.debug("Edge with given ID : {} does not exist.  ", id.get());
				throw new GraphEdgeNotExistsException();
			}
		}else if (StringUtils.isNotBlank(relationship.getgId())) {
			edgeIter = graphInstance.traversal().E().has(GraphConstants.GRAPH_UUID_COLUMN_NAME, relationship.getgId());

			if (edgeIter.hasNext()) {
				edge = edgeIter.next();
		//		logger.debug("Edge with given ID : {} was already available. Going to update it", id.get());
			} else {
		//		logger.debug("Edge with given ID : {} does not exist.  ", id.get());
				throw new GraphEdgeNotExistsException();
			}
		} else {
			edge = source.addEdge(relationship.getLabel(), target);
			updateUUIDInEdge(edge,newUUID);
			logger.debug("Edge created with ID : {}", edge.id());
		}

		updateEdgeProperties(relationship, edge);

		return edge;
	}

	/**
	 * Update edge properties.
	 *
	 * @param relationship the relationship
	 * @param edge the edge
	 */
	private void updateEdgeProperties(GraphEdge relationship, final Edge edge) {
		if (relationship.getProperties() != null) {
			logger.debug("updating properties : {}, of Edge with ID : {} ", relationship.getProperties(), edge.id());
			doUpdateEdgeProperties(relationship, edge);
		} else {
			logger.debug("Edge with ID : {}, properties : null ", edge.id());
		}
	}

	/**
	 * Do update edge properties.
	 *
	 * @param relationship the relationship
	 * @param edge the edge
	 */
	private void doUpdateEdgeProperties(GraphEdge relationship, final Edge edge) {
		for (Map.Entry<String, Object> property : relationship.getProperties().entrySet()) {
			if (!GraphConstants.GRAPH_UUID_COLUMN_NAME.equalsIgnoreCase(property.getKey())) {
				edge.property(property.getKey(), property.getValue());
			}
		}
	}

	/**
	 * Retrieve edge.
	 *
	 * @param key           the key
	 * @param graphInstance the graph instance
	 * @return the optional
	 * @throws GraphUnmarshallingException the graph unmarshalling exception
	 * @throws GraphServiceException 
	 */
	public Optional<GraphEdge> retrieveEdge(Object key, Graph graphInstance, String mode) throws GraphUnmarshallingException, GraphServiceException {

		logger.debug("Going to retrieveEdge for given edge ID : {}", key);

		Iterator<Edge> edge = null;
		if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
			edge = graphInstance.edges(key);
		} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
			edge = graphInstance.traversal().E().has(GraphConstants.GRAPH_UUID_COLUMN_NAME, key);
		}else{
			logger.debug("Given mode of ID is not supported : mode : {}", mode);
			throw new GraphServiceException("Given mode of ID is not supported");
		}
		
		final Optional<GraphEdge> optionalRelationship;

		if (!edge.hasNext()) {
			optionalRelationship = Optional.empty();
		} else {
			optionalRelationship = Optional.of(graphFormer.unmarshallRelationship(edge.next()));
		}

		return optionalRelationship;
	}

	/**
	 * Query relationships.
	 *
	 * @param queryParams   the query params
	 * @param graphInstance the graph instance
	 * @return the stream
	 * @throws GraphServiceException 
	 */
	public Stream<GraphEdge> queryRelationships(Map<String, Object> queryParams, Graph graphInstance) throws GraphServiceException {

		if (queryParams.containsKey(GraphConstants.GRAPH_ID)) {
			try {
				final Optional<GraphEdge> relationship = retrieveEdge(queryParams.get(GraphConstants.GRAPH_ID),
						graphInstance,GraphConstants.GRAPH_METHOD_MODE_ID);

				if (relationship.isPresent()) {
					return Stream.of(relationship.get());

				} else {
					return Stream.empty();
				}
			} catch (GraphUnmarshallingException e) {
				logger.warn(String.format(GraphConstants.GRAPH_UNMARSHALLING_EXCEPTION_STRING_FORMATER,
						ApplicationMessages.GRAPH_ABSTRACT_TINKERPOP_CHAMP_GRAPH_WARN, "Failed to unmarshall relationship"),
						ExceptionUtils.getStackTrace(e));
				return Stream.empty();
			}
		}

		final GraphTraversal<Edge, Edge> query = graphInstance.traversal().E();

		for (Map.Entry<String, Object> filter : queryParams.entrySet()) {
			if (filter.getKey().equals(GraphConstants.GRAPH_LABEL)) {
				continue;
			} else {
				query.has(filter.getKey(), filter.getValue());
			}
		}

		if (queryParams.containsKey(GraphConstants.GRAPH_LABEL)) {
			hasLabel(query, queryParams.get(GraphConstants.GRAPH_LABEL));
		}

		final Iterator<GraphEdge> objIter = new Iterator<GraphEdge>() {

			private GraphEdge next;

			@Override
			public boolean hasNext() {
				while (query.hasNext()) {
					try {
						next = graphFormer.unmarshallRelationship(query.next());
						return true;
					} catch (GraphUnmarshallingException e) {
						logger.warn(String.format(GraphConstants.GRAPH_UNMARSHALLING_EXCEPTION_STRING_FORMATER,
								ApplicationMessages.GRAPH_ABSTRACT_TINKERPOP_CHAMP_GRAPH_WARN,
								"Failed to unmarshall tinkerpop vertex during query, returning partial results"),
								ExceptionUtils.getStackTrace(e));
					}
				}

				graphInstance.tx().commit();

				next = null;
				return false;
			}

			@Override
			public GraphEdge next() {
				if (next == null) {
					throw new NoSuchElementException();
				}

				return next;
			}
		};

		return StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(objIter, Spliterator.ORDERED | Spliterator.NONNULL), false);
	}

	private Vertex getVertexByGid(String gid, Graph graphInstance) {
		return graphInstance.traversal().V().has(GraphConstants.GRAPH_UUID_COLUMN_NAME, gid).next();
	}
	
	private Edge doPatchEdge(GraphEdge relationship, Graph graphInstance, String mode) throws GraphEdgeNotExistsException, GraphServiceException {
		logger.debug("Going to doPatchEdge for given edge data : {}", relationship);
		Edge edge = null;

		if (relationship.getSource() == null || relationship.getTarget() == null
				|| !relationship.getSource().getId().isPresent() || !relationship.getTarget().getId().isPresent()) {
			throw new IllegalArgumentException("Invalid source/target");
		}

		if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
			Optional<Object> id = relationship.getId();
			edge = doReplaceEdgeUsingIdMode(relationship, graphInstance, id);
		}else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
			edge = doReplaceEdgeUsingGIdMode(relationship, graphInstance, relationship.getgId());
		}else {
			logger.debug("Given mode of ID is not supported : mode : {}", mode);
			throw new GraphServiceException("Given mode of ID is not supported");
		}

		if (relationship.getProperties() != null) {
			logger.debug("updating properties : {}, of Edge with ID : {}", relationship.getProperties(), edge.id());
			doUpdateEdgeProperties(relationship, edge);
		} else {
			logger.debug("Edge with ID : {}, properties : null ", edge.id());
		}

		return edge;
	}

	private Edge doReplaceEdge(GraphEdge relationship, Graph graphInstance, String mode) throws GraphEdgeNotExistsException, GraphServiceException {
		logger.debug("Going to doPatchEdge for given edge data : {}", relationship);
		Edge edge = null;

		if (relationship.getSource() == null || relationship.getTarget() == null
				|| !relationship.getSource().getId().isPresent() || !relationship.getTarget().getId().isPresent()) {
			logger.debug("Invalid source/target : ID of source or destination not available");
			throw new IllegalArgumentException("Invalid source/target");
		}
		
		if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
			Optional<Object> id = relationship.getId();
			edge = doReplaceEdgeUsingIdMode(relationship, graphInstance, id);
		}else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
			edge = doReplaceEdgeUsingGIdMode(relationship, graphInstance, relationship.getgId());
		}else {
			logger.debug("Given mode of ID is not supported : mode : {}", mode);
			throw new GraphServiceException("Given mode of ID is not supported");
		}

		// clear all the existing properties
		Iterator<Property<Object>> it = edge.properties();
		while (it.hasNext()) {
			it.next().remove();
		}

		updateUUIDInEdge(edge, relationship.getgId());
		
		if (relationship.getProperties() != null) {
			logger.debug("updating properties : {}, of Edge with ID : {}", relationship.getProperties(), edge.id());
			doUpdateEdgeProperties(relationship, edge);
		}

		return edge;
	}

	private Edge doReplaceEdgeUsingGIdMode(GraphEdge relationship, Graph graphInstance,String gId) throws GraphEdgeNotExistsException {
		Edge edge;
		if (gId!=null) {
			final Iterator<Edge> edgeIter = graphInstance.traversal().E().has(GraphConstants.GRAPH_UUID_COLUMN_NAME,
					gId);
			
			if (edgeIter.hasNext()) {

				String sourceId = relationship.getSource().getgId();
				String destId = relationship.getTarget().getgId();

				edge = edgeIter.next();

				if ((sourceId!=null && destId!=null)) {
					Vertex source = getVertexByGid(sourceId, graphInstance);
					Vertex dest = getVertexByGid(destId, graphInstance);

					if ((source != null && dest != null)
							&& (!edge.outVertex().id().toString().equalsIgnoreCase(source.id().toString())
									|| !edge.inVertex().id().toString().equals(dest.id().toString()))) {
						throw new IllegalArgumentException("source/target can't be updated");
					}
				}

			} else {
				logger.debug("Edge with given gId : {} does not exist", gId);
				throw new GraphEdgeNotExistsException();
			}
		} else {
			logger.debug("Edge with given ID : null does not exist");
			throw new GraphEdgeNotExistsException();
		}
		return edge;
	}

	private Edge doReplaceEdgeUsingIdMode(GraphEdge relationship, Graph graphInstance, Optional<Object> id)
			throws GraphEdgeNotExistsException {
		Edge edge;
		if (id.isPresent()) {
			final Iterator<Edge> edgeIter = graphInstance.edges(id.get());

			if (edgeIter.hasNext()) {

				Optional<Object> sourceId = relationship.getSource().getId();
				Optional<Object> destId = relationship.getTarget().getId();

				edge = edgeIter.next();

				if ((sourceId.isPresent() && destId.isPresent())
						&& (!edge.outVertex().id().toString().equalsIgnoreCase(sourceId.get().toString())
								|| !edge.inVertex().id().toString().equals(destId.get().toString()))) {
					throw new IllegalArgumentException("source/target can't be updated");
				}
			} else {
				logger.debug("Edge with given ID : {} does not exist", id.get());
				throw new GraphEdgeNotExistsException();
			}
		} else {
			logger.debug("Edge with given ID : null does not exist");
			throw new GraphEdgeNotExistsException();
		}
		return edge;
	}
	
	/**
	 * Gets the edge mode by edge label and target vertex label.
	 *
	 * @param labelList         the label list
	 * @param targetVertexLabel the target vertex label
	 * @return the edge mode by edge label and target vertex label
	 */
	private String getEdgeModeByEdgeLabelAndTargetVertexLabel(List<String> labelList, List<String> targetVertexLabel) {
		if (labelList != null && !labelList.isEmpty() && targetVertexLabel != null && !targetVertexLabel.isEmpty()) {
			return GraphConstants.EDGE_MODE_EDGELABEL_TARGETVERTEXLABEL;
		} else if (labelList != null && !labelList.isEmpty()) {
			return GraphConstants.EDGE_MODE_EDGELABEL;
		} else if (targetVertexLabel != null && !targetVertexLabel.isEmpty()) {
			return GraphConstants.EDGE_MODE_TARGETVERTEXLABEL;
		} else {
			return GraphConstants.EDGE_MODE_ONLY_ID;
		}
	}

	@Override
	public List<GraphEdge> getEdgesUsingIdMode(Object vertexId, List<String> labelList, List<String> targetVertexLabel,
			GraphTransaction transaction) throws GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphServiceException {
		logger.debug("Going to getEdges for given vertexId : {}, labelList : {}, targetVertexLabel : {}", vertexId,
				labelList, targetVertexLabel);
		List<GraphEdge> edgeList = new ArrayList<>();
		Graph createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);

		try {
			GraphVertex graphVertex = getVById(vertexId,null, transaction);

			String mode = getEdgeModeByEdgeLabelAndTargetVertexLabel(labelList, targetVertexLabel);

			switch (mode) {
			case GraphConstants.EDGE_MODE_EDGELABEL_TARGETVERTEXLABEL:
				break;
			case GraphConstants.EDGE_MODE_EDGELABEL:
				Stream<GraphEdge> relationships = retrieveEdges(graphVertex, createThreadedTx,
						GraphConstants.GRAPH_METHOD_MODE_ID, labelList.toArray(new String[labelList.size()]));
				edgeList = relationships.collect(Collectors.toList());
				break;
			case GraphConstants.EDGE_MODE_TARGETVERTEXLABEL:
				break;
			case GraphConstants.EDGE_MODE_ONLY_ID:
				Stream<GraphEdge> edgesConnectedToID = retrieveEdges(graphVertex, createThreadedTx,GraphConstants.GRAPH_METHOD_MODE_ID);
				edgeList = edgesConnectedToID.collect(Collectors.toList());
				break;
			default:
				break;
			}

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}
			return edgeList;
		} catch (GraphObjectNotExistsException e) {
			logger.error("GraphObjectNotExistsException in getEdges : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in getEdges : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in getEdges : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

	@Override
	public List<GraphEdge> getEdgesUsingGIdMode(Object vertexId, List<String> labelList, List<String> targetVertexLabel,
			GraphTransaction transaction) throws GraphObjectNotExistsException, TransactionException,
			GraphUnmarshallingException, GraphServiceException {
		logger.debug("Going to getEdges for given vertexId : {}, labelList : {}, targetVertexLabel : {}", vertexId,
				labelList, targetVertexLabel);
		List<GraphEdge> edgeList = new ArrayList<>();
		Graph createThreadedTx = graphTransactionManager.getThreadedTransaction(transaction);

		try {
			GraphVertex graphVertex = getVByUUID(vertexId,null, transaction);

			String mode = getEdgeModeByEdgeLabelAndTargetVertexLabel(labelList, targetVertexLabel);

			switch (mode) {
			case GraphConstants.EDGE_MODE_EDGELABEL_TARGETVERTEXLABEL:
				break;
			case GraphConstants.EDGE_MODE_EDGELABEL:
				Stream<GraphEdge> relationships = retrieveEdges(graphVertex, createThreadedTx,
						GraphConstants.GRAPH_METHOD_MODE_GID, labelList.toArray(new String[labelList.size()]));
				edgeList = relationships.collect(Collectors.toList());
				break;
			case GraphConstants.EDGE_MODE_TARGETVERTEXLABEL:
				break;
			case GraphConstants.EDGE_MODE_ONLY_ID:
				Stream<GraphEdge> edgesConnectedToID = retrieveEdges(graphVertex, createThreadedTx,
						GraphConstants.GRAPH_METHOD_MODE_GID);
				edgeList = edgesConnectedToID.collect(Collectors.toList());
				break;
			default:
				break;
			}

			if (transaction == null) {
				graphTransactionManager.commitTransaction(createThreadedTx);
			}
			return edgeList;
		} catch (GraphObjectNotExistsException e) {
			logger.error("GraphObjectNotExistsException in getEdges : {} ", ExceptionUtils.getStackTrace(e));
			createThreadedTx.tx().rollback();
			throw e;
		} catch (TransactionException e) {
			logger.error("GraphTransactionException in getEdges : {} ", ExceptionUtils.getStackTrace(e));
			throw e;
		} catch (Exception ex) {
			logger.error("Exception in getEdges : {} ", ExceptionUtils.getStackTrace(ex));
			createThreadedTx.tx().rollback();
			throw ex;
		}
	}

}
