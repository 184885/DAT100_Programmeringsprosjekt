package no.hvl.dat100ptc.oppgave1;

public class Main {

	public static void main(String[] args) {

		// TODO
		GPSPoint ny1 = new GPSPoint(1, 2.0, 3.0, 5.0);
		System.out.println(ny1.getTime());
		ny1.setTime(2);
		System.out.println(ny1.toString());
	}

}
