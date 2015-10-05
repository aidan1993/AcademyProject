package project.business;

import javax.ejb.Local;

@Local
public interface StartupBeanLocal {
	void atStartup();
}
