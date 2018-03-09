package kz.monetka.server.entities.docs;

import kz.monetka.server.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MONETKA_PAY_CATEGORY")
public class PaymentCategory extends BaseEntity {

    @Column
    private String name;

    @Column
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
