package com.mylife.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @descirption : view跳转控制器
 * @author : wyh
 * @date : 2020/9/1 15:35
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    /**
     * @description : 跳转view
     * @author : wyh
     * @date : 2020/9/1 15:37
     * @params : [url]
     * @return : java.lang.String
     **/
    @RequestMapping({"","/"})
    public String toView(String url){
        StringBuilder result = new StringBuilder("/pc");
        result.append(url.startsWith("/") ? "" : "/");
        result.append(url);
        return result.toString();
    }
}
