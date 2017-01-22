package com.example.gabriel.caregivers.DataBase;

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


    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        Log.d("Prueba db:  ", CREATE_TABLE_CUIDADOR);

    }

    public Cursor cargarCuidadores() {
        Cursor c = db.rawQuery("SELECT " + CN_NOMBRE_CUIDADOR + " as _id," + CN_APELLIDO_CUIDADOR + "," + CN_CC_CUIDADOR + " from " + TB_CUIDADORES, null);
        return c;
    }

    public Cursor cargarCuidadores(String nombrePaciente) {
        Cursor c = db.rawQuery("SELECT " + CN_NOMBRE_CUIDADOR + " as _id," + CN_APELLIDO_CUIDADOR + "," + CN_CC_CUIDADOR + " from " + TB_CUIDADORES + " where " + CN_NOMBRE_CUIDADOR + " like '%" + nombrePaciente + "%'", null);
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

    public boolean serLoginCorrecto(String cedula, String clave) {
        Cursor c = db.rawQuery("select " + CN_NOMBRE_CUIDADOR + " from " + TB_CUIDADORES + " where " + CN_CLAVE_CUIDADOR + " = '" + clave + "' and " + CN_CC_CUIDADOR + " = '" + cedula + "'", null);
        if (c.getCount() == 1) {
            return true;
        }
        return false;
    }

    public String[] getDatosCuidador(String cedula) {
        Cursor c = db.rawQuery("SELECT " + CN_NOMBRE_CUIDADOR + " as _id," + CN_APELLIDO_CUIDADOR + " from " + TB_CUIDADORES + " where " + CN_CC_CUIDADOR + " ='" + cedula + "'", null);
        c.moveToFirst();
        if (c.getCount() != 0) {
            return new String[]{c.getString(0), c.getString(1)};
        }
        return new String[]{"", ""};
    }

    public void insertarFoto(@NonNull String cedula, @NonNull String path) {
        db.execSQL("UPDATE " + TB_CUIDADORES + " SET " + CN_FOTO_CUIDADOR + "='" + path + "' WHERE " + CN_CC_CUIDADOR +
                "='" + cedula + "';");
    }

    public void actualizarNombreApellidoCuidador(@NonNull String nuevoNombre, @NonNull String nuevoApellido, @NonNull String cedula) {
        db.execSQL("UPDATE " + TB_CUIDADORES + " SET " + CN_NOMBRE_CUIDADOR + "='" + nuevoNombre + "' , " + CN_APELLIDO_CUIDADOR + " = '" + nuevoApellido + "' WHERE " + CN_CC_CUIDADOR + "='" + cedula + "';");
    }

    public void actualizarClaveCuidador(@NonNull String nuevaClave, @NonNull String cedula) {
        db.execSQL("update " + TB_CUIDADORES + " set " + CN_CLAVE_CUIDADOR + " = '" + nuevaClave + "' where " + CN_CC_CUIDADOR + " = '" + cedula + "';");
    }

    public String getPathFoto(String cedula) {
        Cursor c = db.rawQuery("select " + CN_FOTO_CUIDADOR + " from " + TB_CUIDADORES + " where " + CN_CC_CUIDADOR + " = '" + cedula + "';", null);
        c.moveToFirst();
        return c.getString(0);


    }


}
