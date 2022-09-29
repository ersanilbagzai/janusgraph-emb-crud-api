package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.utils.former.EdgeBuildOrPropertiesStep;

import java.util.Map;

public class EdgeBuildOrPropertiesStepImpl implements EdgeBuildOrPropertiesStep {

	private final GraphEdge.Builder relationshipBuilder;

	public EdgeBuildOrPropertiesStepImpl(GraphEdge.Builder relationshipBuilder) {
		this.relationshipBuilder = relationshipBuilder;
	}

	@Override
	public GraphEdge build() {
		return relationshipBuilder.build();
	}

	@Override
	public EdgeBuildOrPropertiesStep withProperty(String key, Object value) {
		relationshipBuilder.property(key, value);
		return this;
	}

	@Override
	public EdgeBuildOrPropertiesStep withProperties(Map<String, Object> properties) {
		relationshipBuilder.properties(properties);
		return this;
	}

}
