package project.business;

import javax.ejb.Local;

@Local
public interface LiveFeedBeanLocal {
	void runLiveData();
}
