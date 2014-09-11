
-- Table: CATEGORY
CREATE TABLE CATEGORY ( 
    CatID      INTEGER     PRIMARY KEY AUTOINCREMENT,
    CatName    CHAR( 50 )  NOT NULL
                           UNIQUE,
    CatDes     TEXT,
    CatImgPath TEXT,
    CatStt     INT( 1 )    DEFAULT ( 0 ),
    CatLevel   INTEGER 
);

INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt], [CatLevel]) VALUES (1, 'Động vật', 'Các con vật thân thuộc hằng ngày với con người.', 'assets/cate_0_img/cho.jpg', 0, 4);
INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt], [CatLevel]) VALUES (2, 'Hoa quả', 'Các loại hoa quả cơ bản chúng ta thường gặp hằng ngày.', 'assets/cate_1_img/cam.jpg', 0, 3);
INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt], [CatLevel]) VALUES (3, 'Nghề nghiệp', 'Một số nghề nghiệp phổ biến chúng ta nên biết.', 'assets/cate_2_img/canhsat.jpg', 0, 5);
INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt], [CatLevel]) VALUES (4, 'Thức ăn', 'Những thứ chúng ta ăn hằng ngày', 'assets/food/thucan.jpg', 0, 2);
INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt], [CatLevel]) VALUES (5, 'Nhân vật hoạt hình', 'Các nhân vật hoạt hình quen thuộc', 'assets/cartoon/doraemon.jpg', 0, 7);
INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt], [CatLevel]) VALUES (6, 'Gia đình', 'Các cách xưng hô trong gia đình', 'assets/family/giadinh.jpg', 0, 6);
INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt], [CatLevel]) VALUES (7, 'Quốc tịch', 'Tên các quốc gia trên thế giới viết bằng tiếng Nhật.', 'assets/quoctich/vietnam.jpg', 0, 8);
INSERT INTO [CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt], [CatLevel]) VALUES (8, 'Đồ dùng hằng ngày', 'Các đồ vật chúng ta thường dùng hằng ngày', 'assets/dodac/butchi.jpg', 0, 1);

-- Table: CUSTOM_CATEGORY
CREATE TABLE CUSTOM_CATEGORY ( 
    CatID      INTEGER     PRIMARY KEY AUTOINCREMENT,
    CatName    CHAR( 50 )  NOT NULL
                           UNIQUE,
    CatDes     TEXT,
    CatImgPath TEXT,
    CatStt     INT( 1 )    DEFAULT ( 0 ) 
);

INSERT INTO [CUSTOM_CATEGORY] ([CatID], [CatName], [CatDes], [CatImgPath], [CatStt]) VALUES (1, 'My Custom Category', 'This is the default custom category', null, 0);

-- Table: CUSTOM_QUESTION
CREATE TABLE CUSTOM_QUESTION ( 
    QuestId         INTEGER     PRIMARY KEY AUTOINCREMENT,
    QuestKey        CHAR( 50 )  NOT NULL
                                UNIQUE,
    QuestStt        INT( 1 )    DEFAULT ( 0 ),
    CatName         CHAR( 50 )  NOT NULL
                                REFERENCES CUSTOM_CATEGORY ( CatName ),
    QuestImgPath    TEXT        NOT NULL,
    QuestDefinition TEXT        NOT NULL 
);


