package no.hvl.dat100ptc.oppgave5;

import no.hvl.dat100ptc.TODO;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import javax.swing.JOptionPane;

public class ShowProfile extends EasyGraphics {

	private static final int MARGIN = 50; // margin on the sides

	private static final int MAXBARHEIGHT = 500; // assume no height above 500 meters

	private GPSPoint[] gpspoints;

	public ShowProfile() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn (uten .csv): ");
		GPSComputer gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length; // number of data points

		makeWindow("Height profile", 2 * MARGIN + 3 * N, 2 * MARGIN + MAXBARHEIGHT);

		// top margin + height of drawing area
		showHeightProfile(MARGIN + MAXBARHEIGHT);
	}

	public void showHeightProfile(int ybase) {

		int x = MARGIN; // første høyde skal tegnes ved MARGIN
		int y;

		// TODO
		double[] e = new double[gpspoints.length];
		for (int i = 0; i < e.length; i++) {
			e[i] = gpspoints[i].getElevation();
		}
		double maxE = GPSUtils.findMax(e);
		double ystep = (MAXBARHEIGHT) / maxE;
		// Drawing x/y-axis
		int xAxis = MARGIN - 2;
		int xend = MARGIN + gpspoints.length * 3 - 1;
		drawLine(xAxis, ybase, xend, ybase);// x-akse
		drawLine(xAxis, MARGIN, xAxis, ybase);// y-akse

		setColor(0, 0, 0);
		String txt = "moh \n(meter over havet)";
		int ytxt = drawString(txt, xAxis - txt.length(), MARGIN - 5);

		String scale = getText("Skalering høyde (y/n):"); // Velger om ein vil skalere at max høyde blir øverst på
															// grafen
		boolean sc = false;
		switch (scale) {
		case "y", "yes", "j", "ja", "1":
			sc = true;
			break;
		}
		if (sc == true) {
			moveString(ytxt, xAxis - txt.length(), MARGIN - 20 - (int) (2.5 * ystep));
			drawLine(xAxis, MARGIN - (int) (2.5 * ystep), xAxis, MARGIN);
		}

		for (int i = 0; i < MAXBARHEIGHT / (int) (10 * ystep) + 1; i++) {
			if (i % 10 == 0) {
				setColor(100, 100, 100);
			} else {
				setColor(150, 150, 150);
			}
			int yHeight = ybase - i * 10;
			if (sc == true) {
				yHeight = ybase - (int) (i * 10 * ystep);
			}

			drawLine(xAxis, yHeight, xend, yHeight);
		}

		int sfactor = 1000;
		int t0 = gpspoints[0].getTime();
		String sfac = getText("Skalering tid: \n(1:input)");
		switch (sfac.length()) {
		case 1, 2, 3, 4, 5:
			for (int j = 1; j < 10; j++) {
				String num = "" + j;
				if (sfac.contains(num)) {
					sfactor = Integer.parseInt(sfac);
				}
			}
			break;
		}

		for (int i = 0; i < gpspoints.length; i++) {
			setColor(0, 255, 0);
			y = ybase;
			if (e[i] > 0) { // sørger for at negative verdier ikkje havner under grafen
				y = ybase - (int) e[i];
				if (sc == true) {
					y = ybase - (int) (e[i] * ystep);
				}
			}
			if (i != 0 && e[i] > e[i - 1]) {
				setColor(255, 32, 1);
			} else if (i != 0 && e[i] == e[i - 1]) {
				setColor(32, 1, 255);
			}
			drawLine(x, ybase, x, y);
			x++;
			drawLine(x, ybase, x, y);
			x += 2;

			if (i < gpspoints.length - 1) {
				int t1 = gpspoints[i + 1].getTime();
				pause((t1 - t0) * 1000 / sfactor);
				t0 = t1;
			}

		}

		// Tekst til y-aksen (dette skal vere høgde i moh.)
		/*
		 * Tenkte å tegne inn x- og y-akser. Finner så høgaste punktet og teikner linjer
		 * frå nærmaste 5m/10m og heilt ned til x-aksen.
		 * 
		 * Lurer samtidig på å skalere heile grafen så ein den ikkje blir verande heilt
		 * nede i vinduet, men dette tenker eg må gjøres etterpå då omrekninga må
		 * gjørast på høgdemåla også
		 */

	}

}
