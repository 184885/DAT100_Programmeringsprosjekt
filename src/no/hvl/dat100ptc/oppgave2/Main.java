package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class Main {

	
	public static void main(String[] args) {

		// TODO
		GPSPoint gps1 = new GPSPoint(1, 2.3, 4.5,6.7);
		GPSPoint gps2 = new GPSPoint(123, 4.5,5.6,7.8);
		GPSData data = new GPSData(2);
		boolean g1 = data.insertGPS(gps1);
		boolean g2 = data.insertGPS(gps2);
		data.print();
		
	}
}
