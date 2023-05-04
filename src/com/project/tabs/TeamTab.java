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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.database.*;
import project.models.MyModel;
import project.models.Team;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TeamTab {
	private JPanel panel;
	Connection conn  = null;
	static int teamId=-1;
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel nameLabel = new JLabel("Name:");
	JLabel budgetLabel = new JLabel("Budget:");
	
	JTextField nameTextField = new JTextField();
	JTextField budgetTextField=new JTextField();
	
	JButton addBtn = new JButton("Add");
	JButton deleteBtn = new JButton("Delete");
	JButton editBtn = new JButton("Edit");
	JButton searchBtn = new JButton("Search");
	JButton refreshBtn = new JButton("Refresh");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	
	public TeamTab() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        conn = DbConnection.getConnection();
        
        panel.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt) {
                // when the CarTab is selected, call the refresh method
                refreshTable();
            }
        });
        
        // UpPanel
        upPanel.setLayout(new GridLayout(4,1));
		upPanel.add(nameLabel);
		upPanel.add(nameTextField);
		upPanel.add(budgetLabel);
		upPanel.add(budgetTextField);
        
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
    
    public static int getTeamId() {
    	return teamId;
    }
    
    public void refreshTable() {
		try {
			ResultSet result = Team.getAllTeams(conn);
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
    	nameTextField.setText("");
    	budgetTextField.setText("");
	}
    
    class AddAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Team.insertTeam(conn, nameTextField.getText(),
						Integer.parseInt(budgetTextField.getText()));
				refreshTable();
				clearForm();
				teamId = -1;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
    
    class EditAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Team.updateTeam(conn,nameTextField.getText(), 
						Double.parseDouble(budgetTextField.getText()),teamId);
				refreshTable();
				clearForm();
				teamId=-1;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
    
    class MouseAction implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			
			int row = table.getSelectedRow();
			nameTextField.setText(table.getValueAt(row, 0).toString());
			budgetTextField.setText(table.getValueAt(row, 1).toString());
			teamId = Integer.parseInt(table.getValueAt(row, 2).toString());
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
				Team.deleteTeam(conn, teamId);
				refreshTable();
				clearForm();
				teamId= -1;
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
				ResultSet result = Team.searchTeams(conn, nameTextField.getText());
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
