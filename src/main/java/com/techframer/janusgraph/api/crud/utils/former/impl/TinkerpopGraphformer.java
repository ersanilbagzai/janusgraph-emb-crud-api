package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.graph.exceptions.GraphUnmarshallingException;
import com.techframer.janusgraph.api.crud.graph.connection.constants.GraphConstants;
import com.techframer.janusgraph.api.crud.utils.former.EdgeBuildOrPropertiesStep;
import com.techframer.janusgraph.api.crud.utils.former.Graphformer;
import com.techframer.janusgraph.api.crud.utils.former.VertexBuildOrPropertiesStep;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TinkerpopGraphformer implements Graphformer<Vertex, Edge> {

	private static final Logger logger = LogManager.getLogger(TinkerpopGraphformer.class);

	@Override
	public Vertex marshallObject(GraphVertex object) throws GraphUnmarshallingException {
		throw new UnsupportedOperationException(
				"Cannot marshall object to Tinkerpop Vertex without adding it to a graph");
	}

	@Override
	public Edge marshallRelationship(GraphEdge relationship) throws GraphUnmarshallingException {
		throw new UnsupportedOperationException(
				"Cannot marshall relationships to Tinkerpop Edge without adding it to a graph");
	}

	@Override
	public GraphVertex unmarshallObject(Vertex vertex) throws GraphUnmarshallingException {
		final String type = vertex.label();
		final VertexBuildOrPropertiesStep aaiObjBuilder = GraphVertex.create().ofType(type).withKey(vertex.id());

		final Iterator<VertexProperty<Object>> properties = vertex.properties();
		while (properties.hasNext()) {
			final VertexProperty<Object> property = properties.next();
			aaiObjBuilder.withProperty(property.key(), property.value());
		}

		GraphVertex build = aaiObjBuilder.build();
		updateGIdAndProperties(build);
		return build;
	}
	
	
	public void updateGIdAndProperties(GraphVertex vertex) throws GraphUnmarshallingException   {
		if (vertex == null || vertex.getProperties() == null
				|| vertex.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) == null) {
			logger.error("GraphServiceException : Given Vertex does not have gId");
			throw new GraphUnmarshallingException("");
		}
		vertex.setgId(vertex.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString());
		vertex.getProperties().remove(GraphConstants.GRAPH_UUID_COLUMN_NAME);
	}
	
	public void updateGIdAndProperties(GraphEdge edge) throws GraphUnmarshallingException   {
		if (edge == null || edge.getProperties() == null
				|| edge.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) == null) {
			logger.error("GraphServiceException : Given edge does not have gId");
			throw new GraphUnmarshallingException("");
		}
		edge.setgId(edge.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString());
		edge.getProperties().remove(GraphConstants.GRAPH_UUID_COLUMN_NAME);
	}
	
	@Override
	public List<GraphVertex> unmarshallObject(List<Vertex> vertices) throws GraphUnmarshallingException {
		if (vertices == null || vertices.isEmpty()) {
			throw new IllegalArgumentException("vetices are null or empty");
		}

		return vertices.stream().unordered().parallel().map(vertex -> {
			try {
				return unmarshallObject(vertex);
			} catch (GraphUnmarshallingException e) {
				logger.error("Exception in unmarshallObject for vertex ID : {}", vertex.id());
			}
			return null;
		}).collect(Collectors.toList());

	}
	
	@Override
	public Map<String, GraphVertex> unmarshallObjectToMap(List<Vertex> vertices) throws GraphUnmarshallingException {
		if (vertices == null || vertices.isEmpty()) {
			throw new IllegalArgumentException("vetices are null or empty");
		}

		List<GraphVertex> graphVertices = vertices.stream().unordered().parallel().map(vertex -> {
			try {
				return unmarshallObject(vertex);
			} catch (GraphUnmarshallingException e) {
				logger.error("Exception in unmarshallObject for vertex ID : {}", vertex.id());
			}
			return null;
		}).collect(Collectors.toList());

		return graphVertices.stream().unordered().parallel().collect(
				Collectors.toMap(GraphVertex::getStringId, Function.identity(), (o1, o2) -> o1, ConcurrentHashMap::new));
	}
	
	@Override
	public Map<String, GraphVertex> unmarshallObjectToMap(Vertex vertex) throws GraphUnmarshallingException {
		Map<String, GraphVertex> hashMap = new HashMap<>();

		final String type = vertex.label();
		final VertexBuildOrPropertiesStep aaiObjBuilder = GraphVertex.create().ofType(type).withKey(vertex.id());

		final Iterator<VertexProperty<Object>> properties = vertex.properties();
		while (properties.hasNext()) {
			final VertexProperty<Object> property = properties.next();
			aaiObjBuilder.withProperty(property.key(), property.value());
		}
		GraphVertex build = aaiObjBuilder.build();
		Optional<Object> id = build.getId();
		if (id.isPresent()) {
			hashMap.put(id.get().toString(), build);
			return hashMap;
		}
		return null;
	}
	
	public class VerticesAndFields {
		private List<Vertex> vertices;
		private List<String> fields;

		public List<Vertex> getVertices() {
			return vertices;
		}

		public void setVertices(List<Vertex> vertices) {
			this.vertices = vertices;
		}

		public List<String> getFields() {
			return fields;
		}

		public void setFields(List<String> fields) {
			this.fields = fields;
		}
	}
	
	@Override
	public Map<String, GraphVertex> unmarshallObjectToMap(List<Vertex> vertices, List<String> fields)
			throws GraphUnmarshallingException {
		if (vertices == null || vertices.isEmpty()) {
			throw new IllegalArgumentException("vetices are null or empty");
		}

		List<GraphVertex> graphVertices = vertices.stream().unordered().parallel().map(vertex -> {
			try {
				return unmarshallObject(vertex, fields);
			} catch (GraphUnmarshallingException e) {
				logger.error("Exception in unmarshallObject for vertex ID : {}", vertex.id());
			}
			return null;
		}).collect(Collectors.toList());

		return graphVertices.stream().unordered().parallel().collect(
				Collectors.toMap(GraphVertex::getStringId, Function.identity(), (o1, o2) -> o1, ConcurrentHashMap::new));
	}
	
	@Override
	public Map<String, GraphVertex> unmarshallObjectToMap(Vertex vertex,List<String> fields) throws GraphUnmarshallingException {
		Map<String, GraphVertex> hashMap = new HashMap<>();

		final String type = vertex.label();
		final VertexBuildOrPropertiesStep aaiObjBuilder = GraphVertex.create().ofType(type).withKey(vertex.id());

		final Iterator<VertexProperty<Object>> properties = vertex.properties(fields.toArray(new String[0]));
		while (properties.hasNext()) {
			final VertexProperty<Object> property = properties.next();
			aaiObjBuilder.withProperty(property.key(), property.value());
		}
		GraphVertex build = aaiObjBuilder.build();
		Optional<Object> id = build.getId();
		if (id.isPresent()) {
			hashMap.put(id.get().toString(), build);
			return hashMap;
		}
		return null;
	}
	
	@Override
	public GraphVertex unmarshallObject(Vertex vertex ,List<String> fields) throws GraphUnmarshallingException {
		
		if(!fields.contains(GraphConstants.GRAPH_METHOD_MODE_GID)||fields.contains(GraphConstants.GRAPH_METHOD_MODE_GID)==false) {
			fields.add(GraphConstants.GRAPH_METHOD_MODE_GID);
		}
		final String type = vertex.label();
		final VertexBuildOrPropertiesStep aaiObjBuilder = GraphVertex.create().ofType(type).withKey(vertex.id());

		final Iterator<VertexProperty<Object>> properties = vertex.properties(fields.toArray(new String[0]));
		while (properties.hasNext()) {
			final VertexProperty<Object> property = properties.next();
			aaiObjBuilder.withProperty(property.key(), property.value());
		}
		GraphVertex build = aaiObjBuilder.build();
		updateGIdAndProperties(build);
		return build;
			}

	@Override
	public GraphEdge unmarshallRelationship(Edge edge) throws GraphUnmarshallingException {
		final GraphVertex source = unmarshallObject(edge.outVertex());
		final GraphVertex target = unmarshallObject(edge.inVertex());
		final String type = edge.label();
		final EdgeBuildOrPropertiesStep aaiRelBuilder = GraphEdge.create().ofType(type).withKey(edge.id()).withSource()
				.from(source).build().withTarget().from(target).build();

		final Iterator<Property<Object>> properties = edge.properties();

		while (properties.hasNext()) {
			final Property<Object> property = properties.next();

			aaiRelBuilder.withProperty(property.key(), property.value());
		}

		GraphEdge build = aaiRelBuilder.build();
		updateGIdAndProperties(build);
		return build;
	}
	
	

}
