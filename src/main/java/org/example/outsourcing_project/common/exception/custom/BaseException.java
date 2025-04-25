package org.example.outsourcing_project.common.exception.custom;

import lombok.Getter;
import org.example.outsourcing_project.common.exception.ErrorCode;

@Getter
public class BaseException extends RuntimeException {
    ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}