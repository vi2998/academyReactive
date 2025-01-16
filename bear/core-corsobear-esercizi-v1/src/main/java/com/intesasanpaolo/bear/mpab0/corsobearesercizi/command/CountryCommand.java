package com.intesasanpaolo.bear.mpab0.corsobearesercizi.command;

import com.intesasanpaolo.bear.core.command.BaseCommand;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.model.CountryModel;
import com.intesasanpaolo.bear.mpab0.corsobearesercizi.service.CountryService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountryCommand extends BaseCommand<List<CountryModel>> {

    @Autowired
    BeanFactory beanFactory;

    @Override
    protected List<CountryModel> doExecute()  {
        List<CountryModel> countryModelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CountryModel countryModel = new CountryModel(i, "nome_" + i + "-lingua_" + i + " -continente_"+ i);
            countryModelList.add(countryModel);
        }
        return countryModelList;
    }
}
