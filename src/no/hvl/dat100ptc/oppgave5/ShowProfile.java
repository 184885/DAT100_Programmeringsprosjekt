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

		// double max = MAXBARHEIGHT / GPSUtils.findMax(e);
		setColor(0, 0, 255);
		for (int i = 0; i < gpspoints.length; i++) {
			y = ybase;
			if (e[i] <= 0) {
				/*
				 * for (int j = i - 1; j > 0; j--) {
				 * 
				 * if (e[j] > 0) { y = ybase-(int) (e[j] * max); j = 0; }
				 * 
				 * }
				 */

			} else {
				y = ybase - (int) e[i];
			}

			drawLine(x, ybase, x, y);
			x++;
			// drawLine(x,ybase,x,y);
			x++;
		}
		// System.out.print(max+" Hello "+ e[9]);
		// y = MARGIN+(int)(e[9]*max);
		// drawLine(x,ybase,x,y);
	}

}
