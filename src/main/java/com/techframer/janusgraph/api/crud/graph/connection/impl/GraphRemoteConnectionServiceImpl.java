package com.techframer.janusgraph.api.crud.graph.connection.impl;

import com.techframer.janusgraph.api.crud.graph.connection.IGraphRemoteConnectionService;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.janusgraph.core.JanusGraph;
import org.springframework.stereotype.Service;

/**
 * The Class GraphRemoteConnectionServiceImpl.
 */
@Service("GraphRemoteConnectionServiceImpl")
public class GraphRemoteConnectionServiceImpl implements IGraphRemoteConnectionService {

	@Override
	public JanusGraph open(String filePath) {
		/*try {
			return AnonymousTraversalSource.traversal().withRemote(filePath).getGraph();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}*/
		return null;
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
