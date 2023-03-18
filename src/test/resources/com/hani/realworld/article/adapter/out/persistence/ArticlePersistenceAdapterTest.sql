insert into article (article_id, author_id, slug, title, description, body, created_at, updated_at)
    values (1, 1, 'user1-article', 'user1 article', 'user1 description', 'user1 body', now(), now());
insert into article (article_id, author_id, slug, title, description, body, created_at, updated_at)
    values (2, 2, 'user2-article', 'user2 article', 'user2 description', 'user2 body', now(), now());

insert into article_jpa_entity_users (article_jpa_entity_article_id, users)
    values(1, 1);
insert into article_jpa_entity_users (article_jpa_entity_article_id, users)
    values(2, 1);

insert into tag (article_id, tag_value) values (1, 'user1');
insert into tag (article_id, tag_value) values (2, 'user2');
