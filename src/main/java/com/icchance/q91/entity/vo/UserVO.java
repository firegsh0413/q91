package com.icchance.q91.entity.vo;

import lombok.*;

/**
 * <p>
 * 帳號VO
 * </p>
 * @author 6687353
 * @since 2023/7/20 16:16:01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private Integer id;
    /** 帳號 */
    private String account;
    /** 暱稱 */
    private String username;
    /** 令牌 */
    private String token;
    /** 頭像圖片base64 */
    private String avatar;
    /** 錢包地址 */
    private String address;
    /** 是否實名制 */
    private Integer isCertified;
}
