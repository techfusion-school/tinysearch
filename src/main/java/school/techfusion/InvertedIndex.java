package school.techfusion;

import java.util.*;

public class InvertedIndex {

    private final Map<Term, List<DocumentId>> storage = new HashMap<>();

    public List<DocumentId> getDocumentIds(Term term) {
        return Objects.requireNonNullElseGet(storage.get(term), List::of);
    }

    public void saveTerms(List<Term> terms, DocumentId documentId) {
        for (Term term : terms) {
            storage.compute(term, (__, prev) -> {
                if (prev == null) {
                    prev = new ArrayList<>();
                }
                prev.add(documentId);
                return prev;
            });
        }
    }
}
