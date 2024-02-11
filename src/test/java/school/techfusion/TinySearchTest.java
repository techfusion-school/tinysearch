package school.techfusion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class TinySearchTest {

    private final TinySearch tinySearch = new TinySearch();

    @Test
    public void shouldIndexSimpleDocument() {
        // given
        Document helloWorldDoc = new Document("Hello World");
        tinySearch.indexDocument(helloWorldDoc);

        // when
        List<Document> result = tinySearch.searchQuery(new SearchQuery("world"));

        // then
        Assertions.assertEquals(List.of(helloWorldDoc), result);
    }

    @Test
    public void shouldNotReturnWrongDocuments() {
        // given
        Document helloWorldDoc = new Document("Hello World");
        tinySearch.indexDocument(helloWorldDoc);

        // when
        List<Document> result = tinySearch.searchQuery(new SearchQuery("foobar"));

        // then
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnTwoDocuments() {
        // given
        Document helloWorldDoc = new Document("Hello World");
        Document myNameDoc = new Document("Hello, my name is Anton");
        tinySearch.indexDocument(helloWorldDoc);
        tinySearch.indexDocument(myNameDoc);

        // when
        List<Document> result = tinySearch.searchQuery(new SearchQuery("Hello"));

        // then
        Assertions.assertEquals(Set.of(helloWorldDoc, myNameDoc), Set.copyOf(result));
    }

    @Test
    public void shouldFilterOutIrrelevantDocs() {
        // given
        List.of(new Document("one two"), new Document("three four"), new Document("third"))
                .forEach(tinySearch::indexDocument);

        // when
        List<Document> result = tinySearch.searchQuery(new SearchQuery("five four"));

        // then
        Assertions.assertTrue(result.isEmpty());
    }
}