package com.github.atsarev01.mochito.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (code = HttpStatus.BAD_REQUEST)
public class EmployeeStorageIsFullExeption extends RuntimeException{
}
