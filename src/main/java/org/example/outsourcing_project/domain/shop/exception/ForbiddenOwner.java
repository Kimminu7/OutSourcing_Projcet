package org.example.outsourcing_project.domain.shop.exception;

import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;

public class ForbiddenOwner extends BaseException {

    public ForbiddenOwner() {
        super(ErrorCode.FORBIDDEN_OWNER);
    }
}
