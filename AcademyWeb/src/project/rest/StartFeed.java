package project.rest;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;

import project.business.LiveFeedBeanLocal;

@Path("/livefeed")
@EJB(name="ejb/LiveFeed", beanInterface=LiveFeedBeanLocal.class)
public class StartFeed {
	@GET
	@Produces("text/plain")
	public boolean runLiveData(@QueryParam("loop")int loop) {
		try {
			InitialContext context = new InitialContext();
			LiveFeedBeanLocal bean = (LiveFeedBeanLocal)context.lookup("java:comp/env/ejb/LiveFeed");
			
			bean.runLiveData(loop);
			
		} catch(Exception ex) {
			Logger log =  Logger.getLogger(this.getClass());
			log.error("ERROR " + ex.getMessage());
		}
		
		return true;
	}
}
