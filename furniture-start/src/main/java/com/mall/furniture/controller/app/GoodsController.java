package com.mall.furniture.controller.app;

import com.github.pagehelper.PageInfo;
import com.mall.furniture.manager.FurnitureCategoryManager;
import com.mall.furniture.model.FurnitureCategory;
import com.mall.furniture.model.FurnitureGoods;
import com.mall.furniture.util.ResponseUtil;
import com.mall.furniture.util.annotation.Order;
import com.mall.furniture.util.annotation.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import com.mall.furniture.manager.FurnitureGoodsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品服务
 *
 * @author xieyongchao
 * @date 2019/2/22
 */

@RestController
@RequestMapping("/app/goods")
@Validated
public class GoodsController {
    @Autowired
    private FurnitureGoodsManager goodsManager;
    @Autowired
    private FurnitureCategoryManager categoryManager;

    /**
     * 根据条件搜素商品
     * <p>
     *
     * @param categoryId 分类类目ID，可选
     * @param keyword    关键字，可选
     * @param page       分页页数
     * @param size       分页大小
     * @param sort       排序方式，支持"add_time", "retail_price"或"name"
     * @param order      排序类型，顺序或者降序
     * @return 根据条件搜素的商品详情
     */
    @GetMapping("list")
    public Object list(
        Integer categoryId,
        String keyword,
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @Sort(accepts = {"add_time", "retail_price", "name"}) @RequestParam(defaultValue = "add_time") String sort,
        @Order @RequestParam(defaultValue = "desc") String order) {

        //查询列表数据
        List<FurnitureGoods> goodsList = goodsManager.querySelective(categoryId, keyword, page, size, sort, order);


        Map<String, Object> data = new HashMap<>();
        data.put("goodsList", goodsList);
        data.put("count", PageInfo.of(goodsList).getTotal());

        return ResponseUtil.ok(data);
    }

    /**
     * 商品分类类目
     *
     * @param id 分类类目ID
     * @return 商品分类类目
     */
    @GetMapping("category")
    public Object category(@NotNull Integer id) {
        FurnitureCategory cur = categoryManager.findById(id);
        Map<String, Object> data = new HashMap<>();
        data.put("category", cur);
        return ResponseUtil.ok(data);
    }

    /**
     * 在售的商品总数
     *
     * @return 在售的商品总数
     */
    @GetMapping("count")
    public Object count() {
        Integer goodsCount = goodsManager.queryOnSale();
        Map<String, Object> data = new HashMap<>();
        data.put("goodsCount", goodsCount);
        return ResponseUtil.ok(data);
    }
}
