package com.atr.restfull.springbootjpa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.atr.restfull.springbootjpa.model.Employee;

public class Snippet {
	public static Specification<Employee> hasLocationNoOrLocationFilter(List<Employee> locations) {
	        return (root, query, cb) -> {
	            List<Predicate> predicates = new ArrayList<>();
	
	            for (Employee location : locations) { 
					if (Objects.nonNull(location.getId())) {
						Predicate predWithLocationID =  cb.equal(root.get(location.getId()), "locationNumber");
					} else {
	
	               Predicate locationPredicate = cb.and(cb.equal(cb.lower(root.<String>get("city")), location.getCity()),
									cb.equal(cb.lower(root.<String>get("state")), location.getState()),
									cb.greaterThanOrEqualTo(root.<Date>get("scheduleDate"), formattedFromDate),
									cb.lessThanOrEqualTo(root.<Date>get("scheduleDate"), formattedToDate));
				
					}
	
	                if (null != predWithLocationID)
	                    predicates.add(predWithLocationID);
	                else
	                    predicates.add(locationPredicate);
	
	            }//for loop
	            Predicate finalPredicate;
	     
				Predicate finalQuery = cb.or(predicates.toArray(new Predicate[0]));
	            return finalPredicate;
	        };
	    }
		
		
		
		
		
		
			private Specification<RecordView> locationPredicate(Location location) {
			return new Specification<RecordView>() {
				@Override
				public Predicate toPredicate(Root<RecordView> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					Predicate predicate = criteriaBuilder.conjunction();
					if (Objects.nonNull(location.getLocationNumber())) {
						return criteriaBuilder.equal(root.get(location.getLocationNumber()), "locationNumber");
					} else {
						if (Objects.nonNull(location.getCity())) {
							predicate = criteriaBuilder.or(predicate,
									criteriaBuilder.equal(root.get("city"), location.getCity()));
						}
						if (Objects.nonNull(location.getState())) {
							predicate = criteriaBuilder.or(predicate,
									criteriaBuilder.equal(root.get("state"), location.getState()));
						}
	
					}
					return predicate;
	
					// return criteriaBuilder.like(root.get(location.getLocationNumber()),
					// "%"+name+"%");
				}
			};
		}
}

