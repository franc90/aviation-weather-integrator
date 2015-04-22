package pl.edu.agh.awi.persistence.model;


import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UniquePropertyExtractorTest {

    @Test
    public void shouldExtractIndexedProperties() {
        String indexName = "someIndex";
        String indexValue = "value";
        NodeWithIndex nodeWithIndex = new NodeWithIndex();
        nodeWithIndex.setSomeIndex(indexValue);
        Map<String, Object> extracted = UniquePropertyExtractor.extractUniqueProperties(nodeWithIndex);
        assertTrue(extracted.containsKey(indexName));
        assertEquals(indexValue, extracted.get(indexName));

    }

    private class NodeWithIndex {

        @Unique
        private String someIndex;

        public String getSomeIndex() {
            return someIndex;
        }

        public void setSomeIndex(String someIndex) {
            this.someIndex = someIndex;
        }
    }
}