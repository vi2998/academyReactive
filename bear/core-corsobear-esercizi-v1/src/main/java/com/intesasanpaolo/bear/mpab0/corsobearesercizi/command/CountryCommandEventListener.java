package com.intesasanpaolo.bear.mpab0.corsobearesercizi.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountryCommandEventListener extends BaseCommand<List<CountryModel>> {

    @Autowired
    CountryService countryService;

    private String lingua;

    public CountryCommandEventListener(String lingua) {
        this.lingua = lingua;
    }

    @Override
    protected List<CountryModel> doExecute() throws Exception {
        return countryService.getLanguage(lingua);
    }
}
