package school.techfusion;

import java.util.*;

public class DocumentIndex {

    private final Map<DocumentId, Document> documents = new HashMap<>();

    private long currentDocumentId = 0;

    public List<Document> getDocuments(Collection<DocumentId> documentIds) {
        return documentIds.stream()
                .map(documents::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public DocumentId save(Document document) {
        DocumentId id = new DocumentId(currentDocumentId++);
        documents.put(id, document);
        return id;
    }
}
