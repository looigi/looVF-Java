package com.looigi.loovf;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.looigi.loovf.Soap.DBRemoto;
import com.looigi.loovf.db_locale.db_dati;
import com.looigi.loovf.griglia.AdapterRecyclerView;
import com.looigi.loovf.griglia.ImageListForRecycler;
import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private static final Utility ourInstance = new Utility();

    public static Utility getInstance() {
        return ourInstance;
    }

    private int Contatore;

    private Utility() {
    }

    private ArrayList<ImageListForRecycler> prepareData(List<String> Titoli, List<String> Percorsi,List<String> ID){
        ArrayList<ImageListForRecycler> theimage = new ArrayList<>();

        for(int i = 0; i< Titoli.size(); i++){
            ImageListForRecycler createList = new ImageListForRecycler();
            createList.setImage_title(Titoli.get(i));
            createList.setImage_URL(Percorsi.get(i));
            createList.setImage_id(ID.get(i));
            theimage.add(createList);
        }

        return theimage;
    }

    public void CaricaRecyclerView(String Ritorno, boolean Salva) {
        VariabiliGlobali.getInstance().getRecyclerView().setHasFixedSize(true);

        ArrayList<ImageListForRecycler> createLists = new ArrayList<>();
        String[] c = Ritorno.split("§");
        String idCategoria = "";

        for (String cc : c) {
            ImageListForRecycler i = new ImageListForRecycler();

            String[] ccc = cc.split(";");
            String[] t = ccc[0].split("\\\\");
            String tt = t[t.length-1];
            String path = ccc[0];
            path = path.replace("***PV***", ";").replace("\\", "/");

            String ID = cc;
            String Titoli = tt;
            String Data = ccc[2];
            String Dime = ccc[1];
            String Numero = ccc[4];

            if (idCategoria.isEmpty()) {
                // if (Salva) {
                    idCategoria = Integer.toString(Integer.parseInt(ccc[3]));
                // } else {
                //     idCategoria = Integer.toString(Integer.parseInt(ccc[3]));
                // }
            }

            StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaID("1", Integer.parseInt(idCategoria));
            String Categoria = sc.getNomeCategoria();
            String Percorsi = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + Categoria + "/" + path;

            i.setImage_category(idCategoria);
            i.setImage_date(Data);
            i.setImage_dime(Dime);
            i.setImage_id(ID);
            i.setImage_title(Titoli);
            i.setImage_URL(Percorsi);
            i.setImage_number(Numero);

            createLists.add(i);
        }

        if (Salva) {
            db_dati db = new db_dati();
            String rit = db.ScriveRigheGriglia("1", idCategoria, Ritorno);

            if (!rit.isEmpty()) {
                DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                        "ERRORE nel salvataggio dei dati della griglia:\n" + rit,
                        true,
                        "looVF",
                        false);
            }
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(VariabiliGlobali.getInstance().getContext(),2);
        VariabiliGlobali.getInstance().getRecyclerView().setLayoutManager(layoutManager);
        // ArrayList<ImageListForRecycler> createLists = prepareData(Titoli, Percorsi, ID);
        AdapterRecyclerView adapter = new AdapterRecyclerView(VariabiliGlobali.getInstance().getContext(), createLists);
        VariabiliGlobali.getInstance().getRecyclerView().setAdapter(adapter);
    }

    public void riempieSpinner() {
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("");

        if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO") ||
                VariabiliGlobali.getInstance().getModalita().equals("GRIGLIA")) {
            for (StrutturaCategorie s: VariabiliGlobali.getInstance().getCategorieImmagini()) {
                boolean Ok = true;

                if (s.isProtetta() && !VariabiliGlobali.getInstance().getConfigurazione().isVisuaTutto()) {
                    Ok = false;
                }
                if (Ok) {
                    spinnerArray.add(s.getNomeCategoria());
                }
            }
        } else {
            if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                for (StrutturaCategorie s: VariabiliGlobali.getInstance().getCategorieVideo()) {
                    boolean Ok = true;

                    if (s.isProtetta() && !VariabiliGlobali.getInstance().getConfigurazione().isVisuaTutto()) {
                        Ok = false;
                    }
                    if (Ok) {
                        spinnerArray.add(s.getNomeCategoria());
                    }
                }
            }
        }

        boolean Ok = true;

        if (VariabiliGlobali.getInstance().getConfigurazione().isVisuaTutto()) {
            for (String s : spinnerArray) {
                if (s.equals("Tutto")) {
                    Ok = false;
                    break;
                }
            }
        } else {
            Ok = false;
        }

        if (Ok) {
            // StrutturaCategorie sc = new StrutturaCategorie();

            // sc.setIdCategoria(999);
            // sc.setNomeCategoria("Tutto");
            // sc.setPercorsoCategoria("");
            // sc.setProtetta(false);

            spinnerArray.add("Tutto");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                VariabiliGlobali.getInstance().getContext(),
                android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        VariabiliGlobali.getInstance().getsItems().setAdapter(adapter);

        // VariabiliGlobali.getInstance().getsItems().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //         if (VariabiliGlobali.getInstance().isCaricataPagina()) {
        //             String item = parent.getItemAtPosition(position).toString();
//
        //             if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
        //                 VariabiliGlobali.getInstance().setCategoriaSceltaVideo(item);
//
        //                 StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaNome("2", item);
        //                 VariabiliGlobali.getInstance().getConfigurazione().setUltimaCategoriaVideo(sc);
        //             } else {
        //                 VariabiliGlobali.getInstance().setCategoriaSceltaImmagine(item);
//
        //                 StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaNome("1", item);
        //                 VariabiliGlobali.getInstance().getConfigurazione().setUltimaCategoriaImmagini(sc);
        //             }
//
        //             db_dati db = new db_dati();
        //             db.ScriveConfigurazione();
        //         }
        //     }
        //     public void onNothingSelected(AdapterView<?> parent) {
        //     }
        // });

        // VariabiliGlobali.getInstance().getConfigurazione().setUltimaCategoriaImmagini(VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini());
        // VariabiliGlobali.getInstance().setCategoriaSceltaVideo(VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo());

        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            if (VariabiliGlobali.getInstance().getConfigurazione() != null) {
                if (VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo().getNomeCategoria()==null) {
                    VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo().setNomeCategoria("");
                }
                if (!VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo().getNomeCategoria().isEmpty()) {
                    int spinnerPosition = adapter.getPosition(VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo().getNomeCategoria());
                    VariabiliGlobali.getInstance().getsItems().setSelection(spinnerPosition);
                }
            }
        } else {
            if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                if (VariabiliGlobali.getInstance().getConfigurazione() != null) {
                    if (VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini().getNomeCategoria()==null) {
                        VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini().setNomeCategoria("");
                    }
                    if (!VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini().getNomeCategoria().isEmpty()) {
                        int spinnerPosition = adapter.getPosition(VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini().getNomeCategoria());
                        VariabiliGlobali.getInstance().getsItems().setSelection(spinnerPosition);
                    }
                }
            }
        }
    }

    public void CaricaConfigurazione() {
        db_dati db = new db_dati();
        StrutturaConfig sc = db.CaricaConfigurazione();

        if (sc != null) {
            VariabiliGlobali.getInstance().setConfigurazione(sc);
        } else {
            sc = new StrutturaConfig();
            sc.setRandom(true);
            sc.setVisuaTutto(false);

            StrutturaCategorie sc1 = new StrutturaCategorie();
            sc1.setIdCategoria(999);
            sc1.setNomeCategoria("Tutto");
            sc1.setPercorsoCategoria("");
            sc1.setProtetta(false);

            if (sc.getUltimaCategoriaImmagini()==null || sc.getUltimaCategoriaImmagini().getNomeCategoria().isEmpty()) {
                sc.setUltimaCategoriaImmagini(sc1);
            }
            if (sc.getUltimaCategoriaVideo()==null || sc.getUltimaCategoriaVideo().getNomeCategoria().isEmpty()) {
                sc.setUltimaCategoriaVideo(sc1);
            }

            VariabiliGlobali.getInstance().setConfigurazione(sc);

            String Ritorno = db.ScriveConfigurazione();
            if (!Ritorno.isEmpty()) {
                DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                        "ERRORE nel salvataggio della configurazione\n" + Ritorno,
                        true,
                        "looVF",
                        false);
            }
        }

        VariabiliGlobali.getInstance().getChkRandom().setChecked(sc.isRandom());
        VariabiliGlobali.getInstance().getChkVisuaTutto().setChecked(sc.isVisuaTutto());
    }

    public String PrendeErroreDaException(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        return TransformError(errors.toString());
    }

    private String TransformError(String error) {
        String Return=error;

        if (Return.length()>250) {
            Return=Return.substring(0,247)+"...";
        }
        Return=Return.replace("\n"," ");

        return Return;
    }

    public void ScriveInformazioni() {
        TextView t = VariabiliGlobali.getInstance().getTxtInfo();
        // if (VariabiliGlobali.getInstance().isDatiCaricati()) {
        String Caricati = "";
        String Visualizzato = "";
        String Visualizzate = "";
        String NomeFile = "";

        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            Caricati = "Video caricati: " + Long.toString(VariabiliGlobali.getInstance().getQuantiVideo());
            Visualizzato = "Visualizzato: " + Long.toString(VariabiliGlobali.getInstance().getVideoVisualizzato());
            Visualizzate = "Visti: " + Long.toString(VariabiliGlobali.getInstance().getIndiceVideo()) + "/" +
                    Long.toString(VariabiliGlobali.getInstance().getVideoVisualizzati().size()-1);
            if (VariabiliGlobali.getInstance().getVideoCaricato()!=null) {
                long Dime2 = VariabiliGlobali.getInstance().getVideoCaricato().getDimeFile();
                String[] tipo={"b.", "Kb.", "Mb.", "Gb."};
                int quale = 0;
                while (Dime2>1024) {
                    Dime2 /= 1024;
                    quale++;
                }

                NomeFile = "\n" + VariabiliGlobali.getInstance().getVideoCaricato().getNomeFile();
                NomeFile += " dim: " + Long.toString(Dime2) + " " + tipo[quale];
                NomeFile += " data: " + VariabiliGlobali.getInstance().getVideoCaricato().getDataFile();

                VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
            }

            t.setText(Caricati + " - " + Visualizzato + NomeFile + "\n" + Visualizzate);
        } else {
            if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                Caricati = "Immagini caricate: " + Long.toString(VariabiliGlobali.getInstance().getQuanteImmagini());
                Visualizzato = "Visualizzata: " + Long.toString(VariabiliGlobali.getInstance().getImmagineVisualizzata());
                Visualizzate = "Viste: " + Long.toString(VariabiliGlobali.getInstance().getIndiceImmagine()) + "/" +
                        Long.toString(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1);
                if (VariabiliGlobali.getInstance().getImmagineCaricata() != null) {
                    long Dime2 = VariabiliGlobali.getInstance().getImmagineCaricata().getDimeFile();
                    String[] tipo = {"b.", "Kb.", "Mb.", "Gb."};
                    int quale = 0;
                    while (Dime2 > 1024) {
                        Dime2 /= 1024;
                        quale++;
                    }

                    NomeFile = "\n" + VariabiliGlobali.getInstance().getImmagineCaricata().getNomeFile();
                    NomeFile += " dim: " + Long.toString(Dime2) + " " + tipo[quale];
                    NomeFile += " data: " + VariabiliGlobali.getInstance().getImmagineCaricata().getDataFile();

                    VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
                }

                t.setText(Caricati + " - " + Visualizzato + NomeFile + "\n" + Visualizzate);
            }
        }
    }

    public void PrendeUltimoMultimedia(boolean ResettaIndice)  {
        db_dati db = new db_dati();
        boolean CeUltima=false;

        db.LeggeTutteLeViste();

        if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
            long ultimaImmVista = db.LeggeUltimaVista("PHOTO");
            if (ultimaImmVista > -1) {
                VariabiliGlobali.getInstance().setImmagineVisualizzata(ultimaImmVista);

                if (ResettaIndice) {
                    VariabiliGlobali.getInstance().setIndiceImmagine(1);
                    VariabiliGlobali.getInstance().getImmaginiVisualizzate().add(ultimaImmVista);
                }
            }
            CeUltima=true;
        } else {
            if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                long ultimoVideoVisto = db.LeggeUltimaVista("VIDEO");
                if (ultimoVideoVisto > -1) {
                    VariabiliGlobali.getInstance().setVideoVisualizzato(ultimoVideoVisto);

                    if (ResettaIndice) {
                        VariabiliGlobali.getInstance().setIndiceVideo(1);
                        VariabiliGlobali.getInstance().getVideoVisualizzati().add(ultimoVideoVisto);
                    }
                }
                CeUltima = true;
            }
        }
        if (CeUltima) {
            ScriveInformazioni();
            CaricaMultimedia();
        }
    }

    public void IndietreggiaMultimedia() {
        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            long Quale = VariabiliGlobali.getInstance().getIndiceVideo()-1;
            if (Quale >= 0) {
                VariabiliGlobali.getInstance().setIndiceVideo(Quale);
                VariabiliGlobali.getInstance().setVideoVisualizzato(VariabiliGlobali.getInstance().getVideoVisualizzati().get((int) Quale));
            }
        } else {
            if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                long Quale = VariabiliGlobali.getInstance().getIndiceImmagine() - 1;
                if (Quale >= 0) {
                    VariabiliGlobali.getInstance().setIndiceImmagine(Quale);
                    VariabiliGlobali.getInstance().setImmagineVisualizzata(VariabiliGlobali.getInstance().getImmaginiVisualizzate().get((int) Quale));
                }
            }
        }

        ScriveInformazioni();
        CaricaMultimedia();
    }

    public void AvanzaMultimedia() {
        db_dati db = new db_dati();
        boolean Ok = false;

        // Spinner mySpinner = VariabiliGlobali.getInstance().getsItems();
        // String text = mySpinner.getSelectedItem().toString();

        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            if (VariabiliGlobali.getInstance().getIndiceVideo() < VariabiliGlobali.getInstance().getVideoVisualizzati().size()-1) {
                long Quale = VariabiliGlobali.getInstance().getIndiceVideo()+1;
                VariabiliGlobali.getInstance().setIndiceVideo(Quale);
                VariabiliGlobali.getInstance().setVideoVisualizzato(VariabiliGlobali.getInstance().getVideoVisualizzati().get((int) Quale));
                Ok = true;
            } else {
                if (VariabiliGlobali.getInstance().getConfigurazione().isRandom()) {
                    // Random r = new Random();
                    // long prossimo = r.nextInt((int) VariabiliGlobali.getInstance().getQuantiVideo());
                    // VariabiliGlobali.getInstance().setVideoVisualizzato(prossimo);
//
                    // db.ScriveVisti(Long.toString(prossimo), VariabiliGlobali.getInstance().getModalita());
                    VariabiliGlobali.getInstance().setDeveCaricare(true);
                    DBRemoto dbr = new DBRemoto();
                    dbr.RitornaSuccessivoMultimedia();
                } else {
                    long prossimo = VariabiliGlobali.getInstance().getVideoVisualizzato();
                    prossimo++;
                    VariabiliGlobali.getInstance().setVideoVisualizzato(prossimo);

                    db.ScriveVisti(Long.toString(prossimo), VariabiliGlobali.getInstance().getModalita());

                    VariabiliGlobali.getInstance().getVideoVisualizzati().add(VariabiliGlobali.getInstance().getVideoVisualizzato());
                    VariabiliGlobali.getInstance().setIndiceVideo(VariabiliGlobali.getInstance().getVideoVisualizzati().size() - 1);
                    Ok = true;
                }
            }
        } else {
            if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                if (VariabiliGlobali.getInstance().getIndiceImmagine() < VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1) {
                    long Quale = VariabiliGlobali.getInstance().getIndiceImmagine() + 1;
                    VariabiliGlobali.getInstance().setIndiceImmagine(Quale);
                    VariabiliGlobali.getInstance().setImmagineVisualizzata(VariabiliGlobali.getInstance().getImmaginiVisualizzate().get((int) Quale));
                    Ok = true;
                } else {
                    if (VariabiliGlobali.getInstance().getConfigurazione().isRandom()) {
                        // Random r = new Random();
                        // int prossima = r.nextInt((int) VariabiliGlobali.getInstance().getQuanteImmagini());
                        // VariabiliGlobali.getInstance().setImmagineVisualizzata(prossima);
//
                        // db.ScriveVisti(Integer.toString(prossima), VariabiliGlobali.getInstance().getModalita());
                        VariabiliGlobali.getInstance().setDeveCaricare(true);
                        DBRemoto dbr = new DBRemoto();
                        dbr.RitornaSuccessivoMultimedia();
                    } else {
                        long prossima = VariabiliGlobali.getInstance().getImmagineVisualizzata();
                        prossima++;
                        VariabiliGlobali.getInstance().setImmagineVisualizzata(prossima);

                        db.ScriveVisti(Long.toString(prossima), VariabiliGlobali.getInstance().getModalita());

                        VariabiliGlobali.getInstance().getImmaginiVisualizzate().add(VariabiliGlobali.getInstance().getImmagineVisualizzata());
                        VariabiliGlobali.getInstance().setIndiceImmagine(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1);
                        Ok = true;
                    }
                }
            }
        }

        if (Ok) {
            ScriveInformazioni();
            CaricaMultimedia();
        }
    }

    public void VisualizzaMultimedia(String Ritorno) {
        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
            VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.VISIBLE);

            VariabiliGlobali.getInstance().setUltimoRitornoVideo(Ritorno);

            String[] Rit = Ritorno.split("§");
            // ImageView vidView = VariabiliGlobali.getInstance().getImgPlayVideo();

            String ImmVideo = VariabiliGlobali.getInstance().getPercorsoURL() + "/Thumbs/" + Rit[0].replace("\\", "/");
            ImmVideo = ImmVideo.replace(" ","%20");

            Picasso.get().load(ImmVideo).placeholder( R.drawable.progress_animation ).into(VariabiliGlobali.getInstance().getImgPlayVideo());

            StrutturaFiles sf = new StrutturaFiles();
            String[] c = Rit[1].split(";");
            sf.setTipologia("2");
            sf.setNomeFile(c[0].replace("***PV***",";"));
            sf.setDimeFile(Long.parseLong(c[1]));
            sf.setDataFile(c[2]);
            sf.setCategoria(Integer.parseInt(c[3]));

            VariabiliGlobali.getInstance().setVideoCaricato(sf);

            // String vidAddress = sf.getNomeFile().replace("\\", "/");
            // int Categoria = sf.getCategoria()-1;
            // String sCategoria = VariabiliGlobali.getInstance().getCategorieVideo().get(Categoria);
            // vidAddress = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + vidAddress;

            // Uri vidUri = Uri.parse(vidAddress);
            // vidView.setVideoURI(vidUri);
