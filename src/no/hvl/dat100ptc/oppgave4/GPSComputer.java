package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

import no.hvl.dat100ptc.TODO;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	public double totalDistance() {

		double distance = 0;

		// TODO
		for (int i = 0; i < gpspoints.length - 1; i++) {
			double ny = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
			distance += ny;
		}
		return distance;
	}

	public double totalElevation() {

		double elevation = 0;

		// TODO
		for (int i = 0; i < gpspoints.length - 1; i++) {
			if (gpspoints[i].getElevation() < gpspoints[i + 1].getElevation()) {
				elevation += gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
			}
		}
		return elevation;
	}

	public int totalTime() {

		// TODO
		int lengde = gpspoints.length;
		int time = gpspoints[lengde - 1].getTime() - gpspoints[0].getTime();
		return time;
	}

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length - 1];

		// TODO
		for (int i = 0; i < gpspoints.length - 1; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}
		return speeds;
	}

	public double maxSpeed() {

		double maxspeed = 0;

		// TODO
		for (double speed : speeds()) {
			if (maxspeed < speed) {
				maxspeed = speed;
			}
		}
		return maxspeed;
	}

	public double averageSpeed() {

		double average = 0;

		// TODO
		average = totalDistance() / totalTime();

		return average;
	}

	// conversion factor m/s to miles per hour (mps)
	public static final double MS = 2.23;

	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met = 0;
		double speedmph = speed * MS;

		// TODO
		double[] cycling = new double[] { 4.0, 6.0, 8.0, 10.0, 12.0, 16.0 };
		double[] speedsmph = new double[] { 10.0, 12.0, 14.0, 16.0, 20.0 };
		if (speedmph < speedsmph[0]) {
			met = cycling[0];
		} else {
			met = cycling[cycling.length - 1];
		}
		for (int i = 0; i < speedsmph.length - 1; i++) {
			if (speedmph >= speedsmph[i] && speedmph < speedsmph[i + 1]) {
				met = cycling[i + 1];
			}
		}

		double hr = (double) secs / 3600;
		kcal = met * weight * hr;
		return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		double speed[] = speeds();

		// TODO

		// totalkcal=kcal(weight,totalTime(),averageSpeed());
		int t = 0;
		for (int i = 0; i < speed.length; i++) {
			t = gpspoints[i + 1].getTime() - gpspoints[i].getTime();
			totalkcal += kcal(weight, t, speed[i]);
		}

		return totalkcal;
	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		// TODO
		String format1 = "%1$-15s:";
		
		String d =  GPSUtils.formatDouble(totalDistance() / 1000);
		String e =  GPSUtils.formatDouble(totalElevation());
		String ms = GPSUtils.formatDouble(maxSpeed()*3.6);
		String as = GPSUtils.formatDouble(averageSpeed()*3.6);
		String k =  GPSUtils.formatDouble(totalKcal(WEIGHT));
		
		System.out.println("==============================================");
		System.out.println(String.format(format1, "Total Time") + GPSUtils.formatTime(totalTime()));
		System.out.println(String.format(format1, "Total distance") + d + " km");
		System.out.println(String.format(format1, "Total elevation") + e + " m");
		System.out.println(String.format(format1, "Max speed")+ms+" km/t");
		System.out.println(String.format(format1, "Average speed")+as+" km/t");
		System.out.println(String.format(format1, "Energy")+k+" kcal");
		System.out.println("==============================================");
		
	}

}
