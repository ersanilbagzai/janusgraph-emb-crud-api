package com.techframer.janusgraph.api.crud.utils.former;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;

public interface SourceFromStep {
	public SourceBuildOrPropertiesStep from(GraphVertex object);
}
