package com.example.JewelryShop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerDTO extends BaseDTO {
    private Long user_id;
    private Long[] wish_list;
    private Long cart_id;
    private Long[] order_history;
}
