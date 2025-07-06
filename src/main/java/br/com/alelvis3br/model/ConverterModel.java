package br.com.alelvis3br.model;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

public class ConverterModel {
	private static final Logger logger = LoggerFactory.getLogger(ConverterModel.class);

	private String inputFilePath;
	private String outputFilePath;
	private String selectedFormat;
	private String conversionStatus;
	private boolean conversionRunning;

	public ConverterModel() {
		this.conversionStatus = "Aguardando...";
		this.conversionRunning = false;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	public String getSelectedFormat() {
		return selectedFormat;
	}

	public void setSelectedFormat(String selectedFormat) {
		this.selectedFormat = selectedFormat;
	}

	public String getConversionStatus() {
		return conversionStatus;
	}

	public boolean isConversionRunning() {
		return conversionRunning;
	}

	public void convert(String inputFile, String outputFile, String format) {
		this.conversionRunning = true;
		this.conversionStatus = "Iniciando conversão...";
		logger.info("Iniciando conversão de {} para {} ({})", inputFile, outputFile, format);

		File source = new File(inputFile);
		File target = new File(outputFile + "." + format);

		try {
			// Audio Attributes
			AudioAttributes audio = new AudioAttributes();
			audio.setCodec("libmp3lame"); // Use libmp3lame for MP3 encoding (mais confiável)
			audio.setBitRate(128000); // 128 kbps (pode ajustar)
			audio.setChannels(2); // Stereo
			audio.setSamplingRate(44100); // 44.1 kHz

			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setOutputFormat(format); // Formato de saída
			attrs.setAudioAttributes(audio); // Atributos de audio

			Encoder encoder = new Encoder();
			encoder.encode(new MultimediaObject(source), target, attrs);

			this.conversionStatus = "Conversão concluída com sucesso!";
			logger.info("Conversão concluída com sucesso!");

		} catch (InputFormatException e) {
			this.conversionStatus = "Erro: Formato de entrada inválido.";
			logger.error("Formato de entrada inválido: {}", e.getMessage());
		} catch (EncoderException e) {
			this.conversionStatus = "Erro durante a conversão: " + e.getMessage();
			logger.error("Erro durante a conversão: {}", e.getMessage());
		} finally {
			this.conversionRunning = false;
		}
	}
}