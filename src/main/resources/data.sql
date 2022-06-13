-- USERS 테이블 데이터
INSERT INTO USERS(id,user_name,user_type,user_stat) VALUES (1, '이수경', 'NORMAL_USER', 'WITHDRAWAL');
INSERT INTO USERS(id,user_name,user_type,user_stat) VALUES (2, '최상면', 'CORPORATE_USER', 'NORMAL');
INSERT INTO USERS(id,user_name,user_type,user_stat) VALUES (3, '강재석', 'NORMAL_USER', 'NORMAL');
INSERT INTO USERS(id,user_name,user_type,user_stat) VALUES (4, '김구현', 'NORMAL_USER', 'NORMAL');

-- ITEMS 데이터 데이터
INSERT INTO ITEMS(id,item_name,item_type,item_price,item_display_start_date,item_display_end_date)
    VALUES (1,'노브랜드 버터링','NORMAL_ITEM',20000,'2022-01-01','2023-01-01');
INSERT INTO ITEMS(id,item_name,item_type,item_price,item_display_start_date,item_display_end_date)
    VALUES (2,'매일 아침 우유','NORMAL_ITEM',1000,'2022-01-01','2023-05-05');
INSERT INTO ITEMS(id,item_name,item_type,item_price,item_display_start_date,item_display_end_date)
    VALUES (3,'나이키 운동화','CORPORATE_USER_ITEM',40000,'2020-01-01','2023-12-31');
INSERT INTO ITEMS(id,item_name,item_type,item_price,item_display_start_date,item_display_end_date)
    VALUES (4,'스타벅스 써머 텀블러','NORMAL_ITEM',15000,'2021-01-01','2021-08-01');
INSERT INTO ITEMS(id,item_name,item_type,item_price,item_display_start_date,item_display_end_date)
    VALUES (5,'크리스마스 케이크','NORMAL_ITEM',30000,'2022-12-24','2022-12-31');

-- PROMOTION 테이블 데이터
INSERT INTO PROMOTIONS(id,promotion_nm,discount_amount,discount_rate, promotion_start_date,promotion_end_date)
    VALUES (1,'2022 쓱데이',1000,null,'2022-05-01','2022-07-01');
INSERT INTO PROMOTIONS(id,promotion_nm,discount_amount,discount_rate, promotion_start_date,promotion_end_date)
    VALUES (2,'스타벅스몰 오픈기념',null,0.05,'2021-01-05','2022-12-31');
INSERT INTO PROMOTIONS(id,promotion_nm,discount_amount,discount_rate, promotion_start_date,promotion_end_date)
    VALUES (3,'2022 쓱데이',2000,null,'2021-01-01','2021-01-31');

-- PROMOTION_ITEM 매핑 테이블 데이터
INSERT INTO MAP_PROMOTIONS_ITEMS(id,promotion_id,item_id) VALUES (1,1,1);
INSERT INTO MAP_PROMOTIONS_ITEMS(id,promotion_id,item_id) VALUES (2,1,2);
INSERT INTO MAP_PROMOTIONS_ITEMS(id,promotion_id,item_id) VALUES (3,1,3);
INSERT INTO MAP_PROMOTIONS_ITEMS(id,promotion_id,item_id) VALUES (4,1,4);
INSERT INTO MAP_PROMOTIONS_ITEMS(id,promotion_id,item_id) VALUES (5,1,5);

INSERT INTO MAP_PROMOTIONS_ITEMS(id,promotion_id,item_id) VALUES (6,2,4);

INSERT INTO MAP_PROMOTIONS_ITEMS(id,promotion_id,item_id) VALUES (7,3,1);
INSERT INTO MAP_PROMOTIONS_ITEMS(id,promotion_id,item_id) VALUES (8,3,2);
INSERT INTO MAP_PROMOTIONS_ITEMS(id,promotion_id,item_id) VALUES (9,3,3);
