package com.api.email.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/email",produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailController extends BaseController{}


