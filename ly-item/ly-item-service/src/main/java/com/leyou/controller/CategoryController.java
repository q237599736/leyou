package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 根据节点Id查询所有
     * @param pid
     * @return
     */
    @RequestMapping("list")
    public List<Category> list(@RequestParam("pid") Long pid){
        Category category =new Category();
        category.setParentId(pid);
        return categoryService.findCategory(category);
    }

    @RequestMapping("id")
    public Object findCate(){
        return categoryService.findCate(6);
    }

    /**
     * 添加商品分类
     * @param category
     * @return
     */
    @RequestMapping("add")
    public String add(@RequestBody Category category){
        String result = "SUCCESS";
        try {
            categoryService.cateGoryAdd(category);
        }catch (Exception e){
            System.out.println("添加商品失败");
            result = "FALL";
        }
        return  result;
    }

    /**
     * 修改商品分类
     * @param category
     * @return
     */
    @RequestMapping("update")
    public String update(@RequestBody Category category){
        String result = "SUCCESS";
        try {
            categoryService.cateGoryUpdate(category);
        }catch (Exception e){
            System.out.println("修改商品失败");
            result = "FALL";
        }
        return  result;
    }

    /**
     * 删除商品分类
     * @param id
     * @return
     */
    @RequestMapping("deleteById")
    public  String deleteById(@RequestParam("id") Long id){
        String result = "SUCCESS";
        try {
            categoryService.deleteById(id);
        }catch (Exception e){
            System.out.println("修改商品失败");
            result = "FALL";
        }
        return  result;
    }
}
