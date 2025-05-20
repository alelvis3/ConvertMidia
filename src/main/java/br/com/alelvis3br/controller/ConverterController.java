package br.com.alelvis3br.controller;

import java.util.concurrent.CompletableFuture;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import br.com.alelvis3br.model.ConverterModel;
import br.com.alelvis3br.view.ConverterView;

public class ConverterController {
	private ConverterModel model;
	private ConverterView view;

	public ConverterController(ConverterModel model) {
		this.model = model;
	}

	public void setView(ConverterView view) {
		this.view = view;
	}

	public void convert() {
		String inputFilePath = view.getInputFilePath();
		String outputFilePath = view.getOutputFilePath();
		String selectedFormat = view.getSelectedFormat();

		if (inputFilePath == null || inputFilePath.isEmpty() || outputFilePath == null || outputFilePath.isEmpty()) {
			JOptionPane.showInputDialog(view, "Por favor, selecione os arquivos de entrada e saída.");
			return;
		}

		model.setInputFilePath(inputFilePath);
		model.setOutputFilePath(outputFilePath);
		model.setSelectedFormat(selectedFormat);

		view.setStatus("Iniciando conversão...");

		// Executar a conversão em uma thread separada para não travar a interface
		// gráfica
		CompletableFuture.runAsync(() -> {
			model.convert(model.getInputFilePath(), model.getOutputFilePath(), model.getSelectedFormat());

			// Atualizar a interface gráfica com o status da conversão
			SwingUtilities.invokeLater(() -> {
				view.setStatus(model.getConversionStatus());
			});
		});
	}
}