package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.utils.former.KeyStep;
import com.techframer.janusgraph.api.crud.utils.former.SourceStep;

public class EdgeKeyStepImpl implements KeyStep<SourceStep> {

	private final String type;
	private final GraphEdge relationship;

	public EdgeKeyStepImpl(String type) {
		this.type = type;
		this.relationship = null;
	}

	public EdgeKeyStepImpl(GraphEdge relationship) {
		this.type = null;
		this.relationship = relationship;
	}

	@Override
	public SourceStep withKey(Object key) {
		return new SourceStepImpl(type, relationship, key);
	}

	@Override
	public SourceStep withoutKey() {
		return new SourceStepImpl(type, relationship, null);
	}
}
