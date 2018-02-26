package kz.monetka.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
public class PaymentsModel {
    @NotNull
    @NotEmpty
    @JsonProperty("payments")
    private List<PaymentModel> payments;

    public List<PaymentModel> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentModel> payments) {
        this.payments = payments;
    }

    public PaymentsModel() {
    }
}
