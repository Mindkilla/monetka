package kz.monetka.server.entities.docs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.monetka.server.entities.BaseEntity;
import kz.monetka.server.utils.FieldSize;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
@Entity
@Table(name = "MONETKA_PAYMENTS")
public class Payment extends BaseEntity {
    private static final Logger LOGGER = Logger.getLogger(Payment.class);

    @Column(length = FieldSize.UUID)
    @Index(name = "IXS_PAYMENTS_PAYERID")
    @JsonIgnore
    private String payerId;

    @Column
    private Date docDate;

    @Column(precision = FieldSize.AMOUNT_PRECISION, scale = FieldSize.AMOUNT_SCALE)
    private BigDecimal amount;

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