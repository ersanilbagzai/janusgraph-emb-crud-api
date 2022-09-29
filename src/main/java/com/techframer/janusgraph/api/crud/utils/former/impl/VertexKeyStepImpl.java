package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.KeyStep;
import com.techframer.janusgraph.api.crud.utils.former.VertexBuildOrPropertiesStep;

public class VertexKeyStepImpl implements KeyStep<VertexBuildOrPropertiesStep> {

	private final GraphVertex.Builder builder;

	public VertexKeyStepImpl(GraphVertex.Builder builder) {
		this.builder = builder;
	}

	@Override
	public VertexBuildOrPropertiesStep withKey(Object key) {
		builder.key(key);
		return new VertexBuildOrPropertiesStepImpl(builder);
	}

	@Override
	public VertexBuildOrPropertiesStep withoutKey() {
		return new VertexBuildOrPropertiesStepImpl(builder);
	}

}
