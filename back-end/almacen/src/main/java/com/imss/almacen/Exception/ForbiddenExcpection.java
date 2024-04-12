package com.imss.almacen.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenExcpection extends RuntimeException{
    public ForbiddenExcpection(String message)  {
        super(message);
    }
}
