# springboot-jpa-restful

SImple Restful Service API using Java JEE Spring Boot 


database = H2 (in memory database)

JPA to access database

clone the project and run using port 8055


```java
CriteriaBuilder qb = entityManager.getCriteriaBuilder();
CriteriaQuery<Long> cq = qb.createQuery(Long.class);
cq.select(qb.count(cq.from(MyEntity.class)));
cq.where(/*your stuff*/);
return entityManager.createQuery(cq).getSingleResult();
```

## Use Specification interface for `dynamic query`
 repository.finaddl(new specification<model>)

# Test
```java
curl http://localhost:8080/employee?firstName=a
curl http://localhost:8080/employee?lastName=a&id=1
```

## for passing value from the mvn command line to the pom file
```
mvn clean install -DvueBuild=dev
```


# pagenation:
## use &page=0 &size=10
```java
curl http://localhost:8080/employee?firstName=a&page=0&size=5
curl http://localhost:8080/employee?lastName=a&id=1&page=1&size=5
```




# Report
## Get  Agency Report
`Send list of Agencies Ids to the bellow URL:
```
http://localhost:8080/api/agencies/report
```
Type of methofe is Get,  with Array of values which is list of integer numbers of Agencies Id's:
```
http://localhost:8080/api/agencies/report?values=5,11
```
Or
```
http://localhost:8080/api/agencies/report?values=5&values=11
```

# in the Repository.calss we can use:
```java
List<AgenciesReport> findByAgencyIdIn(List<Integer> agencyIdList);
```
or 
```


## Specification combain multi Specification
```java
	/*
	 * return recordViewRepository.findAll( Where() .or() .or );
	 */
		public List<RecordView> generateRecordsReport(
				Date applicationSubmittedStartDate, 
				Date applicationSubmittedEndDate,
				Date sentStartDate, 
				Date sentEndDate, 
				Date receivedStartDate,
				Date receivedEndDate,
				Date expirationDateStart,
				Date expirationDateEnd,
				Date invoiceStartDate,
				Date invoiceEndDate, 
				Date checkRequestedStartDate,
				Date checkRequestedEndDate, 
				String action,
				String department,
				List<String> paymentMethods,
				List<RecordView> locations,
				List<RecordView> agencies,
				List<String> recordTypes,
				Pageable pageable) {
			return recordViewRepository
					.findAll(Specification.where(
							hasLocationNoOrLocationFilter(locations))
					.and(hasAgencyNumberOrAgencyId(agencies))
					//.and(isPremium())
					);
		}//Master Specification 
		
		public static Specification<RecordView> hasLocationNoOrLocationFilter(List<RecordView> locations) {
	        return (root, query, cb) -> {
	            List<Predicate> predicates = new ArrayList<>();
	            Predicate predWithLocationID = null, locationPredicate = null;
	            for (RecordView location : locations) { 
					if (Objects.nonNull(location.getLocationNumber())) {
						 predWithLocationID =  cb.equal(root.get(location.getLocationNumber()), "locationNumber");
					} else {

	                locationPredicate = 
	            		   cb.and(
	            				    cb.equal(cb.lower(root.<String>get("city")), location.getCity()),
									cb.equal(cb.lower(root.<String>get("state")), location.getState()),
									cb.equal(root.<String>get("county"), location.getCounty()));
									//cb.lessThanOrEqualTo(root.<Date>get("scheduleDate"), formattedToDate));
					}
					if (null != predWithLocationID)
	                    predicates.add(predWithLocationID);
	                else
	                    predicates.add(locationPredicate);

	            }//for loop
				Predicate finalQuery = cb.or(predicates.toArray(new Predicate[0]));
	            return finalQuery;
	        };
	    }
	
	
	public static Specification<RecordView> hasAgencyNumberOrAgencyId(List<RecordView> agencies) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (RecordView agency : agencies) {
            	Predicate pred = null;
				if (Objects.nonNull(agency.getAgencyNumber())) {
					 pred =  cb.equal(root.get(agency.getAgencyNumber()), "agencyNumber");
				} 
                if (null != pred) {
                    predicates.add(pred);
                }
            }//for loop
			Predicate finalQuery = cb.or(predicates.toArray(new Predicate[0]));
            return finalQuery;
        };
    }//hasAgencyNumberOrAgencyId

```


## For DTO Master Specification
```java
	public Page<RecordViewReportDTO> getRecordsReportSpecification(ReportDTO reportReq, Pageable pageable) {
		Page<RecordViewReportDTO> records =  recordViewReportRepository.findAll(Specification.where(
				getIndivitualFilterSpecification(reportReq))
				.and(hasLocationNoOrLocationFilter(reportReq.getLocations()))
				.and(hasRecordTypedSpecification(reportReq.getRecordTypes()))
				.and(paymentMethodsSpecification(reportReq.getPaymentMethods()))
				.and(hasAgencyNumberOrAgencyId(reportReq.getAgencies())),pageable);
		 return records;
	}
	
	public long getRecordsReportSpecificationCount(ReportDTO reportReq) {
		long records =  recordViewReportRepository.count(Specification.where(
				getIndivitualFilterSpecification(reportReq))
				.and(hasLocationNoOrLocationFilter(reportReq.getLocations()))
				.and(hasRecordTypedSpecification(reportReq.getRecordTypes()))
				.and(paymentMethodsSpecification(reportReq.getPaymentMethods()))
				.and(hasAgencyNumberOrAgencyId(reportReq.getAgencies())));
		 return records;
	}
```
