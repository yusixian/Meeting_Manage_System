
-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(60) COLLATE utf8_bin NOT NULL,
  `passWord` varchar(60) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT = 6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
-- ----------------------------
-- Records of generalUser
-- ----------------------------
create procedure p_adminAdd(in id int, in userName varchar(60), in passWord varchar(60))
begin
     set @id = id;
     set @userName = userName;
     SET @passWord = passWord;
    insert INTO `admin` VALUES (@id, @userName, @passWord);
end;
-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
call p_adminAdd(1, '旅行者', '123');
call p_adminAdd(2, '迪卢克', 'Diluc');
call p_adminAdd(3, '莫娜', 'Mona');
call p_adminAdd(4, '温迪', 'Venti');
call p_adminAdd(5, '刻晴', 'Keqing');
call p_adminAdd(6, 'test', 'test');
COMMIT;


-- ----------------------------
-- Table structure for generalUser
-- ----------------------------
DROP TABLE IF EXISTS `generalUser`;
CREATE TABLE `generalUser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(60) COLLATE utf8_bin NOT NULL,
  `passWord` varchar(60) COLLATE utf8_bin NOT NULL,
  `realName` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `idCard` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),                   -- 主键 --
  KEY `id` (`id`)                       -- 普通索引 --
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8 COLLATE=utf8_bin; -- 区分大小写 --

create procedure p_generalUserAdd(in id int, in userName varchar(60), in passWord varchar(60),
    in realName varchar(60), in idCard varchar(60), in phone varchar(60), in address varchar(60))
begin
     set @id = id;
     set @userName = userName;
     SET @passWord = passWord;
     set @realName = realName;
     SET @idCard = idCard;
     set @phone = phone;
     SET @address = address;
    insert into `generalUser` values (@id, @userName, @passWord, @realName, @idCard, @phone, @address);
end;
create procedure p_generalUserDelete(in uid int)
begin
     set @id = uid;
     delete from `generalUser` where id = @id;
end;
BEGIN;
call p_generalUserAdd(1, 'ysx', '123456', 'Barbara', '410102200107059004', '18302917670', 'Mond');
call p_generalUserAdd(2, '菲谢尔', '123', 'Fischl', '110101200108073361', '14613842106', 'Mond');
call p_generalUserAdd(3, '班尼特', '123', '六星真神', '', '', '');
call p_generalUserAdd(4, '七七', 'Qiqi', '肚饿真君', '', '', 'Liyue');
call p_generalUserAdd(5, '凝光', 'Ningguang', '富婆', '', '', 'Liyue');
call p_generalUserAdd(101, '砂糖', 'Sucrose', '砂糖', '501238102', '3123', 'Mond');
call p_generalUserAdd(102, '荒泷天下第一斗', '34546758', 'hlyd', '', '15403', 'DaoQi');
call p_generalUserAdd(116, '香菱', '123', '开锅啦开锅啦', '', '', 'Liyue');
call p_generalUserDelete(101);
call p_generalUserAdd(101, '砂糖', 'Sucrose', '砂糖', '501238102', '3123', 'Mond');
COMMIT;


-- ----------------------------
-- Table structure for meetingroom
-- ----------------------------
create table meetingroom
(
    roomID       int auto_increment,
    roomAddress  varchar(80)                 not null,
    roomName     varchar(30) default '普通会议室' not null,
    roomType     tinyint     default 1       not null,
    roomCapacity int         default 50      not null,
    hourPrice    double      default 20      not null,
    roomDescribe varchar(80)                 null,
    constraint meetingRoom_roomID_uindex
        unique (roomID)
);
create procedure p_meetingRoomAdd(in roomID int, in roomAddress varchar(80), in roomName varchar(30),
    in roomType tinyint, in roomCapacity int, in hourPrice double, in roomDescribe varchar(80))
begin
     set @roomID = roomID;
     set @roomAddress = roomAddress;
     SET @roomName = roomName;
     set @roomType = roomType;
     SET @roomCapacity = roomCapacity;
     set @hourPrice = hourPrice;
     SET @roomDescribe = roomDescribe;
    insert into `meetingRoom` values (@roomID, @roomAddress, @roomName, @roomType, @roomCapacity, @hourPrice, @roomDescribe);
end;

create procedure p_meetingRoomUpdate(in rID int, in rAddress varchar(80), in rName varchar(30),
    in rType tinyint, in rCapacity int, in hPrice double, in rDescribe varchar(80))
begin
     set @roomID = rID;
     set @roomAddress = rAddress;
     SET @roomName = rName;
     set @roomType = rType;
     SET @roomCapacity = rCapacity;
     set @hourPrice = hPrice;
     SET @roomDescribe = rDescribe;
    update `meetingRoom` set roomAddress = @roomAddress, roomName = @roomName, roomType = @roomType, roomCapacity = @roomCapacity, hourPrice = @hourPrice, roomDescribe = @roomDescribe where roomID = @roomID;
end;

create procedure p_meetingRoomDelete(in id int)
begin
     set @roomID = id;
     delete from `meetingRoom` where roomID = @roomID;
end;

create procedure p_meetingRoomFindByID(in id int)
begin
     set @roomID = id;
     select * from `meetingRoom` where roomID = @roomID;
end;

create procedure p_meetingRoomAll()
begin
     select * from `meetingRoom`;
end;

create procedure p_meetingRoomAvailableAll()
begin
     select * from `meetingRoom` where roomType = 1;
end;

update `meetingRoom` set  roomAddress = '5号楼5334', roomName = '录播会议室', roomType = 1, roomCapacity = 30,
                             hourPrice = 40, roomDescribe = 'change' where roomID = 6;
call p_meetingRoomAdd(1, '4号楼4322', '大会议室', 1, 200, 60, '可容纳200人的大会议室，适合进行大型会议');
call p_meetingRoomAdd(2, '4号楼4421', '普通会议室', 1, 50, 20, '普通会议室，适合进行中型会议');
call p_meetingRoomAdd(3, '4号楼4316', '小会议室', 1, 30, 8, '小会议室，适合进行小型会议');
call p_meetingRoomAdd(4, '4号楼4514', '多媒体会议室', 1, 40, 15, '多媒体会议室，适合进行需要多媒体的会议');
call p_meetingRoomAdd(5, '图书馆一楼', '学术报告厅', 1, 300, 100, '大型报告厅，可举行晚会等活动');
call p_meetingRoomAdd(6, '5号楼5334', '录播会议室', 1, 30, 30, '存放有录播设备，可进行会议的录制');
call p_meetingRoomDelete(6);
call p_meetingRoomAdd(6, '5号楼5334', '录播会议室', 1, 30, 30, '存放有录播设备，可进行会议的录制');
call p_meetingRoomFindByID(6);
call p_meetingRoomUpdate(6, '5号楼5334', '录播会议室', 1, 30, 40, '存放有录播设备，可进行会议的录制');
call p_meetingRoomAll();
-- ----------------------------
-- Table structure for meeting
-- ----------------------------
create table meeting
(
	id int,
	name varchar(30) default '预定会议' not null,
	partNum int not null,
	startTime datetime not null,
	duration int not null,
	price double not null,
	description varchar(150) null,
	uid int not null,
	rid int not null,
	constraint meeting_generaluser_id_fk
		foreign key (uid) references generaluser (id),
	constraint meeting_meetingroom_roomID_fk
		foreign key (rid) references meetingroom (roomID)
);

create unique index meeting_id_uindex
	on meeting (id);

alter table meeting
	add constraint meeting_pk
		primary key (id);

alter table meeting modify id int auto_increment;

-- 存储过程创建
create procedure p_meetingAdd(in id int, in name varchar(30), in partNum int,
    in startTime datetime, in duration int, in price double, in description varchar(150), in uid int, in rid int)
begin
     set @id = id;
     set @name = name;
     SET @partNum = partNum;
     set @startTime = startTime;
     SET @duration = duration;
     SET @price = price;
     set @des = description;
     SET @uid = uid;
     SET @rid = rid;
    insert into `meeting` values (@id, @name, @partNum, @startTime, @duration, @price, @des, @uid, @rid);
end;

create procedure p_meetingDelete(in mid int)
begin
     set @id = mid;
     delete from `meeting` where id = @id;
end;

create procedure p_meetingFindByID(in mid int)
begin
     set @mid = mid;
     select * from `meeting` where id = @mid;
end;

create procedure p_meetingUpdate(in mid int, in Name varchar(30), in pNum int,
    in sTime datetime, in d int, in p double, in des varchar(150), in u int, in r int)
begin
     set @id = mid;
     set @name = Name;
     SET @partNum = pNum;
     set @startTime = sTime;
     SET @duration = d;
     SET @price = p;
     set @des = des;
     SET @uid = u;
     SET @rid = r;
    update `meeting` set name = @name, partNum = @partNum, startTime = @startTime, duration = @duration, price = @price, description = @des, uid = @uid, rid = @rid where id = @id;
end;

create procedure p_meetingAll()
begin
     select * from `meeting`;
end;

-- 创建触发器 自动计算总价
create trigger trig_meetingUpdate
before update on `meeting` for each row
begin
    declare hPrice double;
    declare roomid int;
    select  NEW.rid into roomid;
    select hourPrice into hPrice from meetingroom where meetingroom.roomID = roomid;

    set NEW.price = NEW.duration*hPrice;
end;

create definer = root@localhost trigger trig_meetingAdd
    before insert
    on meeting
    for each row
begin
    declare hPrice double;
    declare roomid int;
    select  NEW.rid into roomid;
    select hourPrice into hPrice from meetingroom where meetingroom.roomID = roomid;

    set NEW.price = NEW.duration*hPrice;
end;

-- 这是总价其实都不对  触发器自动进行计算
call p_meetingAdd(1,'预定会议', 45, '2021-12-28 13:00:00', 2, 5,'讨论希娜小姐今天吃什么', 102, 3);
call p_meetingAdd(2,'预定会议', 45, '2021-12-21 14:00:00', 2, 20, '哼哼', 102, 2);
call p_meetingAdd(3,'元旦晚会', 250, '2022-12-30 18:00:00', 3, 0, '元旦快乐哦', 5, 5);
call p_meetingAll();
call p_meetingDelete(3);
call p_meetingAdd(3,'元旦晚会', 250, '2022-12-30 18:00:00', 3, 0, '元旦快乐哦', 5, 5);
call p_meetingFindByID(3);
call p_meetingUpdate(3,'元旦晚会', 250, '2022-12-31 19:00:00', 3, 0, 'Change~', 5, 5);
call p_meetingAdd(11,'天知道什么会议', 30, '2022-12-30 18:00:00', 3, 0, '元旦快乐哦', 5, 2);
call p_meetingAdd(14,'文艺晚会', 250, '2022-01-25 18:00:00', 3, 0, '元旦快乐哦', 5, 5);
insert into `meeting` (name, partNum, startTime, duration, price, description, uid, rid) values('test', 20, '2021-02-21 14:00:00', 2, 0, 'describe 啦啦啦', 1, 1);
-- ----------------------------
-- Table structure for equipment
-- ----------------------------
create table equipment
(
	id int,
	name varchar(50) not null,
	status tinyint not null,
	value double not null,
	mid int not null,
	constraint equipment_meetingroom_roomID_fk
		foreign key (mid) references meetingroom (roomID)
);
create unique index equipment_id_uindex
	on equipment (id);
alter table equipment
	add constraint equipment_pk
		primary key (id);
alter table equipment modify id int auto_increment;

-- 存储过程创建
create procedure p_equipmentAdd(in id int, in name varchar(50), in status tinyint, in value double, in mid int)
begin
     set @id = id;
     set @name = name;
     SET @status = status;
     set @value = value;
     SET @mid = mid;
    insert into `equipment` values (@id, @name, @status, @value, @mid);
end;

create procedure p_equipmentDelete(in mid int)
begin
     set @id = mid;
     delete from `equipment` where id = @id;
end;

create procedure p_equipmentFindByID(in mid int)
begin
     set @mid = mid;
     select * from `equipment` where id = @mid;
end;

create procedure p_equipmentUpdate(in eid int, in ename varchar(50), in estatus tinyint, in evalue double, in emid int)
begin
     set @id = eid;
     set @name = ename;
     SET @status = estatus;
     set @value = evalue;
     SET @mid = emid;
    update `equipment` set name = @name, status = @status, value = @value, mid = @mid where id = @id;
end;

create procedure p_equipmentAll()
begin
     select * from `equipment`;
end;

call p_equipmentAdd(1, '小葵花牌投影仪', 1, 1050, 4);
call p_equipmentAdd(2, 'xxxx牌录播相机', 1, 670, 6);
call p_equipmentAdd(3, '向日葵牌投影仪', 1, 990, 1);
call p_equipmentAdd(4, 'xxx牌音响', 1, 305, 5);
call p_equipmentAll();
call p_equipmentDelete(3);
call p_equipmentAll();
call p_equipmentAdd(3, '向日葵牌投影仪', 1, 990, 1);
call p_equipmentUpdate(4, '小爱音箱', 1, 305, 5);
call p_equipmentFindByID(3);

-- 创建用户-会议数视图
create view view_userMeetingView as
    select MAX(uid) as uid,
           MAX(userName) as userName,
           COUNT(*) as 'meetingCount'
    from meeting a join generalUser b on a.uid = b.id
    group by uid order by meetingCount desc ;

select userName from `view_userMeetingView` as t where t.meetingCount = (select max(t1.meetingCount) from `view_userMeetingView` as t1) order by userName desc ;

-- 创建会议室-会议数视图
create view view_roomMeetingView as
    select MAX(rid) as rid,
           MAX(roomName) as roomName,
           COUNT(*) as 'meetingCount'
    from meeting a join meetingroom b on a.rid = b.roomID
    group by rid order by meetingCount desc ;

select roomName from `view_roomMeetingView` as t where t.meetingCount = (select max(t1.meetingCount) from `view_roomMeetingView` as t1) order by roomName desc ;
