package com.tp.controller;

import com.tp.common.context.BaseContext;
import com.tp.common.dto.CartItemDTO;
import com.tp.common.entity.Cart;
import com.tp.common.entity.CartItem;
import com.tp.common.entity.Product;
import com.tp.common.result.Result;
import com.tp.common.vo.CartVO;
import com.tp.service.CartItemService;
import com.tp.service.CartService;
import com.tp.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final ProductService productService;

    public CartController(CartService cartService,
                          CartItemService cartItemService,
                          ProductService productService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    /**
     * 获取用户购物车
     * @return 购物车信息
     */
    @GetMapping("")
    public Result<CartVO> getOrCreateCart() {
        Long userId = BaseContext.getCurrentUserId();
        Cart cart = cartService.getOrCreateCart(userId);
        List<CartItem> items = cartItemService.getByCartId(cart.getId());

        CartVO vo = CartVO.builder()
                .cartItems(items)
                .cart(cart)
                .build();

        return Result.success(vo);
    }

    /**
     * 添加商品到购物车
     * @param dto 商品信息
     * @return 操作结果
     */
    @PostMapping("/item")
    public Result<String> addToCart(@RequestBody CartItemDTO dto) {
        Long userId = BaseContext.getCurrentUserId();
        Product product = productService.getById(dto.getProductId());
        CartItem item = CartItem.builder()
                .cartId(dto.getCartId())
                .productId(product.getId())
                .productName(product.getName())
                .productImage(product.getImage())
                .quantity(dto.getQuantity())
                .unit(BigDecimal.valueOf(product.getPrice()))
                .build();
        cartItemService.addToCart(item);

        return Result.success();
    }

    /**
     * 更新购物车项
     * @param id 购物车项ID
     * @param quantity 数量
     * @return 操作结果
     */
    @PostMapping("/item/update")
    public Result<String> updateCartItem(@RequestParam("id") Long id, @RequestParam("quantity") Integer quantity) {
        cartItemService.updateQuantity(id, quantity);

        return Result.success();
    }

    /**
     * 删除购物车项
     * @param id 购物车项ID
     * @return 操作结果
     */
    @PostMapping("/item/delete/{id}")
    public Result<String> deleteById(@PathVariable("id") Long id) {
        cartItemService.deleteById(id);
        return Result.success();
    }

    /**
     * 清空购物车
     * @return 操作结果
     */
    @PostMapping("/clear")
    public Result<String> clearCart() {
        Long userId = BaseContext.getCurrentUserId();
        Cart cart = cartService.getOrCreateCart(userId);
        Long cartId = cart.getId();
        cartItemService.clearByCartId(cartId);
        return Result.success();
    }
}
