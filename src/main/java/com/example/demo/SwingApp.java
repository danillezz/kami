package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SpringBootApplication
public class SwingApp {
	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(SwingApp.class)
				.headless(false).run(args);

		EventQueue.invokeLater(() -> {
			MainForm f = ctx.getBean(MainForm.class);
			JTextArea txtResult = f.getTxtResult();
			JPanel resultPanel = f.getResultPanel();
			JFrame frame = new JFrame("Расчетные задачи КАМИ");
			frame.setContentPane(f.getPanel());
			frame.setSize(800,600);
			frame.setVisible(true);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			JScrollPane scroll = new JScrollPane(txtResult);
			scroll.setBounds(txtResult.getX(), txtResult.getY(), txtResult.getWidth(), txtResult.getHeight());
			scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			resultPanel.add(scroll);
		});
	}
}
