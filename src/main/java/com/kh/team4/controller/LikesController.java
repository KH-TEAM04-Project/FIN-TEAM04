package com.kh.team4.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@Log4j2
@CrossOrigin(origins = "http://localhost:3000")


public class LikesController {
}
