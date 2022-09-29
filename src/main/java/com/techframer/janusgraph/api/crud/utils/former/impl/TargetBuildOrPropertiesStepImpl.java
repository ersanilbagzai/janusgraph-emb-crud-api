package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.EdgeBuildOrPropertiesStep;
import com.techframer.janusgraph.api.crud.utils.former.TargetBuildOrPropertiesStep;

public class TargetBuildOrPropertiesStepImpl implements TargetBuildOrPropertiesStep {

	private final String relationshipType;
	private final GraphEdge relationship;
	private final Object relationshipKey;
	private final GraphVertex source;
	private final GraphVertex.Builder targetBuilder;

	public TargetBuildOrPropertiesStepImpl(String relationshipType, GraphEdge relationship, Object relationshipKey,
			GraphVertex source, GraphVertex.Builder targetBuilder) {
		this.relationshipType = relationshipType;
		this.relationship = relationship;
		this.relationshipKey = relationshipKey;
		this.source = source;
		this.targetBuilder = targetBuilder;
	}

	@Override
	public TargetBuildOrPropertiesStep withProperty(String key, Object value) {
		targetBuilder.property(key, value);
		return this;
	}

	@Override
	public EdgeBuildOrPropertiesStep build() {

		final GraphEdge.Builder relationshipBuilder;

		if (relationship != null)
			relationshipBuilder = new GraphEdge.Builder(relationship);
		else
			relationshipBuilder = new GraphEdge.Builder(source, targetBuilder.build(), relationshipType);

		if (relationshipKey != null)
			relationshipBuilder.key(relationshipKey);

		return new EdgeBuildOrPropertiesStepImpl(relationshipBuilder);
	}

}
