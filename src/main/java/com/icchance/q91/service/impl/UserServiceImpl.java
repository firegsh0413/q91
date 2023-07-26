package com.icchance.q91.service.impl;

import com.alibaba.fastjson.JSON;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.model.vo.PointVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.error.ServiceException;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.UserDTO;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.vo.ModifyCaptchaVO;
import com.icchance.q91.entity.vo.UserVO;
//import com.icchance.q91.entity.vo.CaptchaVO;
import com.icchance.q91.mapper.UserMapper;
import com.icchance.q91.redis.RedisKit;
import com.icchance.q91.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;



/**
 * <p>
 * 帳號相關服務類實做
 * </p>
 * @author 6687353
 * @since 2023/7/20 14:54:50
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 16;
    private static final Random RANDOM = new SecureRandom();

    private final CaptchaService captchaService;
    private final RedisKit redisKit;
    //private final CaptchaCacheServiceRedisImpl captchaCacheServiceRedis;
    public UserServiceImpl(CaptchaService captchaService, RedisKit redisKit) {
        this.captchaService = captchaService;
        //this.captchaCacheServiceRedis = captchaCacheServiceRedis;
        this.redisKit = redisKit;
    }

    /**
     * <p>
     * 取得驗證碼
     * </p>
     * @param account 帳號
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 15:25:48
     */
    @Override
    public Result getCaptcha(String account) {
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaType("blockPuzzle");
        //captchaVO.setClientUid("q91");
        //captchaCacheServiceRedis.set("test", "test", 0);
       // captchaCacheServiceRedis.get("test");

        ResponseModel responseModel = captchaService.get(captchaVO);
        CaptchaVO repData = (CaptchaVO) responseModel.getRepData();
        Object o = redisKit.get("RUNNING:CAPTCHA:" + repData.getToken());
        String jsonString = JSON.toJSONString(o);
        PointVO pointVO = JSON.parseObject(jsonString, PointVO.class);
        //PointVO pointVO = (PointVO) redisKit.get("RUNNING:CAPTCHA:" + repData.getToken());

        ModifyCaptchaVO modifyCaptchaVO = ModifyCaptchaVO.builder()
                .cutoutImage("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .shadeImage("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII")
                .xAxis(175)
                .yAxis(97)
                .cId("iVBORw0KGgoAAAANSUhEUgAAADc")
                .build();
/*        ModifyCaptchaVO modifyCaptchaVO = ModifyCaptchaVO.builder()
                .cutoutImage(repData.getOriginalImageBase64())
                .shadeImage(repData.getJigsawImageBase64())
                .xAxis(pointVO.getX())
                .yAxis(pointVO.getY())
                .cId(repData.getToken())
                .build();*/
        return Result.builder()
                .resultCode(ResultCode.SUCCESS)
                .resultMap(modifyCaptchaVO)
                .build();
    }

    /**
     * <p>
     * 註冊
     * </p>
     *
     * @param account         帳號
     * @param username        暱稱
     * @param password        密碼
     * @param chkPassword     確認密碼
     * @param fundPassword    支付密碼
     * @param chkFundPassword 確認支付密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 16:33:59
     */
    @Override
    public Result register(String account, String username, String password, String chkPassword, String fundPassword, String chkFundPassword) {
        if (!(checkAccountValid(account) && checkAccountValid(password))) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_VALID).build();
        }
        if (!checkUsernameValid(username)) {
            return Result.builder().resultCode(ResultCode.USERNAME_NOT_VALId).build();
        }
        if (!checkFundPasswordValid(fundPassword)) {
            return Result.builder().resultCode(ResultCode.FUND_PASSWORD_NOT_VALID).build();
        }
        // 檢核密碼
        if (!chkPassword.equals(password)) {
            return Result.builder().resultCode(ResultCode.PASSWORD_NOT_MATCH).build();
        }
        if (!chkFundPassword.equals(fundPassword)) {
            return Result.builder().resultCode(ResultCode.FUND_PASSWORD_NOT_MATCH).build();
        }

