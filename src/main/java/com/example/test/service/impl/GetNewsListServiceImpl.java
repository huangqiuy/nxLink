package com.example.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.test.entity.workplusreturn.Items;
import com.example.test.entity.workplusreturn.ResultInfo;
import com.example.test.entity.workplusreturn.ReturnInfo;
import com.example.test.service.IGetNewsListService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author HQY
 */
@Service
public class GetNewsListServiceImpl implements IGetNewsListService {

    @Value("${nx.title.newsHttp}")
    private String newsHttp;

    @Override
    public String getNewsList() {
        String returnJson = new String();
        try {
            Document doc = Jsoup.connect(newsHttp).get();
            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setStatus(0);
            returnInfo.setMessage("everything is ok");
            ArrayList<Items> itemsList = new ArrayList<>();
            Elements listElements = doc.select("dl[class='articlelist-liststyle21']").select("dd");
            for (Element newsEle : listElements) {
                Elements srcImg = newsEle.select(".pic img");
                String imgUrl = srcImg.attr("src");
                String majorTitle = srcImg.attr("alt");
                Elements href = newsEle.select("div.pic");
                String url = href.select("a[href]").attr("href");
                Elements con = newsEle.select(".con");
                Elements select = con.select("div[class='text textOverClm clm3']");
                String subTitle = select.text();
                Elements eleDate = newsEle.select(".date");
                String year = eleDate.select("div.date_box3").text();
                Elements date_box1 = eleDate.select("div.date_box1");
                String monthAndDay = date_box1.text().replace(" ", "");
                String date = new String(year + "-" + monthAndDay);
                imgUrl = "https:" + imgUrl;
                Items items = new Items();
                items.setTitle(majorTitle);
                items.setSub_title(subTitle);
                items.setDate_time(date);
                items.setSource("南兴官网");
                items.setEvent_type("Url");
                items.setEvent_value("https://www.nanxing.com" + url);
                items.setIcon_type("Url");
                items.setIcon_value(imgUrl);
                itemsList.add(items);
            }
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setItems(itemsList);
            returnInfo.setResult(resultInfo);
            returnJson = JSON.toJSONString(returnInfo);
            System.out.println(returnJson);
        } catch (Exception e) {

        }

        return returnJson;
    }
}
