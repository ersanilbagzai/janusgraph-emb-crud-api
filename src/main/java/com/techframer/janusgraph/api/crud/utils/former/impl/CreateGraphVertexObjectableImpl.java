package com.techframer.janusgraph.api.crud.utils.former.impl;


import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.utils.former.CreateGraphVertexObjectable;
import com.techframer.janusgraph.api.crud.utils.former.KeyStep;
import com.techframer.janusgraph.api.crud.utils.former.VertexBuildOrPropertiesStep;

public class CreateGraphVertexObjectableImpl implements CreateGraphVertexObjectable {

	@Override
	public KeyStep<VertexBuildOrPropertiesStep> ofType(String type) {
		return new VertexKeyStepImpl(new GraphVertex.Builder(type));
	}

	@Override
	public KeyStep<VertexBuildOrPropertiesStep> from(GraphVertex object) {
		return new VertexKeyStepImpl(new GraphVertex.Builder(object));
	}

}
