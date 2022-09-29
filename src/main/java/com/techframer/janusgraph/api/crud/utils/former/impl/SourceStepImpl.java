package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.utils.former.SourceStep;
import com.techframer.janusgraph.api.crud.utils.former.SourceTypeOrFromStep;

public class SourceStepImpl implements SourceStep {

	private final String relationshipType;
	private final Object key;
	private final GraphEdge relationship;

	public SourceStepImpl(String relationshipType, GraphEdge relationship, Object key) {
		this.relationshipType = relationshipType;
		this.key = key;
		this.relationship = relationship;
	}

	@Override
	public SourceTypeOrFromStep withSource() {
		return new SourceTypeOrFromStepImpl(relationshipType, relationship, key);
	}
	
}
