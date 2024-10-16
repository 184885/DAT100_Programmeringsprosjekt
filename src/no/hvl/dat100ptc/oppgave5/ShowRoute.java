package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import no.hvl.dat100ptc.TODO;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

		xstep = scale(MAPXSIZE, minlon, maxlon);
		ystep = scale(MAPYSIZE, minlat, maxlat);

		showRouteMap(MARGIN + MAPYSIZE);

		replayRoute(MARGIN + MAPYSIZE);

		showStatistics();
	}

	public double scale(int maxsize, double minval, double maxval) {

		double step = maxsize / (Math.abs(maxval - minval));

		return step;
	}

	public void showRouteMap(int ybase) {

		// TODO

		double mlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		double mlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		for (int i = 0; i < gpspoints.length; i++) {
			setColor(1, 150, 32);
			double lat = ybase - (ystep * Math.abs(mlat - GPSUtils.getLatitudes(gpspoints)[i]));
			double lon = (xstep * Math.abs(mlon - GPSUtils.getLongitudes(gpspoints)[i]));

			if (i != 0 && gpspoints[i].getElevation() > gpspoints[i - 1].getElevation()) {
				setColor(150, 32, 1);
			} else if (i != 0 && gpspoints[i].getElevation() == gpspoints[i - 1].getElevation()) {
				setColor(32, 1, 150);

			}
			if (i < gpspoints.length - 1) {
				double lat2 = ybase - (ystep * Math.abs(mlat - GPSUtils.getLatitudes(gpspoints)[i + 1]));
				double lon2 = (xstep * Math.abs(mlon - GPSUtils.getLongitudes(gpspoints)[i + 1]));
				drawLine(MARGIN + (int) lon, (int) lat, MARGIN + (int) lon2, (int) lat2);
			}
			fillCircle(MARGIN + (int) lon, (int) lat, 3);

		}
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0, 0, 0);
		setFont("Courier", 12);

		// TODO

		String[] display = gpscomputer.stringStatistics();
		/*
		 * Teksten ser ikkje helt ut som eg ynskjer, så tenker å splitte Stringane med
		 * kolon Så reformatere dei med større mellomrom fremfor som forhåpentligvis
		 * fikser problemet.
		 */

		drawString(display[1], TEXTDISTANCE, TEXTDISTANCE);
		drawString(display[2], TEXTDISTANCE, TEXTDISTANCE * 2);
		drawString(display[3], TEXTDISTANCE, TEXTDISTANCE * 3);
		drawString(display[4], TEXTDISTANCE, TEXTDISTANCE * 4);
		drawString(display[5], TEXTDISTANCE, TEXTDISTANCE * 5);
		drawString(display[6], TEXTDISTANCE, TEXTDISTANCE * 6);
	}

	public void replayRoute(int ybase) {

		// TODO

		double mlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		double mlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double lat = ybase - (ystep * Math.abs(mlat - GPSUtils.getLatitudes(gpspoints)[0]));
		double lon = MARGIN + (xstep * Math.abs(mlon - GPSUtils.getLongitudes(gpspoints)[0]));
		setColor(0, 0, 255);
		int replay = fillCircle((int) lon, (int) lat, 4);

		for (int i = 0; i < gpspoints.length; i++) {
			setSpeed(2);
			lat = ybase - (ystep * Math.abs(mlat - GPSUtils.getLatitudes(gpspoints)[i]));
			lon = MARGIN + (xstep * Math.abs(mlon - GPSUtils.getLongitudes(gpspoints)[i]));
			moveCircle(replay, (int) lon, (int) lat);
		}
	}

}
