package com.icchance.q91.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 驗證碼VO
 * </p>
 * @author 6687353
 * @since 2023/7/20 16:05:59
 */
@Data
@Builder
public class ModifyCaptchaVO {

    /** 缺塊base64圖片 */
    private String cutoutImage;
    /** 底圖base64圖片 */
    private String shadeImage;
    /** 缺塊x座標 */
    private Integer xAxis;
    /** 缺塊y座標 */
    private Integer yAxis;
    /** 驗證碼uid */
    private String cId;

}
