package Repository;

import Model.HasID;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InFileRepo <T extends  HasID> implements IRepo<T>{
    private final String filePath;
    private final  ObjectMapper objectMapper;
    private final TypeReference<List<T>> typeReference;


    public InFileRepo(String filePath,TypeReference<List<T>> typeReference) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.typeReference = typeReference;
    }

    private List<T> readFromFile() throws IOException {
        try{
            File file = new File(filePath);
            if(!file.exists()){
                return new ArrayList<>();
            }
            return objectMapper.readValue(file,typeReference);

        }
        catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeToFile(List<T> objects) throws IOException {
        try{
            objectMapper.writeValue(new File(filePath) ,objects);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void add(T obj) {
        try {
            List<T> objects = readFromFile();
            objects.add(obj);
            writeToFile(objects);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Integer removeId) {
        try {
            List<T> objects = readFromFile();
            for(int i=0;i<objects.size();i++) {
                if (objects.get(i).getId() == removeId) {
                    objects.remove(i);
                }
            }
//            objects.remove((T) obj->obj.getId()==RemoveId);
            writeToFile(objects);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T obj) {
        try {
            List<T> objects = readFromFile();
            objects.set(obj.getId(),obj);
            writeToFile(objects);
//            for(int i=0;i<objects.size();i++){
//                if(objects.get(i).getId()==obj.getId()){
//                    objects.set(i,obj);
//                }
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T get(Integer getId) {
        try {
            List<T> objects = readFromFile();
            for(T obj:objects){
                if(obj.getId().equals(getId)){
                    return obj;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        try {
            return readFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
