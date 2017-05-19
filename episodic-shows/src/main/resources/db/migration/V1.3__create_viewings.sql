create table viewings (
  id bigint not null auto_increment primary key,
  user_id bigint not null,
  show_id bigint not null,
  episode_id bigint not null,
  updated_at timestamp not null,
  time_code int not null,
  UNIQUE KEY `idx_user_show_unique` (`user_id`,`show_id`)
);