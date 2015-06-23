package com.calc3d.app.elements.actions;

import com.calc3d.app.CopyOfGui;
import com.calc3d.app.TabsManager;
import com.calc3d.app.elements.simpleelements.SimpleElement;

public class ShowSimpleElementAction extends SimpleElementAction {

	public ShowSimpleElementAction(String name, String actionCommand,
			CopyOfGui gui, SimpleElement element) {
		super(name, actionCommand, gui, element);
	}

	@Override
	public boolean isEnabled() {
		TabsManager tabsManager = model.getTabsManager();
		return tabsManager.getTabIndex(element.getName()) == -1;
	}

}
