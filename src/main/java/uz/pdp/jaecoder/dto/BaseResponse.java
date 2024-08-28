package uz.pdp.jaecoder.dto;

public class BaseResponse<T> {
    private T data;
    private boolean success;
    private String error;

    public BaseResponse(T data) {
        this.data = data;
        this.success = true;
    }

    public BaseResponse(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public BaseResponse(String error) {
        this.error = error;
    }
}
