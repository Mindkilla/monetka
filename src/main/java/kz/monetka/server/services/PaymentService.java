package kz.monetka.server.services;

import kz.monetka.server.entities.docs.Payment;
import kz.monetka.server.models.PaymentModel;
import kz.monetka.server.models.PaymentsModel;
import kz.monetka.server.models.ResponseAnswer;
import kz.monetka.server.repository.PaymentRepository;
import kz.monetka.server.utils.RestApiUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
@Service
@Transactional
public class PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class);

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    UserService userService;

    /**
     * Возвращает все платежи пользователя по AuthToken-у,
     * ID платежей кодирутся
     * @param  token  Токен пользователя полученный при входе
     * @return      возвращает список всех платежей PaymentsModel
     * @see         PaymentsModel
     */
    public PaymentsModel findByPayerId(String token){
        String userId = userService.findByToken(token).getUser().getId();
        PaymentsModel outgoingModel = new PaymentsModel();
        List<Payment> payments = paymentRepository.findByPayerId(userId);
        List<PaymentModel> paymentModels = new LinkedList<>();
        payments.forEach(payment ->
                paymentModels.add(new PaymentModel(payment))
        );
        paymentModels.forEach(payment ->
                payment.setId(RestApiUtils.encodeId(payment.getId(), userId)));
        outgoingModel.setPayments(paymentModels);
        return outgoingModel;
    }

    /**
     * Возвращает конкретный платеж пользователя по его кодированному ID и AuthToken-у
     * @param  id     Кодированный ID платежа
     * @param  token  Токен пользователя полученный при входе
     * @return      возвращает запрашиваемый платеж PaymentModel либо ResponseAnswer с ошибкой
     * @see         PaymentModel
     */
    public Object findById(String id, String token){
        if(checkToken(token)){
            String userId = userService.findByToken(token).getUser().getId();
            String decodedId = RestApiUtils.decodeId(id, userId);
            Payment payment = paymentRepository.findByIdAndPayerId(decodedId, userId);
            if (payment != null){
                PaymentModel paymentModel = new PaymentModel(payment);
                paymentModel.setId(id);
                return paymentModel;
            }
        }
        return new ResponseEntity(new ResponseAnswer(HttpStatus.BAD_REQUEST.toString(), "Invalid token"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Создание нового платежа
     * @param  incPayment  входящая модель платежа
     * @param  token  Токен пользователя полученный при входе
     * @return      возвращает модель созданного платежа PaymentModel
     * @see         PaymentModel
     */
    public Object newPayment(PaymentModel incPayment, String token) {
        Payment payment = null;
        PaymentModel outModel= null;
        if(checkToken(token)){
            String userId = userService.findByToken(token).getUser().getId();
            payment = fillPayment(incPayment, userId);
             outModel = new PaymentModel(paymentRepository.save(payment));
             outModel.setId(RestApiUtils.encodeId(outModel.getId(), userId));
        }
        return outModel;
    }

    /**
     * Обновление существующего платежа
     * @param  incPayment  входящая модель платежа
     * @param  token  Токен пользователя полученный при входе
     * @return      возвращает модель обновленного платежа PaymentModel
     * @see         PaymentModel
     */
    public Object updatePayment(PaymentModel incPayment, String token) {
        Payment payment = null;
        PaymentModel outModel= null;
        if(checkToken(token)){
            String userId = userService.findByToken(token).getUser().getId();
            payment = updPayment(incPayment, userId);
            outModel = new PaymentModel(paymentRepository.saveAndFlush(payment));
            outModel.setId(RestApiUtils.encodeId(outModel.getId(), userId));
        }
        return outModel;
    }

    /**
     * Удаление существующего платежа
     * @param  incPayment  входящая модель платежа
     * @param  token  Токен пользователя полученный при входе
     * @return      возвращает ResponseEntity со статусом HttpStatus.OK или HttpStatus.BAD_REQUEST
     * @see         ResponseEntity
     */
    public Object delPayment(PaymentModel incPayment, String token) {
        PaymentModel outModel= null;
        if(checkToken(token)){
            String userId = userService.findByToken(token).getUser().getId();
            String decodedId = RestApiUtils.decodeId(incPayment.getId(), userId);
            paymentRepository.delete(decodedId);
            return new ResponseEntity(new ResponseAnswer(HttpStatus.OK.toString(), ""), HttpStatus.OK);

        }
        return new ResponseEntity(new ResponseAnswer(HttpStatus.BAD_REQUEST.toString(), "Invalid token"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Проверка что токен существует
     * @see UserService
     */
    private boolean checkToken(String token) {
        return userService.checkToken(token);
    }

    /**
     * Заполняет сущность Платежа из модели
     */
    private Payment fillPayment(PaymentModel model, String userId) {
        Payment payment = new Payment();
        payment.setPayerId(userId);
        payment.setSysCreateTime(new Date());
        payment.setAmount(model.getAmount());
        payment.setDocDate(model.getDocDate());
        payment.setArchiveTime(model.getArchiveTime());
        payment.setDeleteTime(model.getDeleteTime());
        payment.setVersion(model.getVersion());
        return payment;
    }

    /**
     * Возвращает сущность Платежа с изменением даты и суммы
     */
    private Payment updPayment(PaymentModel model, String userId) {
        String decodedId = RestApiUtils.decodeId(model.getId(), userId);
        Payment payment = paymentRepository.findByIdAndPayerId(decodedId, userId);
        if (model.getAmount()!=null) {
            payment.setAmount(model.getAmount());
        }
        if (model.getDocDate()!=null) {
            payment.setDocDate(model.getDocDate());
        }
        return payment;
    }
}
