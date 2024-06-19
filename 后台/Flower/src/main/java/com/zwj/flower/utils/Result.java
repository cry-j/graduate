package com.zwj.flower.utils;

import com.zwj.flower.pojo.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author： Jing
 * @Date: 2024/4/14 18:06
 * @Description: Result工具类，用于响应返回数据
 */
@Data
public class Result<T> {
    /** 结果状态 ,具体状态码参见ResultCode.java*/
    private int status;//状态码
    private String message;//状态码信息
    private T data;//存储返回的数据信息（对象类型或者基本类型）
    private List<T> dataList;//存储返回的数据集合
    private Page<T> page;//存储返回的分页类
    private long timestamp ;//用于存储时间戳


    public Result (){
        this.timestamp = System.currentTimeMillis();
    }
    /**
     * @date: 2024/5/27 9:58
     * @description:  运行成功时的操作，没有返回数据
     * @param: []
     * @return: com.zwj.flower.utils.Result<T>
     */
    public static <T> Result<T> success(){
        Result<T> resultData = new Result<>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        return resultData;
    }
    /**
     * @date: 2024/5/27 9:58
     * @description:  同上，具有返回数据，单个类型
     * @param: [T]
     * @return: com.zwj.flower.utils.Result<T>
     */
    public static <T> Result<T> success(T data) {
        Result<T> resultData = new Result<>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }
    /**
     * @date: 2024/5/27 9:59
     * @description:  重载，返回数据为集合
     * @param: [java.util.List<T>]
     * @return: com.zwj.flower.utils.Result<T>
     */
    public static <T> Result<T> success(List<T> dataList) {
        Result<T> resultData = new Result<>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setDataList(dataList);
        return resultData;
    }
    /**
     * @date: 2024/5/27 10:00
     * @description:  返回数据为Page
     * @param: [com.zwj.flower.pojo.Page<T>]
     * @return: com.zwj.flower.utils.Result<T>
     */
    public static <T> Result<T> success(Page<T> page) {
        Result<T> resultData = new Result<>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setPage(page);
        return resultData;
    }
    /**
     * @date: 2024/5/27 10:00
     * @description:  返回失败时的操作
     * @param: [int, java.lang.String]
     * @return: com.zwj.flower.utils.Result<T>
     */
    public static <T> Result<T> fail(int code, String message) {
        Result<T> resultData = new Result<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }
}
