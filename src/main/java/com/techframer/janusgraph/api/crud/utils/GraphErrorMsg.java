package com.techframer.janusgraph.api.crud.utils;

public class GraphErrorMsg {

	private GraphErrorMsg() {

	}

	/** The Constant GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE. */
	public static final String GRAPH_VERTEX_NOT_EXIST_EXCEPTION_MESSAGE = "Vertex not exist in graph";

	/** The Constant GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE. */
	public static final String GRAPH_EDGE_NOT_EXIST_EXCEPTION_MESSAGE = "Edge not exist in graph";

	/** The Constant SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE. */
	public static final String SOMETHING_GONE_WRONG_WITH_GRAPH_TRANSACTION_MESSAGE = "Something gone wrong with graph transaction";

	/** The Constant GRAPH_UNMARSHALL_EXCEPTION_MESSAGE. */
	public static final String GRAPH_UNMARSHALL_EXCEPTION_MESSAGE = "Could not convert graph vertex object into vertex json";

	/** The Constant GRAPH_MARSHALL_EXCEPTION_MESSAGE. */
	public static final String GRAPH_MARSHALL_EXCEPTION_MESSAGE = "Could not convert vertex java object into graph vertex object";

	/** The Constant VERTEX_CANT_BE_UPDATED_AS_GIVEN_ID_IS_NULL_MESSAGE. */
	public static final String VERTEX_CANT_BE_UPDATED_AS_GIVEN_ID_IS_NULL_MESSAGE = "Vertex can't be updated as given ID is null";

	/** The Constant SOURCE_OR_DESTINATION_VERTEX_NOT_EXIST_MESSAGE. */
	public static final String SOURCE_OR_DESTINATION_VERTEX_NOT_EXIST_MESSAGE = "source or destination vertex does not exist";

	/** The Constant FOR_ID. */
	public static final String FOR_ID = "for id : ";
	
	public static final String ILLEGAL_ARGUMENTS_FOUND_MESSAGE = "Provided arguments are not correct, please verify";
	
	public static final String VERTEX_IS_NULL = "vertex is null";
	
	public static final String VERTEX_LABEL_IS_NULL = "vertex label is null";
	
	public static final String VERTEX_ID_IS_NULL = "vertex id is null";

	public static final String SOURCE_VERTEX_ID_IS_NULL = "source vertex id is null";
	
	public static final String DESTINATION_VERTEX_ID_IS_NULL = "destination vertex id is null";
	
	public static final String EDGE_ID_IS_NULL = "edge id is null";
	
	public static final String EDGE_IS_NULL = "edge is null";
	
	public static final String EDGE_LABEL_IS_NULL = "edge label is null";
	
	public static final String SOURCE_VERTEX_IS_NULL = "source vertex is null";
	
	public static final String DESTINATION_VERTEX_IS_NULL = "destination vertex is null";
	
	public static final String EDGE_LABEL_PROVIDED_IS_EMPTY = "edgeLabels provided is empty";
	
	public static final String MESSAGE_GIVEN_VERTEX_DOES_NOT_HAVE_GID = "Given Vertex does not have gId";
	
	public static final String MESSAGE_GIVEN_EDGE_DOES_NOT_HAVE_GID = "Given Edge does not have gId";
}
