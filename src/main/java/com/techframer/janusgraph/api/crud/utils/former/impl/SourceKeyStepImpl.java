package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.SourceBuildOrPropertiesStep;
import com.techframer.janusgraph.api.crud.utils.former.SourceKeyStep;

public class SourceKeyStepImpl implements SourceKeyStep {

	private final String relationshipType;
	private final GraphEdge relationship;
	private final Object relationshipKey;
	private final GraphVertex.Builder sourceBuilder;

	public SourceKeyStepImpl(String relationshipType, GraphEdge relationship, Object relationshipKey,
							 GraphVertex.Builder sourceBuilder) {
		this.relationshipType = relationshipType;
		this.relationship = relationship;
		this.relationshipKey = relationshipKey;
		this.sourceBuilder = sourceBuilder;
	}

	@Override
	public SourceBuildOrPropertiesStep withKey(Object key) {
		sourceBuilder.key(key);
		return new SourceBuildOrPropertiesStepImpl(relationshipType, relationship, relationshipKey, sourceBuilder);
	}

	@Override
	public SourceBuildOrPropertiesStep withoutKey() {
		return new SourceBuildOrPropertiesStepImpl(relationshipType, relationship, relationshipKey, sourceBuilder);
	}

}
