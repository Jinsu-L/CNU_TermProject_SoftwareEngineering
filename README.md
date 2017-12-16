# CNU_TermProject_SoftwareEngineering
2017  Fall Semester, CNU, TermProject, SoftwareEngineering, POS System

## 개발 환경

````
java : 1.8.0_151
UI   : JavaFx
DB   : MySQL
````

## Pos 로그인 비밀번호
````
admin

````

## DB 설치 가이드

1. MySQL설치  
    ```
    sudo apt-get install mysql-server mysql-client
    ```
2. pos POS 데이터베이스 생성  
    ```
    mysqladmin -u root create pos -p
    ```
3. pos 데이터베이스 접속  
    ```
    mysql -u root -p pos
    ```

## DB 계정
````
ID : root
PW : root

````

## DB Setting 쿼리 값

```sql
CREATE TABLE environment
(
    user_number         INTEGER NOT NULL AUTO_INCREMENT,
    password            VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_number)
);

CREATE TABLE coupon
(
    coupon_number       VARCHAR(10) NOT NULL,
    coupon_amount       INTEGER NULL,
    PRIMARY KEY (coupon_number)
);

CREATE TABLE card_payment
(
   payment_number       INTEGER NOT NULL,
   card_number          CHAR(16) NULL,
   card_company         VARCHAR(20) NULL,
   approval_number      INTEGER NULL,
   PRIMARY KEY (payment_number)
);

CREATE TABLE category
(
   category_number           INTEGER NOT NULL AUTO_INCREMENT,
   category_name        VARCHAR(20) NULL,
   PRIMARY KEY(category_number)
);

CREATE TABLE coupon_payment
(
   payment_number       INTEGER NOT NULL,
   coupon_number        CHAR(8) NULL,
   coupon_amount        INTEGER NULL,
   PRIMARY KEY (payment_number)
);

CREATE TABLE item
(
   item_number          INTEGER NOT NULL AUTO_INCREMENT,
   item_name            VARCHAR(20) UNIQUE,
   item_price           INTEGER NULL,
   category_number           INTEGER NOT NULL,
   PRIMARY  KEY(item_number)
);

CREATE TABLE payment
(
   payment_number       INTEGER NOT NULL AUTO_INCREMENT,
   payment_amount       INTEGER NULL,
   payment_type         ENUM('cash','card','coupon') NULL,
   payment_date         DATE NULL,
   shopping_basket_number INTEGER NOT NULL,
   PRIMARY KEY(payment_number)
);

CREATE TABLE shopping_basket
(
   shopping_basket_number INTEGER NOT NULL AUTO_INCREMENT,
   total_amount         INTEGER NULL,
   PRIMARY KEY(shopping_basket_number)
);

CREATE TABLE shopping_history
(
   shopping_history_number INTEGER NOT NULL AUTO_INCREMENT,
   item_quantity        INTEGER NULL,
   shopping_basket_number INTEGER NOT NULL,
   item_number            INTEGER NOT NULL,
   PRIMARY KEY(shopping_history_number)
);

ALTER TABLE card_payment
ADD CONSTRAINT R_4 FOREIGN KEY (payment_number) REFERENCES payment (payment_number)ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE coupon_payment
ADD CONSTRAINT R_5 FOREIGN KEY (payment_number) REFERENCES payment (payment_number)ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE coupon_payment
ADD CONSTRAINT R_8 FOREIGN KEY (coupon_number) REFERENCES coupon (coupon_number)ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE item
ADD CONSTRAINT R_6 FOREIGN KEY (category_number) REFERENCES category (category_number)ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE payment
ADD CONSTRAINT R_1 FOREIGN KEY (shopping_basket_number) REFERENCES shopping_basket (shopping_basket_number)ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE shopping_history
ADD CONSTRAINT R_2 FOREIGN KEY (shopping_basket_number) REFERENCES shopping_basket (shopping_basket_number)ON DELETE CASCADE ON UPDATE CASCADE ;

ALTER TABLE shopping_history
ADD CONSTRAINT R_20 FOREIGN KEY (item_number) REFERENCES item (item_number)ON DELETE CASCADE ON UPDATE CASCADE ;

INSERT INTO category (category_name) VALUES('세트');
INSERT INTO category (category_name) VALUES('단품');
INSERT INTO category (category_name) VALUES('사이드');
INSERT INTO category (category_name) VALUES('음료수');
INSERT INTO category (category_name) VALUES('기타');

INSERT INTO item (item_name, item_price, category_number) VALUES('동하 버거 세트', 9500, 1);
INSERT INTO item (item_name, item_price, category_number) VALUES('동의 버거 세트', 6500, 1);
INSERT INTO item (item_name, item_price, category_number) VALUES('동하 버거', 5500, 2);
INSERT INTO item (item_name, item_price, category_number) VALUES('근우 버거', 3500, 2);
INSERT INTO item (item_name, item_price, category_number) VALUES('감자 튀김', 2300, 3);
INSERT INTO item (item_name, item_price, category_number) VALUES('애플 파이', 3000, 3);
INSERT INTO item (item_name, item_price, category_number) VALUES('소공 쉐이크', 2000, 4);
INSERT INTO item (item_name, item_price, category_number) VALUES('콜라', 1000, 4);
INSERT INTO item (item_name, item_price, category_number) VALUES('성적', 9999, 5);

INSERT INTO environment(password) VALUES('admin');

```
