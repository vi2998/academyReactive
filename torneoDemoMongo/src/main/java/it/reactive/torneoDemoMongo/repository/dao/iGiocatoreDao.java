package it.reactive.torneoDemoMongo.repository.dao;

import it.reactive.torneoDemoMongo.model.GiocatoreModel;
import org.bson.types.ObjectId;

public interface iGiocatoreDao {

    public GiocatoreModel aggiungiAmmonizioneGiocatore(ObjectId idGiocatore);
}