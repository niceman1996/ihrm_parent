package com.ihrm.system.service.impl;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
import com.ihrm.system.service.IPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ihrm.common.utils.PermissionConstants.*;

@Service
@Transactional
public class PermissionServiceImpl extends BaseService<Permission> implements IPermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionPointDao permissionPointDao;
    @Autowired
    private PermissionApiDao permissionApiDao;
    @Autowired
    private IdWorker idWorker;

    Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Override
    public void addPermission(Map<String,Object> map) throws Exception {
        String id = idWorker.nextId()+"";
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        permission.setId(id);
        Integer type = permission.getType();
        switch (type){
            case PY_MENU :
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(id);
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        permissionDao.save(permission);
    }

    @Override
    public void deletePermission(String permissionId) throws CommonException {
        Permission permission = permissionDao.findById(permissionId).get();
        permissionDao.delete(permission);
        Integer type = permission.getType();
        switch (type){
            case PY_MENU:
            case PY_POINT:
                permissionPointDao.deleteById(permissionId);
                break;
            case PY_API:
                permissionApiDao.deleteById(permissionId);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
    }

    @Override
    public void updatePermission(Map<String,Object> map) throws Exception {
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        Permission tempPermission = permissionDao.findById(permission.getId()).get();
        tempPermission.setName(permission.getName());
        tempPermission.setDescription(permission.getDescription());
        tempPermission.setEnVisible(permission.getEnVisible());
        int type = permission.getType();
        logger.info("type的值为:"+type);
        switch (type){
            case PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                menu.setId(permission.getId());
                permissionMenuDao.save(menu);
                break;
            case PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                point.setId(permission.getId());
                permissionPointDao.save(point);
                break;
            case PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map, PermissionApi.class);
                api.setId(permission.getId());
                permissionApiDao.save(api);
                break;
            default:
                throw new  CommonException(ResultCode.FAIL);
        }
        permissionDao.save(tempPermission);
    }

    @Override
    public Map<String,Object> findPermissionById(String permissionId) throws CommonException {
        Permission permission = permissionDao.findById(permissionId).get();
        Integer type = permission.getType();
        Object object = null;
        if (type == PY_MENU){
            object = permissionMenuDao.findById(permissionId).get();
        }else if (type == PY_POINT){
            object = permissionPointDao.findById(permissionId).get();
        }else if (type == PY_API){
            object = permissionApiDao.findById(permissionId).get();
        }else {
            throw new CommonException(ResultCode.FAIL);
        }

        Map<String,Object> map = BeanMapUtils.beanToMap(object);
        map.put("name",permission.getName());
        map.put("type",permission.getType());
        map.put("code",permission.getCode());
        map.put("description",permission.getDescription());
        map.put("pid",permission.getPid());
        map.put("enVisible",permission.getEnVisible());

        return map;
    }

    @Override
    public List<Permission> findAllPermission(Map<String,Object> map) {
        Specification<Permission> permSpec = new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(map.get("type"))){
                    String ty = (String) map.get("type");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    if ("0".equals(ty)){
                        in.value(1).value(2);
                    }else {
                        in.value(Integer.parseInt(ty));
                    }
                }
                if (!StringUtils.isEmpty(map.get("pid"))){
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class),map.get("pid")));
                }
                if (!StringUtils.isEmpty(map.get("enVisible"))){
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class),map.get("enVisible")));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return permissionDao.findAll(permSpec);
    }


}
