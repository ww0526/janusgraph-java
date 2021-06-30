import jline.internal.Log;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.EmptyPath;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.attribute.Geo;
import org.janusgraph.core.attribute.Geoshape;
import org.janusgraph.example.GraphOfTheGodsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class JanusgraphUsingJava {
    private static final Logger LOGGER = LoggerFactory.getLogger(JanusgraphUsingJava.class);
    public static void main(String[] args) {
        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-berkeleyje-lucene.properties");
        GraphTraversalSource g = graph.traversal();
        if (g.V().count().next() == 0) {
            // load the schema and graph data
            GraphOfTheGodsFactory.load(graph);
        }
        Map<Object, Object> saturnProps = g.V().has("name", "saturn").valueMap(true).next();
        LOGGER.info(saturnProps.toString());
        List<Edge> places = g.E().has("place", Geo.geoWithin(Geoshape.circle(37.97, 23.72, 50))).toList();
        LOGGER.info(places.toString());
        System.exit(0);
    }
}
