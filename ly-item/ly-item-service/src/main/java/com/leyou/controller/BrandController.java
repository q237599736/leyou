package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;



    /**
     * 品牌管理中列表分页查询
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @RequestMapping("page")
    public Object findBrandByPage(@RequestParam("key") String key,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("rows") Integer rows,
                                  @RequestParam(required = false,value = "sortBy") String sortBy,
                                  @RequestParam(required = false,value = "desc") boolean desc){
        System.out.println(key+"==="+page+"==="+rows+"==="+sortBy+"==="+desc);

        //PageResult<Brand> brandList = brandService.findBrand(key,page,rows,sortBy,desc);
        PageResult<Brand> brandList = brandService.findBrandByLimit(key,page,rows,sortBy,desc);


        return  brandList;
    }

    /**
     * 品牌添加
     * @param brand
     * @param cids
     */
    @RequestMapping("addOrEditBrand")
    public void addOrEditBrand(Brand brand,
                               @RequestParam("cids") List<Long> cids){
        if (brand.getId()!=null){
            brandService.updateBrand(brand,cids);
        }else {
            brandService.brandCategorySave(brand,cids);
        }

    }

    /**
     * 根据品牌id删除
     * @param id
     */
    @RequestMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") Long id){

        brandService.deleteById(id);
    }

    /**
     * 根据品牌id查询具体分类
     * @param pid
     */
    @RequestMapping("bid/{id}")
    public List<Category> findCategoryByBrandId(@PathVariable("id") Long pid){

        return brandService.findCategoryByBrandId(pid);
    }

    /**
     * 根据分类id查询对应的品牌
     * @param cid
     * @return
     */
    @RequestMapping("cid/{cid}")
    public List<Brand> findBrandByCid(@PathVariable("cid") Long cid){
        return brandService.findBrandByCid(cid);
    }

}
