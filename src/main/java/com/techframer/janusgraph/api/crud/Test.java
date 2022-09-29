package com.techframer.janusgraph.api.crud;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph;

import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {

        Graph graph = EmptyGraph.instance();

        GraphTraversalSource g = graph.traversal().withRemote("E:\\Janus\\apiCrud\\src\\main\\resources\\remote-graph.properties");
        System.out.println(g);
        List<Vertex> vertices = g.V().toList();
        System.out.println(vertices);
    }
}
