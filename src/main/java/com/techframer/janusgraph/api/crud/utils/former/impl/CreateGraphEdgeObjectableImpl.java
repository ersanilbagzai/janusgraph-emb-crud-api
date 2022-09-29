package com.techframer.janusgraph.api.crud.utils.former.impl;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.utils.former.CreateGraphEdgeObjectable;
import com.techframer.janusgraph.api.crud.utils.former.EdgeBuildOrPropertiesStep;
import com.techframer.janusgraph.api.crud.utils.former.KeyStep;
import com.techframer.janusgraph.api.crud.utils.former.SourceStep;

public class CreateGraphEdgeObjectableImpl implements CreateGraphEdgeObjectable {

	@Override
	public KeyStep<SourceStep> ofType(String type) {
		return new EdgeKeyStepImpl(type);
	}

	@Override
	public KeyStep<EdgeBuildOrPropertiesStep> from(GraphEdge relationship) {
		return new KeyStep<EdgeBuildOrPropertiesStep> () {

			@Override
			public EdgeBuildOrPropertiesStep withKey(Object key) {
				return new EdgeBuildOrPropertiesStepImpl(new GraphEdge.Builder(relationship).key(key));
			}

			@Override
			public EdgeBuildOrPropertiesStep withoutKey() {
				return new EdgeBuildOrPropertiesStepImpl(new GraphEdge.Builder(relationship));
			}
		};
	}
	
}
