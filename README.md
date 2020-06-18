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

