package com.group3.dto;


/**
 * @ClassName:
 * @Description:
 * @Author: lvxz
 * @Date: 2018-07-26  18:02
 */
public class ResponseInfoDTO<T> {

    private int code;
    private String msg;
    private T data;


    public static <T> ResponseInfoDTO<T> success(T data){
        return new ResponseInfoDTO<T>(data);
    }
    private ResponseInfoDTO(T data) {
        this.code = 1;
        this.msg = "success";
        this.data = data;
    }

    public static <T> ResponseInfoDTO<T> success(MessageDTO messageDTO){
        return new ResponseInfoDTO<T>(messageDTO);
    }


    public static <T> ResponseInfoDTO<T> success(MessageDTO messageDTO, T data){
        return new ResponseInfoDTO<T>(messageDTO,data);
    }
    private ResponseInfoDTO(MessageDTO messageDTO, T data) {
        this.code = messageDTO.getCode();
        this.msg = messageDTO.getMsg();
        this.data = data;
    }


    public static <T> ResponseInfoDTO<T> fail(String msg){
        return new ResponseInfoDTO<T>(msg);
    }
    private ResponseInfoDTO(String msg) {
        this.code = 0;
        this.msg = msg;
    }


    /**
     * fail
     * @param messageDTO
     * @param <T>
     * @return
     */
    public static <T> ResponseInfoDTO<T> fail(MessageDTO messageDTO){
        return new ResponseInfoDTO<T>(messageDTO);
    }
    private ResponseInfoDTO(MessageDTO messageDTO) {
        if(messageDTO == null){
            return;
        }
        this.code = messageDTO.getCode();
        this.msg = messageDTO.getMsg();
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
