package com.calc3d.app;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

import org.jdesktop.swingx.JXTreeTable;

import com.calc3d.app.panels.GraphicsPane;
import com.calc3d.renderer.Canvas3D;

public class TabsManager implements Serializable {

	JTabbedPane tabs;
	
	public TabsManager(JTabbedPane tabs){
		this.tabs = tabs;
	}
	
	public int newTab(final JComponent panel, String title){
		
		Component c = tabs.add(panel);
		int count = tabs.getTabCount();
		tabs.setTabComponentAt(count-1 , this.generateButton(title));
		return tabs.getTabCount()-1;
	}
	
	private Component generateButton(String title) {
		JPanel pnl = new JPanel();
		pnl.setOpaque(false);
		
		JLabel title1 = new JLabel(title);
		title1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		JLabel close = new JLabel("x");
		pnl.add(title1);
		pnl.add(new TabButton());
		
		return pnl;
	}

	public JXTreeTable getCurrentTreeTable(){
		try{
			JSplitPane split = ((JSplitPane)tabs.getSelectedComponent());
			JScrollPane c = (JScrollPane)split.getComponent(1);
			JXTreeTable result = (JXTreeTable) c.getViewport().getView();
			return result;
		}catch(Exception e){
			
		}
		return null;
	}
	
	public Canvas3D getCurrentCanvas(){
		try{
			JSplitPane split = ((JSplitPane)tabs.getSelectedComponent());
			Canvas3D c = (Canvas3D) split.getComponent(0);

			return c;
		}catch(Exception e){
			
		}
		return null;
	}
	
	public Canvas3D getCanvasAt(int index){
		try{
			JSplitPane split = ((JSplitPane)tabs.getComponent(index));
			Canvas3D c = (Canvas3D) split.getComponent(0);

			return c;
		}catch(Exception e){
			
		}
		return null;
	}

	public String getCurrentTitle() {
		return this.tabs.getTitleAt(tabs.getSelectedIndex());
	}
	
	public void setCurrentTitle(String title){
		int selected = tabs.getSelectedIndex();
		this.tabs.setTitleAt(tabs.getSelectedIndex(), title);
		tabs.updateUI();
	}

	public JTabbedPane getTabs() {
		return this.tabs;
	}

	public void setTabs(JTabbedPane tabs) {
		this.tabs = tabs;
		
	}

	public int getCountOfTabs() {
		return tabs.getComponentCount();
	}

	public String getTitleAt(int i) {
		return this.tabs.getTitleAt(i);
	}

	
	
	/**
	 * 
	 * 
	 */
	
	private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);
        }
 
        public void actionPerformed(ActionEvent e) {
            int i = tabs.getSelectedIndex();
            if (i != -1) {
                tabs.remove(i);
            }
        }
 
        //we don't want to update UI for this button
        public void updateUI() {
        }
 
        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }
	
	private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }
 
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };

	public void setTab(int tabIndex, JComponent pne) {
		this.tabs.setTabComponentAt(tabIndex, pne);
	}

	public JPanel getTabAt(int index) {
		GraphicsPane panel = (GraphicsPane) tabs.getComponentAt(index);
		return panel;
	}

	public void setTitleTabAt(int index, String title) {
		this.tabs.setTitleAt(index, title);
	}
}
