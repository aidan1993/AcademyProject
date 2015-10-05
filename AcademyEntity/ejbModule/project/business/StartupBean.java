package project.business;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;

import org.jboss.logging.Logger;

@Startup
@Singleton
@Remote(StartupBeanRemote.class)
@Local(StartupBeanLocal.class)
@EJB(name="ejb/LiveFeed", beanInterface=LiveFeedBeanLocal.class)
public class StartupBean implements StartupBeanLocal, StartupBeanRemote {
	
	@PostConstruct
	public void atStartup() {
		Logger log =  Logger.getLogger(this.getClass());
		
		try {
			InitialContext context = new InitialContext();
			LiveFeedBeanLocal bean = (LiveFeedBeanLocal)context.lookup("java:comp/env/ejb/LiveFeed");
			
			bean.runLiveData();
			
		} catch(Exception ex) {
			log.error("ERROR " + ex.getMessage());
		}
		
	}
}
