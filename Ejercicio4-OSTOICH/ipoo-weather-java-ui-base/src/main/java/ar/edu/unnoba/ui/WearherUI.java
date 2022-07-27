package ar.edu.unnoba.ui;


import ar.edu.unnoba.model.Channel;
import ar.edu.unnoba.model.City;
import ar.edu.unnoba.service.WeatherService;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class WearherUI extends JFrame implements Observer{

	private WeatherService service;

	private static final String _DEGREE = "\u00b0";
	private static final long serialVersionUID = 1L;

	private EstacionMeteorologica estacion = new EstacionMeteorologica();

	private JPanel climaPanel = new JPanel();
	private JPanel buttons = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel historialPanel = new JPanel();

	//COMPONENTES

	// Panel de BOTONES.
	private JButton b_temperature = new JButton("Temperature");
	private JButton b_clean = new JButton("Clean");
	private JButton b_date = new JButton("Date");

	//Panel de CLIMA ACTUAL.
	private JLabel climaText = new JLabel();
	private JLabel climaImage = new JLabel();

	//Panel de HISTORIAL.

	private modelHistorial model;

	private JList<Clima> listHistorial;

	public WearherUI(String title) {
		setTitle(title);
		getEstacion().addObserver(this);

		model = new modelHistorial(getEstacion());
		listHistorial = new JList<>(model);

		iniciarPanelClima();
		iniciarPanelBotonesYHistorial();



		b_temperature.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getEstacion().ordenarPorTemperatura();
			}
		});

		b_date.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getEstacion().ordenarPorFecha();
			}
		});

		b_clean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getEstacion().clean();
			}
		});

		getContentPane().add(climaPanel, BorderLayout.NORTH);
		getContentPane().add(buttonPanel, BorderLayout.CENTER);
	}

	public modelHistorial getModel() {
		return model;
	}

	public EstacionMeteorologica getEstacion() {
		return estacion;
	}

	public void setEstacion(EstacionMeteorologica estacion) {
		this.estacion = estacion;
	}

	public void close() {
		getEstacion().close();
	}

	public void iniciarPanelClima(){

		climaPanel.setPreferredSize(new Dimension(400, 70));
		climaPanel.setMinimumSize(new Dimension(400, 70));
		climaText.setBackground(Color.DARK_GRAY);
		climaText.setText("Actualizando...");
		climaText.setFont(new Font("arial", Font.BOLD, 18));

		climaPanel.add(climaText, BorderLayout.WEST);
		climaPanel.add(climaImage, BorderLayout.EAST);


	}


	public void iniciarPanelBotonesYHistorial() {

		buttonPanel.setPreferredSize(new Dimension(400, 200));
		buttonPanel.setMinimumSize(new Dimension(400, 200));
		buttonPanel.setBackground(Color.DARK_GRAY);

		//PANEL DEL HISTORIAL
		historialPanel.setPreferredSize(new Dimension(200, 200));
		historialPanel.setMinimumSize(new Dimension(200, 200));

		listHistorial.setPreferredSize(new Dimension(200, 200));
		listHistorial.setMinimumSize(new Dimension(200, 200));

		listHistorial.setVisibleRowCount(10);
		listHistorial.setFont(new Font("arial", Font.PLAIN, 12));

		listHistorial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2){
					abrirVentanaInfo();
				}

			}
		});
/*
		listHistorial.setCellRenderer(new ListCellRenderer<Clima>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Clima> list, Clima value, int index, boolean isSelected, boolean cellHasFocus) {
				JPanel panel = new JPanel();
				panel.setBackground(Color.WHITE);
				if (isSelected){
					panel.setBackground(Color.CYAN);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm:ss"); //VER: DEJAR O SACAR

				JLabel ciudad = new JLabel(value.getCiudad() + " ");
				JLabel fechaYtemp = new JLabel(value.getFecha() + " " + Math.round(value.getTemperatura())+ _DEGREE + " - ");
				JLabel estado = new JLabel(value.getEstado());
				JLabel icon = new JLabel();

				try {
					URL url = new URL(value.getUrl());
					Image image = ImageIO.read(url);
					icon.setIcon((Icon) image);
				} catch (IOException e) {
					e.printStackTrace();
				}

				panel.add(icon);
				panel.add(ciudad);
				panel.add(fechaYtemp);
				panel.add(estado);

				return panel;
			}
		});
		*/
		historialPanel.add(listHistorial);
		JScrollPane barraDesplazamiento = new JScrollPane(listHistorial);
		historialPanel.add(barraDesplazamiento);

		//PANEL DE BOTONES
        buttons.setPreferredSize(new Dimension(200, 200));
		buttons.setMinimumSize(new Dimension(200, 200));
        buttons.setLayout(new GridLayout(3,1,0,20));  //vgap: 5
		buttons.add(b_temperature);
		buttons.add(b_date);
		buttons.add(b_clean);

		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(buttons);
		buttonPanel.add(historialPanel);


	}

	public void abrirVentanaInfo() {
		JPanel panelDialog = new JPanel();
		JLabel labelDialog = new JLabel();
		JDialog dialog = new JDialog();

		dialog.setTitle("Informacion detallada");
		dialog.setSize(500, 200);
		dialog.setLayout(new GridLayout(1,1,0,0));
		dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

		panelDialog.setLayout(new FlowLayout());

		panelDialog.add(labelDialog);
		labelDialog.setText("Ciudad: " + listHistorial.getSelectedValue().getCiudad() + "\n" + "Fecha: " + listHistorial.getSelectedValue().getFecha() + "\n" + "Temperatura: " + listHistorial.getSelectedValue().getTemperatura() + "°" + "\n" + "Estado: " + listHistorial.getSelectedValue().getEstado() + "\n" + "Humedad: " + listHistorial.getSelectedValue().getHumedad() + "\n" + "Presion: " + listHistorial.getSelectedValue().getPresion() + "\n" + "Viento: " + listHistorial.getSelectedValue().getViento());

		dialog.add(panelDialog);
		dialog.setVisible(true);
		System.out.println("Double Click");


	}

	public WeatherService getService() {
		return service;
	}

	public void setService(WeatherService service) {
		this.service = service;
	}

	@Override
	public void update(Observable weather, Object param) {

		Clima climaAct = estacion.getClimaActual();

		try {
			URL url = new URL(climaAct.getUrl());
			Image image = ImageIO.read(url);
			climaImage.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		climaText.setText(climaAct.toString());


	}




	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
					try {
						WearherUI main = new WearherUI("Weather App By: Ostoich");

						main.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent e) {
								main.close();
							}
						});
						main.setVisible(true); // Le decimos que se muestre
						main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						main.setPreferredSize(new Dimension(640, 480)); // Le damos el tamaño a la ventana
						main.pack();
						main.setLocationRelativeTo(null); // Le decimos que no sea relativa a nada, eso hace que quede centrada.
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		);

	}
}

