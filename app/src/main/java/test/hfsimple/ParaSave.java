package test.hfsimple;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2017/4/7.
 */
public class ParaSave {

    private Context context ;

    public ParaSave(Context context) {
        this.context = context ;
    }

    public void saveSerial(String serial){
        SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor = shared.edit() ;
        editor.putString("serial", serial) ;
        editor.commit() ;

    }

    public String getSerial() {
        String serial = "" ;
        SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
        serial = shared.getString("serial", "com13") ;
        return serial ;
    }


    public void savePower(String power){
        SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor = shared.edit() ;
        editor.putString("power", power) ;
        editor.commit() ;

    }

    public String getPower() {
        String power = "" ;
        SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
        power = shared.getString("power", "5v") ;
        return power ;
    }
}
