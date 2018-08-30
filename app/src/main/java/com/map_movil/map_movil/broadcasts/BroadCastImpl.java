package com.map_movil.map_movil.broadcasts;

import android.app.Application;
import android.view.View;

public class BroadCastImpl extends Application {

    private static View view;
    private static boolean suscribir = false;
    private static int visible = 0;

    public static View getView() {
        return view;
    }

    public static void setView(View view) {
        BroadCastImpl.view = view;
    }

    public static boolean isSuscribir() {
        return suscribir;
    }

    public static void setSuscribir(boolean suscribir) {
        BroadCastImpl.suscribir = suscribir;
    }

    public static int getVisible() {
        return visible;
    }

    public static void setVisible(int visible) {
        BroadCastImpl.visible = visible;
    }
}
