package org.example.outsourcing_project.domain.shop.exception;

import org.example.outsourcing_project.common.exception.ErrorCode;
import org.example.outsourcing_project.common.exception.custom.BaseException;

public class NotFoundShop extends BaseException {
    public NotFoundShop() {
        super(ErrorCode.NOT_FOUND_SHOP);
    }
}
