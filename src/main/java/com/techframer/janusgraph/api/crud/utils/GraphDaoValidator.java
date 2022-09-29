package com.techframer.janusgraph.api.crud.utils;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.graph.exceptions.GraphServiceException;
import com.techframer.janusgraph.api.crud.graph.connection.constants.GraphConstants;

import java.util.Optional;

/**
 * The Class GraphDaoValidator.
 */
public class GraphDaoValidator {

	/**
	 * Instantiates a new graph dao validator.
	 */
	private GraphDaoValidator() {

	}

	/**
	 * Validate create vertex arguments.
	 *
	 * @param vertex
	 *            the vertex
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	public static void validateCreateVertexArguments(GraphVertex vertex) throws GraphServiceException {
		if (vertex == null) {
			throw new GraphServiceException(GraphErrorMsg.VERTEX_IS_NULL);
		}
		if (vertex.getLabel() == null) {
			throw new GraphServiceException(GraphErrorMsg.VERTEX_LABEL_IS_NULL);
		}
	}

	/**
	 * Validate replace vertex arguments.
	 *
	 * @param vertex
	 *            the vertex
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	public static void validateReplaceVertexArguments(GraphVertex vertex, String mode) throws GraphServiceException {
		checkIfVertexAndIdExists(vertex, mode);
	}

	/**
	 * Check if vertex and id exists.
	 *
	 * @param vertex
	 *            the vertex
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	private static void checkIfVertexAndIdExists(GraphVertex vertex, String mode) throws GraphServiceException {
		if (vertex == null) {
			throw new GraphServiceException(GraphErrorMsg.VERTEX_IS_NULL);
		}
		if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
			if (!vertex.getId().isPresent()) {
				throw new GraphServiceException(GraphErrorMsg.VERTEX_ID_IS_NULL);
			}
		} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
			if (vertex.getgId() == null) {
				throw new GraphServiceException(GraphErrorMsg.MESSAGE_GIVEN_VERTEX_DOES_NOT_HAVE_GID);
			}
		} else {
			throw new GraphServiceException("Given mode of ID is not supported");
		}
	}

	/**
	 * Validate patch vertex arguments.
	 *
	 * @param vertex
	 *            the vertex
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	public static void validatePatchVertexArguments(GraphVertex vertex, String mode) throws GraphServiceException {
		checkIfVertexAndIdExists(vertex, mode);
	}

	/**
	 * Validate drop vertex arguments.
	 *
	 * @param vertexId
	 *            the vertex id
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	public static void validateDropVertexArguments(Object vertexId) throws GraphServiceException {
		if (vertexId == null) {
			throw new GraphServiceException(GraphErrorMsg.VERTEX_IS_NULL);
		}
	}

	/**
	 * Validate replace edge arguments.
	 *
	 * @param edge
	 *            the edge
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	public static void validateReplaceEdgeArguments(GraphEdge edge, String mode) throws GraphServiceException {
		validateRepladeAndPatchEdgeArguments(edge, mode);
	}

	/**
	 * Validate replade and patch edge arguments.
	 *
	 * @param edge
	 *            the edge
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	private static void validateRepladeAndPatchEdgeArguments(GraphEdge edge, String mode) throws GraphServiceException {
		validateCommonEdgeArgumentsWithoutLabel(edge);

		if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
			Optional<Object> id = edge.getId();
			if (!id.isPresent()) {
				throw new GraphServiceException(GraphErrorMsg.EDGE_ID_IS_NULL);
			}
		} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
			if (edge.getgId() == null) {
				throw new GraphServiceException(GraphErrorMsg.EDGE_ID_IS_NULL);
			}
		} else {
			throw new GraphServiceException("Given mode of ID is not supported");
		}

		validateSourceIdAndDestIdForEdge(edge, mode);
	}

	/**
	 * Validate source id and dest id for edge.
	 *
	 * @param edge
	 *            the edge
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	private static void validateSourceIdAndDestIdForEdge(GraphEdge edge, String mode) throws GraphServiceException {

		GraphVertex source = edge.getSource();
		if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
			if (!source.getId().isPresent()) {
				throw new GraphServiceException(GraphErrorMsg.SOURCE_VERTEX_ID_IS_NULL);
			}
		} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
			if (source.getgId() == null) {
				throw new GraphServiceException(GraphErrorMsg.SOURCE_VERTEX_ID_IS_NULL);
			}
		} else {
			throw new GraphServiceException("Given mode of ID is not supported");
		}

		GraphVertex target = edge.getTarget();

		if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_ID)) {
			if (!target.getId().isPresent()) {
				throw new GraphServiceException(GraphErrorMsg.DESTINATION_VERTEX_ID_IS_NULL);
			}
		} else if (mode.equalsIgnoreCase(GraphConstants.GRAPH_METHOD_MODE_GID)) {
			if (target.getgId() == null) {
				throw new GraphServiceException(GraphErrorMsg.DESTINATION_VERTEX_ID_IS_NULL);
			}
		} else {
			throw new GraphServiceException("Given mode of ID is not supported");
		}
	}

	/**
	 * Validate patch edge arguments.
	 *
	 * @param edge
	 *            the edge
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	public static void validatePatchEdgeArguments(GraphEdge edge, String mode) throws GraphServiceException {
		validateRepladeAndPatchEdgeArguments(edge, mode);
	}

	/**
	 * Validate create edge arguments.
	 *
	 * @param edge
	 *            the edge
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	public static void validateCreateEdgeArguments(GraphEdge edge, String mode) throws GraphServiceException {
		validateCommonEdgeArgumentsWithLabel(edge);
		validateSourceIdAndDestIdForEdge(edge, mode);
	}

	/**
	 * Validate common edge arguments without label.
	 *
	 * @param edge
	 *            the edge
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	private static void validateCommonEdgeArgumentsWithoutLabel(GraphEdge edge) throws GraphServiceException {
		if (edge == null) {
			throw new GraphServiceException(GraphErrorMsg.EDGE_IS_NULL);
		}
		if (edge.getSource() == null) {
			throw new GraphServiceException(GraphErrorMsg.SOURCE_VERTEX_IS_NULL);
		}
		if (edge.getTarget() == null) {
			throw new GraphServiceException(GraphErrorMsg.DESTINATION_VERTEX_IS_NULL);
		}
	}

	/**
	 * Validate common edge arguments with label.
	 *
	 * @param edge
	 *            the edge
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	private static void validateCommonEdgeArgumentsWithLabel(GraphEdge edge) throws GraphServiceException {
		if (edge == null) {
			throw new GraphServiceException(GraphErrorMsg.EDGE_IS_NULL);
		}
		if (edge.getLabel() == null) {
			throw new GraphServiceException(GraphErrorMsg.EDGE_LABEL_IS_NULL);
		}
		if (edge.getSource() == null) {
			throw new GraphServiceException(GraphErrorMsg.SOURCE_VERTEX_IS_NULL);
		}
		if (edge.getTarget() == null) {
			throw new GraphServiceException(GraphErrorMsg.DESTINATION_VERTEX_IS_NULL);
		}
	}

	/**
	 * Validate drop edge arguments.
	 *
	 * @param edgeId
	 *            the edge id
	 * @throws GraphServiceException
	 *             the graph service exception
	 */
	public static void validateDropEdgeArguments(String edgeId) throws GraphServiceException {
		if (edgeId == null) {
			throw new GraphServiceException(GraphErrorMsg.EDGE_ID_IS_NULL);
		}
	}
}
