package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.SourceBuildOrPropertiesStep;
import com.techframer.janusgraph.api.crud.utils.former.SourceKeyStep;
import com.techframer.janusgraph.api.crud.utils.former.SourceTypeOrFromStep;

public class SourceTypeOrFromStepImpl implements SourceTypeOrFromStep {

	private final String relationshipType;
	private final Object relationshipKey;
	private final GraphEdge relationship;

	public SourceTypeOrFromStepImpl(String relationshipType, GraphEdge relationship, Object key) {
		this.relationshipType = relationshipType;
		this.relationship = relationship;
		this.relationshipKey = key;
	}

	@Override
	public SourceKeyStep ofType(String type) {
		final GraphVertex.Builder sourceBuilder = new GraphVertex.Builder(type);
		return new SourceKeyStepImpl(relationshipType, relationship, relationshipKey, sourceBuilder);
	}

	@Override
	public SourceBuildOrPropertiesStep from(GraphVertex object) {
		final GraphVertex.Builder sourceBuilder = new GraphVertex.Builder(object);
		return new SourceBuildOrPropertiesStepImpl(relationshipType, relationship, relationshipKey, sourceBuilder);
	}

}
