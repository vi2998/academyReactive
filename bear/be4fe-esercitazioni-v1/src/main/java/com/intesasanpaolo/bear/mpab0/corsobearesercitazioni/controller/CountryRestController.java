package com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.controller;

import com.intesasanpaolo.bear.core.controller.BaseController;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.command.CountryRestCommand;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.resource.CountryResource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "country")
public class CountryRestController extends BaseController {

    @Autowired
    BeanFactory beanFactory;

    @PostMapping(value = "/{id}")
    public ResponseEntity<String> postCountry(@PathVariable Long id) throws Exception {
        String chiave = beanFactory.getBean(CountryRestCommand.class, id).execute();
        return ResponseEntity.ok(chiave);
    }
}
