package com.tp.common.exception;

@SuppressWarnings("unused")
public class ExceptionMessage {
    // User related exceptions
    public static final String USER_NOT_FOUND = "用户不存在";
    public static final String USERNAME_DUPLICATE = "用户名已存在";
    public static final String PASSWORD_MISMATCH = "密码错误";
    public static final String USER_FORBIDDEN = "用户被封禁";
    public static final String USER_BALANCE_NOT_ENOUGH = "用户余额不足";

    // Cart related exceptions
    public static final String CART_NOT_FOUND = "购物车不存在";
    public static final String CART_USER_ID_NULL = "用户ID不能为空";
    public static final String CART_STATUS_INVALID = "购物车状态无效";
    public static final String CART_ID_NULL = "购物车ID不能为空";

    // CartItem related exceptions
    public static final String CART_ITEM_NOT_FOUND = "购物车商品不存在";
    public static final String CART_ITEM_ID_NULL = "购物车商品ID不能为空";
    public static final String CART_ITEM_PRODUCT_ID_NULL = "商品ID不能为空";
    public static final String CART_ITEM_QUANTITY_INVALID = "商品数量必须大于0";
    public static final String CART_ITEM_QUANTITY_EXCEEDED = "商品数量不能超过1000";
    public static final String CART_ITEM_PRICE_INVALID = "商品价格必须大于等于0";

    // Order related exceptions
    public static final String ORDER_NOT_FOUND = "订单不存在";
    public static final String ORDER_ID_NULL = "订单ID不能为空";
    public static final String ORDER_USER_ID_NULL = "用户ID不能为空";
    public static final String ORDER_TOTAL_AMOUNT_INVALID = "订单总金额必须大于等于0";
    public static final String ORDER_STATUS_INVALID = "订单状态无效";
    public static final String ORDER_PAYMENT_METHOD_INVALID = "支付方式无效";
    public static final String ORDER_CANNOT_CANCEL = "当前订单状态无法取消";
    public static final String ORDER_CANNOT_PAY = "当前订单状态无法支付";
    public static final String ORDER_CANNOT_SHIP = "当前订单状态无法发货";
    public static final String ORDER_CANNOT_RECEIVE = "当前订单状态无法确认收货";

    // OrderItem related exceptions
    public static final String ORDER_ITEM_NOT_FOUND = "订单商品不存在";
    public static final String ORDER_ITEM_ID_NULL = "订单商品ID不能为空";
    public static final String ORDER_ITEM_ORDER_ID_NULL = "订单ID不能为空";
    public static final String ORDER_ITEM_PRODUCT_ID_NULL = "商品ID不能为空";
    public static final String ORDER_ITEM_QUANTITY_INVALID = "商品数量必须在1-1000之间";
    public static final String ORDER_ITEM_UNIT_PRICE_INVALID = "商品单价必须大于等于0";
    public static final String ORDER_ITEM_TOTAL_PRICE_INVALID = "商品总价必须大于等于0";
    public static final String ORDER_ITEM_LIST_EMPTY = "订单商品列表不能为空";
    public static final String ORDER_ITEM_ORDER_ID_MISMATCH = "订单商品不属于同一订单";
    public static final String ORDER_ITEM_PRODUCT_ID_MISMATCH = "订单商品ID与原订单商品不符";
    public static final String ORDER_ITEM_CANNOT_DELETE = "已发货或完成的订单商品不能删除";

    // Comment related exceptions
    public static final String COMMENT_NOT_FOUND = "评论不存在";
    public static final String COMMENT_ID_NULL = "评论ID不能为空";
    public static final String COMMENT_PRODUCT_ID_NULL = "商品ID不能为空";
    public static final String COMMENT_USER_ID_NULL = "用户ID不能为空";
    public static final String COMMENT_CONTENT_EMPTY = "评论内容不能为空";
    public static final String COMMENT_CONTENT_TOO_LONG = "评论内容不能超过1000个字符";
    public static final String COMMENT_RATING_INVALID = "评分必须在1-5之间";
    public static final String COMMENT_ALREADY_EXISTS = "您已经评论过该商品";
    public static final String COMMENT_PARENT_NOT_FOUND = "父评论不存在";
    public static final String COMMENT_PARENT_ID_INVALID = "父评论ID无效";
    public static final String COMMENT_STATUS_INVALID = "评论状态无效";

    // Address related exceptions
    public static final String ADDRESS_ID_NULL = "地址ID不能为空";
    public static final String ADDRESS_USER_ID_NULL = "用户ID不能为空";
    public static final String NO_DEFAULT_ADDRESS = "不存在默认地址";

    // Squat related exceptions
    public static final String SQUAT_ID_NULL = "蹲蹲ID不能为空";
    public static final String SQUAT_USER_ID_NULL = "用户ID或蹲蹲ID不能为空";
    public static final String SQUAT_PRODUCT_ID_NULL = "商品ID不能为空";

    // Message&Conversation related exceptions
    public static final String MESSAGE_ID_NULL = "消息ID不能为空";
    public static final String CONVERSATION_ID_NULL = "会话ID不能为空";
    public static final String CONVERSATION_NOT_FOUND = "会话不存在";
    public static final String CONVERSATION_SENDER_ID_NULL = "发送者ID不能为空";
    public static final String NO_PERMISSION = "没有权限查看此对话";
    public static final String CONVERSATION_USER_ID_NULL = "用户ID不能为空";
}
