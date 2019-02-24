package com.mall.furniture.manager;

import com.mall.furniture.mapper.FurnitureGoodsMapper;
import com.mall.furniture.model.FurnitureGoods;
import com.mall.furniture.model.example.FurnitureGoodsExample;
import com.mall.furniture.model.FurnitureGoods.Column;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xieyongchao
 * @date 2019/2/22
 */
@Service
public class FurnitureGoodsManager {
    private Column[] columns = new Column[] {Column.id, Column.name, Column.brief, Column.picUrl, Column.retailPrice};
    @Resource
    private FurnitureGoodsMapper goodsMapper;

    /**
     * 获取商品
     *
     * @param catId
     * @param keywords
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public List<FurnitureGoods> querySelective(Integer catId, String keywords,
                                               Integer offset, Integer limit, String sort,
                                               String order) {
        FurnitureGoodsExample example = new FurnitureGoodsExample();
        FurnitureGoodsExample.Criteria criteria1 = example.or();
        FurnitureGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(catId) && catId != 0) {
            criteria1.andCategoryIdEqualTo(catId);
            criteria2.andCategoryIdEqualTo(catId);
        }

        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example);
    }

    /**
     * 根据关键字获取分类
     * <p>
     * 关键字可选
     *
     * @param keywords
     * @return
     */
    public List<Integer> getCatIds(String keywords) {
        FurnitureGoodsExample example = new FurnitureGoodsExample();
        FurnitureGoodsExample.Criteria criteria1 = example.or();
        FurnitureGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        List<FurnitureGoods> goodsList = goodsMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for (FurnitureGoods goods : goodsList) {
            cats.add(goods.getCategoryId());
        }
        return cats;
    }

    /**
     * 获取所有在售物品总数
     *
     * @return
     */
    public Integer queryOnSale() {
        FurnitureGoodsExample example = new FurnitureGoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }
}
