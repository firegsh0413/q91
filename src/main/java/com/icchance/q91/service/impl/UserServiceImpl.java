package com.icchance.q91.service.impl;

import com.alibaba.fastjson.JSON;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.model.vo.PointVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.dao.FakeUserDB;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.vo.ModifyCaptchaVO;
import com.icchance.q91.entity.vo.UserBalanceVO;
import com.icchance.q91.entity.vo.UserVO;
import com.icchance.q91.mapper.UserMapper;
import com.icchance.q91.redis.RedisKit;
import com.icchance.q91.service.AuthUserService;
import com.icchance.q91.service.UserService;
import com.icchance.q91.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;


/**
 * <p>
 * 帳號相關服務類實作
 * </p>
 *
 * @author 6687353
 * @since 2023/7/20 14:54:50
 */
@Service
public class UserServiceImpl implements UserService {

    private final CaptchaService captchaService;
    private final RedisKit redisKit;
    private final JwtUtil jwtUtil;
    private final AuthUserService authUserService;
    private final FakeUserDB fakeUserDB;

    private final UserMapper userMapper;

    //private final CaptchaCacheServiceRedisImpl captchaCacheServiceRedis;
    public UserServiceImpl(CaptchaService captchaService, RedisKit redisKit, JwtUtil jwtUtil, AuthUserService authUserService,
                           FakeUserDB fakeUserDB, UserMapper userMapper) {
        this.captchaService = captchaService;
        //this.captchaCacheServiceRedis = captchaCacheServiceRedis;
        this.redisKit = redisKit;
        this.jwtUtil = jwtUtil;
        this.authUserService = authUserService;
        this.fakeUserDB = fakeUserDB;
        this.userMapper = userMapper;
    }

    /**
     * <p>
     * 註冊
     * </p>
     *
     * @param account         帳號
     * @param username        暱稱
     * @param password        密碼
     * @param fundPassword    支付密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 16:33:59
     */
    @Override
    public Result register(String account, String username, String password, String fundPassword) {
/*        if (!(checkAccountValid(account) && checkAccountValid(password))) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_VALID).build();
        }
        if (!checkUsernameValid(username)) {
            return Result.builder().resultCode(ResultCode.USERNAME_NOT_VALId).build();
        }
        if (!checkFundPasswordValid(fundPassword)) {
            return Result.builder().resultCode(ResultCode.FUND_PASSWORD_NOT_VALID).build();
        }

        if (Objects.nonNull(this.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account)))) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_ALREADY_EXIST).build();
        }*/
/*        // 隨機生成錢包地址
        String address;
        boolean isDuplicate;
        do {
            address = generateRandomAddress();
            isDuplicate = Objects.nonNull(this.getOne(Wrappers.<User>lambdaQuery().eq(User::getAddress, address)));
        } while (isDuplicate);*/

        User user = User.builder().account(account).username(username).password(password).fundPassword(fundPassword).build();
        //測試
        //authUserService.createUser(user);
        fakeUserDB.create(user);

        //redisKit.set(account, user);
        //UserVO userVO = UserVO.builder().account(account).username(username).build();
        UserVO userVO = UserVO.builder().account(account).username(username).build();
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(userVO).build();
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

        // 1. 滑塊圖形驗證 verify
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captcha);
        //ResponseModel verification = captchaService.verification(captchaVO);

