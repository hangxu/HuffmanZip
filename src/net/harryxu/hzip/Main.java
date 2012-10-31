package net.harryxu.hzip;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ListSelectionModel;

public class Main extends JFrame {
	private JTextField outField;
	private File outPath;
	private File[] compressFiles;
	private JTextField DeOutField;
	private JTextField DeInField;
	private File diFile;
	private File doPath;
	private JList list;

	public Main() {
		setTitle("Hzip -- The Best Huffman Zip Program");
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JTabbedPane tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Compress", null, panel, null);

		JButton btnAddFile = new JButton("Add File");
		btnAddFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile();
			}
		});

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove();
			}
		});

		JButton btnCompress = new JButton("Compress");
		btnCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				compress();
			}
		});
		btnCompress.setFont(new Font("Tahoma", Font.BOLD, 11));

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Input File List",
				TitledBorder.LEFT, TitledBorder.TOP, null, null));
		JScrollPane scrollpane = new JScrollPane(list);
		outField = new JTextField();
		outField.setEditable(false);
		outField.setColumns(10);

		JButton btnOutPath = new JButton("Out Path");
		btnOutPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseOutPath();
			}
		});

		JButton btnReset_1 = new JButton("Reset");
		btnReset_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGap(38)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.TRAILING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		btnAddFile)
																.addGap(18)
																.addComponent(
																		btnRemove)
																.addPreferredGap(
																		ComponentPlacement.RELATED,
																		78,
																		Short.MAX_VALUE)
																.addComponent(
																		btnReset_1)
																.addGap(18)
																.addComponent(
																		btnCompress))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.TRAILING)
																				.addComponent(
																						scrollpane,
																						GroupLayout.PREFERRED_SIZE,
																						406,
																						GroupLayout.PREFERRED_SIZE)
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addComponent(
																										outField,
																										GroupLayout.DEFAULT_SIZE,
																										325,
																										Short.MAX_VALUE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										btnOutPath)))
																.addPreferredGap(
																		ComponentPlacement.RELATED)))
								.addGap(35)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap(14, Short.MAX_VALUE)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														outField,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnOutPath))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(scrollpane,
										GroupLayout.PREFERRED_SIZE, 181,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(btnAddFile)
												.addComponent(btnRemove)
												.addComponent(btnCompress)
												.addComponent(btnReset_1))
								.addContainerGap()));
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Decompress", null, panel_1, null);

		DeOutField = new JTextField();
		DeOutField.setEditable(false);
		DeOutField.setColumns(10);

		JButton btnOutPath_1 = new JButton("Choose");
		btnOutPath_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseOutput();
			}
		});

		JLabel lblChooseCompressFiletarhz = new JLabel(
				"Choose Compress File(.tar.hz)");

		DeInField = new JTextField();
		DeInField.setEditable(false);
		DeInField.setColumns(10);

		JButton btnChoose = new JButton("Choose");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseInput();
			}
		});

		JLabel lblChooseOutputPath = new JLabel("Choose Output Path");

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});

		JButton btnDecompress = new JButton("Decompress");
		btnDecompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				decompress();
			}
		});
		btnDecompress.setFont(new Font("Tahoma", Font.BOLD, 11));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addGap(40)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblChooseCompressFiletarhz)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel_1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblChooseOutputPath)
																						.addGroup(
																								Alignment.TRAILING,
																								gl_panel_1
																										.createSequentialGroup()
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.TRAILING)
																														.addComponent(
																																DeInField,
																																Alignment.LEADING,
																																GroupLayout.DEFAULT_SIZE,
																																304,
																																Short.MAX_VALUE)
																														.addComponent(
																																DeOutField,
																																GroupLayout.DEFAULT_SIZE,
																																304,
																																Short.MAX_VALUE))
																										.addGap(18)
																										.addGroup(
																												gl_panel_1
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																btnChoose)
																														.addComponent(
																																btnOutPath_1,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																Short.MAX_VALUE)))
																						.addGroup(
																								Alignment.TRAILING,
																								gl_panel_1
																										.createSequentialGroup()
																										.addComponent(
																												btnReset)
																										.addPreferredGap(
																												ComponentPlacement.RELATED,
																												227,
																												Short.MAX_VALUE)
																										.addComponent(
																												btnDecompress)))
																		.addGap(48)))
										.addGap(0)));
		gl_panel_1
				.setVerticalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												lblChooseCompressFiletarhz)
										.addGap(18)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																DeInField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(btnChoose))
										.addGap(36)
										.addComponent(lblChooseOutputPath)
										.addGap(18)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																DeOutField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnOutPath_1))
										.addGap(49)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																btnDecompress)
														.addComponent(btnReset))
										.addGap(52)));
		panel_1.setLayout(gl_panel_1);
	}

	private void chooseOutPath() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setMultiSelectionEnabled(false);
		int result = fc.showOpenDialog(this);
		if (result == JFileChooser.CANCEL_OPTION)
			return;
		outPath = fc.getSelectedFile();
		if (outPath == null) {
			JOptionPane.showMessageDialog(this, "Invalid File Name",
					"Invalid File Name", JOptionPane.ERROR_MESSAGE);
			return;
		}
		outField.setText(outPath.getAbsolutePath());
	}

	private void chooseFile() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setMultiSelectionEnabled(true);
		int result = fc.showOpenDialog(this);
		if (result == JFileChooser.CANCEL_OPTION)
			return;
		if (compressFiles == null)
			compressFiles = fc.getSelectedFiles();
		else {
			File[] files = fc.getSelectedFiles();
			File[] tmp = compressFiles;
			compressFiles = new File[tmp.length + files.length];
			System.arraycopy(tmp, 0, compressFiles, 0, tmp.length);
			System.arraycopy(files, 0, compressFiles, tmp.length, files.length);
		}
		DefaultListModel model = new DefaultListModel();
		for (int i = 0; i < compressFiles.length; i++)
			model.add(i, (compressFiles[i].isDirectory() ? "DIR " : "FILE ")
					+ compressFiles[i].getAbsolutePath());
		list.setModel(model);
	}
	private void chooseInput() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Hzip file","hz"));
		int result = fc.showOpenDialog(this);
		if (result == JFileChooser.CANCEL_OPTION)
			return;
			diFile= fc.getSelectedFile();
			this.DeInField.setText(diFile.getAbsolutePath());
			if(doPath ==null){
				doPath = diFile.getAbsoluteFile().getParentFile();
				this.DeOutField.setText(doPath.getAbsolutePath());
			}
	}
	private void chooseOutput() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setMultiSelectionEnabled(false);
		int result = fc.showOpenDialog(this);
		if (result == JFileChooser.CANCEL_OPTION)
			return;
		doPath = fc.getSelectedFile();
		this.DeOutField.setText(doPath.getAbsolutePath());
	}
	private void reset() {
		Object[] options = { "OK", "CANCEL" };
		int result = JOptionPane.showOptionDialog(null,
				"Are you sure to reset all text fields?", "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		if (result == JOptionPane.OK_OPTION) {
			this.DeInField.setText("");
			this.DeOutField.setText("");
			this.outField.setText("");
			this.outPath = null;
			this.compressFiles = null;
			this.list.setModel(new DefaultListModel());
		}
	}

	private void remove() {
		if (compressFiles == null)
			return;
		int n = list.getSelectedIndex();
		File[] tmp = compressFiles;
		compressFiles = new File[tmp.length - 1];
		if (n == 0)
			System.arraycopy(tmp, 0, compressFiles, 1, tmp.length - 1);
		else if (n == tmp.length - 1)
			System.arraycopy(tmp, 0, compressFiles, 0, tmp.length - 1);
		else {
			System.arraycopy(tmp, 0, compressFiles, 0, n);
			System.arraycopy(tmp, n + 1, compressFiles, n, tmp.length - n - 1);
		}
		DefaultListModel model = new DefaultListModel();
		for (int i = 0; i < compressFiles.length; i++)
			model.add(i, (compressFiles[i].isDirectory() ? "DIR " : "FILE ")
					+ compressFiles[i].getAbsolutePath());
		list.setModel(model);
	}

	private void decompress() {
		if (diFile==null) {
			JOptionPane.showMessageDialog(this,
					"Please select a compress file",
					"None file selected", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (doPath == null) {
			Object[] options = { "OK", "CANCEL" };
			String default_path = diFile.getAbsolutePath();
			int result = JOptionPane.showOptionDialog(
					null,
					"Output path has not selected. Set it as "
							+ default_path.substring(0, default_path
									.lastIndexOf(File.separatorChar)) + " ?",
					"Warning", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			if (result != JOptionPane.OK_OPTION)
				return;
			doPath = new File(default_path.substring(0, default_path
									.lastIndexOf(File.separatorChar)));
			this.DeOutField.setText(doPath.getAbsolutePath());
		}
		if(Driver.Decompress(diFile, doPath)==false)
			JOptionPane.showMessageDialog(this,
					"Decompress Fail",
					"Fail!", JOptionPane.ERROR_MESSAGE);
		
			
		else{
			JOptionPane.showMessageDialog(this,
					"The file successfully be unpacked, and the output file is in "+doPath.getAbsolutePath(),
					"Success!", JOptionPane.INFORMATION_MESSAGE);
			try {
				java.awt.Desktop.getDesktop().open(doPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
	private void compress() {
		if (compressFiles == null || compressFiles.length == 0) {
			JOptionPane.showMessageDialog(this,
					"Please select one or more files to compress",
					"None file selected", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outPath == null) {
			Object[] options = { "OK", "CANCEL" };
			String default_path = compressFiles[0].getAbsolutePath();
			int result = JOptionPane.showOptionDialog(
					null,
					"Output path has not selected. Set it as "
							+ default_path.substring(0, default_path
									.lastIndexOf(File.separatorChar)) + " ?",
					"Warning", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			if (result != JOptionPane.OK_OPTION)
				return;
			outPath = new File(default_path.substring(0, default_path
									.lastIndexOf(File.separatorChar)));
			outField.setText(outPath.getAbsolutePath());
		}
		String name ="";
		if(this.compressFiles.length==1){
			name = compressFiles[0].getName().substring(0, compressFiles[0].getName().lastIndexOf("."));
		}else{
			name = compressFiles[0].getName();
			String basePath = compressFiles[0].getAbsolutePath();
			basePath = basePath.substring(0, basePath.length() - name.length()-1);
			name = basePath.substring(basePath.lastIndexOf(File.separatorChar)+1,basePath.length());
		}
		String output_path = Driver.Compress(compressFiles, outPath.getAbsolutePath(), name);
		if(output_path==null)
			JOptionPane.showMessageDialog(this,
					"Compress Fail",
					"Fail!", JOptionPane.ERROR_MESSAGE);
		else{
			JOptionPane.showMessageDialog(this,
					"The files successfully be compressed, and the output file is in "+output_path,
					"Success!", JOptionPane.INFORMATION_MESSAGE);
			try {
				java.awt.Desktop.getDesktop().open(outPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main gui = new Main();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setSize(500, 360);
		gui.setLocation(250, 200);

	}
}
