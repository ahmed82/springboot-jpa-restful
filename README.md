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


## pagenation:
use &page=0 &size=10
```java
curl http://localhost:8080/employee?firstName=a&page=0&size=5
curl http://localhost:8080/employee?lastName=a&id=1&page=1&size=5
```

## Specification combain multi Specification

