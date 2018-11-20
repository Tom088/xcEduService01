package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Test
    public void testFindPage(){

        int page = 0;//从0开始
        int size = 10;//每页记录数

        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);

        System.out.println(all);
    }

    //添加
    @Test
    public void testInsert(){

        //定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        System.out.println(cmsPage);
    }

    //删除
    @Test
    public void testDelete(){
        cmsPageRepository.deleteById("s01");
    }

    //修改
    @Test
    public void testUpdate(){

        //查询对象
        Optional<CmsPage> optional = cmsPageRepository.findById("5a754adf6abb500ad05688d9");

        if (optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("index.html");
            cmsPageRepository.save(cmsPage);
        }
    }

    //查询
    @Test
    public void testFind(){

        CmsPage pageName = cmsPageRepository.findByPageName("index.html");

        System.out.println(pageName);
    }

    //自定义条件查询
    @Test
    public void testFindAllByExample(){

        int page = 0;//从0开始
        int size = 10;//每页记录数
        Pageable pageable = PageRequest.of(page,size);
        //条件值对象
        CmsPage cmsPage = new CmsPage();
        //cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //cmsPage.setTemplateId("5a925be7b00ffc4b3c1578b5");
        cmsPage.setPageAliase("轮播");
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher=exampleMatcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        //定义example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }
}
