package com.asche.usosqlite.utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.asche.usosqlite.utilidades.UtilidadesMascota;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

public class ConexionSQLiteHelper  extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UtilidadesUsuario.CREATE_TABLE);
        db.execSQL(UtilidadesMascota.CREATE_TABLE);
        db.execSQL(UtilidadesRaza.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL(UtilidadesUsuario.DROP_TABLE);
        db.execSQL(UtilidadesMascota.DROP_TABLE);
        db.execSQL(UtilidadesRaza.DROP_TABLE);
        this.onCreate(db);
    }
}
