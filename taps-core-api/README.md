# DB TABLE 정리

## DDL
### TBL_USER_INFO
```sql
create table taps.tbl_user_info (
    user_no bigint not null,
    user_email varchar(255) not null,
    ins_date timestamp not null,
    ins_oprt varchar(255) not null,
    upd_date timestamp not null,
    upd_oprt varchar(255) not null,
    primary key (user_no)
)
```

### TBL_USER_INFO_HISTORY
```sql
create table taps.tbl_user_info_history (
    id bigint not null,
    user_email varchar(255) not null,
    ins_date timestamp not null,
    ins_oprt varchar(255) not null,
    primary key (id)
)
```

### TBL_USER_CREDENTIAL
```sql
create table taps.tbl_user_credential (
  id bigint not null,
  user_no bigint not null,
  service_type varchar(30) not null,
  client_id varchar(4096) not null,
  client_secret varchar(4096) not null,
  ins_date timestamp not null,
  ins_oprt varchar(255) not null,
  upd_date timestamp not null,
  upd_oprt varchar(255) not null,
  primary key (id)
)

alter table taps.tbl_user_credential
    add constraint FK_TBL_USER_CREDENTIAL_USER_NO
        foreign key (user_no)
            references taps.tbl_user_info
```

### TBL_USER_CREDENTIAL_HISTORY
```sql
create table taps.tbl_user_credential_history (
  id bigint not null,
  user_no bigint not null,
  service_type varchar(30) not null,
  client_id varchar(4096) not null,
  client_secret varchar(4096) not null,
  ins_date timestamp not null,
  ins_oprt varchar(255) not null,
  primary key (id)
)
```
