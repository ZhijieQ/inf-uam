package gui;

import grupo.GrupoEstudio;
import mensaje.*;

import java.awt.Label;
import java.awt.event.*;

import javax.swing.*;

import mailUam.*;
import mensaje.Pregunta;

public class GUIVerRespuesta extends GUIMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton buttonAtras;
	private JPanel panel;
	private Pregunta pregunta;
	private JLabel labelPregunta;

	public GUIVerRespuesta(MailUam modelo) {
		super(modelo);
		buttonAtras = new JButton("Atras");
		panel = new JPanel();
		labelPregunta = new JLabel();
		JPanel p1= new JPanel();
		p1.add(labelPregunta);
		p1.add(panel);
		p1.add(buttonAtras);
		add(p1);
	}
	
	/**
	 * @return the buttonAtras
	 */
	public JButton getButtonAtras() {
		return buttonAtras;
	}

	/**
	 * @param buttonAtras the buttonAtras to set
	 */
	public void setButtonAtras(JButton buttonAtras) {
		this.buttonAtras = buttonAtras;
	}

	/**
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	/**
	 * @return the pregunta
	 */
	public Pregunta getPregunta() {
		return pregunta;
	}

	/**
	 * @param pregunta the pregunta to set
	 */
	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	/**
	 * @return the labelPregunta
	 */
	public JLabel getLabelPregunta() {
		return labelPregunta;
	}

	/**
	 * @param labelPregunta the labelPregunta to set
	 */
	public void setLabelPregunta(JLabel labelPregunta) {
		this.labelPregunta = labelPregunta;
	}

	@Override
	public void setControlador(ActionListener c) {
		//TODO Auto-generated method stub
		super.setControlador(c);
		buttonAtras.addActionListener(c);
	}

	public void setValores(Pregunta pregunta) {
		this.pregunta= pregunta;
		labelPregunta.setText(pregunta.getCuerpo());
		panel.removeAll();
		for(Respuesta r: pregunta.getListaRespuestas()){
			panel.add(new JLabel("["+r.getRemitente().getCorreo()+"] "+r.getCuerpo()));
		}
	}

}
