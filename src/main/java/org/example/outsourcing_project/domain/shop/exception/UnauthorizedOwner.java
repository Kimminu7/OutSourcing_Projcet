package org.example.outsourcing_project.domain.shop.exception;

import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;

public class UnauthorizedOwner extends BaseException {

    public UnauthorizedOwner() {
        super(ErrorCode.UNAUTHORIZED_OWNER);
    }
}
