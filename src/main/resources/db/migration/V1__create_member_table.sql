create table member
(id VARCHAR(36) not null,
 created_at datetime(6),
 deleted_at datetime(6),
 updated_at datetime(6),
 age integer not null,
 data json,
 name varchar(255),
 sex varchar(255),
 primary key (id)) engine=InnoDB

create INDEX idx_member_id on member (id);
