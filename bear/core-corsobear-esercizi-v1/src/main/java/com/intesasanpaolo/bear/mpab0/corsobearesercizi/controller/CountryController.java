package com.intesasanpaolo.bear.mpab0.corsobearesercizi.controller;

import com.intesasanpaolo.bear.core.controller.CoreController;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.command.*;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.factory.Mapper;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.resource.CountryResource;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "country")
public class CountryController extends CoreController {

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    Mapper mapper;


    //@GetMapping(value = "/countries")
    public ResponseEntity<List<CountryResource>> getCountries() {
        List<CountryResource> countryResourceList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CountryResource countryResource = new CountryResource(i, "name", "language", "continent");
            countryResourceList.add(countryResource);
        }
        return ResponseEntity.ok(countryResourceList);
    }

    //@GetMapping(value = "/countries")
    public ResponseEntity<List<CountryResource>> getCountriesCommand() throws Exception {
        List<CountryModel> countryModelList = beanFactory.getBean(CountryCommand.class).execute();
        return ResponseEntity.ok(mapper.countryModelsToResources(countryModelList));
    }

    //@GetMapping(value = "/countries")
    public ResponseEntity<List<CountryResource>> getCountriesCommandService() throws Exception {
        List<CountryModel> countryModelList = beanFactory.getBean(CountryCommandService.class).execute();
        return ResponseEntity.ok(mapper.countryModelsToResources(countryModelList));
    }


    //@GetMapping(value = "/countries")
    public ResponseEntity<List<CountryResource>> getCountriesCountryCommandServiceParam(int id, String info) throws Exception {
        List<CountryModel> countryModelList = beanFactory.getBean(CountryCommandServiceParam.class, id, info).execute();
        return ResponseEntity.ok(mapper.countryModelsToResources(countryModelList));
    }

    //@GetMapping(value = "/countries")
    public ResponseEntity<List<CountryResource>> getCountriesCountryCommandServiceJDBC() throws Exception {
        List<CountryModel> countryModelList = beanFactory.getBean(CountryCommandServiceJDBC.class).execute();
        return ResponseEntity.ok(mapper.countryModelsToResources(countryModelList));
    }

    @PostMapping(value = "/country")
    public ResponseEntity<CountryResource> postCountryJpa(@RequestParam Long id) throws Exception {
        CountryModel countryModel = beanFactory.getBean(CountryCommandServiceJPA.class, id).execute();
        return ResponseEntity.ok(mapper.countryModelsToResource(countryModel));
    }
}
