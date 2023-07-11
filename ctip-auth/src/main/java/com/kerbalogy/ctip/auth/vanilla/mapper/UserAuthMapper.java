package com.kerbalogy.ctip.auth.vanilla.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kerbalogy.ctip.auth.vanilla.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/10 22:17
 * @description
 */
@Repository
public interface UserAuthMapper extends BaseMapper<UserAuth> {
    
}
