package com.techframer.janusgraph.api.crud.controller.impl;

import com.techframer.janusgraph.api.crud.controller.GraphController;
import com.techframer.janusgraph.api.crud.graph.connection.constants.GraphConstants;
import com.techframer.janusgraph.api.crud.graph.exceptions.*;
import com.techframer.janusgraph.api.crud.graph.transaction.exceptions.TransactionException;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.service.IGraphService;
import com.techframer.janusgraph.api.crud.utils.GraphErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "api/graph")
@ResponseBody
public class GraphControllerImpl implements GraphController {

	@Autowired
	IGraphService graphService;

	@Override
	public ResponseEntity echo() {
		return ResponseEntity.ok().body("alive");
	}

	@Override
	public GraphVertex getVById(String vertexId, String tId) {
		try {
			return graphService.getVById(vertexId, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE, GraphErrorMsg.FOR_ID, vertexId));
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (TransactionException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE, vertexId));
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE, vertexId));
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public GraphVertex getVByUUID(String vertexId, String tId) throws GraphObjectNotExistsException,
			TransactionException, GraphUnmarshallingException, GraphServiceException {
		try {
			return graphService.getVByUUID(vertexId, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE, GraphErrorMsg.FOR_ID, vertexId));
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (TransactionException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE, vertexId));
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE, vertexId));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public GraphVertex addV(GraphVertex vertex, String tId)
			throws GraphObjectNotExistsException, GraphMarshallingException, TransactionException {
		try {
			return graphService.addV(vertex, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE, GraphErrorMsg.FOR_ID, vertex.getId()));
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (Exception e) {
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;

	}

	@Override
	public GraphVertex patchVByVId(GraphVertex vertex, String tId) throws GraphObjectNotExistsException,
			TransactionException, GraphMarshallingException, GraphUnmarshallingException, GraphServiceException {
		try {
			return graphService.patchVByVId(vertex, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE, GraphErrorMsg.FOR_ID, vertex.getId()));
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public GraphVertex patchVByUUID(GraphVertex vertex, String tId) throws GraphObjectNotExistsException,
			TransactionException, GraphMarshallingException, GraphUnmarshallingException, GraphServiceException {
		try {
			return graphService.patchVByUUID(vertex, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE, GraphErrorMsg.FOR_ID, vertex.getId()));
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public GraphVertex replaceVByVId(GraphVertex vertex, String tId)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException,
			GraphMarshallingException, GraphUnmarshallingException {
		try {
			return graphService.replaceVByVId(vertex, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE, GraphErrorMsg.FOR_ID, vertex.getId()));
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public GraphVertex replaceVByUUID(GraphVertex vertex, String tId)
			throws GraphServiceException, GraphObjectNotExistsException, TransactionException,
			GraphMarshallingException, GraphUnmarshallingException {
		try {
			return graphService.replaceVByUUID(vertex, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE, GraphErrorMsg.FOR_ID, vertex.getId()));
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean dropVById(String vertexId, String tId) throws GraphServiceException, GraphObjectNotExistsException,
			TransactionException, GraphUnmarshallingException {
		try {
			return graphService.dropVById(vertexId, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE, GraphErrorMsg.FOR_ID, vertexId));
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Boolean dropVByUUID(String vertexId, String tId) throws GraphServiceException, GraphObjectNotExistsException,
			TransactionException, GraphUnmarshallingException {
		try {
			return graphService.dropVByUUID(vertexId, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(String.format(GraphConstants.STRING_STRING_INTEGER_STRING_FORMATER,
					GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE, GraphErrorMsg.FOR_ID, vertexId));
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public GraphEdge addE(GraphEdge edge, String tId)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException,
			GraphEdgeNotExistsException, GraphMarshallingException, GraphServiceException {
		try {
			return graphService.addE(edge, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.SOURCE_OR_DESTINATION_VERTEX_NOT_EXIST_MESSAGE);
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphEdgeNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public GraphEdge patchEById(GraphEdge edge, String tId) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException {
		try {
			return graphService.patchEById(edge, null);
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphEdgeNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public GraphEdge patchEByUUID(GraphEdge edge, String tId) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException {
		try {
			return graphService.patchEByUUID(edge, null);
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphEdgeNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public GraphEdge replaceEById(GraphEdge edge, String tId) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException {
		try {
			return graphService.replaceEById(edge, null);
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphEdgeNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public GraphEdge replaceEByUUID(GraphEdge edge, String tId) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException {
		try {
			return graphService.replaceEByUUID(edge, null);
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphMarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_MARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphEdgeNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean dropEById(String edgeId, String tId)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException {
		try {
			return graphService.dropEById(edgeId, null);
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphEdgeNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Boolean dropEByUUID(String edgeId, String tId)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException {
		try {
			return graphService.dropEByUUID(edgeId, null);
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphEdgeNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public GraphEdge getEById(String edgeId, String tId) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException {
		try {
			return graphService.getEById(edgeId, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.SOURCE_OR_DESTINATION_VERTEX_NOT_EXIST_MESSAGE);
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphEdgeNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public GraphEdge getEByUUID(String edgeId, String tId) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException {
		try {
			return graphService.getEByUUID(edgeId, null);
		} catch (GraphObjectNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.SOURCE_OR_DESTINATION_VERTEX_NOT_EXIST_MESSAGE);
		} catch (TransactionException e) {
			throw new BusinessException(GraphErrorMsg.SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE);
		} catch (GraphUnmarshallingException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_UNMARSHALL_EXCEPTION_MESSAGE);
		} catch (GraphEdgeNotExistsException e) {
			throw new BusinessException(GraphErrorMsg.GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE);
		} catch (GraphServiceException e) {
			throw new BusinessException(GraphErrorMsg.ILLEGAL_ARGUMENTS_FOUND_MESSAGE);
		} catch (Exception e) {
			throw e;
		}
	}
}
