package com.calc3d.app.export;

import com.calc3d.app.elements.simpleelements.SimpleElement;

public interface IExporter {
	public void export(SimpleElement element, String source);
}
