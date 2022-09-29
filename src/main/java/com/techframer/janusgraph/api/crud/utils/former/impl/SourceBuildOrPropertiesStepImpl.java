package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.SourceBuildOrPropertiesStep;
import com.techframer.janusgraph.api.crud.utils.former.TargetStep;
import com.techframer.janusgraph.api.crud.utils.former.TargetStepImpl;

public class SourceBuildOrPropertiesStepImpl implements SourceBuildOrPropertiesStep {

	private GraphVertex.Builder sourceBuilder;
	private Object relationshipKey;
	private GraphEdge relationship;
	private String relationshipType;

	public SourceBuildOrPropertiesStepImpl(String relationshipType, GraphEdge relationship,
			Object relationshipKey, GraphVertex.Builder sourceBuilder) {
		this.relationshipType = relationshipType;
		this.relationship = relationship;
		this.relationshipKey = relationshipKey;
		this.sourceBuilder = sourceBuilder;
	}

	@Override
	public SourceBuildOrPropertiesStep withProperty(String key, Object value) {
		sourceBuilder.property(key, value);
		return this;
	}
	
	@Override
	public TargetStep build() {
		return new TargetStepImpl(relationshipType, relationship, relationshipKey, sourceBuilder.build());
	}
	
}
