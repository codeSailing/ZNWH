package com.quickdone.znwh.exceptios;


/**
 * Created by psf on 2017/10/22.
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -2469865628947556263L;
    private String message;
    private int errCode;


    public BaseException(){
        super();
    }

    public BaseException(String message){
        this.message=message;
    }

    public BaseException(String message, int errCode){
        this.message=message;
        this.errCode=errCode;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}