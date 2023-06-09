package com.uni.project;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.project.tabs.CoachTab;
import com.project.tabs.PlayerTab;
import com.project.tabs.TeamTab;

import project.models.PlayersAndTeamsSearch;

import javax.swing.JTabbedPane;

public class Mainframe extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panelTeams = new JPanel();
	JPanel panelPlayers = new JPanel();
	JPanel panelCoaches = new JPanel();
	JPanel panelSearch = new JPanel();
	
	JTabbedPane tab = new JTabbedPane();
	
	public Mainframe() {
		this.setSize(500, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		PlayerTab playerTab = new PlayerTab();
		TeamTab teamTab = new TeamTab();
		CoachTab coachTab = new CoachTab();
		PlayersAndTeamsSearch playerAndTeams = new PlayersAndTeamsSearch();
        
        tab.add(playerTab.getPanel(), "Players");
        
        tab.add(teamTab.getPanel(), "Teams");
        tab.add(coachTab.getPanel(), "Coaches");
        tab.add(playerAndTeams.getPanel(),"Search");
        
        
		
		getContentPane().add(tab, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

}
