package com.icchance.q91.service.impl;

import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.dao.FakeUserDB;
import com.icchance.q91.entity.dto.UserBalanceDTO;
import com.icchance.q91.entity.model.User;
import com.icchance.q91.entity.vo.UserBalanceVO;
import com.icchance.q91.entity.vo.UserVO;
import com.icchance.q91.mapper.UserMapper;
import com.icchance.q91.redis.RedisKit;
import com.icchance.q91.service.AuthUserService;
import com.icchance.q91.service.UserBalanceService;
import com.icchance.q91.service.UserService;
import com.icchance.q91.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final UserBalanceService userBalanceService;
    private final FakeUserDB fakeUserDB;
    private final UserMapper userMapper;

    //private final CaptchaCacheServiceRedisImpl captchaCacheServiceRedis;
    public UserServiceImpl(CaptchaService captchaService, RedisKit redisKit, JwtUtil jwtUtil, AuthUserService authUserService,
                           UserBalanceService userBalanceService, FakeUserDB fakeUserDB, UserMapper userMapper) {
        this.captchaService = captchaService;
        //this.captchaCacheServiceRedis = captchaCacheServiceRedis;
        this.redisKit = redisKit;
        this.jwtUtil = jwtUtil;
        this.authUserService = authUserService;
        this.userBalanceService = userBalanceService;
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

        if (Objects.nonNull(authUserService.getByAccount(account))) {
            return Result.builder().repCode(ResultCode.ACCOUNT_ALREADY_EXIST.code).repMsg(ResultCode.ACCOUNT_ALREADY_EXIST.msg).build();
        }
        User user = User.builder().account(account).username(username).password(password).fundPassword(fundPassword).build();
        authUserService.createUser(user);
        //測試
        //fakeUserDB.create(user);

        //redisKit.set(account, user);
        // 新增錢包資訊
        UserBalanceDTO userBalanceDTO = UserBalanceDTO.builder()
                .userId(user.getId())
                .balance(new BigDecimal(0))
                .availableAmount(new BigDecimal(0))
                .build();
        userBalanceService.addEntity(userBalanceDTO);
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
        if (Objects.isNull(user)) {
            return Result.builder().repCode(ResultCode.ACCOUNT_NOT_EXIST.code).repMsg(ResultCode.ACCOUNT_NOT_EXIST.msg).build();
        }
        if (!user.getPassword().equals(password)) {
            return Result.builder().repCode(ResultCode.ACCOUNT_OR_PASSWORD_WRONG.code).repMsg(ResultCode.ACCOUNT_OR_PASSWORD_WRONG.msg).build();
        }
        String token = jwtUtil.createToken(account, user.getId());
        //UserVO userVO = UserVO.builder().account(account).username("johndoe").token(token).build();
        UserVO userVO = UserVO.builder()
                .account(user.getAccount())
                .username(user.getUsername())
                .token(token)
                .build();
        return Result.builder().repCode(ResultCode.SUCCESS.code).repMsg(ResultCode.SUCCESS.msg).repData(userVO).build();
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
        Integer userId = jwtUtil.parseUserId(token);

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
        Integer userId = jwtUtil.parseUserId(token);
        User user = authUserService.getById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
/*        userVO.setAccount("johndoe");
        userVO.setUsername("johndoe");
        userVO.setAvatar("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAIAQMAAAD+wSzIAAAABlBMVEX///+/v7+jQ3Y5AAAADklEQVQI12P4AIX8EAgALgAD/aNpbtEAAAAASUVORK5CYII");
        userVO.setIsCertified(0);*/
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
        Integer userId = jwtUtil.parseUserId(token);
        User user = authUserService.getById(userId);
        user.setUsername(username);
        user.setAvatar(avatar);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
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
/*        UserBalanceVO userBalanceVO = UserBalanceVO.builder()
                .address("qwertqwertyuiyui")
                .balance(new BigDecimal("99.99"))
                .availableAmount(new BigDecimal("99.99"))
                .sellBalance(new BigDecimal(0))
                .tradingAmount(new BigDecimal(0))
                .build();*/
        Integer userId = jwtUtil.parseUserId(token);
        UserBalanceVO userBalanceVO = userBalanceService.getInfo(userId);
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
        Integer userId = jwtUtil.parseUserId(token);
        User user = authUserService.getById(userId);
        user.setName(name);
        user.setIdNumber(idNumber);
        user.setIdCard(idCard);
        user.setFacePhoto(facePhoto);
        user.setCertified(Boolean.TRUE);
        user.setUpdateTime(LocalDateTime.now());
        //fakeUserDB.update(user);
        authUserService.updateById(user);
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
        Integer userId = jwtUtil.parseUserId(token);
        User user = authUserService.getById(userId);
        if (!user.getPassword().equals(oldPassword)) {
            return Result.builder().repCode(ResultCode.PASSWORD_NOT_MATCH.code).repMsg(ResultCode.PASSWORD_NOT_MATCH.msg).build();
        }
        user.setPassword(newPassword);
        user.setUpdateTime(LocalDateTime.now());
        //fakeUserDB.update(user);
        authUserService.updateById(user);
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
        Integer userId = jwtUtil.parseUserId(token);
        User user = authUserService.getById(userId);
        if (!user.getFundPassword().equals(oldFundPassword)) {
            return Result.builder().repCode(ResultCode.PASSWORD_NOT_MATCH.code).repMsg(ResultCode.PASSWORD_NOT_MATCH.msg).build();
        }
        user.setFundPassword(newFundPassword);
        user.setUpdateTime(LocalDateTime.now());
        //fakeUserDB.update(user);
        authUserService.updateById(user);
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

}
