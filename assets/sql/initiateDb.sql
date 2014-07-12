
-- Table: CATEGORY
CREATE TABLE CATEGORY ( 
    CatID      INTEGER     PRIMARY KEY AUTOINCREMENT,
    CatName    CHAR( 50 )  NOT NULL
                           UNIQUE,
    CatDes     TEXT,
    CatImgPath TEXT,
    CatStt     INT( 1 )    DEFAULT ( 0 ) 
);

INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt]) VALUES (1, 'Động vật', 'Các con vật thân thuộc hằng ngày với con người.', 'assets/cate_0_img/cho.jpg', 0);
INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt]) VALUES (2, 'Hoa quả', 'Các loại hoa quả cơ bản chúng ta thường gặp hằng ngày.', 'assets/cate_1_img/cam.jpg', 0);
INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt]) VALUES (3, 'Nghề nghiệp', 'Một số nghề nghiệp phổ biến chúng ta nên biết.', 'assets/cate_2_img/canhsat.jpg', 0);

-- Table: QUESTION
CREATE TABLE QUESTION ( 
    QuestId         INTEGER     PRIMARY KEY AUTOINCREMENT,
    QuestKey        CHAR( 50 )  NOT NULL
                                UNIQUE,
    QuestStt        INT( 1 )    DEFAULT ( 0 ),
    CatName         CHAR( 50 )  NOT NULL
                                REFERENCES CATEGORY ( CatName ),
    QuestImgPath    TEXT        NOT NULL,
    QuestDefinition TEXT        NOT NULL 
);

INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (1, 'いぬ', 0, 'Động vật', 'assets/cate_0_img/cho.jpg', 'chó');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (2, 'ねこ', 0, 'Động vật', 'assets/cate_0_img/meo.jpg', 'mèo');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (3, 'ぶた', 0, 'Động vật', 'assets/cate_0_img/lon.jpg', 'lợn');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (4, 'にわとり', 0, 'Động vật', 'assets/cate_0_img/ga.jpg', 'gà');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (5, 'あひる', 0, 'Động vật', 'assets/cate_0_img/vit.jpg', 'vịt');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (6, 'うま', 0, 'Động vật', 'assets/cate_0_img/ngua.jpg', 'ngựa');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (7, 'うし', 0, 'Động vật', 'assets/cate_0_img/bo.jpg', 'bò');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (8, 'ねずみ', 0, 'Động vật', 'assets/cate_0_img/chuot.jpg', 'chuột');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (9, 'とり', 0, 'Động vật', 'assets/cate_0_img/chim.jpg', 'chim');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (10, 'ライオン', 0, 'Động vật', 'assets/cate_0_img/sutu.jpg', 'sư tử');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (11, 'ぞう', 0, 'Động vật', 'assets/cate_0_img/voi.jpg', 'voi');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (12, 'さかな', 0, 'Động vật', 'assets/cate_0_img/ca.jpg', 'cá');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (13, 'いちご', 0, 'Hoa quả', 'assets/cate_1_img/dau-tay.jpg', 'dâu tây');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (14, 'もも', 0, 'Hoa quả', 'assets/cate_1_img/dao.jpg', 'đào');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (15, 'すいか', 0, 'Hoa quả', 'assets/cate_1_img/duahau.jpg', 'dưa hấu');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (16, 'ぶどう', 0, 'Hoa quả', 'assets/cate_1_img/nho.jpg', 'nho');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (17, 'なし', 0, 'Hoa quả', 'assets/cate_1_img/le.jpg', 'lê');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (18, 'みかん', 0, 'Hoa quả', 'assets/cate_1_img/quyt.jpg', 'quýt');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (19, 'りんご', 0, 'Hoa quả', 'assets/cate_1_img/tao.jpg', 'táo');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (20, 'バナナ', 0, 'Hoa quả', 'assets/cate_1_img/chuoi.jpg', 'chuối');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (21, 'オレンジ', 0, 'Hoa quả', 'assets/cate_1_img/cam.jpg', 'cam');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (22, 'きょうし', 0, 'Nghề nghiệp', 'assets/cate_2_img/giaovien.jpg', 'giáo viên');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (23, 'がくせい', 0, 'Nghề nghiệp', 'assets/cate_2_img/hocsinh.jpg', 'học sinh');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (24, 'ちょうりし', 0, 'Nghề nghiệp', 'assets/cate_2_img/daubep.jpg', 'đầu bếp');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (25, 'いしゃ', 0, 'Nghề nghiệp', 'assets/cate_2_img/bacsi.jpg', 'bác sĩ');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (26, 'うんてんしゅ', 0, 'Nghề nghiệp', 'assets/cate_2_img/taixe.jpg', 'tài xế');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (27, 'けいさつかん', 0, 'Nghề nghiệp', 'assets/cate_2_img/canhsat.jpg', 'cảnh sát');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (28, 'がか', 0, 'Nghề nghiệp', 'assets/cate_2_img/hoasi.jpg', 'họa sĩ');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (29, 'ジャーナリスト', 0, 'Nghề nghiệp', 'assets/cate_2_img/nhabao.jpg', 'nhà báo');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (30, 'けんちくか', 0, 'Nghề nghiệp', 'assets/cate_2_img/kientrucsu.jpg', 'kiến trúc sư');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (31, 'りようし', 0, 'Nghề nghiệp', 'assets/cate_2_img/thocattoc.jpg', 'thợ cắt tóc');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (32, 'かんごふ', 0, 'Nghề nghiệp', 'assets/cate_2_img/yta.jpg', 'y tá');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (33, 'てんいん', 0, 'Nghề nghiệp', 'assets/cate_2_img/nhan_vien_nha_hang.jpg', 'nhân viên nhà hàng');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (34, 'かいしゃいん', 0, 'Nghề nghiệp', 'assets/cate_2_img/nhan_vien_cong_ty.jpg', 'nhân viên công ty');
