package com.leyou.controller;


import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecGroupService specGroupService;

    /**
     * 查询规格参数组列表
     * @return
     */
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findSpecGroupList(@PathVariable("cid") Long cateGoryId){
        return specGroupService.findSpecGroupList(cateGoryId);
    }


    /**
     * 保存商品规格组
     * @param specGroup
     */
    @RequestMapping("group")
        public  void saveSpecGroup(@RequestBody SpecGroup specGroup){
              System.out.println();

              if (specGroup.getId()==null){
                  specGroupService.saveSpecGroup(specGroup);
              }
                    specGroupService.updateSpecGroup(specGroup);
    }


    /**
     * 根据商品规格组id删除
     */
    @RequestMapping("group/{id}")
    public  void deleteBySpecGroupId(@PathVariable("id") Long id){
        specGroupService.deleteBySpecGroupId(id);
    }

    /**
     * 根据主id查询组参数列表
     * @param gid
     * @return
     */
    @RequestMapping("params")
    public List<SpecParam> findSpecParamByGid(@RequestParam("gid") Long gid){
        return  specGroupService.findSpecParamByGid(gid);
    }
}
