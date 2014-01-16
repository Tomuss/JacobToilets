import org.cybergarage.upnp.device.InvalidDescriptionException;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JacobDevice dev = new JacobDevice();
		} catch (InvalidDescriptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
