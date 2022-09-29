package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.VertexBuildOrPropertiesStep;

import java.util.Map;

public class VertexBuildOrPropertiesStepImpl implements VertexBuildOrPropertiesStep {

	private final GraphVertex.Builder builder;

	public VertexBuildOrPropertiesStepImpl(GraphVertex.Builder builder) {
		this.builder = builder;
	}

	@Override
	public GraphVertex build() {
		return builder.build();
	}

	@Override
	public VertexBuildOrPropertiesStepImpl withProperty(String key, Object value) {
		builder.property(key, value);
		return this;
	}

	@Override
	public VertexBuildOrPropertiesStepImpl withProperties(Map<String, Object> properties) {
		builder.properties(properties);
		return this;
	}
}
