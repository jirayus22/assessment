package com.kbtg.bootcamp.posttest.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ticket", schema = "public", catalog = "Lotteries")
public class TicketEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ticket_id", nullable = false)
    private Integer ticketId;
    @NotNull(message = "Ticket cannot be null")
    @DecimalMin(value = "100000", inclusive = true, message = "Please enter a 6-digit number.")
    @DecimalMax(value = "999999", inclusive = true, message = "Please enter a 6-digit number.")
    @Basic
    @Column(name = "ticket", nullable = false)
    private Integer ticket;
    @NotNull
    @Basic
    @Column(name = "price", nullable = false)
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 100000, message = "Price must be less than or equal to 100000")
    private Integer price;
    @NotNull
    @Basic
    @Column(name = "amount", nullable = false)
    @Min(value = 1, message = "Amount must be greater than or equal to 1")
    @Max(value = 100000, message = "Amount must be less than or equal to 100000")
    private Integer amount;
    @Basic
    @Column(name = "create_at", nullable = true)
    private Timestamp createAt;
    @Basic
    @Column(name = "create_by", nullable = true)
    private Integer createBy;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketEntity that = (TicketEntity) o;
        return ticketId == that.ticketId && ticket == that.ticket && Objects.equals(createAt, that.createAt) && Objects.equals(createBy, that.createBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, ticket, createAt, createBy);
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
