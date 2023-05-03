import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Batoh {

    private double celkovaCenaBatoha;
    private double celkovaVahaBatoha;

    private List<Integer> zoznamVah;
    private List<Integer> zoznamcien;
    private List<Double> zoznamKoeficientov;

    private int aktualnaVahaBatoha = 0;

    private InputStream suborSVahou = ClassLoader.getSystemResourceAsStream("H4_a.txt");
    private InputStream suborSCenou = ClassLoader.getSystemResourceAsStream("H4_c.txt");
    private Scanner scannerPreVahu = new Scanner(suborSVahou);
    private Scanner scannerPreCenu = new Scanner(suborSCenou);
    private double aktualnyPocetPredmetov = 0;


    private PrintWriter printWriterPreCenu = new PrintWriter("subory\\vyslednaCena.txt");
    private PrintWriter printWriterPreVahu = new PrintWriter("subory\\vyslednaVaha.txt");

    public Batoh() throws IOException {
        this.zoznamVah = new ArrayList<>();
        this.zoznamcien = new ArrayList<>();
        this.zoznamKoeficientov = new ArrayList<>();

    }

    public void spocitajMaximalnuVahuBatoha() {


        var aktualnaVaha = 0;
        var aktualnaCena = 0;


        while (scannerPreVahu.hasNext() && scannerPreCenu.hasNext()) {
            aktualnaVaha = scannerPreVahu.nextInt();
            aktualnaCena = scannerPreCenu.nextInt();
            double koeficient = (float) aktualnaCena / aktualnaVaha;
            this.zoznamcien.add(aktualnaCena);
            this.celkovaVahaBatoha += aktualnaVaha;
            this.celkovaCenaBatoha += aktualnaCena;
            this.zoznamVah.add(aktualnaVaha);
            this.zoznamKoeficientov.add(koeficient);


            this.aktualnyPocetPredmetov++;
        }
        this.aktualnaVahaBatoha = (int) this.celkovaVahaBatoha;
        this.ziskajVysledok();

    }

    private void ziskajVysledok() {
        int index;
        for (int i = 0; i < this.zoznamKoeficientov.size(); i++) {
            index = this.zoznamKoeficientov.indexOf(Collections.min(this.zoznamKoeficientov));


            this.aktualnaVahaBatoha -= this.zoznamVah.get(index);
            this.zoznamcien.remove(index);
            this.zoznamVah.remove(index);
            this.zoznamKoeficientov.remove(index);
            this.aktualnyPocetPredmetov--;


            i--;

            //this.ziskatAktualnuVahuBatoha();
            double kapacitaBatoha = 11500;
            //  double kapacitaBatoha = 10;
            double maximalnyPocetPredmetovVBatohu = 350;
            if (this.aktualnaVahaBatoha <= kapacitaBatoha && this.aktualnyPocetPredmetov <= maximalnyPocetPredmetovVBatohu) {
                break;
            }


        }

        this.zapisDoSuboru();


    }


    private void zapisDoSuboru() {
        int celkovaVaha = 0;
        int celkovaCena = 0;
        for (int i = 0; i < this.zoznamVah.size(); i++) {

            printWriterPreVahu.println(this.zoznamVah.get(i).toString());
            printWriterPreCenu.println(this.zoznamcien.get(i).toString());


            celkovaVaha += this.zoznamVah.get(i);
            celkovaCena += this.zoznamcien.get(i);


        }
        printWriterPreCenu.close();
        printWriterPreVahu.close();
        System.out.println("Celkova cena " + celkovaCena);
        System.out.println("Celkova vaha " + celkovaVaha);

        System.out.println("Pociatocna vaha batoha " + this.celkovaVahaBatoha);
        System.out.println("Pociatocna cena batoha " + this.celkovaCenaBatoha);

    }
}
