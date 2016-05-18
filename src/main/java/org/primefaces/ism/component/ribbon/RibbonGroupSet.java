/*
 * Generated, Do Not Modify
 */
/*
 * Copyright 2009-2013 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.ism.component.ribbon;

import javax.faces.component.UIComponentBase;
import javax.faces.application.ResourceDependencies;

@ResourceDependencies({

})
public class RibbonGroupSet extends UIComponentBase {


	public static final String COMPONENT_TYPE = "org.primefaces.ism.component.RibbonGroupSet";
	public static final String COMPONENT_FAMILY = "org.primefaces.ism.component";
	private static final String DEFAULT_RENDERER = "org.primefaces.ism.component.RibbonGroupSetRenderer";

	protected enum PropertyKeys {

		set
                ,style
		,styleClass;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
}
	}

	public RibbonGroupSet() {
		setRendererType(DEFAULT_RENDERER);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public java.lang.String getSet() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.set, null);
	}
	public void setSet(java.lang.String _set) {
		getStateHelper().put(PropertyKeys.set, _set);
	}

	public java.lang.String getStyle() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.style, null);
	}
	public void setStyle(java.lang.String _style) {
		getStateHelper().put(PropertyKeys.style, _style);
	}

	public java.lang.String getStyleClass() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.styleClass, null);
	}
	public void setStyleClass(java.lang.String _styleClass) {
		getStateHelper().put(PropertyKeys.styleClass, _styleClass);
	}

}