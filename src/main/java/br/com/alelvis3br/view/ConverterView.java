package br.com.alelvis3br.view;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.alelvis3br.controller.ConverterController;
import br.com.alelvis3br.model.ConverterModel;

public class ConverterView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField inputFilePathField;
	private JTextField outputFilePathField;
	private JComboBox<String> formatComboBox;
	private JButton chooseInputButton;
	private JButton chooseOutputButton;
	private JButton convertButton;
	private JTextArea statusTextArea;

	private ConverterController controller;

	public ConverterView(ConverterController controller) {
		this.controller = controller;
		setTitle("Conversor de Áudio e Vídeo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		initComponents();
		layoutComponents();
		setVisible(true);
	}

	private void initComponents() {
		inputFilePathField = new JTextField(30);
		outputFilePathField = new JTextField(30);
		formatComboBox = new JComboBox<>(new String[] { "mp3", "mp4", "wav", "avi", "flac" });
		chooseInputButton = new JButton("Escolher Arquivo de Entrada");
		chooseOutputButton = new JButton("Escolher Arquivo de Saída");
		convertButton = new JButton("Converter");
		statusTextArea = new JTextArea(5, 30);
		statusTextArea.setEditable(false);

		// Action Listeners
		chooseInputButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseInputFile();
			}
		});

		chooseOutputButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseOutputFile();
			}
		});

		convertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.convert();
			}
		});
	}

	private void layoutComponents() {
		JPanel mainPanel = new JPanel(new MigLayout("", "[][grow,fill]", "[][][][]"));

		mainPanel.add(new JLabel("Arquivo de Entrada:"), "");
		mainPanel.add(inputFilePathField, "growx, wrap");
		mainPanel.add(chooseInputButton, "span, wrap");

		mainPanel.add(new JLabel("Arquivo de Saída:"), "");
		mainPanel.add(outputFilePathField, "growx, wrap");
		mainPanel.add(chooseOutputButton, "span, wrap");

		mainPanel.add(new JLabel("Formato de Saída:"), "");
		mainPanel.add(formatComboBox, "wrap");

		mainPanel.add(convertButton, "span, center, wrap");
		mainPanel.add(new JScrollPane(statusTextArea), "span, grow, wrap");

		add(mainPanel);
	}

	private void chooseInputFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Áudio/Vídeo", "mp3", "mp4", "wav",
				"avi", "flac", "ogg", "mkv");
		fileChooser.setFileFilter(filter);

		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			inputFilePathField.setText(selectedFile.getAbsolutePath());
		}
	}

	private void chooseOutputFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			outputFilePathField.setText(selectedFile.getAbsolutePath());
		}
	}

	public String getInputFilePath() {
		return inputFilePathField.getText();
	}

	public String getOutputFilePath() {
		return outputFilePathField.getText();
	}

	public String getSelectedFormat() {
		return (String) formatComboBox.getSelectedItem();
	}

	public void setStatus(String status) {
		statusTextArea.setText(status);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ConverterModel model = new ConverterModel();
			ConverterController controller = new ConverterController(model);
			ConverterView view = new ConverterView(controller);
			controller.setView(view);
		});
	}


}