/*        if (Objects.nonNull(this.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account)))) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_ALREADY_EXIST).build();
        }*/
        // 隨機生成錢包地址
        String address;
        boolean isDuplicate;
        do {
            address = generateRandomAddress();
            isDuplicate = Objects.nonNull(this.getOne(Wrappers.<User>lambdaQuery().eq(User::getAddress, address)));
        } while (isDuplicate);

        User user = User.builder().account(account).username(username).password(password).fundPassword(fundPassword).address(address).build();

        //this.save(user);
        //redisKit.set(account, user);
        //UserVO userVO = UserVO.builder().account(account).username(username).build();
        UserVO userVO = UserVO.builder().account("johndoe").username("johndoe").build();
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(userVO).build();
    }

    /**
     * <p>
     * 登錄
     * </p>
     *
     * @param account  帳號
     * @param password 密碼
     * @param cId      驗證碼uid
     * @param captcha  驗證碼結果
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 09:45:10
     */
    @Override
    public Result login(String account, String password, String cId, String captcha) {
/*        this.getOne(Wrappers.<User>lambdaQuery()
                .eq(User::getAccount, account)
                .eq(User::getPassword, password));

        UserDTO userDTO = UserDTO.builder().account(account).password(password).cId(cId).captcha(captcha).build();*/
/*        if (redisKit.hasKey(account)) {
            User user = (User) redisKit.get(account);
            if (!user.getPassword().equals(password)) {
                return Result.builder().resultCode(ResultCode.PASSWORD_NOT_MATCH).build();
            }
            UserVO userVO = UserVO.builder().account(account).username(user.getUsername()).token(account).build();
            return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(userVO).build();
        }*/
        UserVO userVO = UserVO.builder().account("johndoe").username("johndoe").token("qwertyuio").build();
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(userVO).build();
        //return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
    }

    /**
     * <p>
     * 登出
     * </p>
     *
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 10:32:52
     */
    @Override
    public Result logout(String token) {
/*        if (!redisKit.hasKey(token)) {
            return Result.builder().resultCode(ResultCode.SYSTEM_UNDER_MAINTAIN).build();
        }*/
        return Result.builder().resultCode(ResultCode.SUCCESS).build();

    }

    /**
     * <p>
     * 取得會員個人訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/25 17:28:51
     */
    @Override
    public Result getUserInfo(String token) {
        if (!redisKit.hasKey(token)) {
            return Result.builder().resultCode(ResultCode.SYSTEM_UNDER_MAINTAIN).build();
        }
        User user = (User) redisKit.get(token);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setAvatar("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII");
        userVO.setIsCertified(0);
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(userVO).build();
    }

    @Override
    public Result getBalance(String token) {
        return null;
    }

    @Override
    public Result getTransaction(String token) {
        return null;
    }

    private boolean checkAccountValid(String input) {
        return Pattern.matches("^[a-zA-Z0-9]{6,12}$", input);
    }

    private boolean checkUsernameValid(String input) {
        return Pattern.matches("^[a-zA-Z\\u4e00-\\u9fa5]{1,8}$", input);
    }

    private boolean checkFundPasswordValid(String input) {
        return Pattern.matches("^[0-9]{6}$", input);
    }

    private String generateRandomAddress() {
        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    private String getRemoteId(HttpServletRequest request) {
        String xfwd = request.getHeader("X-Forwarded-For");
        String ip = getRemoteIpFromXfwd(xfwd);
        String ua = request.getHeader("user-agent");
        return StringUtils.isNotBlank(ip) ? ip + ua : request.getRemoteAddr() + ua;
    }

    private static String getRemoteIpFromXfwd(String xfwd) {
        if (StringUtils.isNotBlank(xfwd)) {
            String[] ipList = xfwd.split(",");
            return StringUtils.trim(ipList[0]);
        } else {
            return null;
        }
    }
}
