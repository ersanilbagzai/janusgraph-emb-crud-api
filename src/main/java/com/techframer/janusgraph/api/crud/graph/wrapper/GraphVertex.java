package com.techframer.janusgraph.api.crud.graph.wrapper;

import com.techframer.janusgraph.api.crud.graph.connection.constants.GraphConstants;
import com.techframer.janusgraph.api.crud.utils.former.impl.CreateGraphVertexObjectableImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The Class GraphVertex.
 */
public class GraphVertex {

	public GraphVertex(){
		this.id = Optional.empty();
	}
	
	private GraphVertex(Builder builder) {
		this.label = builder.label;
		this.id = builder.id;
		this.properties = builder.properties;
		this.gId = builder.gId;
	}

	public static CreateGraphVertexObjectableImpl create() {
		return new CreateGraphVertexObjectableImpl();
	}
	
	/** The label. */
	private String label;

	/** The id. */
	private Optional<Object> id;

	/** The properties. */
	private Map<String, Object> properties;

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
	 * Gets the object id.
	 *
	 * @return the object id
	 */
	public Object getObjectId() {
		if (id.isPresent()) {
			return id.get();
		} else {
			return null;
		}
	}
	

	/**
	 * Gets the string id.
	 *
	 * @return the string id
	 */
	public String getStringId() {
		if (id.isPresent()) {
			return id.get().toString();
		} else {
			return null;
		}
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
		return "GraphVertex [label=" + label + ", id=" + id + ", properties=" + properties + ", gId=" + gId + "]";
	}



	public static class Builder {
		private final String label;
		private String gId;
		private final Map<String, Object> properties = new HashMap<>();

		private Optional<Object> id = Optional.empty();

		public Builder(String type) {
			if (type == null) {
				throw new IllegalArgumentException("Type cannot be null");
			}

			this.label = type;
		}

		public Builder(GraphVertex object) {
			label = object.getLabel();
			id = object.getId();
			properties(object.getProperties());
			if (object.getProperties() != null
					&& object.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) != null) {
				gId = object.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME).toString();
			}else
				gId = object.getgId();
			removeGIdFromGraphVertexIfAvailable(object);
		}

		private void removeGIdFromGraphVertexIfAvailable(GraphVertex vertex) {
			if (vertex != null && vertex.getProperties() != null
					&& vertex.getProperties().get(GraphConstants.GRAPH_UUID_COLUMN_NAME) != null) {
				vertex.getProperties().remove(GraphConstants.GRAPH_UUID_COLUMN_NAME);
			}
		}
		
		public Builder key(Object key) {
			if (key == null) {
				throw new IllegalArgumentException("Key cannot be set to null");
			}

			this.id = Optional.of(key);
			return this;
		}

		public Builder property(String key, Object value) {
			if (key == null) {
				throw new IllegalArgumentException("Property key cannot be null");
			}

			properties.put(key, value);
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

		public GraphVertex build() {
			return new GraphVertex(this);
		}
	}
}
