package com.techframer.janusgraph.api.crud.utils.former;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.impl.TargetTypeOrFromStepImpl;

public class TargetStepImpl implements TargetStep {

	private String relationshipType;
	private GraphEdge relationship;
	private Object relationshipKey;
	private GraphVertex source;

	public TargetStepImpl(String relationshipType, GraphEdge relationship, Object relationshipKey,
			GraphVertex source) {
		this.relationshipType = relationshipType;
		this.relationship = relationship;
		this.relationshipKey = relationshipKey;
		this.source = source;
	}

	@Override
	public TargetTypeOrFromStep withTarget() {
		return new TargetTypeOrFromStepImpl(relationshipType, relationship, relationshipKey, source);
	}
	
}
