package project.main;

import java.util.Scanner;

import project.data.DataAccess;
import project.data.LiveFeed;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DataAccess.clearDatabase();
		LiveFeed.getLiveFeed(1);
	}

}
