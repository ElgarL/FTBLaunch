package net.ftb.gui.dialogs;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.ftb.data.ModPack;
import net.ftb.data.TexturePack;
import net.ftb.gui.LaunchFrame;
import net.ftb.gui.panes.TexturepackPane;


public class TexturePackFilterDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 502164632752311498L;
	
	private JPanel panel = new JPanel();
	private JLabel compatibleLbl = new JLabel("Compatible Pack:"), resolutionLbl = new JLabel("Mod Pack Resolution:");
	private JComboBox compatibleBox, resolutionBox;
	private JButton applyButton = new JButton("Apply Filter"), cancelButton = new JButton("Cancel"), searchButton = new JButton("Search Packs");

	private TexturepackPane instance;
	
	public TexturePackFilterDialog(final TexturepackPane instance) {
		super(LaunchFrame.getInstance(), true);
		
		this.instance = instance;
		
		setupGui();
		
		// TODO: Overhaul Filter dialog towards texture packs
		// Because more than likely ftb won't have a texture pack, and there is no server versions.
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				instance.compatible = (String)compatibleBox.getSelectedItem();
				instance.resolution = (String)resolutionBox.getSelectedItem();
				instance.updateFilter();
				setVisible(false);
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SearchDialog sd = new SearchDialog(instance);
				sd.setVisible(true);
			}
		});
	}

	private void setupGui() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/logo_ftb.png")));
		setTitle("Filter");
		
		int textures = TexturePack.getTexturePackArray().size();
		
		ArrayList<String> comp = new ArrayList<String>();
		comp.add("All");
		for(int i = 0; i < textures; i++) {
			String[] s = TexturePack.getTexturePack(i).getCompatible();
			for(int j = 0; j < s.length; j++) {
				if(!comp.contains(ModPack.getPack(s[j].trim()).getName())) {
					comp.add(ModPack.getPack(s[j].trim()).getName());
				}
			}
		}
		compatibleBox = new JComboBox(comp.toArray(new String[]{}));
		
		ArrayList<String> res = new ArrayList<String>();
		res.add("All");
		for(int i = 0; i < textures; i++) {
			if(!res.contains(TexturePack.getTexturePack(i).getResolution())) {
				res.add(TexturePack.getTexturePack(i).getResolution());
			}
		}
		resolutionBox = new JComboBox(res.toArray(new String[]{}));
		
		compatibleBox.setSelectedItem(instance.compatible);
		resolutionBox.setSelectedItem(instance.resolution);
		
		setBounds(350, 300, 280, 181);
		setResizable(false);
		panel.setBounds(0, 0, 230, 140);
		panel.setLayout(null);
		setContentPane(panel);
		compatibleLbl.setBounds(10, 10, 144, 30);
		compatibleBox.setBounds(146, 10, 118, 30);
		resolutionBox.setBounds(146, 40, 118, 30);
		resolutionLbl.setBounds(10, 40, 144, 30);
		applyButton.setBounds(10, 117, 254, 25);
		searchButton.setBounds(10, 81, 118, 25);
		getRootPane().setDefaultButton(applyButton);
		cancelButton.setBounds(146, 81, 118, 25);
		panel.add(compatibleLbl);
		panel.add(resolutionLbl);
		panel.add(compatibleBox);
		panel.add(resolutionBox);
		panel.add(applyButton);
		panel.add(cancelButton);
		panel.add(searchButton);
	}
}
