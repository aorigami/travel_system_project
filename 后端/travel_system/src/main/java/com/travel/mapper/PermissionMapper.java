package com.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.pojo.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("select p.* from permission p,role_permission rp where rp.roleId = #{roleId} and rp.permissionId = p.id")
    List<Permission> findPersByRoleIds(String roleId);

}
