create schema edasrc;
	
create table edasrc.tasks (
	id serial primary key,
	title varchar(20),
	completed char(1),
	user_id varchar(20)
);

insert into edasrc.tasks (title, completed)
select * from (
	select 'task 1', 'N' union 
	select 'task 2', 'N' union
	select 'task 3', 'N' union
	select 'task 4', 'N' union
	select 'task 5', 'N'
);

delete from edasrc.tasks where id > 5;

select* from edasrc.tasks order by id asc;
