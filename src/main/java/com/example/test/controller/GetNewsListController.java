package com.example.test.controller;

import com.example.test.service.IGetNewsListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author HQY
 */
@RestController
@RequestMapping("/getNewsList")
public class GetNewsListController {

    @Resource
    private IGetNewsListService getNewsListService;

    @GetMapping
    public String getNewsList() {
        String newsList = getNewsListService.getNewsList();
        return newsList;
    }
}
