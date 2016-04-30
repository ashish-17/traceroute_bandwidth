/**
 * 
 */
package com.ashish_jindal.traceroute_bandwidth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ashish
 *
 */
@Controller
@RequestMapping("/")
public class NewDataController {

    @RequestMapping(method = RequestMethod.GET)
    public String showRootPage(ModelMap model) {
        return "new";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String showIndexPage(ModelMap model) {
        return "new";
    }

    @RequestMapping(value = "tracert", method = RequestMethod.GET)
    public String showTracertPage(ModelMap model) {
        return "tracert";
    }
}
