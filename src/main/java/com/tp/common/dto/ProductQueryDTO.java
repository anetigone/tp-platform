package com.tp.common.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class ProductQueryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer page;
    private Integer size;
    //商品成色：0：全新, 1：99新, 2：95新, 3：9成新,4： 8成新以下
    private List<Integer> conditions;
    private List<String> keywords;
    private Long categoryId;
    private Double minPrice;
    private Double maxPrice;
}
