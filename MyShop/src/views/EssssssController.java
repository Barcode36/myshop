/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Utils.Constants;
import static controller.ReglagePaneController.lireFichier;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafxapplication3.JavaFXApplication3;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Christ
 */
public class EssssssController implements Initializable {

    @FXML
    private ProgressBar progres;
    
    public static String mySerNber;
    /**
     * Initializes the controller class.
     */
    private Task taskWorker(int seconde) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < seconde; i++) {
                    updateProgress(i + 1, seconde);
                    Thread.sleep(100);
                }
                return null;
            }

        };
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //ReglagePaneController.ecrireFichier("", lignes);
        
        Task task = taskWorker(100);
        
        List<String> shopInfosList = new ArrayList<>();
        shopInfosList.addAll(lireFichier("myshop"));
        
        progres.progressProperty().bind(task.progressProperty());
        mySerNber = getSerialNumber();
        //System.out.println("mySerNber00: "+mySerNber);
        String tab0[] = mySerNber.split(" ");
        //for (String tab1 : tab) {
            //System.out.println("tabiii "+tab[tab.length-1]);
        mySerNber = tab0[tab0.length-1];
        //System.out.println("mySerNber: "+mySerNber);
        //ecrireSerNb(mySerNber);
       
       // }
        
        //File f = new File("tmp.txt");
       
        /*try {
            File f = new File("tmp.txt");
            System.out.println("d: "+f.exists());
            BufferedReader in = new BufferedReader(new FileReader(f.getAbsolutePath()));
            String line="";
           line = in.readLine();
                serNb = line;
      // Afficher le contenu du fichier
            //line=in.readLine();
          //System.out.println ("ligne"+line);
            
        in.close();
       
            
            System.out.println("sernb: "+serNb);
        } catch (IOException ex) {
            System.out.println(""+ex);
        }*/
        
        if( !( shopInfosList.get(0).equals(crypt(mySerNber)))){
            javax.swing.JOptionPane.showMessageDialog(null,"MyShop Info \n"
                    + "Vous n'avez pas accès à cette application \n"
                    + "Contactez le +228 90628725 pour plus d'informations! Merci"); 
             System.exit(0);
        }
         
        task.setOnSucceeded(e -> {
             
         
            try {
                
                
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource(Constants.MainView));

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.getIcons().add(new Image(JavaFXApplication3.class.getResourceAsStream("/img/afnacos.ico")));
                primaryStage.setTitle("MyShop");
                primaryStage.setMaximized(false);
                //System.out.println("suis-je passé? oui ou non????");
                primaryStage.show();

                primaryStage.setOnHiding(((event) -> {
                    System.exit(0);
                }));
                Stage st = (Stage) progres.getScene().getWindow();
                st.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        });
        Thread th = new Thread(task);
        th.start();

    }
    
    private String  getSerialNumber(){
        String result = "";
            try
        {
            //String result = null;
            Process a = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
            System.out.println("");
            /*BufferedReader inputa
            = new BufferedReader(new InputStreamReader(a.getInputStream()));
            String linea;
            
            while ((linea = inputa.readLine()) != null)
            {
            result += linea;
            }
            if (result.equalsIgnoreCase(" ")) {
            System.out.println("Result is empty");
            } else
            {
            System.out.println("result000a: "+result);
            ///motherboard.setText(result);
            }
            inputa.close();*/
            
            Process p = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
            BufferedReader input
            = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null)
            {
            result += line;
            }
            if (result.equalsIgnoreCase(" ")) {
            System.out.println("Result is empty");
            } else
            {
            //System.out.println("result: "+result);
            ///motherboard.setText(result);
            }
            input.close();
        } catch (IOException ex)
        {
            System.out.println(""+ex);
        }
            return result;
            
    }
    
    private static MessageDigest digester;

    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }
    
    /*private void ecrireSerNb(String serNber){
         final String chemin = "tmp.txt";
        final File fichier =new File(chemin); 
        try {
            // Creation du fichier
            fichier .createNewFile();
            // creation d'un writer (un écrivain)
            final FileWriter writer = new FileWriter(fichier,true);
            try {
                writer.write(serNber+"\n");
              // System.out.println("fichier créé");
            } finally {
                // quoiqu'il arrive, on ferme le fichier
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
        }
    }
    
    private String lireSerNber(String chemin) throws FileNotFoundException, IOException{
        File f = new File(chemin);
        BufferedReader in = new BufferedReader(new FileReader(f.getAbsoluteFile()));
        String line="";
        while ((line = in.readLine()) != null)
        {
      // Afficher le contenu du fichier
            //line=in.readLine();
          //System.out.println ("ligne"+line);
        }
        in.close();
        return line;
    }*/
    

}
