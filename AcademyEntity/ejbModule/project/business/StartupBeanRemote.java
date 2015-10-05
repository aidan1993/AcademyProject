package project.business;

import javax.ejb.Remote;

@Remote
public interface StartupBeanRemote {
	void atStartup();
}
