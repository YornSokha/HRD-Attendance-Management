/*
 Navicat Premium Data Transfer

 Source Server         : postgres
 Source Server Type    : PostgreSQL
 Source Server Version : 90100
 Source Host           : localhost:5432
 Source Catalog        : db_somchbab
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90100
 File Encoding         : 65001

 Date: 14/08/2019 15:29:47
*/


-- ----------------------------
-- Sequence structure for tb_academic_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_academic_id_seq";
CREATE SEQUENCE "public"."tb_academic_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tb_attendance_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_attendance_id_seq";
CREATE SEQUENCE "public"."tb_attendance_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tb_class_names_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_class_names_id_seq";
CREATE SEQUENCE "public"."tb_class_names_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tb_classenrolls_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_classenrolls_id_seq";
CREATE SEQUENCE "public"."tb_classenrolls_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tb_classroom_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_classroom_id_seq";
CREATE SEQUENCE "public"."tb_classroom_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tb_course_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_course_id_seq";
CREATE SEQUENCE "public"."tb_course_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tb_generation_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_generation_id_seq";
CREATE SEQUENCE "public"."tb_generation_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tb_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_role_id_seq";
CREATE SEQUENCE "public"."tb_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tb_teacher_assignments_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_teacher_assignments_id_seq";
CREATE SEQUENCE "public"."tb_teacher_assignments_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tb_users_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tb_users_id_seq";
CREATE SEQUENCE "public"."tb_users_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for sc_academics
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_academics";
CREATE TABLE "public"."sc_academics" (
  "id" int4 NOT NULL DEFAULT nextval('tb_academic_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "status" bool NOT NULL DEFAULT true,
  "deleted" bool NOT NULL DEFAULT false
)
;
COMMENT ON COLUMN "public"."sc_academics"."status" IS 'true = active, false = inactive, default = true';

-- ----------------------------
-- Records of sc_academics
-- ----------------------------
INSERT INTO "public"."sc_academics" VALUES (1, 'ITE', 't', 'f');
INSERT INTO "public"."sc_academics" VALUES (21, 'CTEC', 't', 'f');
INSERT INTO "public"."sc_academics" VALUES (22, 'RUPP', 't', 'f');
INSERT INTO "public"."sc_academics" VALUES (25, 'CKCC', 't', 'f');
INSERT INTO "public"."sc_academics" VALUES (26, 'a', 't', 'f');
INSERT INTO "public"."sc_academics" VALUES (27, 'a', 't', 'f');

-- ----------------------------
-- Table structure for sc_attendances
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_attendances";
CREATE TABLE "public"."sc_attendances" (
  "id" int4 NOT NULL DEFAULT nextval('tb_attendance_id_seq'::regclass),
  "classenroll_id" int4 NOT NULL,
  "date_from" varchar(15) COLLATE "pg_catalog"."default",
  "date_to" varchar(15) COLLATE "pg_catalog"."default",
  "leave_status" varchar(2) COLLATE "pg_catalog"."default" NOT NULL,
  "reason" varchar(255) COLLATE "pg_catalog"."default",
  "teacher_response_status" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'p'::bpchar,
  "admin_response_status" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'p'::bpchar,
  "leave_time" varchar(6) COLLATE "pg_catalog"."default",
  "arrive_time" varchar(6) COLLATE "pg_catalog"."default",
  "am_pm" varchar(2) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'am'::character varying,
  "deleted" bool NOT NULL DEFAULT false,
  "permission_count" int2 NOT NULL DEFAULT 0,
  "status" varchar(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'p'::character varying,
  "duration" int2,
  "created_at" timestamp(6)
)
;
COMMENT ON COLUMN "public"."sc_attendances"."leave_status" IS 'p = permission,l = late, g = go outside, le = leave early';
COMMENT ON COLUMN "public"."sc_attendances"."teacher_response_status" IS 'p = pending, r = rejected, a = accepted';
COMMENT ON COLUMN "public"."sc_attendances"."admin_response_status" IS 'p = pending, r = rejected, a = accepted';
COMMENT ON COLUMN "public"."sc_attendances"."status" IS 'p = pending, r = rejected, a = accepted';

-- ----------------------------
-- Records of sc_attendances
-- ----------------------------
INSERT INTO "public"."sc_attendances" VALUES (35, 70, '2019-08-07', '2019-08-07', 'g', 'To School', 'p', 'a', '04:08', '04:10', 'AM', 'f', 0, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (41, 90, '2019-08-13', '2019-08-13', 'p', 'fds', 'p', 'p', '', '', 'AM', 'f', 1, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (42, 90, '2019-08-13', '2019-08-13', 'g', 'fsds', 'p', 'p', '11:24', '11:04', 'PM', 'f', 0, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (43, 90, '2019-08-13', '2019-08-13', 'p', 'ytu', 'p', 'p', '', '', 'PM', 'f', 1, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (44, 90, '2019-08-13', '2019-08-13', 'p', 'fds', 'p', 'p', '', '', 'AM', 'f', 1, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (45, 90, '2019-08-13', '2019-08-13', 'p', 'rersdrfyt', 'p', 'p', '', '', 'AM', 'f', 1, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (46, 90, '2019-08-13', '2019-08-13', 'p', 'sfdg', 'p', 'p', '', '', 'AM', 'f', 1, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (47, 90, '2019-08-13', '2019-08-13', 'p', 'sdfsf', 'p', 'p', '', '', 'AM', 'f', 1, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (38, 70, '2019-08-07', '2019-08-07', 'g', 'ជុះអាចម៍', 'a', 'a', '01:50', '02:00', 'PM', 'f', 0, 'a', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (40, 93, '2019-08-06', '2019-08-07', 'p', 'Sick', 'p', 'p', '', '', 'AM', 'f', 3, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (39, 83, '2019-08-09', '2019-08-09', 'g', 'GO school', 'a', 'a', '03:00', '05:00', 'PM', 'f', 0, 'a', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (48, 90, '2019-08-13', '2019-08-13', 'p', 'fsdfsdfs', 'p', 'a', '', '', 'PM', 'f', 1, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (49, 92, '2019-08-13', '2019-08-13', 'l', 'Sleep late', 'p', 'r', '', '01:50', 'PM', 'f', 0, 'r', 20, NULL);
INSERT INTO "public"."sc_attendances" VALUES (50, 92, '2019-08-14', '2019-08-14', 'le', 'Go to company', 'p', 'r', '03:00', '', 'PM', 'f', 0, 'r', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (51, 94, '2019-08-14', '2019-08-14', 'p', 'Go to hospital', 'p', 'p', '', '', 'AM', 'f', 2, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (52, 94, '2019-08-14', '2019-08-14', 'g', 'I go to RUPP to register.', 'p', 'p', '11:11', '03:33', 'AM', 'f', 0, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (53, 94, '2019-08-14', '2019-08-14', 'le', 'I have to go school', 'p', 'p', '03:32', '', 'AM', 'f', 0, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (54, 94, '2019-08-14', '2019-08-14', 'p', 'I go to hospital to take care of my little sister.', 'p', 'p', '', '', 'AM', 'f', 2, 'p', 0, NULL);
INSERT INTO "public"."sc_attendances" VALUES (55, 94, '2019-08-14', '2019-08-14', 'l', 'Hello World', 'p', 'p', '', '03:04', 'AM', 'f', 0, 'p', 32, NULL);

-- ----------------------------
-- Table structure for sc_class_names
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_class_names";
CREATE TABLE "public"."sc_class_names" (
  "id" int4 NOT NULL DEFAULT nextval('tb_class_names_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "status" bool NOT NULL DEFAULT true,
  "deleted" bool NOT NULL
)
;

-- ----------------------------
-- Records of sc_class_names
-- ----------------------------
INSERT INTO "public"."sc_class_names" VALUES (1, 'BTB', 't', 'f');
INSERT INTO "public"."sc_class_names" VALUES (2, 'SR', 't', 'f');
INSERT INTO "public"."sc_class_names" VALUES (3, 'KP', 't', 'f');
INSERT INTO "public"."sc_class_names" VALUES (4, 'PP', 't', 'f');

-- ----------------------------
-- Table structure for sc_classenrolls
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_classenrolls";
CREATE TABLE "public"."sc_classenrolls" (
  "id" int4 NOT NULL DEFAULT nextval('tb_classenrolls_id_seq'::regclass),
  "classroom_id" int4,
  "status" bool DEFAULT true,
  "deleted" bool DEFAULT false,
  "user_id" int4
)
;

-- ----------------------------
-- Records of sc_classenrolls
-- ----------------------------
INSERT INTO "public"."sc_classenrolls" VALUES (70, 29, 't', 'f', 136);
INSERT INTO "public"."sc_classenrolls" VALUES (76, 29, 't', 'f', 140);
INSERT INTO "public"."sc_classenrolls" VALUES (79, 30, 't', 'f', 142);
INSERT INTO "public"."sc_classenrolls" VALUES (80, 31, 't', 'f', 141);
INSERT INTO "public"."sc_classenrolls" VALUES (82, 32, 't', 'f', 144);
INSERT INTO "public"."sc_classenrolls" VALUES (83, 33, 't', 'f', 143);
INSERT INTO "public"."sc_classenrolls" VALUES (84, 32, 't', 'f', 145);
INSERT INTO "public"."sc_classenrolls" VALUES (85, 34, 't', 'f', 145);
INSERT INTO "public"."sc_classenrolls" VALUES (86, 29, 't', 'f', 149);
INSERT INTO "public"."sc_classenrolls" VALUES (87, 29, 't', 'f', 148);
INSERT INTO "public"."sc_classenrolls" VALUES (90, 29, 't', 'f', 147);
INSERT INTO "public"."sc_classenrolls" VALUES (91, 29, 't', 'f', 150);
INSERT INTO "public"."sc_classenrolls" VALUES (92, 30, 't', 'f', 151);
INSERT INTO "public"."sc_classenrolls" VALUES (93, 30, 't', 'f', 152);
INSERT INTO "public"."sc_classenrolls" VALUES (94, 29, 't', 'f', 155);

-- ----------------------------
-- Table structure for sc_classrooms
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_classrooms";
CREATE TABLE "public"."sc_classrooms" (
  "id" int4 NOT NULL DEFAULT nextval('tb_classroom_id_seq'::regclass),
  "status" bool NOT NULL DEFAULT true,
  "deleted" bool NOT NULL DEFAULT false,
  "course_id" int4 NOT NULL,
  "class_name_id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of sc_classrooms
-- ----------------------------
INSERT INTO "public"."sc_classrooms" VALUES (29, 't', 'f', 10, 1);
INSERT INTO "public"."sc_classrooms" VALUES (30, 't', 'f', 10, 2);
INSERT INTO "public"."sc_classrooms" VALUES (31, 't', 'f', 11, 1);
INSERT INTO "public"."sc_classrooms" VALUES (32, 't', 'f', 12, 1);
INSERT INTO "public"."sc_classrooms" VALUES (33, 't', 'f', 12, 2);
INSERT INTO "public"."sc_classrooms" VALUES (34, 't', 'f', 13, 1);

-- ----------------------------
-- Table structure for sc_courses
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_courses";
CREATE TABLE "public"."sc_courses" (
  "id" int4 NOT NULL DEFAULT nextval('tb_course_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "generation_id" int4 NOT NULL,
  "status" bool NOT NULL DEFAULT true,
  "deleted" bool NOT NULL DEFAULT false
)
;

-- ----------------------------
-- Records of sc_courses
-- ----------------------------
INSERT INTO "public"."sc_courses" VALUES (10, 'Basic Course', 9, 't', 'f');
INSERT INTO "public"."sc_courses" VALUES (11, 'Advance Course', 9, 't', 'f');
INSERT INTO "public"."sc_courses" VALUES (12, 'Basic Course', 10, 't', 'f');
INSERT INTO "public"."sc_courses" VALUES (13, 'Advance Course', 10, 't', 'f');

-- ----------------------------
-- Table structure for sc_generations
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_generations";
CREATE TABLE "public"."sc_generations" (
  "id" int4 NOT NULL DEFAULT nextval('tb_generation_id_seq'::regclass),
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "academic_id" int4 NOT NULL,
  "status" bool NOT NULL DEFAULT true,
  "deleted" bool NOT NULL DEFAULT false
)
;

-- ----------------------------
-- Records of sc_generations
-- ----------------------------
INSERT INTO "public"."sc_generations" VALUES (9, 'Generation 7', 1, 't', 'f');
INSERT INTO "public"."sc_generations" VALUES (10, 'Generation 8', 1, 't', 'f');

-- ----------------------------
-- Table structure for sc_roles
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_roles";
CREATE TABLE "public"."sc_roles" (
  "id" int4 NOT NULL DEFAULT nextval('tb_role_id_seq'::regclass),
  "role" varchar(40) COLLATE "pg_catalog"."default" NOT NULL,
  "status" bool DEFAULT true,
  "deleted" bool DEFAULT false
)
;

-- ----------------------------
-- Records of sc_roles
-- ----------------------------
INSERT INTO "public"."sc_roles" VALUES (1, 'DIRECTOR', 't', 'f');
INSERT INTO "public"."sc_roles" VALUES (2, 'ADMIN', 't', 'f');
INSERT INTO "public"."sc_roles" VALUES (3, 'TEACHER', 't', 'f');
INSERT INTO "public"."sc_roles" VALUES (4, 'STUDENT', 't', 'f');

-- ----------------------------
-- Table structure for sc_teacher_assignments
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_teacher_assignments";
CREATE TABLE "public"."sc_teacher_assignments" (
  "id" int4 NOT NULL DEFAULT nextval('tb_teacher_assignments_id_seq'::regclass),
  "classroom_id" int4 NOT NULL,
  "user_id" int4 NOT NULL,
  "class_teacher" bool NOT NULL DEFAULT false
)
;

-- ----------------------------
-- Records of sc_teacher_assignments
-- ----------------------------
INSERT INTO "public"."sc_teacher_assignments" VALUES (5, 29, 135, 't');
INSERT INTO "public"."sc_teacher_assignments" VALUES (6, 30, 137, 't');
INSERT INTO "public"."sc_teacher_assignments" VALUES (7, 33, 146, 't');
INSERT INTO "public"."sc_teacher_assignments" VALUES (8, 32, 153, 't');
INSERT INTO "public"."sc_teacher_assignments" VALUES (10, 29, 154, 't');

-- ----------------------------
-- Table structure for sc_user_roles
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_user_roles";
CREATE TABLE "public"."sc_user_roles" (
  "user_id" int4 NOT NULL,
  "role_id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of sc_user_roles
-- ----------------------------
INSERT INTO "public"."sc_user_roles" VALUES (134, 2);
INSERT INTO "public"."sc_user_roles" VALUES (135, 3);
INSERT INTO "public"."sc_user_roles" VALUES (136, 4);
INSERT INTO "public"."sc_user_roles" VALUES (137, 3);
INSERT INTO "public"."sc_user_roles" VALUES (139, 1);
INSERT INTO "public"."sc_user_roles" VALUES (140, 4);
INSERT INTO "public"."sc_user_roles" VALUES (141, 4);
INSERT INTO "public"."sc_user_roles" VALUES (142, 4);
INSERT INTO "public"."sc_user_roles" VALUES (143, 4);
INSERT INTO "public"."sc_user_roles" VALUES (144, 4);
INSERT INTO "public"."sc_user_roles" VALUES (145, 4);
INSERT INTO "public"."sc_user_roles" VALUES (146, 3);
INSERT INTO "public"."sc_user_roles" VALUES (147, 4);
INSERT INTO "public"."sc_user_roles" VALUES (148, 4);
INSERT INTO "public"."sc_user_roles" VALUES (149, 4);
INSERT INTO "public"."sc_user_roles" VALUES (150, 4);
INSERT INTO "public"."sc_user_roles" VALUES (151, 4);
INSERT INTO "public"."sc_user_roles" VALUES (152, 4);
INSERT INTO "public"."sc_user_roles" VALUES (153, 3);
INSERT INTO "public"."sc_user_roles" VALUES (154, 3);
INSERT INTO "public"."sc_user_roles" VALUES (155, 4);

-- ----------------------------
-- Table structure for sc_users
-- ----------------------------
DROP TABLE IF EXISTS "public"."sc_users";
CREATE TABLE "public"."sc_users" (
  "id" int4 NOT NULL DEFAULT nextval('tb_users_id_seq'::regclass),
  "fullname" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(65) COLLATE "pg_catalog"."default" NOT NULL,
  "status" bool NOT NULL DEFAULT true,
  "deleted" bool NOT NULL DEFAULT false,
  "gender" char(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'M'::bpchar,
  "phone" varchar(50) COLLATE "pg_catalog"."default",
  "email" varchar(40) COLLATE "pg_catalog"."default" NOT NULL,
  "dob" varchar(15) COLLATE "pg_catalog"."default",
  "created_by" int4 NOT NULL,
  "photo" varchar(255) COLLATE "pg_catalog"."default",
  "nationality" varchar(40) COLLATE "pg_catalog"."default",
  "pob" varchar(50) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sc_users"."status" IS '1 = active, 0 = inactive';
COMMENT ON COLUMN "public"."sc_users"."gender" IS 'F = Femal, M  = Male, O = Other';

-- ----------------------------
-- Records of sc_users
-- ----------------------------
INSERT INTO "public"."sc_users" VALUES (135, 'Kong Vongsovannareach', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '32456578', 'teacher@gmail.com', '2019-08-07', 134, 'c2412139-25aa-4219-9708-88d4a31dddbd.jpg', 'dfsf', 'sfjdk');
INSERT INTO "public"."sc_users" VALUES (137, 'Heng Sengthai', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '34', 'hengsengthai@gmail.com', '2019-08-07', 134, 'default-profile.jpg', 'fds', 'kdfsl');
INSERT INTO "public"."sc_users" VALUES (136, 'Lun Sim', '$2a$10$koWVkjFZJ9TEk.BZpagjse5BcdJuilc6opFl.RaQjaOoJqEEm2xxe', 't', 'f', 'm', '09876789', 'lunsim@gmail.com', '2019-08-07', 0, '98c90a06-f05f-44b1-b87a-f8e3324d3371.jpg', 'Khmer', 'fds');
INSERT INTO "public"."sc_users" VALUES (139, 'Doctor Kim', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '012345787', 'doctor@gmail.com', '2019-08-07', 134, 'b52afef5-d138-4e17-a890-39eb5822ae77.jpg', 'Korean', 'Korean');
INSERT INTO "public"."sc_users" VALUES (140, 'Sokhy', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '0987656', 'sokhy@gmail.com', '2019-08-08', 134, '4d1f1faa-c4e2-457b-b35f-83b99892168b.jpg', 'Khmer', 'fdsjkf
');
INSERT INTO "public"."sc_users" VALUES (141, 'Lun Sem', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '8675433243546', 'lunsem@gmail.com', '2019-08-07', 134, 'default-profile.jpg', 'fsd', '123');
INSERT INTO "public"."sc_users" VALUES (142, 'Sok Peav', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '43', 'sokpeav@gmail.com', '2019-08-08', 134, 'default-profile.jpg', 'dsf', 'fdsdfs');
INSERT INTO "public"."sc_users" VALUES (143, 'Sem Kosal', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', 'fsddsf', 'semkosal@gmail.com', '2019-08-01', 134, '2b9d5a6e-8459-4f6c-85f1-7bea0ae5e0b9.jpg', 'dfsdsf', 'dsfdsfsd');
INSERT INTO "public"."sc_users" VALUES (144, 'Sokha', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '098765432', 'ssokha@gmail.com', '2019-08-08', 134, '683f2337-322a-4358-b174-27a1a8761daf.jpg', 'Khmer', 'dsf');
INSERT INTO "public"."sc_users" VALUES (145, 'Sim', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', 'w2', 'ssim@gmail.com', '2019-08-09', 134, 'default-profile.jpg', 'dfs', 'fds');
INSERT INTO "public"."sc_users" VALUES (146, 'Heng Sengthai', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '932843', 'ssengthai@gmail.com', '2019-08-09', 134, 'default-profile.jpg', 'Khmer', 'fsd');
INSERT INTO "public"."sc_users" VALUES (148, 'Sok Menghok', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '32465786', 'sokmenghok@gmail.com', '1111-11-11', 134, 'default-profile.jpg', 'Khmer', 'fds');
INSERT INTO "public"."sc_users" VALUES (149, 'Ton Navin', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'f', '9328746', 'tonnavin@gmail.com', '1122-12-22', 134, 'default-profile.jpg', 'fds', 'jfdsk
');
INSERT INTO "public"."sc_users" VALUES (150, 'Kea Kimleang', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '3829', 'kimleang@gmail.com', '1111-11-11', 134, 'default-profile.jpg', 'jsdfkl', '23fjs
');
INSERT INTO "public"."sc_users" VALUES (151, 'Meng Socheata', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'f', '8343', 'socheata@gmail.com', '1111-11-11', 134, 'default-profile.jpg', 'kfsjl', 'fjdsk
');
INSERT INTO "public"."sc_users" VALUES (152, 'Soa Mavin', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '2323', 'mavin@gmail.com', '1111-11-11', 134, 'default-profile.jpg', 'dfsf', 'fdsfs');
INSERT INTO "public"."sc_users" VALUES (147, 'Mok Monita', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'f', '0987654', 'mokmonita@gmail.com', '1111-11-11', 0, 'a6db046a-3f32-40a3-9d42-7b1678e3f3e5.jpg', 'Khmer', 'dsjklf');
INSERT INTO "public"."sc_users" VALUES (134, 'Yorn Sokha', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '099839388', 'admin@gmail.com', '1998-08-05', 0, 'cce02e63-5517-4c75-93de-2494ac1ecbb1.jpg', '', 'PP');
INSERT INTO "public"."sc_users" VALUES (153, 'Deamon', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '3243', 'deamon@gmail.com', '2019-08-14', 134, '365928d1-ca33-40dc-bcb0-d81c2b6d486f.jpg', 'dsf', '123');
INSERT INTO "public"."sc_users" VALUES (154, 'Lun Sim', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '3243', 'lunsim.hrd@gmail.com', '2019-08-14', 134, 'default-profile.jpg', 'fdsf', 'dsfdfs');
INSERT INTO "public"."sc_users" VALUES (155, 'Kit dara', '$2a$10$/Ff4lEjWupVnTUxn7OL08OOYbn5k3THeAbpuPjOSGQkQ9uty87OO.', 't', 'f', 'm', '324', 'kitdara@gmail.com', '2019-08-07', 134, 'default-profile.jpg', 'dfsfsdf', 'fdsf');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."tb_academic_id_seq"
OWNED BY "public"."sc_academics"."id";
SELECT setval('"public"."tb_academic_id_seq"', 29, true);
ALTER SEQUENCE "public"."tb_attendance_id_seq"
OWNED BY "public"."sc_attendances"."id";
SELECT setval('"public"."tb_attendance_id_seq"', 56, true);
ALTER SEQUENCE "public"."tb_class_names_id_seq"
OWNED BY "public"."sc_class_names"."id";
SELECT setval('"public"."tb_class_names_id_seq"', 5, false);
ALTER SEQUENCE "public"."tb_classenrolls_id_seq"
OWNED BY "public"."sc_classenrolls"."id";
SELECT setval('"public"."tb_classenrolls_id_seq"', 95, true);
ALTER SEQUENCE "public"."tb_classroom_id_seq"
OWNED BY "public"."sc_classrooms"."id";
SELECT setval('"public"."tb_classroom_id_seq"', 35, true);
ALTER SEQUENCE "public"."tb_course_id_seq"
OWNED BY "public"."sc_courses"."id";
SELECT setval('"public"."tb_course_id_seq"', 14, true);
ALTER SEQUENCE "public"."tb_generation_id_seq"
OWNED BY "public"."sc_generations"."id";
SELECT setval('"public"."tb_generation_id_seq"', 11, true);
ALTER SEQUENCE "public"."tb_role_id_seq"
OWNED BY "public"."sc_roles"."id";
SELECT setval('"public"."tb_role_id_seq"', 5, false);
ALTER SEQUENCE "public"."tb_teacher_assignments_id_seq"
OWNED BY "public"."sc_teacher_assignments"."id";
SELECT setval('"public"."tb_teacher_assignments_id_seq"', 11, true);
ALTER SEQUENCE "public"."tb_users_id_seq"
OWNED BY "public"."sc_users"."id";
SELECT setval('"public"."tb_users_id_seq"', 156, true);

-- ----------------------------
-- Primary Key structure for table sc_academics
-- ----------------------------
ALTER TABLE "public"."sc_academics" ADD CONSTRAINT "tb_academic_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sc_attendances
-- ----------------------------
ALTER TABLE "public"."sc_attendances" ADD CONSTRAINT "tb_attendance_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sc_class_names
-- ----------------------------
ALTER TABLE "public"."sc_class_names" ADD CONSTRAINT "tb_course_name_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sc_classenrolls
-- ----------------------------
ALTER TABLE "public"."sc_classenrolls" ADD CONSTRAINT "tb_classroom_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sc_classrooms
-- ----------------------------
ALTER TABLE "public"."sc_classrooms" ADD CONSTRAINT "tb_classroom_name_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sc_courses
-- ----------------------------
ALTER TABLE "public"."sc_courses" ADD CONSTRAINT "tb_course_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sc_generations
-- ----------------------------
ALTER TABLE "public"."sc_generations" ADD CONSTRAINT "tb_generation_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sc_roles
-- ----------------------------
ALTER TABLE "public"."sc_roles" ADD CONSTRAINT "tb_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sc_teacher_assignments
-- ----------------------------
ALTER TABLE "public"."sc_teacher_assignments" ADD CONSTRAINT "tb_teacher_assignment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sc_users
-- ----------------------------
ALTER TABLE "public"."sc_users" ADD CONSTRAINT "tb_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table sc_attendances
-- ----------------------------
ALTER TABLE "public"."sc_attendances" ADD CONSTRAINT "fkclassenrollid" FOREIGN KEY ("classenroll_id") REFERENCES "public"."sc_classenrolls" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table sc_classenrolls
-- ----------------------------
ALTER TABLE "public"."sc_classenrolls" ADD CONSTRAINT "fkuserclassenrolid" FOREIGN KEY ("user_id") REFERENCES "public"."sc_users" ("id") ON DELETE SET DEFAULT ON UPDATE SET DEFAULT;
ALTER TABLE "public"."sc_classenrolls" ADD CONSTRAINT "tb_classroom_classroom_name_id_fkey" FOREIGN KEY ("classroom_id") REFERENCES "public"."sc_classrooms" ("id") ON DELETE SET DEFAULT ON UPDATE SET DEFAULT;

-- ----------------------------
-- Foreign Keys structure for table sc_classrooms
-- ----------------------------
ALTER TABLE "public"."sc_classrooms" ADD CONSTRAINT "fkclassnameid" FOREIGN KEY ("class_name_id") REFERENCES "public"."sc_class_names" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."sc_classrooms" ADD CONSTRAINT "fkcourseid" FOREIGN KEY ("course_id") REFERENCES "public"."sc_courses" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table sc_courses
-- ----------------------------
ALTER TABLE "public"."sc_courses" ADD CONSTRAINT "tb_course_generation_id_fkey" FOREIGN KEY ("generation_id") REFERENCES "public"."sc_generations" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table sc_generations
-- ----------------------------
ALTER TABLE "public"."sc_generations" ADD CONSTRAINT "tb_generation_academic_id_fkey" FOREIGN KEY ("academic_id") REFERENCES "public"."sc_academics" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table sc_teacher_assignments
-- ----------------------------
ALTER TABLE "public"."sc_teacher_assignments" ADD CONSTRAINT "fkclassid" FOREIGN KEY ("classroom_id") REFERENCES "public"."sc_classrooms" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sc_teacher_assignments" ADD CONSTRAINT "fkuserassignmentid" FOREIGN KEY ("user_id") REFERENCES "public"."sc_users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table sc_user_roles
-- ----------------------------
ALTER TABLE "public"."sc_user_roles" ADD CONSTRAINT "fkroleid" FOREIGN KEY ("role_id") REFERENCES "public"."sc_roles" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."sc_user_roles" ADD CONSTRAINT "fkuserid" FOREIGN KEY ("user_id") REFERENCES "public"."sc_users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
