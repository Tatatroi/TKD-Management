//package Repository;
//
//import Model.HasID;
//import com.fasterxml.jackson.core.exc.StreamReadException;
//import com.fasterxml.jackson.core.exc.StreamWriteException;
//import com.fasterxml.jackson.databind.DatabindException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.core.type.TypeReference;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * A repository implementation that stores data in json files.
// * @param <T> The type of objects stored in the repository, which must implement HasId.
// */
//public class InFileRepo <T extends  HasID> implements IRepo<T>{
//    private final String filePath;
//    //private final  ObjectMapper objectMapper;
//    //private final TypeReference<List<T>> typeReference;
//
//    private Map<Integer,T> data;
//
//    /**
//     * Constructs a new inFileRepo with the specified file path, type reference and creates a new object mapper,which helps to write and read objects from json files.
//     * @param filePath          The name of the json file, where data will be stored.
//     * @param typeReference     The type of the data to be stored.
//     */
//   // public InFileRepo(String filePath,TypeReference<List<T>> typeReference) {
//        this.filePath = filePath;
//        this.objectMapper = new ObjectMapper();
//        this.typeReference = typeReference;
//        this.data = new HashMap<>();
//        readFromFile();
//    }
//
//    /**
//     * Tries to read from the json file and transforming the returned list in a map of type (object id, object) and if
//     * the file is empty or doesn't exist it assigns an empty map.
//     */
//    private void readFromFile() {
//        try{
//            File file = new File(filePath);
//            if(!file.exists()){
//                data = new HashMap<>();
//            }
//            List<T> objects = objectMapper.readValue(file,typeReference);
//            data = objects.stream().collect(Collectors.toMap(HasID::getId, obj->obj));
//
//        }
//        catch (IOException e) {
//            data = new HashMap<>();
//        }
//    }
//
//    /**
//     * Tries to write to the file, the list of objects of type T that is specified and throws an exception if it can't.
//     * @param objects       The list of objects of type T that will be written to the file, overwriting the previous contents of the file.
//     * @throws IOException  If it can't write to the file.
//     */
//    private void writeToFile(List<T> objects) {
//        try {
//            objectMapper.writeValue(new File(filePath) ,objects);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void add(T obj) {
//        data.put(obj.getId(),obj);
//        writeToFile(new ArrayList<>(data.values()));
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void remove(Integer removeId) {
//        data.remove(removeId);
//        writeToFile(new ArrayList<>(data.values()));
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void update(T obj) {
//        data.put(obj.getId(),obj);
//        writeToFile(new ArrayList<>(data.values()));
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public T get(Integer getId) {
//        return data.get(getId);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public List<T> getAll() {
//        return new ArrayList<>(data.values());
//    }
//}
