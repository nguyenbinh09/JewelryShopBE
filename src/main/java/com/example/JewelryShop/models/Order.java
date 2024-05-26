package com.example.JewelryShop.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "`order`")
public class Order extends BaseModel {
    @JsonManagedReference
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "completed_time")
    private Date completed_time;

    @Column(name = "status")
    private String status = "Ordering";

    @Column(name = "total_price")
    private Double total_price = 0.0;

    @Column(name = "amount")
    private Double amount = 0.0;
    
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;
}
