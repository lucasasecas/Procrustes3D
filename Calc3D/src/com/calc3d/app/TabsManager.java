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
import java.util.HashMap;

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
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI.TabbedPaneLayout;

import org.jdesktop.swingx.JXTreeTable;

import com.calc3d.app.panels.GraphicsPane;
import com.calc3d.renderer.Canvas3D;

public class TabsManager implements Serializable {

	JTabbedPane tabs;
	HashMap<String, GraphicsPane> closedTabs;
	
	public TabsManager(JTabbedPane tabs){
		this.tabs = tabs;
		this.closedTabs = new HashMap<String, GraphicsPane>();
	}
	
	public int newTab(final JComponent panel, String title){
		
		Component c = tabs.add(panel);
		int count = tabs.getTabCount();
		tabs.setTabComponentAt(count-1 , this.generateButton(title));
		tabs.setTitleAt(count-1, title);
		int newIndex = tabs.getTabCount()-1;
		this.tabs.setSelectedIndex(newIndex);
		return newIndex;
	}
	
	private Component generateButton(String title) {
		JPanel pnl = new JPanel();
		pnl.setOpaque(false);
		
		JLabel title1 = new JLabel(title);
		title1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		JLabel close = new JLabel("x");
		pnl.add(title1);
		pnl.add(new TabButton(title));
		
		return pnl;
	}

	public Component getCurrentTab(){
		try{

			return tabs.getSelectedComponent();
		}catch(Exception e){
			
		}
		return null;
	}
	
	public Canvas3D getCurrentCanvas(){
		try{
			GraphicsPane graphicsPane = ((GraphicsPane)tabs.getSelectedComponent());
			Canvas3D canvas = graphicsPane.getCanvas();

			return canvas;
		}catch(Exception e){
			
		}
		return null;
	}
	
	public Canvas3D getCanvasAt(int index){
		try{
			int count = tabs.getTabCount();
			GraphicsPane graphicsPane = ((GraphicsPane)tabs.getComponentAt(index)); 
			
			Canvas3D c = graphicsPane.getCanvas();

			return c;
		}catch(Exception e){
			e.printStackTrace();
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
		return tabs.getTabCount();
	}

	public String getTitleAt(int i) {
		return this.tabs.getTitleAt(i);
	}

	
	
	/**
	 * 
	 * 
	 */
	
	private class TabButton extends JButton implements ActionListener {
		
		String title;
        public TabButton(String title) {
        	this.title = title;
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
            	GraphicsPane pane = (GraphicsPane) getTabByTitle(this.title);
            	JTabbedPane tbs = tabs;
            	HashMap closed = closedTabs;
            	closedTabs.put(title, pane);
            	i = tabs.indexOfComponent(pane);
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

	public void reset() {
		tabs.removeAll();
		
	}

	public void addChangeListener(ChangeListener changeListener) {
		this.tabs.addChangeListener(changeListener);		
	}

	public void showTab(String name) {
		GraphicsPane pane = closedTabs.get(name);		
		if(pane!=null){
			closedTabs.remove(name);
			this.newTab(pane, name);
		}
	}
	
	public Component getTabByTitle(String title){
		int pos = 0;
		while(pos < tabs.getTabCount()){
			String tit =tabs.getTitleAt(pos); 
			if(tit.equals(title)){
				return tabs.getComponentAt(pos);
				
			}
			pos++;
		}
		return null;	
	}

	public int getTabIndex(String name) {
		int pos = 0;
		while(pos < tabs.getTabCount()){
			String tit =tabs.getTitleAt(pos); 
			if(tit.equals(name)){
				return pos;
				
			}
			pos++;
		}
		return -1;
	}

	public void removeTab(int tabIndex) {
		tabs.remove(tabIndex);
		
	}
}
