package com.looigi.loovf.Soap;

import android.widget.Spinner;

import com.looigi.loovf.DialogMessaggio;
import com.looigi.loovf.StrutturaCategorie;
import com.looigi.loovf.VariabiliGlobali;

public class DBRemoto {
	private String RadiceWS = "http://looigi.ddns.net:1050/";
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
				1000,
				true);
		g.Esegue();
	}

	public void RitornaPermessi() {
    	String Device = VariabiliGlobali.getInstance().getTipoTelefono();
    	String User = VariabiliGlobali.getInstance().getUtenza();

    	Device = Device.replace("&", "***AND***");
		Device = Device.replace("?", "***PI***");

		User = User.replace("&", "***AND***");
		User = User.replace("?", "***PI***");

		String[] i = VariabiliGlobali.getInstance().getIMEI_IMSI().split(";");

		String Urletto="RitornaPermessi?Device=" + Device + "&User=" + User + "&IMEI=" + i[0] + "&IMSI=" + i[1];

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornaPermessi",
				NS,
				SA,
				25000,
				false);
		g.Esegue();
	}

	public void EliminaMultimediaDaID(String id) {
		String tip = "";
		Spinner mySpinner = VariabiliGlobali.getInstance().getsItems();
		if (mySpinner != null && mySpinner.getSelectedItem() != null) {
			String Categoria = mySpinner.getSelectedItem().toString();
			// StrutturaCategorie sc = new StrutturaCategorie();
			if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
				tip = "2";
				for (int i = 0; i < VariabiliGlobali.getInstance().getCategorieVideo().size(); i++) {
					if (Categoria == VariabiliGlobali.getInstance().getCategorieVideo().get(i).getNomeCategoria()) {
						int idCategoria = VariabiliGlobali.getInstance().getCategorieVideo().get(i).getIdCategoria();
						VariabiliGlobali.getInstance().setCategoriaViusalizzata(idCategoria);
						break;
					}
				}
				// sc = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo();
			} else {
				if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
					tip = "1";
					// sc = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini();
					for (int i = 0; i < VariabiliGlobali.getInstance().getCategorieImmagini().size(); i++) {
						if (Categoria == VariabiliGlobali.getInstance().getCategorieImmagini().get(i).getNomeCategoria()) {
							int idCategoria = VariabiliGlobali.getInstance().getCategorieImmagini().get(i).getIdCategoria();
							VariabiliGlobali.getInstance().setCategoriaViusalizzata(idCategoria);
							break;
						}
					}
				}
			}
			int idCategoria = VariabiliGlobali.getInstance().getCategoriaViusalizzata();

			String Urletto="EliminaMultimediaDaId?idTipologia=" + tip +
					"&idCategoria=" + idCategoria +
					"&idMultimedia=" + id;

			GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
					RadiceWS + ws + Urletto,
					"EliminaMultimediaDaId",
					NS,
					SA,
					25000,
					true);
			g.Esegue();
		}
	}

	public void RitornaMultimediaDaID(String id) {
		String tip = "";
		Spinner mySpinner = VariabiliGlobali.getInstance().getsItems();
		if (mySpinner != null && mySpinner.getSelectedItem() != null) {
			String Categoria = mySpinner.getSelectedItem().toString();
			// StrutturaCategorie sc = new StrutturaCategorie();
			if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
				tip = "2";
				for (int i = 0; i < VariabiliGlobali.getInstance().getCategorieVideo().size(); i++) {
					if (Categoria == VariabiliGlobali.getInstance().getCategorieVideo().get(i).getNomeCategoria()) {
						int idCategoria = VariabiliGlobali.getInstance().getCategorieVideo().get(i).getIdCategoria();
						VariabiliGlobali.getInstance().setCategoriaViusalizzata(idCategoria);
						break;
					}
				}
				// sc = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo();
			} else {
				if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
					tip = "1";
					// sc = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini();
					for (int i = 0; i < VariabiliGlobali.getInstance().getCategorieImmagini().size(); i++) {
						if (Categoria == VariabiliGlobali.getInstance().getCategorieImmagini().get(i).getNomeCategoria()) {
							int idCategoria = VariabiliGlobali.getInstance().getCategorieImmagini().get(i).getIdCategoria();
							VariabiliGlobali.getInstance().setCategoriaViusalizzata(idCategoria);
							break;
						}
					}
				}
			}
			int idCategoria = VariabiliGlobali.getInstance().getCategoriaViusalizzata();

			String Urletto="RitornaMultimediaDaId?idTipologia=" + tip +
					"&idCategoria=" + idCategoria +
					"&idMultimedia=" + id;

			GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
					RadiceWS + ws + Urletto,
					"RitornaMultimediaDaId",
					NS,
					SA,
					25000,
					true);
			g.Esegue();
		}
	}

	public void RitornaQuantiFilesPhoto() {
		String Urletto="RitornaQuantiFiles?idTipologia=1";

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornaQuantiFilesPhoto",
				NS,
				SA,
				25000,
				true);
		g.Esegue();
	}

	public void EffettuaRicerca(String Categoria, String Ricerca) {
		String tip = "";
		if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
			tip = "2";
		} else {
			if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
				tip = "1";
			}
		}
		String Urletto="EffettuaRicerca?idTipologia=" + tip +
				"&Categoria=" +Categoria + "&Ricerca=" + Ricerca + "&Quante=100";

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"EffettuaRicerca",
				NS,
				SA,
				25000,
				true);
		g.Esegue();
	}

	public void RitornaImmaginiGriglia() {
		// String tip = "";
		// if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
		// 	tip="2";
		// } else {
		// 	if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
		// 		tip = "1";
		// 	}
		// }
		String Categoria = "";
		// if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
		// 	Categoria = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo();
		// } else {
		// 	if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
		// 		Categoria = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini();
		// 	}
		// }
		StrutturaCategorie sc = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini();
		Categoria = sc.getNomeCategoria();

		if (!Categoria.isEmpty()) {
			String Urletto = "RitornaImmaginiPerGriglia?QuanteImm=50&Categoria=" + Categoria;

			GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
					RadiceWS + ws + Urletto,
					"RitornaImmaginiPerGriglia",
					NS,
					SA,
					25000,
					true);
			g.Esegue();
		} else {
			DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
					"Selezionare una categoria",
					true,
					"looVF",
					false,
					"");
		}
	}

	public void RitornaSuccessivoMultimedia() {
		String tip = "";
		Spinner mySpinner = VariabiliGlobali.getInstance().getsItems();
		String Categoria = mySpinner.getSelectedItem().toString();
		// StrutturaCategorie sc = new StrutturaCategorie();

		if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
			tip="2";
			for (int i = 0; i < VariabiliGlobali.getInstance().getCategorieVideo().size(); i++) {
				if (Categoria == VariabiliGlobali.getInstance().getCategorieVideo().get(i).getNomeCategoria()) {
					int idCategoria = VariabiliGlobali.getInstance().getCategorieVideo().get(i).getIdCategoria();
					VariabiliGlobali.getInstance().setCategoriaViusalizzata(idCategoria);
					break;
				}
			}
		} else {
			if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
				tip = "1";
				for (int i = 0; i < VariabiliGlobali.getInstance().getCategorieImmagini().size(); i++) {
					if (Categoria == VariabiliGlobali.getInstance().getCategorieImmagini().get(i).getNomeCategoria()) {
						int idCategoria = VariabiliGlobali.getInstance().getCategorieImmagini().get(i).getIdCategoria();
						VariabiliGlobali.getInstance().setCategoriaViusalizzata(idCategoria);
						break;
					}
				}
			}
		}

		/* String Categoria = "";
		if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
			Categoria = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo().getNomeCategoria();
			// VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo().getNomeCategoria();
		} else {
			if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
				Categoria = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini().getNomeCategoria();
				// VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini().getNomeCategoria();
			}
		} */

		if (!Categoria.isEmpty()) {
			String Urletto = "RitornaSuccessivoMultimedia?idTipologia=" + tip + "&Categoria=" + Categoria;

			GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
					RadiceWS + ws + Urletto,
					"RitornaSuccessivoMultimedia",
					NS,
					SA,
					25000,
					true);
			g.Esegue();
		} else {
			DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
					"Selezionare una categoria",
					true,
					"looVF",
					false,
					"");
		}
	}

	public void RitornaQuantiFilesVideo() {
		String Urletto="RitornaQuantiFiles?idTipologia=2";

		GestioneWEBServiceSOAP g = new GestioneWEBServiceSOAP(
				RadiceWS + ws + Urletto,
				"RitornaQuantiFilesVideo",
				NS,
				SA,
				30000,
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
				25000,
				true);
		g.Esegue();
	}
}
