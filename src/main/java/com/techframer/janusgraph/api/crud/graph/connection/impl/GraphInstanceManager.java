package com.techframer.janusgraph.api.crud.graph.connection.impl;

import com.techframer.janusgraph.api.crud.graph.connection.IGraphEmbeddedConnectionService;
import com.techframer.janusgraph.api.crud.graph.connection.IGraphInstanceManager;
import com.techframer.janusgraph.api.crud.graph.connection.cache.IGraphInstanceCache;
import com.techframer.janusgraph.api.crud.graph.connection.cache.IGraphTraversalCache;
import com.techframer.janusgraph.api.crud.graph.connection.enums.Graph;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * The Class GraphInstanceManagerService.
 */
@Component("GraphInstanceManager")
public class GraphInstanceManager implements IGraphInstanceManager {

	@Autowired
	Environment environment;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(GraphInstanceManager.class);

	/** The instance cache. */
	@Autowired
	IGraphInstanceCache<JanusGraph> instanceCache;

	/** The traversal cache. */
	@Autowired
	IGraphTraversalCache<GraphTraversalSource> traversalCache;

	/** The embedded connection. */
	@Autowired
	IGraphEmbeddedConnectionService embeddedConnection;

	@Override
	public JanusGraph getGraphInstance() {
		return instanceCache.get(Graph.DEFAULT_GRAPH.toString());
	}

	@Override
	public GraphTraversalSource getGraphTraversal() {
		return traversalCache.get(Graph.DEFAULT_GRAPH.toString());
	}

	@Override
	public boolean refreshGraph() {
		try {
			JanusGraph janusGraph = instanceCache.get(Graph.DEFAULT_GRAPH.toString());
			if (janusGraph != null && janusGraph.isOpen()) {
				embeddedConnection.close(janusGraph);
			}
			instanceCache.clear();
			traversalCache.clear();
			initGraph();
		} catch (Exception e) {
			logger.error("Exception in refreshGraph : {}", ExceptionUtils.getStackTrace(e));
			return false;
		}
		return true;
	}

	@PostConstruct
	public void initGraph() {
		try {
			String filePath = environment.getProperty("graphPropertyFilePath");
			logger.info("property file path : {}",filePath);
			JanusGraph graph = embeddedConnection.open(filePath);
			logger.info("GraphInstance : {}",graph);
			instanceCache.add(Graph.DEFAULT_GRAPH.toString(), graph);
			logger.info("instance cache updated : {}",instanceCache.get(Graph.DEFAULT_GRAPH.toString()));
			GraphTraversalSource traversal = graph.traversal();
			logger.info("GraphTraversal : {}",traversal);
			traversalCache.add(Graph.DEFAULT_GRAPH.toString(), traversal);
			logger.info("traversal cache updated : {}",instanceCache.get(Graph.DEFAULT_GRAPH.toString()));
		} catch (Exception e) {
			logger.error("Exception in initGraph for date :: {} :: {}", new Date(), ExceptionUtils.getStackTrace(e));
		}
	}
}
