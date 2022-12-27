package querqy;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
public class QueryConfig {

    @Builder.Default private final String boolNodeName = "bool";
    @Builder.Default private final String dismaxNodeName = "nestedDismax";
    @Builder.Default private final String constantScoreNodeName = "constantScore";

    private final List<FieldConfig> fields;

    private final Float tie;
    private final String minimumShouldMatch;

    public boolean hasMinimumShouldMatch() {
        return minimumShouldMatch != null;
    }

    public boolean hasTie() {
        return tie != null;
    }

    public static QueryConfig empty() {
        return QueryConfig.builder().build();
    }

    public static class QueryConfigBuilder {
        private final Set<String> fieldNames = new HashSet<>();

        public QueryConfig.QueryConfigBuilder field(final String fieldName, final float weight) {
            return field(
                    FieldConfig.builder()
                            .fieldName(fieldName)
                            .weight(weight)
                            .build()
            );
        }

        public QueryConfig.QueryConfigBuilder field(final FieldConfig field) {
            if (this.fields == null) {
                this.fields = new ArrayList<>();
            }

            final String fieldName = field.getFieldName();
            if (fieldNames.contains(fieldName)) {
                throw new IllegalArgumentException("Duplicate field " + fieldName);
            }
            fieldNames.add(fieldName);

            this.fields.add(field);
            return this;
        }

    }

}
