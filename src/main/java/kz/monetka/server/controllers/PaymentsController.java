package kz.monetka.server.controllers;

import kz.monetka.server.models.PaymentModel;
import kz.monetka.server.models.PaymentsModel;
import kz.monetka.server.services.PaymentService;
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
    private static final String HEADER = "Token";

    @Autowired
    PaymentService paymentService;

    @RequestMapping(value = "/payment/getAll", method = RequestMethod.GET)
    public PaymentsModel getAll(@RequestHeader(name = HEADER) String token) {
        return paymentService.findByPayerId(token);
    }

    @RequestMapping(value = "/payment/get/{id}", method = RequestMethod.GET)
    public Object getOne(@PathVariable("id") String id, @RequestHeader(name = HEADER) String token) {
        return paymentService.findById(id, token);
    }

    @RequestMapping(value = "/payment/new", method = RequestMethod.POST)
    public Object addPayment(@RequestBody PaymentModel incPayment, @RequestHeader(name = HEADER) String token) {
        return paymentService.newPayment(incPayment, token);
    }

    @RequestMapping(value = "/payment/update", method = RequestMethod.PATCH)
    public Object updatePayment(@RequestBody PaymentModel incPayment, @RequestHeader(name = HEADER) String token) {
        return paymentService.updatePayment(incPayment, token);
    }

    @RequestMapping(value = "/payment/delete", method = RequestMethod.DELETE)
    public Object delPayment(@RequestBody PaymentModel incPayment, @RequestHeader(name = HEADER) String token) {
        return paymentService.delPayment(incPayment, token);
    }
}
