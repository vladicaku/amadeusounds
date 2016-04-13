package com.amadeusounds.web;

import com.amadeusounds.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by User on 4/8/2016.
 */
@RestController
@RequestMapping("/test")
public class Test {

    @ResponseBody
    @RequestMapping(path = "/1", method = RequestMethod.GET)
    public Tag test() {
        Tag tag = new Tag();
        tag.setName("TagName");
        return tag;
    }
}
