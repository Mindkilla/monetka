package kz.monetka.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.monetka.server.entities.docs.Payment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
public class PaymentModel {

    private String id;
    private Integer version;
    private Date sysCreateTime;
    private Long archiveTime;
    private Long deleteTime;
    @JsonIgnore
    private String payerId;
    private Date docDate;
    private BigDecimal amount;
    private String category;
    private String categoryIcon;

    public PaymentModel() {
    }

    public PaymentModel(Payment payment) {
        this.id = payment.getId();
        this.version = payment.getVersion();
        this.sysCreateTime = payment.getSysCreateTime();
        this.archiveTime = payment.getArchiveTime();
        this.deleteTime = payment.getDeleteTime();
        this.payerId = payment.getPayerId();
        this.docDate = payment.getDocDate();
        this.amount = payment.getAmount();
        if (payment.getCategory() != null){
            this.category = payment.getCategory().getName();
            this.categoryIcon = payment.getCategory().getIcon();
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getSysCreateTime() {
        return sysCreateTime;
    }

    public void setSysCreateTime(Date sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }

    public Long getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Long archiveTime) {
        this.archiveTime = archiveTime;
    }

    public Long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Long deleteTime) {
        this.deleteTime = deleteTime;
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
}
