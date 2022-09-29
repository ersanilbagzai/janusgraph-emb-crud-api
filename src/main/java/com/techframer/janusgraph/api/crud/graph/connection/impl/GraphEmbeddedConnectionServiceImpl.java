package com.techframer.janusgraph.api.crud.graph.connection.impl;

import com.techframer.janusgraph.api.crud.graph.connection.IGraphEmbeddedConnectionService;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.springframework.stereotype.Service;

/**
 * The Class GraphEmbeddedConnectionServiceImpl.
 */
@Service("GraphEmbeddedConnectionServiceImpl")
public class GraphEmbeddedConnectionServiceImpl implements IGraphEmbeddedConnectionService {

	@Override
	public JanusGraph open(String filePath) {
		return JanusGraphFactory.open(filePath);
	}

	@Override
	public GraphTraversalSource traversal(JanusGraph graph) {
		return graph.traversal();
	}

	@Override
	public void close(JanusGraph graph) {
		graph.close();
	}

	@Override
	public boolean isClosed(JanusGraph graph) {
		return graph.isClosed();
	}

}