        // 2. JWT產生對應token
        User user = authUserService.getByAccount(account);
        String token = jwtUtil.createToken(account, user.getId());
        UserVO userVO = UserVO.builder().account(account).username("johndoe").token(token).build();
        //User user = getUserByToken(token);
        if (!user.getPassword().equals(password)) {
            return Result.builder().repCode(ResultCode.PASSWORD_NOT_MATCH.code).repMsg(ResultCode.PASSWORD_NOT_MATCH.msg).build();
        }

        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(userVO).build();
        //return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
    }

    /**
     * <p>
     * 登出
     * </p>
     *
     * @param token 令牌
     * @author 6687353
     * @since 2023/7/21 10:32:52
     */
    @Override
    public void logout(String token) {
/*        if (!redisKit.hasKey(token)) {
            return Result.builder().resultCode(ResultCode.SYSTEM_UNDER_MAINTAIN).build();
        }*/
        //User user = getUserByToken(token);

        //return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();

    }

    /**
     * <p>
     * 取得會員個人訊息
     * </p>
     *
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/25 17:28:51
     */
    @Override
    public Result getUserInfo(String token) {
/*        User user = getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/
        UserVO userVO = new UserVO();
        //BeanUtils.copyProperties(user, userVO);
        userVO.setAccount("johndoe");
        userVO.setUsername("johndoe");
        userVO.setAvatar("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII");
        userVO.setIsCertified(0);
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(userVO).build();
    }

    /**
     * <p>
     * 修改個人訊息
     * </p>
     * @param token 令牌
     * @param username 用戶名稱
     * @param avatar 用戶頭像
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 18:04:37
     */
    @Override
    public Result updateUserInfo(String token, String username, String avatar) {
/*        User user = getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/
/*        user.setUsername(username);
        user.setAvatar(avatar);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 取得會員錢包訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 09:48:47
     */
    @Override
    public Result getBalance(String token) {
/*        User user = getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/
        UserBalanceVO userBalanceVO = UserBalanceVO.builder()
                .address("qwertqwertyuiyui")
                .balance(new BigDecimal("99.99"))
                .availableAmount(new BigDecimal("99.99"))
                .sellBalance(new BigDecimal(0))
                .tradingAmount(new BigDecimal(0))
                .build();
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(userBalanceVO).build();
    }

    /**
     * <p>
     * 實名認證
     * </p>
     * @param token 令牌
     * @param name 姓名
     * @param idNumber 身份證號
     * @param idCard 身份證照片base64
     * @param facePhoto 人臉識別照片base64
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:35:30
     */
    @Override
    public Result certificate(String token, String name, String idNumber, String idCard, String facePhoto) {
/*        User user = getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }*/

/*        user.setName(name);
        user.setIdNumber(idNumber);
        user.setIdCard(idCard);
        user.setFacePhoto(facePhoto);
        user.setCertified(Boolean.TRUE);
        user.setUpdateTime(LocalDateTime.now());
        fakeUserDB.update(user);*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 設置密碼
     * </p>
     * @param token 令牌
     * @param oldPassword 原密碼
     * @param newPassword 新密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:49:17
     */
    @Override
    public Result updatePassword(String token, String oldPassword, String newPassword) {
/*        User user = getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        if (!user.getPassword().equals(oldPassword)) {
            return Result.builder().resultCode(ResultCode.PASSWORD_NOT_MATCH).build();
        }*/
/*        user.setPassword(newPassword);
        fakeUserDB.update(user);*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
    }

    /**
     * <p>
     * 設置交易密碼
     * </p>
     * @param token 令牌
     * @param oldFundPassword 原交易密碼
     * @param newFundPassword 新交易密碼
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:59:47
     */
    @Override
    public Result updateFundPassword(String token, String oldFundPassword, String newFundPassword) {
/*        User user = getUserByToken(token);
        if (Objects.isNull(user)) {
            return Result.builder().resultCode(ResultCode.ACCOUNT_NOT_EXIST).build();
        }
        if (!user.getFundPassword().equals(oldFundPassword)) {
            return Result.builder().resultCode(ResultCode.PASSWORD_NOT_MATCH).build();
        }*/
/*        user.setFundPassword(newFundPassword);
        fakeUserDB.update(user);*/
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).build();
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

/*    private String generateRandomAddress() {
        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }*/

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

    private static String encodePassword(String passwrod) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(passwrod);
    }

    /**
     * <p>
     * token取回用戶資訊
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.entity.model.User
     * @author 6687353
     * @since 2023/8/18 18:04:46
     */
    @Override
    public User getUserByToken(String token) {
        Integer userId = jwtUtil.parseUserId(token);
        User user = authUserService.getById(userId);
        if (Objects.isNull(user)) {
            return null;
        }
        return user;
    }
}
