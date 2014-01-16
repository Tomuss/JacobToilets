import java.util.Calendar;
import java.util.Date;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Argument;
import org.cybergarage.upnp.ArgumentList;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.StateVariable;
import org.cybergarage.upnp.control.ActionListener;
import org.cybergarage.upnp.control.QueryListener;
import org.cybergarage.upnp.device.InvalidDescriptionException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


public class JacobDevice extends Device implements ActionListener, QueryListener{
	private final static String DESCRIPTION_FILE_NAME = "description/description.xml";
	private final static String CONSUMER_KEY = "pXsjbUm0umoStckxfIzYzg"; 
	private final static String CONSUMER_SECRET = "LNxTvfcl74oaPiTBQ4IQy8PFW1Sd010luM3ZVC62WM";   
	private final static String ACCESS_TOKEN = "2294426413-TLTGLgfEnQryApc49qR4HlnkvGEAqjU8LaDWF0A"; 
	private final static String ACCESS_TOKEN_SECRET = "isNo7MRnmZEt7cwYLL6NNWQILMghIKixPLS3GQewsQyin";
	
	private Twitter twit;
	
	public JacobDevice() throws InvalidDescriptionException{
		super(DESCRIPTION_FILE_NAME);
		
		Action startingPooPoo = getAction("StartingPooPoo");
		startingPooPoo.setActionListener(this);
		
		Action pooPooOver = getAction("PooPooOver");
		pooPooOver.setActionListener(this);
		
		StateVariable pooPooType = getStateVariable("PooPooType");
		pooPooType.setQueryListener(this);
		
		twit = new TwitterFactory().getInstance();
		twit.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		AccessToken aToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		twit.setOAuthAccessToken(aToken);
		
		start();
	}

	@Override
	public boolean queryControlReceived(StateVariable stateVar) {
		System.out.println(stateVar+" receive");
		return false;
	}

	@Override
	public boolean actionControlReceived(Action action) {
		String actionName = action.getName();
		ArgumentList argList = action.getArgumentList();
		
		if(actionName.equals("StartingPooPoo")){
			System.out.println("StartPooPoo Receive");
			try {
				Date t = Calendar.getInstance().getTime();
				twit.updateStatus("Les toilettes sont occupées le "+t.toString()+" #startingPooPoo");
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}else if(actionName.equals("PooPooOver")){
			System.out.println("PooPooOver Receive");
			Argument result = argList.getArgument("PooPooType");
			result.setValue(0);
			
			try {
				Date t = Calendar.getInstance().getTime();
				twit.updateStatus("J'ai fini mon caca, les toilettes sont libres le "+t.toString()+
						". C'est un magnifique perfect! #flushingPooPoo");
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
}
