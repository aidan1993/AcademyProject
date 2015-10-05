package project.business;

import javax.ejb.Remote;

@Remote
public interface LiveFeedBeanRemote {
	void runLiveData();
}
