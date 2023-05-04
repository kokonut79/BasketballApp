package com.project.tabs;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import project.models.Coach;
import project.models.MyModel;



public class CoachTab {
	private JPanel panel;
	Connection conn  = null;
	int coachId=-1;
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel fNameLabel = new JLabel("First Name:");
	JLabel lNameLabel = new JLabel("Last Name:");
	JLabel teamLabel = new JLabel("TeamId:");
	
	JTextField fNameTextField = new JTextField();
	JTextField lNameTextField=new JTextField();
	JTextField teamTextField  =new JTextField();
	
	JButton addBtn = new JButton("Add");
	JButton deleteBtn = new JButton("Delete");
	JButton editBtn = new JButton("Edit");
	JButton searchBtn = new JButton("Search");
	JButton refreshBtn = new JButton("Refresh");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	
    public CoachTab() {
		
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        conn = DbConnection.getConnection();
        
        panel.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                // when the PlayerTab is selected, call the refresh method
                refreshTable();
            }
        });
        
        // UpPanel
        upPanel.setLayout(new GridLayout(6,1));
		upPanel.add(fNameLabel);
		upPanel.add(fNameTextField);
		upPanel.add(lNameLabel);
		upPanel.add(lNameTextField);
		upPanel.add(teamLabel);
		upPanel.add(teamTextField);
        // MidPanel
		midPanel.add(addBtn);
		midPanel.add(deleteBtn);
		midPanel.add(editBtn);
		midPanel.add(searchBtn);
		midPanel.add(refreshBtn);
		
		addBtn.addActionListener(new AddAction());
		deleteBtn.addActionListener(new DeleteAction());
		editBtn.addActionListener(new EditAction());
		searchBtn.addActionListener(new SearchAction());
		refreshBtn.addActionListener(new RefreshAction());
        
        // DownPanel
		myScroll.setPreferredSize(new Dimension(350, 150));
		downPanel.add(myScroll);
		refreshTable();
		table.addMouseListener(new MouseAction());
        
        panel.add(upPanel);
        panel.add(midPanel);
        panel.add(downPanel);  
    }

    public JPanel getPanel() {
        return panel;
    }
    
    public void refreshTable() {
    	try {
			ResultSet result = Coach.getAllCoaches(conn);
			table.setModel(new MyModel(result));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public void clearForm() {
    	fNameTextField.setText("");
    	lNameTextField.setText("");
    	teamTextField.setText("");
	}
    
    class AddAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int teamId= TeamTab.getTeamId();
				
				if(teamId != 0 ) {
					Coach.insertCoaches(conn, fNameTextField.getText(), lNameTextField.getText(), 
							Integer.parseInt(teamTextField.getText()));
				System.err.println("ebasi mamata ");
				refreshTable();
				System.err.println("ne sym gore ");
				clearForm();
				coachId = -1;
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
    
    class EditAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int teamId = TeamTab.getTeamId();
				
				if (teamId < 0) {
					return;
				}
				
				Coach.updateCoaches(conn, coachId, fNameTextField.getText(),
						lNameTextField.getText(),
						Integer.parseInt(teamTextField.getText()));
				refreshTable();
				clearForm();
				coachId=-1;
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
    
    class MouseAction implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			
			int row = table.getSelectedRow();
			coachId = Integer.parseInt(table.getValueAt(row, 0).toString());
			fNameTextField.setText(table.getValueAt(row, 1).toString());
			lNameTextField.setText(table.getValueAt(row, 2).toString());
			teamTextField.setText(table.getValueAt(row, 3).toString());
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
   
    class DeleteAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Coach.deleteCoaches(conn, coachId);
				refreshTable();
				clearForm();
				coachId=-1;
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
    
    class SearchAction implements ActionListener{
			@Override
	    	public void actionPerformed(ActionEvent e) {
				try {
					ResultSet result = Coach.searchCoaches(conn, fNameTextField.getText());
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
    
    
    class RefreshAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshTable();
		}
	}
}
