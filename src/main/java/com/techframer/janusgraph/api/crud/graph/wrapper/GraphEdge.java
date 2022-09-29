package com.techframer.janusgraph.api.crud.graph.wrapper;

import com.techframer.janusgraph.api.crud.graph.connection.constants.GraphConstants;
import com.techframer.janusgraph.api.crud.utils.former.CreateGraphEdgeObjectable;
import com.techframer.janusgraph.api.crud.utils.former.impl.CreateGraphEdgeObjectableImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The Class GraphEdge.
 */
public class GraphEdge {

	public GraphEdge(){
		this.id = Optional.empty();
	}

	public static CreateGraphEdgeObjectable create() {
		return new CreateGraphEdgeObjectableImpl();
	}

	private GraphEdge(Builder builder) {
		this.properties = builder.properties;
		this.source = builder.source;
		this.target = builder.target;
		this.label = builder.label;
		this.id = builder.id;
		this.gId = builder.gId;
	}

	/** The label. */
	private String label;

	/** The id. */
	private Optional<Object> id;

	/** The properties. */
	private Map<String, Object> properties;

	/** The source. */
	private GraphVertex source;

	/** The target. */
	private GraphVertex target;

	private String gId;
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label
	 *            the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Optional<Object> getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Optional<Object> id) {
		this.id = id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setStringId(String id) {
		this.id = Optional.ofNullable(id);
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Sets the properties.
	 *
	 * @param properties
	 *            the properties
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public GraphVertex getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source
	 *            the new source
	 */
	public void setSource(GraphVertex source) {
		this.source = source;
	}

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public GraphVertex getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 *
	 * @param target
	 *            the new target
	 */
	public void setTarget(GraphVertex target) {
		this.target = target;
	}
	
	/**
	 * Gets the g id.
	 *
	 * @return the g id
	 */
	public String getgId() {
		return gId;
	}

	/**
	 * Sets the g id.
	 *
	 * @param gId the new g id
	 */
	public void setgId(String gId) {
		this.gId = gId;
	}

	

	@Override
	public String toString() {
		return "GraphEdge [label=" + label + ", id=" + id + ", properties=" + properties + ", source=" + source
				+ ", target=" + target + ", gId=" + gId + "]";
	}



	public static class Builder {
		private final Map<String, Object> properties = new HashMap<>();
		private final GraphVertex source;
		private final GraphVertex target;
		private final String label;
		private String gId;

		private Optional<Object> id = Optional.empty();

		public Builder(GraphVertex source, GraphVertex target, String label) {
			this.source = source;
			this.target = target;
			this.label = label;

			if (source != null && source.getProperties() != null
					&& source.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) != null) {
				this.source.setgId(source.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString());
			}

			if (target != null && target.getProperties() != null
					&& target.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) != null) {
				this.target.setgId(target.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString());
			}
		}

		public Builder(GraphEdge relationship) {
			this.source = relationship.source;
			this.target = relationship.target;
			this.label = relationship.label;

			properties.putAll(relationship.getProperties());
			
			if (source != null && source.getProperties() != null
					&& source.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) != null) {
				this.source.setgId(source.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString());
			}

			if (target != null && target.getProperties() != null
					&& target.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) != null) {
				this.target.setgId(target.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString());
			}
			
			if (relationship.getProperties() != null
					&& relationship.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) != null) {
				gId = relationship.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString();
			}
			removeGIdFromGraphVertexIfAvailable(source);
			removeGIdFromGraphVertexIfAvailable(target);
			removeGIdFromGraphEdgeIfAvailable(relationship);
		}

		private void removeGIdFromGraphVertexIfAvailable(GraphVertex vertex) {
			if (vertex != null && vertex.getProperties() != null
					&& vertex.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) != null) {
				vertex.getProperties().remove(GraphConstants.GRAPH_UUID_COLUMN_NAME);
			}
		}
		
		private void removeGIdFromGraphEdgeIfAvailable(GraphEdge edge) {
			if (edge != null && edge.getProperties() != null
					&& edge.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) != null) {
				edge.getProperties().remove(GraphConstants.GRAPH_UUID_COLUMN_NAME);
			}
		}
		
		public Builder key(Object key) {
			this.id = Optional.of(key);
			return this;
		}

		public Builder properties(Map<String, Object> properties) {
			if(properties!=null){
				for (Map.Entry<String, Object> property : properties.entrySet()) {
					property(property.getKey(), property.getValue());
				}
			}
			return this;
		}

		public Builder property(String key, Object value) {
			properties.put(key, value);
			return this;
		}

		public GraphEdge build() {
			return new GraphEdge(this);
		}
	}
}
