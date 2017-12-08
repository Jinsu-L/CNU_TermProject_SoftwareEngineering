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
CREATE TABLE card_payment
(
   card_number          CHAR(16) NULL,
   card_company         VARCHAR(20) NULL,
   approval_number      INTEGER NULL,
   payment_number       INTEGER NOT NULL
);

ALTER TABLE card_payment
ADD PRIMARY KEY (payment_number);

CREATE TABLE category
(
   categoryID           INTEGER NOT NULL,
   category_name        VARCHAR(20) NULL
);

ALTER TABLE category
ADD PRIMARY KEY (categoryID);

CREATE TABLE coupon_payment
(
   coupon_number        CHAR(8) NULL,
   coupon_amount        INTEGER NULL,
   payment_number       INTEGER NOT NULL
);

ALTER TABLE coupon_payment
ADD PRIMARY KEY (payment_number);

CREATE TABLE item
(
   item_name            VARCHAR(20) NOT NULL,
   item_price           INTEGER NULL,
   categoryID           INTEGER NOT NULL
);

ALTER TABLE item
ADD PRIMARY KEY (item_name);

CREATE TABLE payment
(
   payment_number       INTEGER NOT NULL,
   payment_amount       INTEGER NULL,
   payment_type         ENUM('cash','card','coupon') NULL,
   payment_date         DATE NULL,
   shopping_basket_number INTEGER NOT NULL
);

ALTER TABLE payment
ADD PRIMARY KEY (payment_number);

CREATE TABLE shopping_basket
(
   shopping_basket_number INTEGER NOT NULL,
   total_amount         INTEGER NULL
);

ALTER TABLE shopping_basket
ADD PRIMARY KEY (shopping_basket_number);

CREATE TABLE shopping_history
(
   shopping_history_number INTEGER NOT NULL,
   item_quantity        INTEGER NULL,
   shopping_basket_number INTEGER NOT NULL,
   item_name            VARCHAR(20) NOT NULL
);

ALTER TABLE shopping_history
ADD PRIMARY KEY (shopping_history_number);

ALTER TABLE card_payment
ADD CONSTRAINT R_4 FOREIGN KEY (payment_number) REFERENCES payment (payment_number);

ALTER TABLE coupon_payment
ADD CONSTRAINT R_5 FOREIGN KEY (payment_number) REFERENCES payment (payment_number);

ALTER TABLE item
ADD CONSTRAINT R_6 FOREIGN KEY (categoryID) REFERENCES category (categoryID);

ALTER TABLE payment
ADD CONSTRAINT R_1 FOREIGN KEY (shopping_basket_number) REFERENCES shopping_basket (shopping_basket_number);

ALTER TABLE shopping_history
ADD CONSTRAINT R_2 FOREIGN KEY (shopping_basket_number) REFERENCES shopping_basket (shopping_basket_number);

ALTER TABLE shopping_history
ADD CONSTRAINT R_20 FOREIGN KEY (item_name) REFERENCES item (item_name);
INSERT INTO category VALUES(1,'set');
INSERT INTO category VALUES(2,'single');
INSERT INTO category VALUES(3,'drink');
INSERT INTO category VALUES(4,'side');
INSERT INTO category VALUES(5,'etc');
```
