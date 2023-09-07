package com.icchance.q91.common.result;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 通用回傳類型
 * </p>
 * @author 6687353
 * @since 2023/9/6 13:32:58
 */
@Getter
@Setter
@NoArgsConstructor
public class Result<T> extends ResultSuper implements Serializable {

    private static final long serialVersionUID = -31367571203992136L;

    /** 請求的資源路徑 */
    private String url;
    /** 時間 */
    private Date date;
    /** 錯誤內容 */
    private String detail;
    /** 回傳數據內容 */
    private T repData;

/*    @Builder
    public Result(String repCode, String repMsg, Date date, String url, String detail, T repData) {
        super(repCode, repMsg);
        this.date = date;
        this.url = url;
        this.detail = detail;
        this.repData = repData;
    }*/

    @Builder
    public Result(String repCode, String repMsg, Date date, String url, String detail, T repData) {
        super(repCode, repMsg);
        this.date = date;
        this.url = url;
        this.detail = detail;
        this.repData = repData;
    }

    @Builder
    public Result(String repCode, String repMsg, T repData) {
        super(repCode, repMsg);
        this.date = new Date();
        this.url = "";
        this.detail = "";
        this.repData = repData;
    }
}
