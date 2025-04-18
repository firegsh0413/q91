package com.icchance.q91.service.impl;

import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.error.ServiceException;
import com.icchance.q91.entity.dto.*;
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
    //private final RedisKit redisKit;
    private final JwtUtil jwtUtil;
    private final AuthUserService authUserService;
    private final UserBalanceService userBalanceService;
    private final UserMapper userMapper;

    //private final CaptchaCacheServiceRedisImpl captchaCacheServiceRedis;
    public UserServiceImpl(CaptchaService captchaService, JwtUtil jwtUtil, AuthUserService authUserService,
                           UserBalanceService userBalanceService, UserMapper userMapper) {
        this.captchaService = captchaService;
        //this.captchaCacheServiceRedis = captchaCacheServiceRedis;
        //this.redisKit = redisKit;
        this.jwtUtil = jwtUtil;
        this.authUserService = authUserService;
        this.userBalanceService = userBalanceService;
        this.userMapper = userMapper;
    }

    /**
     * <p>
     * 註冊
     * </p>
     *
     * @param userDTO UserDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/20 16:33:59
     */
    @Override
    public UserVO register(UserDTO userDTO) {
        if (Objects.nonNull(authUserService.getByAccount(userDTO.getAccount()))) {
            throw new ServiceException(ResultCode.ACCOUNT_ALREADY_EXIST);
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        authUserService.createUser(user);

        //redisKit.set(account, user);
        // 新增錢包資訊
        UserBalanceDTO userBalanceDTO = UserBalanceDTO.builder()
                .userId(user.getId())
                .balance(new BigDecimal(0))
                .availableAmount(new BigDecimal(0))
                .build();
        userBalanceService.addEntity(userBalanceDTO);
        return UserVO.builder().account(userDTO.getAccount()).username(userDTO.getUsername()).build();
    }

    /**
     * <p>
     * 登錄
     * </p>
     *
     * @param userDTO UserDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/21 09:45:10
     */
    @Override
    public UserVO login(UserDTO userDTO) {


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
        captchaVO.setCaptchaVerification(userDTO.getCaptcha());
        //ResponseModel verification = captchaService.verification(captchaVO);

        // 2. JWT產生對應token
        User user = authUserService.getByAccount(userDTO.getAccount());
        if (Objects.isNull(user)) {
            throw new ServiceException(ResultCode.ACCOUNT_NOT_EXIST);
        }
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new ServiceException(ResultCode.ACCOUNT_OR_PASSWORD_WRONG);
        }
        String token = jwtUtil.createToken(userDTO.getAccount(), user.getId());
        return UserVO.builder()
                .account(user.getAccount())
                .username(user.getUsername())
                .token(token)
                .build();
    }

    /**
     * <p>
     * 登出
     * </p>
     *
     * @param baseDTO BaseDTO
     * @author 6687353
     * @since 2023/7/21 10:32:52
     */
    @Override
    public void logout(BaseDTO baseDTO) {
        //Integer userId = jwtUtil.parseUserId(baseDTO.getToken());

    }

    /**
     * <p>
     * 取得會員個人訊息
     * </p>
     *
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/7/25 17:28:51
     */
    @Override
    public UserVO getUserInfo(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        User user = authUserService.getById(userId);
        if (Objects.isNull(user)) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * <p>
     * 修改個人訊息
     * </p>
     * @param userInfoDTO UserInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 18:04:37
     */
    @Override
    public void updateUserInfo(UserInfoDTO userInfoDTO) {
        Integer userId = jwtUtil.parseUserId(userInfoDTO.getToken());
        User user = authUserService.getById(userId);
        if (Objects.isNull(user)) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        user.setUsername(userInfoDTO.getUsername());
        user.setAvatar(userInfoDTO.getAvatar());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    /**
     * <p>
     * 取得會員錢包訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/14 09:48:47
     */
    @Override
    public UserBalanceVO getBalance(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        UserBalanceVO userBalanceVO = userBalanceService.getInfo(userId);
        if (Objects.isNull(userBalanceVO)) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        return userBalanceVO;
    }

    /**
     * <p>
     * 實名認證
     * </p>
     * @param certificateDTO CertificateDTO
     * @author 6687353
     * @since 2023/8/18 17:35:30
     */
    @Override
    public void certificate(CertificateDTO certificateDTO) {
        Integer userId = jwtUtil.parseUserId(certificateDTO.getToken());
        User user = authUserService.getById(userId);
        if (Objects.isNull(user)) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        user.setName(certificateDTO.getName());
        user.setIdNumber(certificateDTO.getIdNumber());
        user.setIdCard(certificateDTO.getIdCard());
        user.setFacePhoto(certificateDTO.getFacePhoto());
        user.setCertified(Boolean.TRUE);
        user.setUpdateTime(LocalDateTime.now());
        authUserService.updateById(user);
    }

    /**
     * <p>
     * 設置密碼
     * </p>
     * @param userInfoDTO UserInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:49:17
     */
    @Override
    public void updatePassword(UserInfoDTO userInfoDTO) {
        Integer userId = jwtUtil.parseUserId(userInfoDTO.getToken());
        User user = authUserService.getById(userId);
        if (Objects.isNull(user)) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        if (!user.getPassword().equals(userInfoDTO.getOldPassword())) {
            throw new ServiceException(ResultCode.PASSWORD_NOT_MATCH);
        }
        user.setPassword(userInfoDTO.getNewPassword());
        user.setUpdateTime(LocalDateTime.now());
        authUserService.updateById(user);
    }

    /**
     * <p>
     * 設置交易密碼
     * </p>
     * @param userInfoDTO UserInfoDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 17:59:47
     */
    @Override
    public void updateFundPassword(UserInfoDTO userInfoDTO) {
        Integer userId = jwtUtil.parseUserId(userInfoDTO.getToken());
        User user = authUserService.getById(userId);
        if (Objects.isNull(user)) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        if (!user.getFundPassword().equals(userInfoDTO.getOldFundPassword())) {
            throw new ServiceException(ResultCode.FUND_PASSWORD_NOT_MATCH);
        }
        user.setFundPassword(userInfoDTO.getNewFundPassword());
        user.setUpdateTime(LocalDateTime.now());
        authUserService.updateById(user);
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
