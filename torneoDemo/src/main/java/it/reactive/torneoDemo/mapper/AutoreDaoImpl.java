//package it.reactive.torneoDemo.mapper;
//
//import it.reactive.demoMVC.dto.AutoreDTO;
//import it.reactive.demoMVC.model.AutoreModel;
//import it.reactive.demoMVC.repository.dao.IAutoreDao;
//import it.reactive.demoMVC.repository.mapper.MapperAutore;
//import org.springframework.stereotype.Repository;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//
//import java.util.HashSet;
//import java.util.Random;
//
//@Repository
//public class AutoreDaoImpl implements IAutoreDao {
//
//
//	@Override
//	public AutoreModel create(AutoreDTO autoreDTO) {
//		Random random=new Random();
//		//CREO IL MODEL DAL DTO
//		AutoreModel autoreModel = MapperAutore.modelFromDto(autoreDTO);
//		//AGGIUNGO I CAMPI A COMPETENZA DB
//		autoreModel.setId(random.nextInt());
//		autoreModel.setTimestamp(System.currentTimeMillis());
//		System.out.println("QUERY PER INSERIRE UN AUTORE");
//		//FIXME IMPLEMENTARE LA QUERY
//		return autoreModel;
//	}
//
//	@Override
//	public AutoreModel read(int id) {
//		System.out.println("QUERY PER LEGGERE UN AUTORE");
//		//FIXME IMPLEMENTARE LA QUERY, PER ORA RESTITUITI VALORI CASUALI
//		return new AutoreModel(id, "nome", "cognome", "IT", System.currentTimeMillis(), new HashSet<>());
//	}
//
//	@Override
//	public AutoreModel update(int id, AutoreDTO autoreDTO) {
//		System.out.println("QUERY PER AGGIORNARE UN AUTORE");
//		throw new NotImplementedException();
//	}
//
//	@Override
//	public AutoreModel delete(int id) {
//		System.out.println("QUERY PER CANCELLARE UN AUTORE");
//		throw new NotImplementedException();
//	}
//}
