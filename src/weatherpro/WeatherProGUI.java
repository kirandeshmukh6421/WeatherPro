package weatherpro;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class WeatherProGUI {
	
	private JFrame frame;
	private JScrollPane scrollPane;
	private JTextField textFieldCity;
	private JButton getWeatherButton;
	private JLabel cityLabel;
	private JLabel unitLabel;
	private static String url;
	private static String unit;
	
	//Made public so it can be modified outside this class i.e. In WeatherMethods class
	public static JTextArea textArea;
	public static JRadioButton radioButtonCelsius;
	public static JRadioButton radioButtonFahrenheit;
	public static JLabel backgroundImage;
	public static JLabel weatherIcon;
	public static JLabel cityName;
	public static JLabel descLabel;
	public static JLabel sunriseLabel;
	public static JLabel sunsetLabel;
	public static JLabel sunrise;
	public static JLabel sunset;
	public static JLabel srTime;
	public static JLabel ssTime;
	private JButton reportButton;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
						WeatherProGUI window = new WeatherProGUI();
						window.frame.setVisible(true);
						
						// Set App Icon
						ImageIcon img = new ImageIcon(this.getClass().getResource(("/icon.png")));
						window.frame.setIconImage(img.getImage());
						
						url = "http://api.openweathermap.org/data/2.5/weather?q=" + GetLocation.getCity()
						+ "&appid=d9abc6bc852175d9fa45827e89496b09";
						WeatherMethods.getData(url, "C");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WeatherProGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Main Frame
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("WeatherPro");
		frame.setBounds(350, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Text Field to take user input (City) 
		textFieldCity = new JTextField();
		try {
			textFieldCity.setText(GetLocation.getCity());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Unable to get your location.");
		}
		textFieldCity.setFont(new Font("Tahoma", Font.PLAIN, 19));
		textFieldCity.setBounds(215, 340, 300, 30);
		frame.getContentPane().add(textFieldCity);
		textFieldCity.setColumns(10);
		textFieldCity.requestFocus();;
		
		//Radio buttons to select units
		radioButtonCelsius = new JRadioButton("Celsius");
		radioButtonCelsius.setOpaque(false);
		radioButtonCelsius.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButtonCelsius.isSelected()) {
					radioButtonFahrenheit.setSelected(false);
				}
				
			}
		});
		radioButtonCelsius.setFont(new Font("Tahoma", Font.PLAIN, 18));
		radioButtonCelsius.setBounds(235, 380, 111, 23);
		//Celsius Radio Button is checked by default
		radioButtonCelsius.setSelected(true);
		frame.getContentPane().add(radioButtonCelsius);
		
		
		
		radioButtonFahrenheit = new JRadioButton("Fahrenheit");
		radioButtonFahrenheit.setOpaque(false);
		radioButtonFahrenheit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radioButtonFahrenheit.isSelected()) {
					radioButtonCelsius.setSelected(false);
				}
			}
		});
		radioButtonFahrenheit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		radioButtonFahrenheit.setBounds(395, 380, 111, 23);
		frame.getContentPane().add(radioButtonFahrenheit);
		
		//Button to get weather data
		getWeatherButton = new JButton("Get Weather");
		getWeatherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				url = "http://api.openweathermap.org/data/2.5/weather?q=" + textFieldCity.getText() + "&appid=d9abc6bc852175d9fa45827e89496b09";
				
				if(radioButtonCelsius.isSelected()) {
					unit = "C";
					try {
						//Get weather data
						WeatherMethods.getData(url, unit);
					
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if(radioButtonFahrenheit.isSelected()) {
					unit = "F";
					try {
						//Get weather data
						WeatherMethods.getData(url, unit);
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}		
			}
		});
		getWeatherButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getWeatherButton.setBounds(130, 420, 150, 35);
		frame.getContentPane().add(getWeatherButton);
		
		reportButton = new JButton("Create Report");
		reportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Weather Report created.");
				if(radioButtonCelsius.isSelected()) {
					unit = "C";
					try {
						//Get weather data
						WeatherReport.createReport(unit);
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if(radioButtonFahrenheit.isSelected()) {
					unit = "F";
					try {
						//Get weather data
						WeatherReport.createReport(unit);
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}	
				
				// Open Weather Report
				try {
					//text file, should be opening in default text editor
		            File file = new File("src/Weather-Report.html");
		            
		            Desktop desktop = Desktop.getDesktop();
		            if(file.exists()) desktop.open(file);
		        } catch (Exception e1) {
                   JOptionPane.showMessageDialog(null, "The file is missing.");
               }
			}	
		});
		reportButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		reportButton.setBounds(320, 420, 150, 35);
		frame.getContentPane().add(reportButton);
		
		// Surrounds the Text Area to make it scrollable
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(100, 180, 400, 146);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		// To avoid textArea from automatically scrolling to the end of the text - Start
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		//End
		textArea.setBorder(null);
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		textArea.setEditable(false);
		
		
		cityLabel = new JLabel("Search City :");
		cityLabel.setLabelFor(textFieldCity);
		cityLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cityLabel.setBounds(85, 340, 115, 30);
		frame.getContentPane().add(cityLabel);
		
		unitLabel = new JLabel("Select Unit :");
		unitLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		unitLabel.setBounds(85, 373, 110, 30);
		frame.getContentPane().add(unitLabel);
		
		backgroundImage = new JLabel();
		backgroundImage.setIcon(new ImageIcon(this.getClass().getResource(("/city.jpg"))));
		backgroundImage.setBounds(0, 0, 607, 473);
		frame.getContentPane().add(backgroundImage);
		
		weatherIcon = new JLabel();
		weatherIcon.setBounds(230, 0, 100, 100);
		backgroundImage.add(weatherIcon);
		
		cityName = new JLabel();
		cityName.setText("");
		cityName.setHorizontalAlignment(SwingConstants.CENTER);
		cityName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cityName.setBounds(130, 114, 300, 30);
		backgroundImage.add(cityName);
		
		descLabel = new JLabel();
		descLabel.setText("");
		descLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		descLabel.setBounds(130, 143, 300, 30);
		backgroundImage.add(descLabel);
		
		sunriseLabel = new JLabel("");
		sunriseLabel.setBounds(30, 10, 64, 64);
		backgroundImage.add(sunriseLabel);
		
		sunsetLabel = new JLabel("");
		sunsetLabel.setBounds(502, 10, 64, 64);
		backgroundImage.add(sunsetLabel);
		
		sunrise = new JLabel("");
		sunrise.setBounds(41, 80, 64, 20);
		backgroundImage.add(sunrise);
		
		sunset = new JLabel("");
		sunset.setBounds(514, 80, 64, 20);
		backgroundImage.add(sunset);
		
		srTime = new JLabel("");
		srTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		srTime.setBounds(30, 110, 72, 14);
		backgroundImage.add(srTime);
		
		ssTime = new JLabel("");
		ssTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ssTime.setBounds(502, 110, 72, 18);
		backgroundImage.add(ssTime);
		
		
	}
}
