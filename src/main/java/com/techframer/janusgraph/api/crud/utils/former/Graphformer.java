package com.techframer.janusgraph.api.crud.utils.former;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.graph.exceptions.GraphUnmarshallingException;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;
import java.util.Map;

public interface Graphformer<V, E> {

	public V marshallObject(GraphVertex object) throws GraphUnmarshallingException;

	public E marshallRelationship(GraphEdge relationship) throws GraphUnmarshallingException;

	public GraphVertex unmarshallObject(V data) throws GraphUnmarshallingException;
	public GraphVertex unmarshallObject(V data ,List<String> fields) throws GraphUnmarshallingException ;
	
	public GraphEdge unmarshallRelationship(E data) throws GraphUnmarshallingException;

	List<GraphVertex> unmarshallObject(List<Vertex> vertices) throws GraphUnmarshallingException;

	Map<String, GraphVertex> unmarshallObjectToMap(List<Vertex> vertices) throws GraphUnmarshallingException;

	Map<String, GraphVertex> unmarshallObjectToMap(Vertex vertex) throws GraphUnmarshallingException;

	Map<String, GraphVertex> unmarshallObjectToMap(Vertex vertex, List<String> fields) throws GraphUnmarshallingException;

	Map<String, GraphVertex> unmarshallObjectToMap(List<Vertex> vertices, List<String> fields)
			throws GraphUnmarshallingException;

}
