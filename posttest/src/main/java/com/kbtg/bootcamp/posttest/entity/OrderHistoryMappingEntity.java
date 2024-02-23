package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "order_history_mapping", schema = "public", catalog = "Lotteries")
public class OrderHistoryMappingEntity {

    @Id
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Basic
    @Column(name = "ticket_id", nullable = true)
    private Integer ticketId;
    @Basic
    @Column(name = "create_at", nullable = true)
    private Timestamp createAt;
    @Basic
    @Column(name = "update_at", nullable = true)
    private Timestamp updateAt;
    @Basic
    @Column(name = "status_id", nullable = true)
    private Integer statusId;

    @Getter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id", insertable = false,updatable = false)
    private TicketEntity ticketEntity;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderHistoryMappingEntity that = (OrderHistoryMappingEntity) o;
        return orderId == that.orderId && userId == that.userId && Objects.equals(ticketId, that.ticketId) && Objects.equals(createAt, that.createAt) && Objects.equals(updateAt, that.updateAt) && Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, ticketId, createAt, updateAt, statusId);
    }
}
