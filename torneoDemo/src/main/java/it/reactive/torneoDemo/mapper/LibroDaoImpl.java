//package it.reactive.torneoDemo.mapper;
//
//import it.reactive.demoMVC.dto.LibroDTO;
//import it.reactive.demoMVC.model.AutoreModel;
//import it.reactive.demoMVC.model.LibroModel;
//import it.reactive.demoMVC.repository.dao.ILibroDao;
//import it.reactive.demoMVC.repository.mapper.MapperLibro;
//import org.springframework.stereotype.Repository;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;
//
//import java.util.Random;
//
//@Repository
//public class LibroDaoImpl implements ILibroDao {
//    @Override
//    public LibroModel create(LibroDTO libroDTO) {
//        Random random=new Random();
//        //CREO IL MODEL DAL DTO, PER POTER CHIAMARE IL DB
//        LibroModel libroModel= MapperLibro.modelFromDto(libroDTO);
//        //AGGIUNGO I CAMPI A COMPETENZA DB
//        libroModel.setChiave(String.valueOf(random.nextInt(100)));//FIXME USARE SEQUENCE
//        libroModel.setTimestamp(System.currentTimeMillis());//TODO USATO SOLO PER SALVARE SUL DB IL TIMESTAMP
//        System.out.println("QUERY PER INSERIRE UN LIBRO");
//        //FIXME IMPLEMENTARE LA QUERY
//        return libroModel;
//    }
//
//    @Override
//    public LibroModel read(int id) {
//        System.out.println("QUERY PER LEGGERE UN LIBRO");
//        //FIXME IMPLEMENTARE LA QUERY, PER ORA RESTITUITI VALORI CASUALI
//        //LA QUERY TRAMITE UNA JOIN RECUPERA SIA LIBRI CHE AUTORI
//        return new LibroModel(String.valueOf(id), "...", new AutoreModel(1,"nome", "cognome", "it", System.currentTimeMillis(), null), "...", System.currentTimeMillis());
//    }
//
//    @Override
//    public LibroModel update(int id, LibroDTO libroDTO) {
//        System.out.println("QUERY PER AGGIORNARE UN LIBRO");
//        //CREO IL MODEL DAL DTO, PER POTER CHIAMARE IL DB
//        LibroModel libroModel= MapperLibro.modelFromDto(libroDTO);
//        //AGGIUNGO DEI CAMPI AL DTO NEL REPOSITORY
//        libroModel.setTimestamp(System.currentTimeMillis());//TODO USATO SOLO PER SALVARE SUL DB IL TIMESTAMP
//        System.out.println("QUERY PER AGGIORNARE UN LIBRO");
//        //FIXME IMPLEMENTARE LA QUERY
//        return libroModel;
//    }
//
//    @Override
//    public LibroModel delete(int id) {
//        System.out.println("QUERY PER CANCELLARE UN LIBRO");
//        throw new NotImplementedException();
//    }
//}
