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
import javax.swing.JComboBox;

import project.database.*;
import project.models.MyModel;
import project.models.Player;
import project.models.Team;



public class PlayerTab {
	private JPanel panel;
	Connection conn  = null;
	int playerId=-1;
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel fNameLabel = new JLabel("First Name:");
	JLabel lNameLabel = new JLabel("Last Name:");
	JLabel numberLabel = new JLabel("Number:");
	JLabel teamLabel = new JLabel("TeamId:");
	JComboBox<String> teamComboBox = new JComboBox<>();
	
	JTextField fNameTextField = new JTextField();
	JTextField lNameTextField=new JTextField();
	JTextField numberTextField=new JTextField();
	JTextField teamTextField  =new JTextField();
	
	JButton addBtn = new JButton("Add");
	JButton deleteBtn = new JButton("Delete");
	JButton editBtn = new JButton("Edit");
	JButton searchBtn = new JButton("Search");
	JButton refreshBtn = new JButton("Refresh");
	
	JTable table=new JTable();
	JScrollPane myScroll=new JScrollPane(table);
	
	public PlayerTab() {
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
		upPanel.add(numberLabel);
		upPanel.add(numberTextField);
		// Add the new teamLabel and teamComboBox components
		upPanel.add(new JLabel("Team:"));
		upPanel.add(teamComboBox);
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
            ResultSet result = Player.getAllPlayers(conn);
            table.setModel(new MyModel(result));

            // Clear the combo box
            teamComboBox.removeAllItems();

            // Populate the combo box with team names
            ResultSet teamResult = Team.getAllTeams(conn);
            while (teamResult.next()) {
                String teamName = teamResult.getString("name");
                teamComboBox.addItem(teamName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void clearForm() {
    	fNameTextField.setText("");
    	lNameTextField.setText("");
    	numberTextField.setText("");
    	teamTextField.setText("");
	}
    
    class AddAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectedTeam = teamComboBox.getSelectedItem().toString();

                // Retrieve the teamId based on the selected team name
                int teamId = Team.getTeamIdByName(conn, selectedTeam);

                if (teamId != 0) {
                    Player.insertPlayers(conn, fNameTextField.getText(), lNameTextField.getText(),
                            Integer.parseInt(numberTextField.getText()), teamId);
                    refreshTable();
                    clearForm();
                    playerId = -1;
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    class EditAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectedTeam = teamComboBox.getSelectedItem().toString();

                // Retrieve the teamId based on the selected team name
                int teamId = Team.getTeamIdByName(conn, selectedTeam);

                if (teamId != 0) {
                    Player.updatePlayers(conn, fNameTextField.getText(), lNameTextField.getText(),
                            Integer.parseInt(numberTextField.getText()), teamId, playerId);
                    refreshTable();
                    clearForm();
                    playerId = -1;
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    class MouseAction implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			
			int row = table.getSelectedRow();
			playerId = Integer.parseInt(table.getValueAt(row, 0).toString());
			fNameTextField.setText(table.getValueAt(row, 1).toString());
			lNameTextField.setText(table.getValueAt(row, 2).toString());
			numberTextField.setText(table.getValueAt(row, 3).toString());
			teamTextField.setText(table.getValueAt(row, 4).toString());
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
				Player.deletePlayers(conn, playerId);;
				refreshTable();
				clearForm();
				playerId=-1;
				
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
				ResultSet result = Player.searchPlayers(conn, fNameTextField.getText());
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
