package project.models;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import project.database.DbConnection;

public class PlayersAndTeamsSearch {
		
	private JPanel panel;
	Connection conn  = null;
	
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel pNameLabel = new JLabel("Player Name:");
	JLabel tNameLabel = new JLabel("Team Name:");
	
	JTextField pNameTextField = new JTextField();
	JTextField tNameTextField=new JTextField();
	
	
	JButton searchBtn = new JButton("Search");
	
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	public PlayersAndTeamsSearch() {
		 panel = new JPanel();
	     panel.setLayout(new GridLayout(3, 1));
	     conn = DbConnection.getConnection();
	     upPanel.setLayout(new GridLayout(4,1));
			upPanel.add(pNameLabel);
			upPanel.add(pNameTextField);
			upPanel.add(tNameLabel);
		
			upPanel.add(tNameTextField);
			
	      // MidPanel
			midPanel.add(searchBtn);
			
			searchBtn.addActionListener(new SearchAction());
			  
	      // DownPanel
			myScroll.setPreferredSize(new Dimension(350, 150));
			downPanel.add(myScroll);
			refreshTable();
			table.addMouseListener(new MouseAction());
	      
	      panel.add(upPanel);
	      panel.add(midPanel);
	      panel.add(downPanel);
		}
	 public void refreshTable() {
			try {
				ResultSet result = Search.getPlayerAndTeamNames(conn , pNameTextField.getText() , tNameTextField.getText());
				
				table.setModel(new MyModel(result));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	class SearchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				ResultSet result = Search.getPlayerAndTeamNames(conn, pNameTextField.getText(),tNameTextField.getText());
				
				table.setModel(new MyModel(result));
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	class MouseAction implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			
			int row = table.getSelectedRow();
			pNameTextField.setText(table.getValueAt(row, 0).toString());
			tNameTextField.setText(table.getValueAt(row, 1).toString());
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
	  public JPanel getPanel() {
	        return panel;
	    }
	  
     
	}
