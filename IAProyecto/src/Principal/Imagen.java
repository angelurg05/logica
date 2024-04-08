package Principal;
import java.io.*;
public class Imagen {
    FileInputStream entrada;
    File archivo;
    
    public Imagen(){
        
    }
    
    public byte[] AbrirImagen(File archivo){
        byte[] bytesImg = new byte[1024*100];
        try {
            entrada = new FileInputStream(archivo);
            entrada.read(bytesImg);
        } catch (Exception e) {
            System.out.println(e);
        }
        return bytesImg;
    }
}
