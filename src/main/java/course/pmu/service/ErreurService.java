package course.pmu.service;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import course.pmu.reponse.ErreurReponse;

@ControllerAdvice
public class ErreurService {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErreurReponse invalidData(MethodArgumentNotValidException ex) {
        ErreurReponse erreurReponse = new ErreurReponse();
        erreurReponse.setMessage(ex.getFieldError().getDefaultMessage());
        erreurReponse.setCode(HttpStatus.FORBIDDEN);
        erreurReponse.setErreur(ex.getFieldError().getField());
       // System.out.println(ex.toString());
        return erreurReponse;
    }
    
    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErreurReponse invalidData(DataIntegrityViolationException ex) {
        ErreurReponse erreurReponse = new ErreurReponse();
        erreurReponse.setMessage(ex.getCause().getMessage());
        erreurReponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return erreurReponse;
    }
}
