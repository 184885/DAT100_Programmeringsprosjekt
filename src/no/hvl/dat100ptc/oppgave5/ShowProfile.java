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
		int t0 = gpspoints[0].getTime();
		int sfactor = Integer.parseInt(getText("Skalering: \n(1:input)"));
		// double max = MAXBARHEIGHT / GPSUtils.findMax(e);

		for (int i = 0; i < gpspoints.length; i++) {
			setColor(0, 255, 0);
			y = ybase - (int) e[i];
			if (i != 0 && e[i] > e[i - 1]) {
				setColor(255, 32, 1);
			} else if (i != 0 && e[i] == e[i - 1]) {
				setColor(32, 1, 255);
			}
			drawLine(x, ybase, x, y);
			x++;
			drawLine(x, ybase, x, y);
			// drawLine(x,ybase,x,y);
			x += 2;
			if (i < gpspoints.length - 1) {
				int t1 = gpspoints[i + 1].getTime();
				pause((t1 - t0) * 1000 / sfactor);
				t0 = t1;
			}

		}
		setColor(0, 0, 0);
		// drawString("TEKST",MARGIN,ybase+20);
	}

}
