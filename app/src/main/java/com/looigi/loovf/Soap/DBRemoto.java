package com.looigi.loovf.Soap;

import android.content.Context;
import android.os.Handler;

public class DBRemoto {
	private String RadiceWS = "http://looigi.no-ip.biz:12345/looVF/";
	private String ws = "looVF.asmx/";
	private String NS="http://looVF.org/";
	private String SA="http://looVF.org/";
    private Runnable rAttendeRispostaCheckURL;
    private Handler hAttendeRispostaCheckURL;
    private int Secondi;
    private int MaxAttesa = 60;

    private String ToglieCaratteriStrani(String Cosa) {
		if (Cosa!=null) {
			String sCosa = Cosa.replace("?", "***PI***");
			sCosa = sCosa.replace("&", "***AND***");

			return sCosa;
		} else {
			return "";
		}
	}

	public void RitornaListe() {
		String Urletto="RitornaListe";

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornaListe",
				NS,
				SA,
				60000,
				true);
		g.Esegue();
	}
}
