package by.alexander.backend.dao.specification;

import by.alexander.backend.entity.Forklift;
import jakarta.persistence.criteria.Predicate;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static by.alexander.backend.util.ConstantsUtil.PERCENT;

@Setter
public class ForkliftSpecification {

    private final static String FIELD_NAME_NUMBER = "number";

    public static Specification<Forklift> withFilter(String number) {
        return (root, query, cb) -> {
            Predicate predicate = cb.isTrue(cb.literal(true));
            if (number != null) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get(FIELD_NAME_NUMBER)),PERCENT + number.toLowerCase() + PERCENT));
            }
            return predicate;
        };
    }
}
