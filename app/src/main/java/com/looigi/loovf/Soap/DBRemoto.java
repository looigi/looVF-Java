package com.looigi.loovf.Soap;

import android.content.Context;
import android.os.Handler;

import com.looigi.loovf.VariabiliGlobali;

public class DBRemoto {
	private String RadiceWS = "http://looigi.no-ip.biz:12345/looVF/";
	private String ws = "looVF.asmx/";
	private String NS="http://looVF.org/";
	private String SA="http://looVF.org/";

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
		String Urletto="RitornaFiles?Aggiorna=N";

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornaFiles",
				NS,
				SA,
				1420000,
				true);
		g.Esegue();
	}

	public void RitornaMultimediaDaID(String Tipologia, String id) {
    	String tip = "";
    	if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
    		tip="2";
		} else {
			tip="1";
		}
		String Urletto="RitornMultimediaDaId?Tipologia=" + tip + "&idMultimedia=" + id;

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornMultimediaDaId",
				NS,
				SA,
				10000,
				true);
		g.Esegue();
	}

	public void RitornaQuantiFilesPhoto() {
		String Urletto="RitornaQuantiFiles?idTipologia=1";

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornaQuantiFilesPhoto",
				NS,
				SA,
				5000,
				true);
		g.Esegue();
	}

	public void RitornaSuccessivoMultimedia() {
		String tip = "";
		if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
			tip="2";
		} else {
			tip="1";
		}
		String Categoria = "";
		if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
			Categoria = VariabiliGlobali.getInstance().getCategoriaSceltaVideo();
		} else {
			Categoria = VariabiliGlobali.getInstance().getCategoriaSceltaImmagine();
		}
		String Urletto="RitornaSuccessivoMultimedia?idTipologia=" + tip + "&Categoria=" + Categoria;

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornaSuccessivoMultimedia",
				NS,
				SA,
				5000,
				false);
		g.Esegue();
	}

	public void RitornaQuantiFilesVideo() {
		String Urletto="RitornaQuantiFiles?idTipologia=2";

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornaQuantiFilesVideo",
				NS,
				SA,
				5000,
				true);
		g.Esegue();
	}

	public void RitornaCategorie() {
		String Urletto="RitornaCategorie";

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornaCategorie",
				NS,
				SA,
				15000,
				true);
		g.Esegue();
	}
}
