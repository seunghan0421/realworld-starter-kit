insert into profile (user_id, profile_id) values (1, 1);
insert into profile (user_id, profile_id) values (2, 2);

insert into profile_jpa_entity_followees (profile_jpa_entity_profile_id, followees)
    values (1, 2)