-- Table: QUESTION
CREATE TABLE QUESTION ( 
    QuestId         INTEGER     PRIMARY KEY AUTOINCREMENT,
    QuestKey        CHAR( 50 )  NOT NULL,
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
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (16, 'ぶどう', 0, 'Hoa quả', 'assets/cate_1_img/nho.jpg', 'nho');
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
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (35, 'なし', 0, 'Hoa quả', 'assets/cate_1_img/le.jpg', 'lê');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (36, 'みかん', 0, 'Hoa quả', 'assets/cate_1_img/quyt.jpg', 'quất');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (37, 'オレンジ', 0, 'Hoa quả', 'assets/cate_1_img/cam.jpg', 'Cam');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (38, 'レモン', 0, 'Hoa quả', 'assets/cate_1_img/chanh.jpg', 'Chanh');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (39, 'すいか', 0, 'Hoa quả', 'assets/cate_1_img/duahau.jpg', 'Dưa hấu');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (40, 'りんご', 0, 'Hoa quả', 'assets/cate_1_img/tao.jpg', 'Táo');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (41, 'バナナ', 0, 'Hoa quả', 'assets/cate_1_img/chuoi.jpg', 'Chuối');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (42, 'にんじん', 0, 'Hoa quả', 'assets/cate_1_img/carot.jpg', 'Cà rốt');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (43, 'トマト', 0, 'Hoa quả', 'assets/cate_1_img/cachua.jpg', 'Cà chua');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (44, 'まめ', 0, 'Hoa quả', 'assets/cate_1_img/dauxanh.jpg', 'Đậu xanh');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (45, 'たべもの', 0, 'Thức ăn', 'assets/food/thucan.jpg', 'Thức ăn');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (46, 'くだもの', 0, 'Thức ăn', 'assets/food/hoaqua.jpg', 'Hoa Quả');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (47, 'のみもの', 0, 'Thức ăn', 'assets/food/douong.jpg', 'Đồ uống');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (48, 'にく', 0, 'Thức ăn', 'assets/food/thit.jpg', 'Thịt');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (49, 'ぎゅうにく', 0, 'Thức ăn', 'assets/food/bo.jpg', 'Thịt bò');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (50, 'とりにく', 0, 'Thức ăn', 'assets/food/ga.jpg', 'Thịt gà');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (51, 'ぶたにく', 0, 'Thức ăn', 'assets/food/lon.jpg', 'Thịt lợn');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (52, 'みず', 0, 'Thức ăn', 'assets/food/nuoc.jpg', 'Nước');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (53, 'おちゃ', 0, 'Thức ăn', 'assets/food/traxanh.jpg', 'Trà xanh');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (54, 'こうちゃ', 0, 'Thức ăn', 'assets/food/traden.jpg', 'Trà đen');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (55, 'ミルク', 0, 'Thức ăn', 'assets/food/sua.jpg', 'Sữa');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (56, 'コーヒー', 0, 'Thức ăn', 'assets/food/cafe.jpg', 'Cà phê');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (57, 'ビール', 0, 'Thức ăn', 'assets/food/bia.jpg', 'Bia');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (58, 'たまご', 0, 'Thức ăn', 'assets/food/trung.jpg', 'Trứng');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (59, 'さかな', 0, 'Thức ăn', 'assets/food/ca.jpg', 'Cá');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (60, 'ごはん', 0, 'Thức ăn', 'assets/food/com.jpg', 'Cơm');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (61, 'やさい', 0, 'Thức ăn', 'assets/food/rau.jpg', 'Rau');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (62, 'ドラえもん', 0, 'Nhân vật hoạt hình', 'assets/cartoon/doraemon.jpg', 'Đô Rê Mon (Doraemon)');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (63, 'のびた', 0, 'Nhân vật hoạt hình', 'assets/cartoon/nobita.jpg', 'Nô bi ta (Nobita)');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (64, 'しずか', 0, 'Nhân vật hoạt hình', 'assets/cartoon/xuka.jpg', 'Xuka　(Shizuka )');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (65, 'すねお', 0, 'Nhân vật hoạt hình', 'assets/cartoon/xeko.jpg', 'Xêkô（Suneo)');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (66, 'ジャイアン', 0, 'Nhân vật hoạt hình', 'assets/cartoon/jaian.jpg', 'Chaien ( Jaian)');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (67, 'そんごく', 0, 'Nhân vật hoạt hình', 'assets/cartoon/songoku.jpg', 'Songoku');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (68, 'ナルト', 0, 'Nhân vật hoạt hình', 'assets/cartoon/naruto.jpg', 'Naruto');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (69, 'サスケ', 0, 'Nhân vật hoạt hình', 'assets/cartoon/sasuke.jpg', 'Sasuke');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (70, 'サクラ', 0, 'Nhân vật hoạt hình', 'assets/cartoon/sakura.jpg', 'Sakura');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (71, 'ヒナタ', 0, 'Nhân vật hoạt hình', 'assets/cartoon/hinata.jpg', 'Hinata');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (72, 'ツナデ', 0, 'Nhân vật hoạt hình', 'assets/cartoon/tsunade.jpg', 'Tsunade');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (73, 'ピカチュウ', 0, 'Nhân vật hoạt hình', 'assets/cartoon/pikachu.jpg', 'Pikachu');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (74, 'ロックリー', 0, 'Nhân vật hoạt hình', 'assets/cartoon/rocklee.jpg', 'Rook Lee');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (75, 'かずく', 0, 'Gia đình', 'assets/family/giadinh.jpg', 'Gia đình');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (76, 'おねえさん', 0, 'Gia đình', 'assets/family/chigai.jpg', 'Chị gái');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (77, 'おにいさん', 0, 'Gia đình', 'assets/family/anhtrai.jpg', 'Anh trai');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (78, 'いとうと', 0, 'Gia đình', 'assets/family/emgai.jpg', 'Em gái');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (79, 'ととうと', 0, 'Gia đình', 'assets/family/emtrai.jpg', 'Em trai');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (80, 'かない', 0, 'Gia đình', 'assets/family/vo.jpg', 'Vợ');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (81, 'しゅじｎ', 0, 'Gia đình', 'assets/family/chong.jpg', 'Chồng');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (82, 'こども', 0, 'Gia đình', 'assets/family/concai.jpg', 'Con');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (83, 'おとうさん', 0, 'Gia đình', 'assets/family/bo.jpg', 'Bố');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (84, 'おかあさん', 0, 'Gia đình', 'assets/family/me.jpg', 'Mẹ');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (85, 'マレーシア', 0, 'Quốc tịch', 'assets/quoctich/malay.jpg', 'Malaysia');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (86, 'インドネシア', 0, 'Quốc tịch', 'assets/quoctich/indo.jpg', 'Indonesia');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (87, 'ベトナム', 0, 'Quốc tịch', 'assets/quoctich/vietnam.jpg', 'Việt Nam');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (88, 'かんこく', 0, 'Quốc tịch', 'assets/quoctich/hanquoc.jpg', 'Hàn Quốc');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (89, 'フィリピン', 0, 'Quốc tịch', 'assets/quoctich/philip.jpg', 'Phi líp pin');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (90, 'タイ', 0, 'Quốc tịch', 'assets/quoctich/thai.jpg', 'Thái Lan');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (91, 'にほん', 0, 'Quốc tịch', 'assets/quoctich/nhat.jpg', 'Nhật Bản');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (92, 'インド', 0, 'Quốc tịch', 'assets/quoctich/ando.jpg', 'Ấn Độ');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (93, 'アメリカ', 0, 'Quốc tịch', 'assets/quoctich/my.jpg', 'Mỹ');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (94, 'ちゅうごく', 0, 'Quốc tịch', 'assets/quoctich/trungquoc.jpg', 'Trung Quốc');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (95, 'たばこ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/thuocla.jpg', 'Thuốc lá');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (96, 'シャープペンシル', 0, 'Đồ dùng hằng ngày', 'assets/dodac/chimay.jpg', 'Bút chì máy');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (97, 'ボールペン', 0, 'Đồ dùng hằng ngày', 'assets/dodac/butbi.jpg', 'Bút bi');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (98, 'てがみ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/thu.jpg', 'Thư');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (99, 'ドア', 0, 'Đồ dùng hằng ngày', 'assets/dodac/cua.jpg', 'Cửa ra vào');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (100, 'はこ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/hop.jpg', 'Hộp');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (101, 'いす', 0, 'Đồ dùng hằng ngày', 'assets/dodac/ghe.jpg', 'Ghế');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (102, 'つくえ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/ban.jpg', 'Bàn');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (103, 'しんぶん', 0, 'Đồ dùng hằng ngày', 'assets/dodac/bao.jpg', 'Báo');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (104, 'テレビ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/tivi.jpg', 'Ti vi');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (105, 'ざっし', 0, 'Đồ dùng hằng ngày', 'assets/dodac/tapchi.jpg', 'Tạp chí');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (106, 'じしょ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/tudien.jpg', 'Từ điển');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (107, 'かぎ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/chiakhoa.jpg', 'Chìa khóa');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (108, 'えんぴつ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/butchi.jpg', 'Bút chì');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (109, 'かばん', 0, 'Đồ dùng hằng ngày', 'assets/dodac/cap.jpg', 'Cặp');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (110, 'ほん', 0, 'Đồ dùng hằng ngày', 'assets/dodac/sach.jpg', 'Sách');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (111, 'マッチ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/diem.jpg', 'Diêm');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (112, 'でんわ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/dienthoai.jpg', 'Điện thoại');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (113, 'ラジオ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/radio.jpg', 'Ra đi ô');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (114, 'まど', 0, 'Đồ dùng hằng ngày', 'assets/dodac/cuaso.jpg', 'Cửa sổ');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (115, 'かみ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/giay.jpg', 'Giấy');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (116, 'ノート', 0, 'Đồ dùng hằng ngày', 'assets/dodac/vo.jpg', 'Vở');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (117, 'とけい', 0, 'Đồ dùng hằng ngày', 'assets/dodac/dongho.jpg', 'Đồng hồ');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (118, 'ライター', 0, 'Đồ dùng hằng ngày', 'assets/dodac/batlua.jpg', 'Bật lửa');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (119, 'うち', 0, 'Đồ dùng hằng ngày', 'assets/dodac/nha.jpg', 'Nhà');
INSERT INTO [QUESTION] ([QuestId], [QuestKey], [QuestStt], [CatName], [QuestImgPath], [QuestDefinition]) VALUES (120, 'カメラ', 0, 'Đồ dùng hằng ngày', 'assets/dodac/mayanh.jpg', 'Máy Ảnh');
