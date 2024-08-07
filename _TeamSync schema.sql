CREATE TABLE `User` (
  `id` integer PRIMARY KEY,
  `user_name` varchar(255),
  `email` varchar(255),
  `phone` varchar(255),
  `password` varchar(255),
  `role` enum(SCRUM_MASTER,DEVELOPER,NORMAL_USER,TESTER),
  `depatment_id` integer
);

CREATE TABLE `Project` (
  `id` integer PRIMARY KEY,
  `project_name` varchar(255),
  `project_description` varchar(255),
  `project_creation_date` timestamp,
  `project_status` varchar(255),
  `project_created_by` integer
);

CREATE TABLE `Task` (
  `id` integer PRIMARY KEY,
  `project_id` integer,
  `task_name` varchar(255),
  `task_description` varchar(255),
  `task_creation_date` timestamp,
  `task_start_date` timestamp,
  `task_end_date` timestamp,
  `task_status` varchar(255),
  `task_created_by` integer,
  `due_date` date
);

CREATE TABLE `Resource` (
  `id` integer PRIMARY KEY,
  `user_id` integer,
  `available_status` boolean,
  `skills` varchar(255),
  `shift_working_in` varchar(255),
  `task_id` integer,
  `subtask_id` integer
);

CREATE TABLE `File` (
  `id` integer PRIMARY KEY,
  `task_id` integer,
  `user_id` integer,
  `file_name` varchar(255),
  `file_path` varchar(255),
  `uploading_time` timestamp
);

CREATE TABLE `Notification` (
  `id` integer PRIMARY KEY,
  `message` varchar(255),
  `send_at` timestamp,
  `task_id` integer
);

CREATE TABLE `Comments` (
  `id` integer PRIMARY KEY,
  `comment` varchar(255),
  `created_at` timestamp,
  `user_id` integer,
  `task_id` integer
);

CREATE TABLE `Observer` (
  `id` integer PRIMARY KEY,
  `notification_type` varchar(255),
  `subscription_status` varchar(255),
  `task_id` integer
);

CREATE TABLE `Milestone` (
  `id` integer PRIMARY KEY,
  `milestone_name` varchar(255),
  `milestone_date` timestamp,
  `project_id` integer,
  `status` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `Template` (
  `id` integer PRIMARY KEY,
  `template_name` varchar(255)
);

CREATE TABLE `TemplateAndTask` (
  `id` integer PRIMARY KEY,
  `project_id` integer,
  `template_id` integer
);

ALTER TABLE `Resource` ADD FOREIGN KEY (`id`) REFERENCES `User` (`id`);

ALTER TABLE `Task` ADD FOREIGN KEY (`id`) REFERENCES `Resource` (`id`);

ALTER TABLE `Observer` ADD FOREIGN KEY (`id`) REFERENCES `Task` (`id`);

ALTER TABLE `Task` ADD FOREIGN KEY (`id`) REFERENCES `Project` (`id`);

ALTER TABLE `Milestone` ADD FOREIGN KEY (`id`) REFERENCES `Project` (`id`);

ALTER TABLE `Template` ADD FOREIGN KEY (`id`) REFERENCES `TemplateAndTask` (`id`);

ALTER TABLE `Task` ADD FOREIGN KEY (`id`) REFERENCES `TemplateAndTask` (`id`);

ALTER TABLE `Notification` ADD FOREIGN KEY (`id`) REFERENCES `Task` (`id`);

ALTER TABLE `File` ADD FOREIGN KEY (`id`) REFERENCES `User` (`id`);

ALTER TABLE `Comments` ADD FOREIGN KEY (`id`) REFERENCES `User` (`id`);

ALTER TABLE `File` ADD FOREIGN KEY (`id`) REFERENCES `Task` (`id`);

ALTER TABLE `Comments` ADD FOREIGN KEY (`id`) REFERENCES `Task` (`id`);
