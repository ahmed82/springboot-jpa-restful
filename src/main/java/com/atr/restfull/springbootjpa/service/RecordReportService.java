package com.atr.restfull.springbootjpa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.atr.project.model.RecordView;
import com.atr.project.model.ReportAgenciesDTO;
import com.atr.project.model.ReportDTO;
import com.atr.project.model.ReportLocationDTO;
import com.atr.project.repository.RecordViewRepository;

@Service
public class RecordReportService {

	@Autowired
	RecordViewRepository recordViewRepository;

	private Specification<RecordView> getIndivitualSpecification(Date applicationSubmittedStartDate,
			Integer departmentId) {
		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();

			if (Objects.nonNull(applicationSubmittedStartDate)) {
				predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.<Date>get("applicationSubmittedDate"),
						applicationSubmittedStartDate));
			}
			if (Objects.nonNull(departmentId)) {
				predicate = cb.and(predicate, cb.equal(root.get("departmentId"), departmentId));
			}
			return predicate;
		};
	}

	public static Specification<RecordView> hasLocationNoOrLocationFilter(List<ReportLocationDTO> locations) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			Predicate predWithLocationID = null, locationPredicate = null;
			for (ReportLocationDTO location : locations) {
				if (Objects.nonNull(location.getLocationNumber())) {
					predWithLocationID = cb.equal(root.get("locationNumber"), location.getLocationNumber());
				} else {

					if (Objects.nonNull(location.getCity())) {
						predWithLocationID = cb.and(cb.equal(root.get("city"), location.getCity()));
					}
					if (Objects.nonNull(location.getState())) {
						predWithLocationID = cb.and(cb.equal(root.get("state"), location.getState()));
					}
					if (Objects.nonNull(location.getCounty())) {
						predWithLocationID = cb.and(cb.equal(root.get("county"), location.getCounty()));
					}
					if (Objects.nonNull(location.getZip())) {
						predWithLocationID = cb.and(cb.equal(root.get("zip"), location.getZip()));
					}
					if (Objects.nonNull(location.getBannerId())) {
						predWithLocationID = cb.and(cb.equal(root.get("bannerId"), location.getBannerId()));
					}
					if (Objects.nonNull(location.getDivisionId())) {
						predWithLocationID = cb.and(cb.equal(root.get("divisionId"), location.getDivisionId()));
					}
					if (Objects.nonNull(location.getRegionId())) {
						predWithLocationID = cb.and(cb.equal(root.get("regionId"), location.getRegionId()));
					}
					if (Objects.nonNull(location.getDistrictId())) {
						predWithLocationID = cb.and(cb.equal(root.get("districtId"), location.getDistrictId()));
					}
				}
				if (null != predWithLocationID)
					predicates.add(predWithLocationID);
				else if (null != locationPredicate)
					predicates.add(locationPredicate);

			} // for loop
			Predicate finalQuery = cb.or(predicates.toArray(new Predicate[0]));
			return finalQuery;
		};
	}// hasLocationNoOrLocationFilter

	public static Specification<RecordView> hasAgencyNumberOrAgencyId(List<ReportAgenciesDTO> agencies) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			for (ReportAgenciesDTO agency : agencies) {
				Predicate pred = null;
				if (Objects.nonNull(agency.getAgencyNumber())) {
					pred = cb.equal(root.get(agency.getAgencyNumber()), "agencyNumber");
				}
				if (null != pred) {
					predicates.add(pred);
				}
			} // for loop
			Predicate finalQuery = cb.or(predicates.toArray(new Predicate[0]));
			return finalQuery;
		};
	}// hasAgencyNumberOrAgencyId

	/// For DTO Master Specification
	public List<RecordView> getRecordsReportSpecification(ReportDTO reportReq) {
		return recordViewRepository.findAll(Specification
				.where(getIndivitualSpecification(reportReq.getApplicationSubmittedStartDate(),
						reportReq.getDepartmentId()))
				.and(hasLocationNoOrLocationFilter(reportReq.getLocations())
						.and(hasAgencyNumberOrAgencyId(reportReq.getAgencies()))));
		// .and(isPremium())

	}

}
