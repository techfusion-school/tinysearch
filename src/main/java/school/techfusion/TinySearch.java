package school.techfusion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TinySearch {

    private final TermProducer termProducer = new TermProducer();
    private final InvertedIndex invertedIndex = new InvertedIndex();
    private final DocumentIndex documentIndex = new DocumentIndex();

    public List<Document> searchQuery(SearchQuery searchQuery) {
        List<Term> terms = termProducer.produceTerms(searchQuery.query());

        Set<DocumentId> currentDocumentIds = new HashSet<>();
        boolean firstTerm = true;
        for (Term term : terms) {
            List<DocumentId> documentIds = invertedIndex.getDocumentIds(term);

            if (firstTerm) {
                currentDocumentIds = Set.copyOf(documentIds);
                firstTerm = false;
            } else {
                currentDocumentIds = intersect(currentDocumentIds, Set.copyOf(documentIds));
            }
        }
        return documentIndex.getDocuments(currentDocumentIds);
    }

    public void indexDocument(Document document) {
        DocumentId documentId = documentIndex.save(document);
        List<Term> terms = termProducer.produceTerms(document.text());
        invertedIndex.saveTerms(terms, documentId);
    }

    private Set<DocumentId> intersect(Set<DocumentId> currentDocumentIds, Set<DocumentId> documentIds) {
        Set<DocumentId> result = new HashSet<>();

        for (DocumentId documentId : documentIds) {
            if (currentDocumentIds.contains(documentId)) {
                result.add(documentId);
            }
        }

        return result;
    }
}
