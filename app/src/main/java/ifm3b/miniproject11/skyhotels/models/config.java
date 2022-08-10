package ifm3b.miniproject11.skyhotels.models;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class config implements Serializable {
    private String userEmail;
    private boolean logState;

    public config(boolean logState,String user){
        this.logState = logState;
        this.userEmail = user;
    }

    public config(config configFile){
        this.userEmail = configFile.getUser();
        this.logState = configFile.isLogged();
    }

    public String getUser() {
        return userEmail;
    }

    public void setUserEmail(String user) {
        this.userEmail = user;
    }

    public boolean isLogged() {
        return logState;
    }

    public void setLogState(boolean logState) {
        this.logState = logState;
    }

    public static void writeState(Context context,config config){
        ObjectOutputStream obOut = null;
        try{
            FileOutputStream fos = new FileOutputStream(context.getFilesDir()+"/userconfig.dat");
            BufferedOutputStream bufOuT = new BufferedOutputStream(fos);

            obOut = new ObjectOutputStream(bufOuT);
            obOut.writeObject(config);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(obOut != null){
                try{
                    obOut.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static config readState(Context context){
        ObjectInputStream onIn = null;
        try{
            FileInputStream fin = new FileInputStream(context.getFilesDir()+"/userconfig.dat");
            BufferedInputStream bufIn = new BufferedInputStream(fin);
            onIn = new ObjectInputStream(bufIn);

            Object obj = onIn.readObject();
            if(obj instanceof config){
                return (config) obj;
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            if(onIn != null){
                try {
                    onIn.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
