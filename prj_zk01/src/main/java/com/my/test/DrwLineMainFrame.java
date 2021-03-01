package com.my.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrwLineMainFrame extends JFrame
{

    private JPanel drawPnl;

    private Point p1;
    private Point p2;

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

                    DrwLineMainFrame frame = new DrwLineMainFrame("DrawLine");
                    frame.setVisible(true);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public DrwLineMainFrame(String title)
    {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setTitle(title);
        buildUI();
    }

    private boolean enableTrack = false;

    private void buildUI()
    {
        setLayout(new BorderLayout());

        drawPnl = new JPanel();
        drawPnl.setBackground(Color.white);
        getContentPane().add(drawPnl, BorderLayout.CENTER);

        drawPnl.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mousePressed(MouseEvent e)
            {
                p1 = e.getPoint();
                enableTrack = true;
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                p2 = e.getPoint();
                enableTrack = false;
                // drawPnl.repaint();
                drawLine(drawPnl.getGraphics());
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                // TODO Auto-generated method stub
            }
        });

        drawPnl.addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseMoved(MouseEvent e)
            {

            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (enableTrack)
                {
                    // drawPnl.getGraphics().drawLine(p1.x, p1.y, e.getX(), e.getY());
                }
            }
        });
    }

    private void drawLine(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        int x1 = p1.x;
        int y1 = p1.y;
        int x2 = p2.x;
        int y2 = p2.y;

        System.out.println("p1=" + p1 + ", p2=" + p2);
        System.out.println("1. |y2-y1|+1 ==> " + (Math.abs(y2 - y1) + 1) + ", y1=" + p1.y + ", y2=" + p2.y);
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1))
        {
            System.out.println("2. |y2-y1|+1 ==> " + (Math.abs(y2 - y1) + 1) + ", y1=" + p1.y + ", y2=" + p2.y);
            int XSign = x1 > x2 ? -1 : 1;
            int YSign = y1 > y2 ? -1 : 1;

            int N = Math.abs(y2 - y1) + 1;

            float minL1 = (Math.abs(x2 - x1) + 1) * 1f * 3 / (2 * N + 1);
            float maxL1 = (Math.abs(x2 - x1) + 1) * 1f * 3 / (2 * N - 3);

            System.out.println("N=" + N + ", minL1=" + minL1 + ", maxL1=" + maxL1 + ", Math.abs(x2-x1+1)=" + Math.abs(x2 - x1 + 1));
            float L1 = (minL1 + maxL1) / 2f;
            System.out.println("===> L1=" + L1);

            float theta = (Math.abs(x2 - x1) + 1 - L1 * (2 / 3f * N - 1)) / 2f;

            System.out.println("theta=" + theta * XSign);

            g2.drawLine(x1, y1, round(x1 + (theta + 1 / 3f * L1 - 1) * XSign), y1);

            float xs = x1 + theta * XSign;
            int ys = y1 + 1 * YSign;

            for (int i = 0; i < N - 2; i++)
            {
                g2.drawLine(round(xs), ys, round(xs + (L1 - 1) * XSign), ys);

                xs = xs + 2 / 3f * L1 * XSign;
                ys = ys + 1 * YSign;
            }

            g2.drawLine(round(xs), ys, round(xs + (theta + 1 / 3f * L1 - 1) * XSign), ys);
        }
        else
        {
            y1 = p1.x;
            x1 = p1.y;
            y2 = p2.x;
            x2 = p2.y;

            int XSign = x1 > x2 ? -1 : 1;
            int YSign = y1 > y2 ? -1 : 1;

            int N = Math.abs(y2 - y1) + 1;

            float minL1 = (Math.abs(x2 - x1) + 1) * 1f * 3 / (2 * N + 1);
            float maxL1 = (Math.abs(x2 - x1) + 1) * 1f * 3 / (2 * N - 3);

            System.out.println("N=" + N + ", minL1=" + minL1 + ", maxL1=" + maxL1 + ", Math.abs(x2-x1+1)=" + Math.abs(x2 - x1 + 1));
            float L1 = (minL1 + maxL1) / 2f;
            System.out.println("===> L1=" + L1);

            float theta = (Math.abs(x2 - x1) + 1 - L1 * (2 / 3f * N - 1)) / 2f;

            System.out.println("theta=" + theta * XSign);

            // g2.drawLine(x1, y1, round(x1 + delta + 1/3f * L1 -1) , y1);
            g2.drawLine(y1, x1, y1, round(x1 + (theta + 1 / 3f * L1 - 1) * XSign));

            float xs = x1 + theta * XSign;
            int ys = y1 + 1 * YSign;

            for (int i = 0; i < N - 2; i++)
            {
                // g2.drawLine(round(xs), ys, round(xs + L1 -1) , ys);
                g2.drawLine(ys, round(xs), ys, round(xs + (L1 - 1) * XSign));

                xs = xs + 2 / 3f * L1 * XSign;
                ys = ys + 1 * YSign;
            }

            // g2.drawLine(round(xs), ys, round(xs + delta + 1/3f * L1 -1) , ys);
            g2.drawLine(ys, round(xs), ys, round(xs + (theta + 1 / 3f * L1 - 1) * XSign));
        }
    }

    private int round(float f)
    {
        return Math.round(f);
    }

    // ///////////////////////////////////////////////////////////

    public static void drawLine(Graphics g2, int x1, int y1, int x2, int y2)
    {
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1))
        {
            int XSign = x1 > x2 ? -1 : 1;
            int YSign = y1 > y2 ? -1 : 1;

            int N = Math.abs(y2 - y1) + 1;

            float minL1 = (Math.abs(x2 - x1) + 1) * 1f * 3 / (2 * N + 1);
            float maxL1 = (Math.abs(x2 - x1) + 1) * 1f * 3 / (2 * N - 3);

            float L1 = (minL1 + maxL1) / 2f;

            float theta = (Math.abs(x2 - x1) + 1 - L1 * (2 / 3f * N - 1)) / 2f;

            g2.drawLine(x1, y1, Math.round(x1 + (theta + 1 / 3f * L1 - 1) * XSign), y1);

            float xs = x1 + theta * XSign;
            int ys = y1 + 1 * YSign;

            for (int i = 0; i < N - 2; i++)
            {
                g2.drawLine(Math.round(xs), ys, Math.round(xs + (L1 - 1) * XSign), ys);

                xs = xs + 2 / 3f * L1 * XSign;
                ys = ys + 1 * YSign;
            }

            g2.drawLine(Math.round(xs), ys, Math.round(xs + (theta + 1 / 3f * L1 - 1) * XSign), ys);
        }
        else
        {
            int tmp = y1;
            y1 = x1;
            x1 = tmp;
            tmp = y2;
            y2 = x2;
            x2 = tmp;

            int XSign = x1 > x2 ? -1 : 1;
            int YSign = y1 > y2 ? -1 : 1;

            int N = Math.abs(y2 - y1) + 1;

            float minL1 = (Math.abs(x2 - x1) + 1) * 1f * 3 / (2 * N + 1);
            float maxL1 = (Math.abs(x2 - x1) + 1) * 1f * 3 / (2 * N - 3);

            float L1 = (minL1 + maxL1) / 2f;

            float theta = (Math.abs(x2 - x1) + 1 - L1 * (2 / 3f * N - 1)) / 2f;

            g2.drawLine(y1, x1, y1, Math.round(x1 + (theta + 1 / 3f * L1 - 1) * XSign));

            float xs = x1 + theta * XSign;
            int ys = y1 + 1 * YSign;

            for (int i = 0; i < N - 2; i++)
            {
                g2.drawLine(ys, Math.round(xs), ys, Math.round(xs + (L1 - 1) * XSign));

                xs = xs + 2 / 3f * L1 * XSign;
                ys = ys + 1 * YSign;
            }

            g2.drawLine(ys, Math.round(xs), ys, Math.round(xs + (theta + 1 / 3f * L1 - 1) * XSign));
        }
    }
}