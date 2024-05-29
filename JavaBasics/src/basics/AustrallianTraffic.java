package basics;

public class AustrallianTraffic implements CentralTrafficInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CentralTrafficInterface c = new AustrallianTraffic();
		c.greenGo();
		c.redStop();
		c.flashYellowWait();
		//c.walkOnSysbol();  //throws error as c is an object referring to CentralTrafficInterface methods but walkOnSymbol belongs to AustrallianTraffic class
		AustrallianTraffic a = new AustrallianTraffic();
		a.walkOnSymbol();
	}

	@Override
	public void greenGo() {
		// TODO Auto-generated method stub
		System.out.println("greenGo implementation");
	}

	@Override
	public void redStop() {
		// TODO Auto-generated method stub
		System.out.println("redStop implementation");
	}

	@Override
	public void flashYellowWait() {
		// TODO Auto-generated method stub
		System.out.println("flashYellowWait implementation");
	}
	
	public void walkOnSymbol() {
		// TODO Auto-generated method stub
		System.out.println("walkOnSysmbol implementation");
	}

}
