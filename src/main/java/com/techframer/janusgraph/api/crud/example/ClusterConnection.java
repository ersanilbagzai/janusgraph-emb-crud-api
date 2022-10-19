package com.techframer.janusgraph.api.crud.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class ClusterConnection {

    private static final Logger logger = LogManager.getLogger(ClusterConnection.class);

    public static void main(String[] args) throws Exception {
        Cluster cluster = Cluster.open("D:\\corporate_training\\Janus\\janusgraph-emb-crud-api\\src\\main\\resources\\remote-objects.yaml");

        GraphTraversalSource g = traversal().withRemote(DriverRemoteConnection.using(cluster, "g"));
        logger.info("Getting vertex using traversal object");
        List<Vertex> vertices = g.V().toList();
        logger.info("Vertices retrieved using traversal object : {}", vertices);

        logger.info("Success");

        g.close();
        cluster.close();

    }
}
