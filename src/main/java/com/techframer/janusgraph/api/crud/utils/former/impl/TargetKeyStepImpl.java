package com.techframer.janusgraph.api.crud.utils.former.impl;


import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.TargetBuildOrPropertiesStep;
import com.techframer.janusgraph.api.crud.utils.former.TargetKeyStep;

public class TargetKeyStepImpl implements TargetKeyStep {

	private final String relationshipType;
	private final Object relationshipKey;
	private final GraphEdge relationship;
	private final GraphVertex source;
	private final GraphVertex.Builder targetBuilder;

	public TargetKeyStepImpl(String relationshipType, GraphEdge relationship, Object key, GraphVertex source,
			GraphVertex.Builder targetBuilder) {
		this.relationshipType = relationshipType;
		this.relationship = relationship;
		this.relationshipKey = key;
		this.source = source;
		this.targetBuilder = targetBuilder;
	}

	@Override
	public TargetBuildOrPropertiesStep withKey(Object key) {
		targetBuilder.key(key);
		return new TargetBuildOrPropertiesStepImpl(relationshipType, relationship, relationshipKey, source,
				targetBuilder);
	}

	@Override
	public TargetBuildOrPropertiesStep withoutKey() {
		return new TargetBuildOrPropertiesStepImpl(relationshipType, relationship, relationshipKey, source,
				targetBuilder);
	}

}
