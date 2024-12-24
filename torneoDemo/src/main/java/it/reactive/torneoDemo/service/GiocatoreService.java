package it.reactive.torneoDemo.service;

import it.reactive.torneoDemo.repository.dao.IGiocatoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiocatoreService {

    @Autowired
    IGiocatoreDao iGiocatoreDao;

}
