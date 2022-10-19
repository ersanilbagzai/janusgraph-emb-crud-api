package com.techframer.janusgraph.api.crud.example.trans;

import com.techframer.janusgraph.api.crud.graph.connection.IGraphEmbeddedConnectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ThreadedTransactionTest {

    private static final Logger logger = LogManager.getLogger(ThreadedTransactionTest.class);

    @Autowired
    IGraphEmbeddedConnectionService embeddedConnection;

    public static void main(String[] args) throws InterruptedException {
        JanusGraph graph = JanusGraphFactory.open("D:\\corporate_training\\Janus\\janusgraph-emb-crud-api\\src\\main\\resources\\janusgraph-cql.properties");

        Graph threadedGraph = graph.tx().createThreadedTx();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                System.out.println("Do something with 'threadedGraph'");
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++)
            threads[i].join();

        threadedGraph.tx().commit();

        graph.close();
    }
}
