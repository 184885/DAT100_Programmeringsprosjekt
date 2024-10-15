package no.hvl.dat100ptc.oppgave5;

import java.io.Console;
import java.util.Scanner;
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

		for (int i = 0; i < gpspoints.length; i++) {
			double lat = gpspoints[i].getLatitude() * xstep;
			double lon = gpspoints[i].getLongitude() * ystep;
			drawCircle((int) lat, (int) lon, 10);
		}

	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0, 0, 0);
		setFont("Courier", 12);

		// TODO
		Console console = System.console();

		if(console == null) {
			System.out.println("Console is not available to current JVM process");
			return;
		}
		
		
		
		
		//Scanner sc = new Scanner(System.console().reader());
		gpscomputer.displayStatistics();
		//String display = System.console().readLine();//sc.nextLine();
		
		
		//sc.close();
		//drawString(display, TEXTDISTANCE, TEXTDISTANCE);

	}

	public void replayRoute(int ybase) {

		// TODO
		int replay = drawCircle((int) (gpspoints[0].getLatitude() * xstep), (int) (gpspoints[0].getLongitude() * ystep),
				10);
		setSpeed(5);
		for (int i = 0; i < gpspoints.length; i++) {
			double lat = gpspoints[i].getLatitude() * xstep;
			double lon = gpspoints[i].getLongitude() * ystep;
			moveCircle(replay, (int) lat, (int) lon);
		}
	}

}
