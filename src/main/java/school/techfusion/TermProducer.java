package school.techfusion;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TermProducer {

    public List<Term> produceTerms(String searchQuery) {
        return Arrays.stream(searchQuery.split("[\\s,]+"))
                .map(part -> new Term(part.toLowerCase(new Locale("ru"))))
                .toList();
    }
}
