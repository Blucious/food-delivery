package org.group9.fooddelivery.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class OrderListItem {
    private Integer orderId;
    private Integer state;
    private BigDecimal Totalprice;
    private Integer Totalquantity;
    private List<String> foods;

    private Integer id;
    private BigDecimal price;
    private String name;
    private Integer quantity;

    @Override
    public String toString() {
        return "OrderListItem{" +
                "orderId=" + orderId +
                ", state=" + state +
                ", Totalprice=" + Totalprice +
                ", Totalquantity=" + Totalquantity +
                ", foods=" + foods +
                ", id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }

//    public BigDecimal getTotalPrice() {
//        return price.multiply(BigDecimal.valueOf(quantity));
//    }


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getTotalprice() {
        return Totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        Totalprice = totalprice;
    }

    public Integer getTotalquantity() {
        return Totalquantity;
    }

    public void setTotalquantity(Integer totalquantity) {
        Totalquantity = totalquantity;
    }

    public List<String> getFoods() {
        return foods;
    }

    public void setFoods(List<String> foods) {
        this.foods = foods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
