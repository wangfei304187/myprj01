package com.my.images;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import org.dcm4che2.imageioimpl.plugins.dcm.DicomImageReaderSpi;

public class DicomMultiframePlayer extends JFrame implements ActionListener, Runnable {

    private static final long serialVersionUID = 1L;
    private JLabel fileLabel;
    private JTextField fileField;
    private JButton btnChoose;
    private JButton btnPlay;
    private JButton btnPause;
    private JButton btnStop;
    private JButton btnExit;    
    private Vector<BufferedImage> images;
    private ImagePanel imagePanel;  
    private boolean stop;
    private int currentFrame;

    public DicomMultiframePlayer() {
        super("DICOM Multiframe Player using dcm4che - by samucs-dev");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        images = new Vector<BufferedImage>();
        imagePanel = new ImagePanel();

        fileLabel = new JLabel("File:");
        fileField = new JTextField(30);
        btnChoose = this.createJButton(25, 25, "...");

        btnPlay = this.createJButton(80,25,"Play");
        btnPause = this.createJButton(80,25,"Pause");
        btnStop = this.createJButton(80,25,"Stop");     
        btnExit = this.createJButton(80,25,"Exit");
        btnStop.setEnabled(false);

        JPanel panelNorth = new JPanel();
        panelNorth.add(fileLabel);
        panelNorth.add(fileField);
        panelNorth.add(btnChoose);

        JPanel panelSouth = new JPanel();
        panelSouth.add(btnPlay);
        panelSouth.add(btnPause);
        panelSouth.add(btnStop);
        panelSouth.add(btnExit);

        this.getContentPane().add(panelNorth, BorderLayout.NORTH);
        this.getContentPane().add(imagePanel, BorderLayout.CENTER);
        this.getContentPane().add(panelSouth, BorderLayout.SOUTH);

        this.setSize(new Dimension(500,500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void run() {
        while(true) {
            if (!btnPlay.isEnabled()) {             
                if (stop) break;                
                currentFrame++;
                if (currentFrame == images.size())
                    currentFrame = 0;
                imagePanel.setImage(images.get(currentFrame));              
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {        
        if (e.getSource().equals(btnChoose)) {
            JFileChooser chooser = new JFileChooser();
            int action = chooser.showOpenDialog(this);
            switch(action) {
                case JFileChooser.APPROVE_OPTION:
                    this.openFile(chooser.getSelectedFile());
                    break;
                case JFileChooser.CANCEL_OPTION:
                    return;
            }
        }       
        if (e.getSource().equals(btnPlay)) {
            btnPlay.setEnabled(false);
            btnPause.setEnabled(true);
            btnStop.setEnabled(true);
            stop = false;
            new Thread(this).start();           
        }
        if (e.getSource().equals(btnPause)) {
            btnPlay.setEnabled(true);
            btnPause.setEnabled(false);
            btnStop.setEnabled(true);
            stop = false;
        }
        if (e.getSource().equals(btnStop)) {
            btnPlay.setEnabled(true);
            btnPause.setEnabled(false);
            btnStop.setEnabled(false);
            stop = true;
            currentFrame = 0;
            imagePanel.setImage(images.get(0));         
        }
        if (e.getSource().equals(btnExit)) {
            System.exit(0);
        }
    }

    private JButton createJButton(int width, int height, String text) {
        JButton b = new JButton(text);
        b.setMinimumSize(new Dimension(width, height));
        b.setMaximumSize(new Dimension(width, height));
        b.setPreferredSize(new Dimension(width, height));
        b.addActionListener(this);
        return b;
    }

    private void openFile(File file) {
        images.clear();
        try {
            System.out.println("Reading DICOM image...");           
            ImageReader reader = new DicomImageReaderSpi().createReaderInstance();
            FileImageInputStream input = new FileImageInputStream(file);
            reader.setInput(input);         
            int numFrames = reader.getNumImages(true);
            System.out.println("DICOM image has "+ numFrames +" frames...");            
            System.out.println("Extracting frames...");
            for (int i=0; i < numFrames; i++) {
                BufferedImage img = reader.read(i);
                images.add(img);
                System.out.println(" > Frame "+ (i+1));
            }           
            System.out.println("Finished.");
        } catch(Exception e) {
            e.printStackTrace();
            imagePanel.setImage(null);
            return;
        }
        stop = false;
        currentFrame = 0;
        imagePanel.setImage(images.get(0));
    }

    private class ImagePanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private BufferedImage image;
        public ImagePanel() {
            super();
            this.setPreferredSize(new Dimension(1024,1024));
            this.setBackground(Color.black);            
        }
        public void setImage(BufferedImage image) {
            this.image = image;
            this.updateUI();
        }
        @Override
        public void paint(Graphics g) {
            if (this.image != null) {
                g.drawImage(this.image, 0, 0, image.getWidth(), image.getHeight(), null);
            }
        }
    };

    public static void main(String[] args) {
        new DicomMultiframePlayer();
    }

}