package xyz.javista.core.specification;

import org.springframework.data.jpa.domain.Specification;
import xyz.javista.core.domain.Order;
import xyz.javista.core.query.GetOrderListQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;


public class OrderSpecification implements Specification<Order> {

    private GetOrderListQuery query;

    public OrderSpecification(GetOrderListQuery query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if(query.getEndDate()!=null){
            return criteriaBuilder.greaterThan(root.get("endDatetime"), LocalDateTime.parse(query.getEndDate()));
        }
        return null;
    }
}
