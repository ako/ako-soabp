create table relation_types (
  code varchar(10) primary key
, description varchar(100) unique
);
create table addresses (
  id numeric primary key
, street varchar(100)
, housenumber varchar(10)
, city varchar(100)
, country varchar(100)
);
create table relations (
  id numeric primary key
, firstname varchar(100)
, lastname varchar(100)
, relation_type_code varchar(10)
, address_id numeric
);
alter table relations add constraint relation_type_fk
  foreign key (relation_type_code) references relation_types(code);
alter table relations add constraint address_id_fk
  foreign key (address_id) references addresses(id);

create sequence relation_id_seq;
create sequence address_id_seq;
