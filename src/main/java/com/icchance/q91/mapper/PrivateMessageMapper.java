package com.icchance.q91.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icchance.q91.entity.model.PrivateMessage;
import com.icchance.q91.entity.vo.PrivateMessageVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 站內信mapper
 * </p>
 * @author 6687353
 * @since 2023/8/18 15:01:36
 */
public interface PrivateMessageMapper extends BaseMapper<PrivateMessage> {

    @Select("SELECT COUNT(*) FROM PRIVATE_MESSAGE WHERE USER_ID = #{userId} AND IS_READ = #{isRead}")
    int getPrivateMessageAmount(Integer userId, Integer isRead);

    List<PrivateMessageVO> getPrivateMessageList(Integer userId);

    @Update("UPDATE PRIVATE_MESSAGE SET IS_READ = #{isRead}, UPDATE_TIME = #{updateTime} WHERE ID = #{id} AND USER_ID = #{userId}")
    int updateIsRead(Integer id, Integer userId, Integer isRead, LocalDateTime updateTime);

    @Delete("DELETE FROM PRIVATE_MESSAGE WHERE ID = #{id} AND USER_ID = #{userId}")
    int delete(Integer id, Integer userId);
}
