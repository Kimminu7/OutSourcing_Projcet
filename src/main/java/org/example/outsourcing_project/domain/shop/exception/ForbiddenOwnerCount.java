package org.example.outsourcing_project.domain.shop.exception;

import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;

public class ForbiddenOwnerCount extends BaseException {
    public ForbiddenOwnerCount() {
        super(ErrorCode.FORBIDDEN_OWNER_COUNT);
    }
}
