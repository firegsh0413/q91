package com.icchance.q91.common.error;

/**
 * <p>
 * 業務異常
 * </p>
 * @author 6687353
 * @since 2023/9/5 15:37:51
 */
public class ServiceException extends AbstractException {

    private static final long serialVersionUID = 4205338879153573L;

    public ServiceException(String error) {
        super(error);
    }

    public ServiceException(String error, String... data) {
        super(error, data);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(ServiceExceptionModel e) {
        super(e.getCode(), e.getMessage());
    }

    public ServiceException(ServiceExceptionModel e, String... data) {
        super(e.getCode(), e.getMessage(), data);
    }

    @Override
    public Throwable fillInStackTrace(){
        return this;
    }
}
