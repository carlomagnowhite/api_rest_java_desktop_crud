
import java.net.URI;
import java.net.http.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.*;



public class Crud {
    
    public static void GET(DefaultTableModel dm, JTable tabla){
        String api = "http://localhost/quinto-api/api.php";
        
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(api))
                .GET()
                .build();
        
        try {
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());            
            JSONArray jSONArray = new JSONArray(respuesta.body());
            JSONObject jSONObject;
            Object data[] = new Object[5];
            for (int i = 0; i < jSONArray.length(); i++) {
                jSONObject = jSONArray.getJSONObject(i);
                data[0] = jSONObject.get("CED_EST");
                data[1] = jSONObject.get("NOM_EST");
                data[2] = jSONObject.get("APE_EST");
                data[3] = jSONObject.get("DIR_EST");
                data[4] = jSONObject.get("TEL_EST");
                
                dm.addRow(data);
            }
            tabla.setModel(dm);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: " +e.getMessage());
        }
        
    }

    public static void POST(JTextField ced, JTextField nombre, JTextField apellido, JTextField direccion, JTextField telefono){
        //VAR
        String api = "http://localhost/quinto-api/api.php";
        String postData = 
                "CED_EST="+ced.getText().trim()+
                "&NOM_EST="+nombre.getText().trim()+
                "&APE_EST="+apellido.getText().trim()+
                "&DIR_EST="+direccion.getText().trim()+
                "&TEL_EST="+telefono.getText().trim();
        //START
        //CLIENTE
        HttpClient cliente = HttpClient.newHttpClient();
        //SOLICITUD
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(api))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(postData))
                .build();
        try {
            //RESPUESTA
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            if (respuesta.statusCode() == 200) {
                JOptionPane.showMessageDialog(null, "REGISTRO INSERTADO CORRECTAMENTE","Success",JOptionPane.PLAIN_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrió algo inesperado -> "+respuesta.statusCode(),"Error",JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " +ex.getMessage());
        }
    }
    
    public static void PUT(JTextField ced, JTextField nombre, JTextField apellido, JTextField direccion, JTextField telefono){
        //VAR
        String api = "http://localhost/quinto-api/api.php";
        JSONObject json = new JSONObject();
        //START
        json.put("CED_EST", ced.getText().trim());
        json.put("NOM_EST", nombre.getText().trim());
        json.put("APE_EST", apellido.getText().trim());
        json.put("DIR_EST", direccion.getText().trim());
        json.put("TEL_EST", telefono.getText().trim());
        
        //CLIENTE
        HttpClient cliente = HttpClient.newHttpClient();
        //SOLICITUD
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(api))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();
        try {
            //RESPUESTA
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            if (respuesta.statusCode() == 200) {
                JOptionPane.showMessageDialog(null, "Actualización Exitosa","Success",JOptionPane.PLAIN_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrió algo inesperado","Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } 
    }
    
    public static void DELETE(JTextField ced){
        //VAR
        String api = "http://localhost/quinto-api/api.php?CED_EST="+ced.getText().trim();
        //START - Cliente
        HttpClient cliente = HttpClient.newHttpClient();
        //SOLICITUD
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(api))
                .DELETE()
                .build();
        try {
            //RESPUESTA
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            if (respuesta.statusCode() == 200) {
                JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente","Success",JOptionPane.PLAIN_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Ocurrió algo inesperado","Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Exception - > " +ex.getMessage(),"Exception",JOptionPane.ERROR_MESSAGE);
        }
    }
}
