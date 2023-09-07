package com.icchance.q91.common.result;

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
@AllArgsConstructor
@NoArgsConstructor
public class ResultSuper implements Serializable {

    private static final long serialVersionUID = -752507042913307293L;
    /** 錯誤代碼 */
    private String repCode;
    /** 錯誤訊息 */
    private String repMsg;

}
