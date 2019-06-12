package com.looigi.loovf;

import android.content.Intent;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // Detected click
        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            StrutturaFiles sf = VariabiliGlobali.getInstance().getVideoCaricato();

            String vidAddress = sf.getNomeFile().replace("\\", "/");
            int Categoria = sf.getCategoria();
            StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaID("2", Categoria);
            String sCategoria = sc.getNomeCategoria();
            vidAddress = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + vidAddress;

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            Uri data = Uri.parse(vidAddress);
            String estensione = vidAddress.substring(vidAddress.length()-3,vidAddress.length());

            intent.setDataAndType(data, "video/" + estensione);
            VariabiliGlobali.getInstance().getContext().startActivity(intent);
        }

        // VariabiliGlobali.getInstance().setTastopremuto("");

        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Utility.getInstance().AvanzaMultimedia();
            return false; // Right to left
        }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Utility.getInstance().IndietreggiaMultimedia();
            return false; // Left to right
        }

        if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            return false; // Bottom to top
        }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            return false; // Top to bottom
        }

        return false;
    }
}
