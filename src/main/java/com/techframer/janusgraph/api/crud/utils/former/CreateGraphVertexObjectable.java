package com.techframer.janusgraph.api.crud.utils.former;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;

public interface CreateGraphVertexObjectable {

	public KeyStep<VertexBuildOrPropertiesStep> ofType(String type);
	public KeyStep<VertexBuildOrPropertiesStep> from(GraphVertex object);
	
}
