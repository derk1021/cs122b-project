select max(substring(id, 3)) as id from stars;

SELECT MAX(id) as id FROM genres;

select max(substring(id, 3)) as id from movies;

SELECT id,name FROM genres;

insert into movies values(?,?,?,?);

insert into genres_in_movies values(?,?);

select * from movies where id in (select movie_id from stars_in_movies where star_id=?);