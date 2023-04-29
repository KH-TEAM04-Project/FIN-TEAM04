package com.kh.team4.controller;

import com.kh.team4.test.Test;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/xx")
    public Test test() {
    Test aaa = new Test();
    aaa.setNumber(5);
    aaa.setName("석준");
    aaa.setNickname("석통령");

    return aaa;
    }
}
