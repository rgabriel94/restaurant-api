package com.gabriel.restaurant.order.entity;

import com.gabriel.restaurant.order.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "closing_date")
    private Date closingDate;

    @Column(name = "closing_price", nullable = false)
    private double closingPrice = 0D;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private List<OrderLine> orderLines = new ArrayList<>();

    public void calculateClosingPrice() {
        orderLines.forEach(orderLine -> closingPrice += orderLine.totalPriceOfLine());
    }
}