//
            // MediaController vidControl = new MediaController(VariabiliGlobali.getInstance().getContext());
            // vidControl.setAnchorView(vidView);
            // vidView.setMediaController(vidControl);
//
            // vidView.start();

            // VideoPlayer v = new VideoPlayer(VariabiliGlobali.getInstance().getContext(),
            //         vidView,
            //         VariabiliGlobali.getInstance().getPgrBar(),
            //         vidAddress);

            // Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            // Uri data = Uri.parse(vidAddress);
            // String estensione = vidAddress.substring(vidAddress.length()-3,vidAddress.length());
//
            // intent.setDataAndType(data, "video/" + estensione);
            // VariabiliGlobali.getInstance().getContext().startActivity(intent);

            // Utility.getInstance().LoadVideo(vidView, vidAddress, sf.getNomeFile(), sCategoria, sf.getDimeFile());
        } else {
            if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                VariabiliGlobali.getInstance().setUltimoRitornoImmagine(Ritorno);
                VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.VISIBLE);

                StrutturaFiles sf = new StrutturaFiles();

                String[] Rit = Ritorno.split("§");

                String[] c = Rit[1].split(";");
                sf.setTipologia("1");
                sf.setNomeFile(c[0].replace("***PV***", ";"));
                sf.setDimeFile(Long.parseLong(c[1]));
                sf.setDataFile(c[2]);
                sf.setCategoria(Integer.parseInt(c[3]));
                VariabiliGlobali.getInstance().setImmagineCaricata(sf);

                String NomeImmagine = sf.getNomeFile().replace("\\", "/");
                int Categoria = sf.getCategoria();
                StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaID("1", Categoria);
                String sCategoria = sc.getNomeCategoria();
                NomeImmagine = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + NomeImmagine;
                NomeImmagine = NomeImmagine.replace(" ", "%20");

                ImageView mImageView = VariabiliGlobali.getInstance().getiView();

                Picasso.get().load(NomeImmagine).placeholder(R.drawable.progress_animation).into(mImageView);
            } else {
                VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.GONE);
            }
        }

        Utility.getInstance().ScriveInformazioni();
    }

    public boolean ePari(int numero) {
        if ((numero % 2) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void CaricaMultimedia() {
        // db_dati db = new db_dati();
        DBRemoto dbr = new DBRemoto();

        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            long Quale = VariabiliGlobali.getInstance().getVideoVisualizzato();
            dbr.RitornaMultimediaDaID(Long.toString(Quale));
        } else {
            if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                long Quale = VariabiliGlobali.getInstance().getImmagineVisualizzata();
                dbr.RitornaMultimediaDaID(Long.toString(Quale));
            }
        }
    }

    public void AccendeSpegneOggettiInBaseAiPermessi() {
        if (VariabiliGlobali.getInstance().isAmministratore()) {
            VariabiliGlobali.getInstance().getChkVisuaTutto().setVisibility(LinearLayout.VISIBLE);
            if (VariabiliGlobali.getInstance().getConfigurazione().isVisuaTutto()) {
                VariabiliGlobali.getInstance().getLayTastoVideo().setVisibility(LinearLayout.VISIBLE);
            } else {
                VariabiliGlobali.getInstance().getLayTastoVideo().setVisibility(LinearLayout.GONE);
            }
        } else{
            VariabiliGlobali.getInstance().getChkVisuaTutto().setVisibility(LinearLayout.GONE);
            VariabiliGlobali.getInstance().getLayTastoVideo().setVisibility(LinearLayout.GONE);
        }
    }

    /* public void LoadVideo(final VideoView mVideoView, String videoUrl, String Titolo, String Categoria, long Dime) {
        // final ProgressDialog pDialog = new ProgressDialog(VariabiliGlobali.getInstance().getContext());

        // Set progressbar message
        long Dime2 = Dime;
        String[] tipo={"b.", "Kb.", "Mb.", "Gb."};
        int quale = 0;
        while (Dime2>1024) {
            Dime2 /= 1024;
            quale++;
        }

        String Testo = "Buffering...\n"+Titolo+"\nCategoria: "+Categoria+"\nSize: "+Long.toString(Dime2) + " " + tipo[quale];
        VariabiliGlobali.getInstance().getTxtCaricamento().setText(Testo);
        VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.VISIBLE);
        // pDialog.setMessage("Buffering...\n"+Titolo+"\nCategoria: "+Categoria+"\nSize: "+Long.toString(Dime2) + " " + tipo[quale]);
        // pDialog.setIndeterminate(false);
        // pDialog.setCancelable(false);
        // Show progressbar
        // pDialog.show();

        VariabiliGlobali.getInstance().getPgrBar().setIndeterminate(true);
        VariabiliGlobali.getInstance().getPgrBar().setMax(100);
        VariabiliGlobali.getInstance().getPgrBar().setProgress(0);
        Contatore = 0;
//
        VariabiliGlobali.getInstance().sethSpinner(new Handler(Looper.getMainLooper()));
        VariabiliGlobali.getInstance().setRunSpinner ( new Runnable() {
            @Override
            public void run() {
                Contatore++;
                if (Contatore>100) {
                    Contatore=0;
                }
                VariabiliGlobali.getInstance().getPgrBar().setProgress(0);
                VariabiliGlobali.getInstance().gethSpinner().postDelayed(VariabiliGlobali.getInstance().getRunSpinner(),100);
            }
        });
        VariabiliGlobali.getInstance().gethSpinner().postDelayed(VariabiliGlobali.getInstance().getRunSpinner(),100);

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(VariabiliGlobali.getInstance().getContext());
            mediacontroller.setMediaPlayer(mVideoView);
            mediacontroller.setAnchorView(mVideoView);

            Uri videoUri = Uri.parse(videoUrl.replace(" ","%20"));
            mVideoView.setMediaController(mediacontroller);
            mVideoView.setVideoURI(videoUri);
        } catch (Exception e) {
            VariabiliGlobali.getInstance().gethSpinner().removeCallbacks(VariabiliGlobali.getInstance().getRunSpinner());
            VariabiliGlobali.getInstance().setRunSpinner(null);

            VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Utility.getInstance().PrendeErroreDaException(e),
                    true, "looVF", false);
        }

        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                VariabiliGlobali.getInstance().gethSpinner().removeCallbacks(VariabiliGlobali.getInstance().getRunSpinner());
                VariabiliGlobali.getInstance().setRunSpinner(null);

                VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);
                mVideoView.start();
                // pDialog.dismiss();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                VariabiliGlobali.getInstance().gethSpinner().removeCallbacks(VariabiliGlobali.getInstance().getRunSpinner());
                VariabiliGlobali.getInstance().setRunSpinner(null);

                VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);
                // if (pDialog.isShowing()) {
                //     pDialog.dismiss();
                // }
                // finish();
            }
        });
    } */
}
