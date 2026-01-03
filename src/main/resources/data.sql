CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(50),
    avatar VARCHAR(500),
    status INTEGER DEFAULT 1, -- 0:禁用 1:正常
    credit INTEGER DEFAULT 0,
    balance DECIMAL(10,2) DEFAULT 0.00,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    realname VARCHAR(255),
    phone VARCHAR(50),
    email VARCHAR(255),
    avatar VARCHAR(500),
    role INTEGER DEFAULT 0, -- 0:管理员 1:客服
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    province VARCHAR(100),
    city VARCHAR(100),
    street VARCHAR(255),
    detail VARCHAR(500),
    zip_code VARCHAR(20),
    is_default INTEGER DEFAULT 0, -- 0:非默认 1:默认
    status INTEGER DEFAULT 1, -- 0:禁用 1:正常
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    icon VARCHAR(500),
    status INTEGER DEFAULT 1,
    sort INTEGER DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seller_id BIGINT NOT NULL,
    category_id BIGINT,
    address_id BIGINT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image VARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    quantity INTEGER DEFAULT 0,
    `condition` INTEGER DEFAULT 0, -- 商品成色：0：全新, 1：99新, 2：95新, 3：9成新,4： 8成新以下
    status INTEGER DEFAULT 1, -- 0-下架，1-在售，2-已卖出，3-已预订
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE IF NOT EXISTS product_image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    url VARCHAR(500) NOT NULL,
    name VARCHAR(255),
    description TEXT,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    status INTEGER DEFAULT 1, -- 1:正常 2:取消
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255),
    product_image VARCHAR(500),
    quantity INTEGER DEFAULT 1,
    unit DECIMAL(10,2),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES cart(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE IF NOT EXISTS `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seller_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    order_no VARCHAR(100) NOT NULL UNIQUE,
    remark TEXT,
    amount DECIMAL(10,2) DEFAULT 0.00,
    discount DECIMAL(10,2) DEFAULT 0.00,
    status INTEGER DEFAULT 0, -- 0:待付款，1:待发货，2:待收货，3:已完成，4:已取消，5:退款中，6:已退款，7:交易关闭
    pay_method INTEGER DEFAULT 0, -- 0:余额支付,1:微信支付,2:支付宝支付
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users(id),
    FOREIGN KEY (buyer_id) REFERENCES users(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(255),
    product_image VARCHAR(500),
    unit_price DECIMAL(10,2) NOT NULL,
    quantity INTEGER NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES `order`(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE IF NOT EXISTS collects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT unique_user_product UNIQUE (user_id, product_id)
);

CREATE TABLE IF NOT EXISTS squat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    remind_type INTEGER, -- 0-降价提醒，1-上架提醒
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE IF NOT EXISTS comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    content TEXT,
    star INTEGER, -- 评分
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (order_id) REFERENCES `order`(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE IF NOT EXISTS conversation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    last_message_id BIGINT,
    sender_last_message_id BIGINT,
    receiver_last_message_id BIGINT,
    sender_unread_count INTEGER DEFAULT 0,
    receiver_unread_count INTEGER DEFAULT 0,
    sender_deleted INTEGER DEFAULT 0, -- 0-未删除，1-已删除
    receiver_deleted INTEGER DEFAULT 0,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conversation_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT,
    message_type INTEGER DEFAULT 1, -- 1-文本，2-图片，3-语音
    is_read INTEGER DEFAULT 0, -- 0-未读，1-已读
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 然后添加外键约束 todo 解决循环引用
ALTER TABLE conversation ADD CONSTRAINT fk_conversation_users_sender
    FOREIGN KEY (sender_id) REFERENCES users(id);
ALTER TABLE conversation ADD CONSTRAINT fk_conversation_users_receiver
    FOREIGN KEY (receiver_id) REFERENCES users(id);
ALTER TABLE conversation ADD CONSTRAINT fk_conversation_message
    FOREIGN KEY (last_message_id) REFERENCES message(id);

ALTER TABLE message ADD CONSTRAINT fk_message_conversation
    FOREIGN KEY (conversation_id) REFERENCES conversation(id);
ALTER TABLE message ADD CONSTRAINT fk_message_users_sender
    FOREIGN KEY (sender_id) REFERENCES users(id);
ALTER TABLE message ADD CONSTRAINT fk_message_users_receiver
    FOREIGN KEY (receiver_id) REFERENCES users(id);

-- 为常用查询字段创建索引
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_product_seller_id ON product(seller_id);
CREATE INDEX idx_product_category_id ON product(category_id);
CREATE INDEX idx_order_buyer_id ON `order`(buyer_id);
CREATE INDEX idx_order_seller_id ON `order`(seller_id);
CREATE INDEX idx_collection_user_id ON collects(user_id);
CREATE INDEX idx_comment_product_id ON comment(product_id);
CREATE INDEX idx_message_conversation_id ON message(conversation_id);
CREATE INDEX idx_conversation_sender_receiver ON conversation(sender_id, receiver_id);

insert into users (username, password, email, phone, avatar, status, credit, balance) values ('user1', 'test1', 'admin@example.com', '12345678901', 'https://example.com/avatar.png', 1, 0, 0);
insert into admin (username, password, realname, phone, email, avatar, role) values ('admin', 'test', '管理员', '12345678901', 'admin@example.com', 'https://example.com/avatar.png', 0);
insert into category (name, description, icon, status, sort) values ('测试分类', '这是一个测试分类', 'https://example.com/icon.png', 1, 1);
insert into address (user_id, province, city, street, detail, zip_code, is_default, status) values (1, '广东省', '广州市', '天河区', '天河路1号', '510000', 1, 1);
insert into product (seller_id, category_id, address_id, name, description, image, price, quantity, `condition`, status, create_time, update_time) VALUES (1, 1, 1, '测试商品', '这是一个测试商品', 'https://example.com/product.png', 10.00, 100, 1, 1, now(), now());
