INSERT INTO public.ORM_USER(name,password,salt,email,phone_number) VALUES ( 'user_1', 'ff342e862e7c3285cdc07e56d6b8973b', '412365a109674b2dbb1981ed561a4c70', 'user1@xkcoding.com', '17300000001');
INSERT INTO public.ORM_USER(name,password,salt,email,phone_number) VALUES ('user_2', '6c6bf02c8d5d3d128f34b1700cb1e32c', 'fcbdd0e8a9404a5585ea4e01d0e4d7a0', 'user2@xkcoding.com', '17300000002');

INSERT INTO public.share_email_detail(id, name, creator, create_time, email_data, receivers, content, subject, status, last_update_time, send_type) VALUES (1, '1', '1', '2020-04-11', '1', '1', '1', '1', 1, '2020-04-11', 'USER');
INSERT INTO public.share_email_detail(id, name, creator, create_time, email_data, receivers, content, subject, status, last_update_time, send_type) VALUES (2, 'DDD', 'DDD', '2020-04-10', '1222', '1', '1', '1', 1, '2020-04-02', 'ORM_USER');
