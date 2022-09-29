package com.techframer.janusgraph.api.crud.utils.former;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;

public interface CreateGraphEdgeObjectable {

	public KeyStep<SourceStep> ofType(String type);
	public KeyStep<EdgeBuildOrPropertiesStep> from(GraphEdge relationship);
	
}
