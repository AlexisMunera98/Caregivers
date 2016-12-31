package com.example.gabriel.caregivers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by gabriel on 19/12/2016.
 */

public class DataBaseManager {
    public static final String TB_CUIDADORES = "cuidadores";
    public static final String CN_CC_CUIDADOR = "cedula_cuidador";
    public static final String CN_NOMBRE_CUIDADOR = "nombre_cuidador";
    public static final String CN_APELLIDO_CUIDADOR = "apellido_cuidador";
    public static final String CN_CLAVE_CUIDADOR = "clave_cuidador";
    public static final String CN_FOTO_CUIDADOR = "foto_cuidador";

    public static final String CREATE_TABLE_CUIDADOR = "CREATE TABLE " + TB_CUIDADORES + " (\n" +
            CN_CC_CUIDADOR + " NUMERIC  PRIMARY KEY NOT NULL,\n" +
            CN_NOMBRE_CUIDADOR + " VARCHAR(40)  NOT NULL,\n" +
            CN_APELLIDO_CUIDADOR + " VARCHAR(40)  NOT NULL,\n" +
            CN_CLAVE_CUIDADOR + " VARCHAR(10)  NOT NULL,\n" +
            CN_FOTO_CUIDADOR + " TEXT " +
            ")";
    //"_id NUMERIC AUTOINCREMENT, \n" +

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        Log.d("Prueba db:  ", CREATE_TABLE_CUIDADOR);

    }

    public Cursor cargarCuidadores() {
        Cursor c = db.rawQuery("SELECT " + CN_NOMBRE_CUIDADOR + " as _id," + CN_APELLIDO_CUIDADOR + " from " + TB_CUIDADORES, null);
        return c;
    }

    public void insertarCuidador(@NonNull String cedula, @NonNull String nombre,
                                 @NonNull String apellido, @NonNull String clave, @Nullable String foto) {
        ContentValues cv = new ContentValues();
        cv.put(CN_CC_CUIDADOR, cedula);
        cv.put(CN_NOMBRE_CUIDADOR, nombre);
        cv.put(CN_APELLIDO_CUIDADOR, apellido);
        cv.put(CN_CLAVE_CUIDADOR, clave);
        cv.put(CN_FOTO_CUIDADOR, foto);
        db.insert(TB_CUIDADORES, null, cv);

    }


}
