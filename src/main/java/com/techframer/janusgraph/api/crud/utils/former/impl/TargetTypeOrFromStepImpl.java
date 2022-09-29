package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.TargetBuildOrPropertiesStep;
import com.techframer.janusgraph.api.crud.utils.former.TargetKeyStep;
import com.techframer.janusgraph.api.crud.utils.former.TargetTypeOrFromStep;

public class TargetTypeOrFromStepImpl  implements TargetTypeOrFromStep {

	private final String relationshipType;
	private final Object relationshipKey;
	private final GraphEdge relationship;
	private final GraphVertex source;

	public TargetTypeOrFromStepImpl(String relationshipType, GraphEdge relationship, Object key, GraphVertex source) {
		this.relationshipType = relationshipType;
		this.relationship = relationship;
		this.relationshipKey = key;
		this.source = source;
	}

	@Override
	public TargetKeyStep ofType(String type) {
		final GraphVertex.Builder targetBuilder = new GraphVertex.Builder(type);
		return new TargetKeyStepImpl(relationshipType, relationship, relationshipKey, source, targetBuilder);
	}
	
	@Override
	public TargetBuildOrPropertiesStep from(GraphVertex object) {
		final GraphVertex.Builder targetBuilder = new GraphVertex.Builder(object);
		return new TargetBuildOrPropertiesStepImpl(relationshipType, relationship, relationshipKey, source, targetBuilder);
	}
	
}
