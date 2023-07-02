package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import com.heima.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {
    /**
     * app登录功能
     *
     * @param dto 登录传输实体类
     * @return 统一响应
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        // 1.正常登录
        if (StringUtils.isNotBlank(dto.getPhone())&&StringUtils.isNotBlank(dto.getPassword())){
            // 1.1.查询用户信息
            ApUser dbuser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if (dbuser == null){
                return ResponseResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST,"用户信息不存在");
            }
            // 1.2.对比密码
            String salt = dbuser.getSalt();
            String password = dto.getPassword();
            String pswd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            if (!pswd.equals(dbuser.getPassword())){
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
            // 1.3.返回数据 jwt user
            String token = AppJwtUtil.getToken(dbuser.getId().longValue());
            Map<String, Object> map = new HashMap<>();
            map.put("token",token);
            dbuser.setSalt("");
            dbuser.setPassword("");
            map.put("user",dbuser);

            return ResponseResult.okResult(map);
        }else {
            // 2.游客登陆
            Map<String, Object> map = new HashMap<>();
            map.put("token",AppJwtUtil.getToken(0L));
            return ResponseResult.okResult(map);
        }

    }
}
