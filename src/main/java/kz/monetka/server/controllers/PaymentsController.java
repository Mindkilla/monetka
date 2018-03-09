package kz.monetka.server.controllers;

import kz.monetka.server.models.PaymentModel;
import kz.monetka.server.services.PaymentService;
import kz.monetka.server.utils.Consts;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
@RestController
public class PaymentsController {
    private static final Logger LOGGER = Logger.getLogger(PaymentsController.class);

    @Autowired
    PaymentService paymentService;

    @GetMapping(value = "/payment/getAll")
    public Object getAll(@RequestHeader(name = Consts.HEADER) String token) {
        return paymentService.findByPayerId(token);
    }

    @GetMapping(value = "/payment/{id}")
    public Object getOne(@PathVariable("id") String id, @RequestHeader(name = Consts.HEADER) String token) {
        return paymentService.findById(id, token);
    }

    @PostMapping(value = "/payment")
    public Object addPayment(@RequestBody PaymentModel incPayment, @RequestHeader(name = Consts.HEADER) String token) {
        return paymentService.newPayment(incPayment, token);
    }

    @PatchMapping(value = "/payment")
    public Object updatePayment(@RequestBody PaymentModel incPayment, @RequestHeader(name = Consts.HEADER) String token) {
        return paymentService.updatePayment(incPayment, token);
    }

    @DeleteMapping(value = "/payment/{id}")
    public Object delPayment(@PathVariable("id") String id, @RequestHeader(name = Consts.HEADER) String token) {
        return paymentService.delPayment(id, token);
    }
}
