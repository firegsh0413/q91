package com.icchance.q91.common.constant;

/**
 * <p>
 * request常數
 * </p>
 * @author 6687353
 * @since 2023/9/8 11:39:17
 */
public interface RequestConstant {

    /**
     * <p>
     * 請求頭
     * </p>
     * @author 6687353
     * @since 2023/9/8 11:39:52
     */
    interface Head {
        /** 請求頭包含的内容 */
        String MULTIPART = "multipart/";
        /** 請求頭中的 IP  */
        String X_FORWARDED_FOR = "x-forwarded-for";
        /** 請求頭中的 IP */
        String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
        /** 請求頭中的 IP */
        String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
        /** 請求頭中的 IP */
        String HTTP_X_FORWARDED_IP = "HTTP_X_FORWARDED_IP";
        /** 請求頭中的 JWT */
        String JWT = "jwt";
        /** 登錄者所屬用戶 */
        String ORG_ROOT_ID = "orgRootId";
        /** 登入者帳戶名稱 */
        String USER_NAME = "username";
    }
    interface Default {
        /** 預設使用者名稱 */
        String DEFAULT_USER_NAME = "UNKNOWN";
        /** 預設JWT認證內容 */
        String DEFAULT_JWT_TOKEN = "UNKNOWN";
        /** 預設登錄者所屬用戶 */
        Integer DEFAULT_ORG_ROOT_ID = 1;
    }
}
