package com.techframer.janusgraph.api.crud.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class RemoteConnection {

    private static final Logger logger = LogManager.getLogger(RemoteConnection.class);

    public static void main(String[] args) throws Exception {

        logger.info("Connection to Remote Graph and getting traversal");
        GraphTraversalSource g = traversal().withRemote("D:\\corporate_training\\Janus\\janusgraph-emb-crud-api\\src\\main\\resources\\remote-graph.properties");
        logger.info("Connection to Remote Graph Successful and Graph traversal : {} ", g);

        logger.info("Getting vertex using traversal object");
        List<Vertex> vertices = g.V().toList();
        logger.info("Vertices retrieved using traversal object : {}", vertices);

        logger.info("Success");

        g.close();

    }
}
