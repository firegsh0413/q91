package com.icchance.q91.common.result;

import com.icchance.q91.common.constant.ResultCode;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 回傳結果通用類
 * </p>
 * @author 6687353
 * @since 2023/7/20 13:26:51
 */
@Getter
@Setter
@NoArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = -752507042913307293L;
    /** 錯誤代碼 */
    private String resultCode;
    /** 返回數據 */
    private Object resultMap;
    /** 錯誤訊息 */
    private String msg;

    @Builder
    public Result(ResultCode resultCode, Object resultMap) {
        this.resultCode = resultCode.code;
        this.resultMap = resultMap;
        this.msg = resultCode.msg;
    }
}
