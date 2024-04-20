package com.example.JewelryShop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_at")
    @CreatedDate
    private Date created_at;
    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updated_at;
    @Column(name = "created_by")
    @CreatedBy
    private Long created_by;
    @Column(name = "updated_by")
    @LastModifiedBy
    private Long updated_by;
    @Column(name = "is_deleted")
    private Boolean is_deleted;
}
