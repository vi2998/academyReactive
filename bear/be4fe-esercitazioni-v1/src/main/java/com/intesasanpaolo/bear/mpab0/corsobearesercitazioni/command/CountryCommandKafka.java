package com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.dto.ProvaMessaggioDTO;
import com.intesasanpaolo.bear.mpab0.corsobearesercitazioni.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountryCommandKafka extends BaseCommand<Void> {

    @Autowired
    CountryService countryService;

    ProvaMessaggioDTO provaMessaggioDTO;

    public CountryCommandKafka(ProvaMessaggioDTO provaMessaggioDTO) {
        this.provaMessaggioDTO = provaMessaggioDTO;
    }

    @Override
    protected Void doExecute() throws Exception {
        countryService.produci(provaMessaggioDTO);
        return null;
    }
}
