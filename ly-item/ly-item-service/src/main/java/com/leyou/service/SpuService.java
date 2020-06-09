package com.leyou.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpuService {

    @Autowired
    SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    /**
     * 查询商品列表 分页
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuVo> findSpuByPage(String key, Integer saleable,
                                           Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        PageInfo<SpuVo> list = new PageInfo<SpuVo>(spuMapper.findSpuPage(key,saleable));
        return new PageResult<SpuVo>(list.getTotal(),list.getList());
    }

    /**
     * 保存商品信息
     * @param spuVo
     */
    public void saveSpuDetail(SpuVo spuVo) {

        Date nowDate = new Date();


        Spu spu = new Spu();
        spu.setTitle(spuVo.getTitle());
        spu.setSubTitle(spuVo.getSubTitle());
        spu.setBrandId(spuVo.getBrandId());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setSaleable(false);
        spu.setValid(true);
        spu.setCreateTime(nowDate);
        spu.setLastUpdateTime(nowDate);
        spuMapper.insert(spu);

        //保存spu 扩展表
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);




        List<Sku> skus = spuVo.getSkus();
        skus.forEach(sku ->{
            sku.setSpuId(spu.getId());
            sku.setEnable(true);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);

            //库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);

        });

    }

    /**
     * 根据spuId查询商品集列表
     *
     * @param spuId
     * @return
     */
    public SpuDetail findSpuDetailBySpuId(Long spuId) {
        return spuDetailMapper.selectByPrimaryKey(spuId);
    }

    /**
     * 修改商品信息
     * @param spuVo
     */
    public void updateSpuDetail(SpuVo spuVo) {
        Date nowDate = new Date();

        spuVo.setCreateTime(null);
        spuVo.setLastUpdateTime(nowDate);
        spuVo.setSaleable(null);
        spuVo.setValid(null);
        spuMapper.updateByPrimaryKeySelective(spuVo);

        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);


        List<Sku> skus = spuVo.getSkus();
        skus.forEach(s ->{
            s.setEnable(false);
            skuMapper.updateByPrimaryKey(s);
            stockMapper.deleteByPrimaryKey(s.getId());
        });

        List<Sku> skus1 = spuVo.getSkus();
        skus1.forEach(sku ->{
            sku.setSpuId(spuVo.getId());
            sku.setEnable(true);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);

            //库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);

        });


    }

    /**
     * 根据spuId删除spu详情
     * @param spuId
     */

    public void deleteSpuBySpuId(Long spuId) {

        List<Sku> skusList = skuMapper.findSkusBySpuId(spuId);
        skusList.forEach(s ->{
            s.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(s);
            stockMapper.deleteByPrimaryKey(s.getId());
        });

        spuDetailMapper.deleteByPrimaryKey(spuId);

        spuMapper.deleteByPrimaryKey(spuId);


    }
    /**
     * 操作上下架
     * @param spuId
     */
    public void upOrDown(Long spuId,int saleable) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setSaleable(saleable ==1? true :false);
        spuMapper.updateByPrimaryKeySelective(spu);

    }
}
