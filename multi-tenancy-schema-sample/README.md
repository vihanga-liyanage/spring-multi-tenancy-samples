# spring-boot-multi-tenancy

## DB DDL
This sample works with postgre SQL.
```java
create schema multi_tenancy_en_schema collate latin1_swedish_ci;

create table city
(
	id int not null
		primary key,
	name varchar(200) not null
);

insert into multi_tenancy_en_schema.city values (1, 'English city');

create schema multi_tenancy_fr_schema collate latin1_swedish_ci;

create table city
(
	id int not null
		primary key,
	name varchar(200) not null
);


insert into multi_tenancy_fr_schema.city values (1, 'French city');
```

## Run it

```
mvn clean spring-boot:run
```

## Testing

* `curl -X GET -u vihanga:  http://localhost:8080/1 -H 'Content-Type: application/json' -H 'X-TenantID: multi_tenancy_en_schema'`
* `curl -X GET -u vihanga:  http://localhost:8080/1 -H 'Content-Type: application/json' -H 'X-TenantID: multi_tenancy_fr_schema'`
