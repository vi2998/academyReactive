package com.intesasanpaolo.bear.mpab0.corsobearesercizi.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountryCommandServiceJPA extends BaseCommand<CountryModel> {

    private Long id;

    public CountryCommandServiceJPA(Long id) {
        this.id = id;
    }

    @Autowired
    CountryService countryService;

    @Override
    protected CountryModel doExecute() throws Exception {
        return countryService.getJpa(id).get();
    }
}
