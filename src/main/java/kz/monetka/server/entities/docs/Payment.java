package kz.monetka.server.entities.docs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.monetka.server.entities.BaseEntity;
import kz.monetka.server.entities.Comment;
import kz.monetka.server.utils.Consts;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
@Entity
@Table(name = "MONETKA_PAYMENTS")
public class Payment extends BaseEntity {

    @Column(length = Consts.UUID)
    @Index(name = "IXS_PAYMENTS_PAYERID")
    @JsonIgnore
    private String payerId;

    @Column
    private Date docDate;

    @Comment("Категория платежа")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId")
    private PaymentCategory category;

    @Column(precision = Consts.AMOUNT_PRECISION, scale = Consts.AMOUNT_SCALE)
    private BigDecimal amount;

    public PaymentCategory getCategory() {
        return category;
    }

    public void setCategory(PaymentCategory category) {
        this.category = category;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payerId='" + payerId + '\'' +
                ", docDate=" + docDate +
                ", amount=" + amount +
                '}';
    }

    public Payment() {
    }
}
