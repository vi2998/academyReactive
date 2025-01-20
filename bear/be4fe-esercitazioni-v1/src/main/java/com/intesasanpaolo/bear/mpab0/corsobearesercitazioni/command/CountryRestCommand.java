package com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountryRestCommand extends BaseCommand<String> {

    private Long id;

    public CountryRestCommand(Long id) {
        this.id = id;
    }

    @Autowired
    CountryService countryService;

    @Override
    protected String doExecute() throws Exception {
        return (countryService.getCountry(id));
    }
}
