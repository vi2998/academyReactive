package com.intesasanpaolo.bear.mpab0.corsobearesercizi.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.service.CountryService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)

public class CountryCommandServiceParam extends BaseCommand<List<CountryModel>> {

    @Autowired
    CountryService countryService;

    private int id;
    private String info;

    public CountryCommandServiceParam(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public List<CountryModel> doExecute() {
        return countryService.getCountriesParam(id, info);
    }
}
