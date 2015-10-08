package project.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;

import project.business.LiveFeedBeanLocal;

@Stateless
@Path("/livefeed")
@EJB(name="ejb/LiveFeed", beanInterface=LiveFeedBeanLocal.class)
public class StartFeed {
	
	private InitialContext context;
	private LiveFeedBeanLocal bean = null;
	private long lastCall = 0;
	
	@GET
	public void runLiveData(@QueryParam("loop")int loop) {
		try {
			
			if((System.currentTimeMillis() - lastCall) > 9000) {
				lastCall = System.currentTimeMillis();
				if(loop == 0) {
					context = new InitialContext();
					bean = (LiveFeedBeanLocal)context.lookup("java:comp/env/ejb/LiveFeed");
				}
				
				bean.runLiveData();
			}
		} catch(Exception ex) {
			Logger log =  Logger.getLogger(this.getClass());
			log.error("ERROR " + ex.getMessage());
		}
	}
}
