package com.tp.controller;

import com.tp.common.dto.ProductDTO;
import com.tp.common.entity.Product;
import com.tp.common.entity.ProductImage;
import com.tp.common.result.PageResult;
import com.tp.common.result.Result;
import com.tp.service.ProductImageService;
import com.tp.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImageService;

    public ProductController(ProductService productService, ProductImageService productImageService) {
        this.productService = productService;
        this.productImageService = productImageService;
    }

    /**
     * 创建商品
     * @param dto 商品信息
     * @return 创建结果(商品ID)
     */
    @PostMapping("")
    public Result<Long> create(ProductDTO dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        Long id = productService.create(product);

        return Result.success(id);
    }

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品信息
     */
    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable("id") Long id) {
        Product product = productService.getById(id);
        return Result.success(product);
    }

    /**
     * 分页查询商品
     * //todo 按条件查询
     * @return 商品列表
     */
    @GetMapping("/list")
    public PageResult<Product> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<Product> products = productService.getPage(page, size);
        Long total = productService.count();
        return PageResult.success(products, total, page, size);
    }

    /**
     * 根据卖家ID查询商品
     * @param sellerId 卖家ID
     * @return 商品列表
     */
    @GetMapping("/seller/{sellerId}")
    public PageResult<Product> getBySellerId(@PathVariable("sellerId") Long sellerId,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<Product> products = productService.getBySellerId(sellerId);
        Long total = productService.countBySellerId(sellerId);

        return PageResult.success(products, total, page, size);
    }

    /**
     * 删除商品
     * @param id 商品ID
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    public Result<String> deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);

        return Result.success();
    }

    /**
     * 修改商品信息
     * @param id 商品ID
     * @param dto 商品信息
     * @return 修改结果
     */
    @PostMapping("/update/{id}")
    public Result<String> update(@PathVariable("id") Long id, @RequestBody ProductDTO dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        product.setId(id);
        productService.update(product);

        return Result.success();
    }

    @PostMapping("/image")
    public Result<String> uploadImage() {
        //todo 上传图片
        return Result.success();
    }

    /**
     * 获取商品图片
     * @param id 商品ID
     * @return 商品图片列表
     */
    @GetMapping("/image/{id}")
    public Result<List<ProductImage>> getImage(@PathVariable("id") Long id) {
        List<ProductImage> images = productImageService.getByProductId(id);
        return Result.success(images);
    }

    /**
     * 删除商品图片
     * @param id 图片ID
     * @return 删除结果
     */
    @PostMapping("/image/delete/{id}")
    public Result<String> deleteImage(@PathVariable("id") Long id) {
        productImageService.deleteById(id);
        return Result.success();
    }
}
