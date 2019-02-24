package com.mall.furniture.manager;

import com.mall.furniture.mapper.FurnitureCategoryMapper;
import com.mall.furniture.model.FurnitureCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xieyongchao
 * @date 2019/2/22
 */
@Service
public class FurnitureCategoryManager {
    @Resource
    private FurnitureCategoryMapper categoryMapper;
    public FurnitureCategory findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}
