/*
 * Copyright 2009-2014 PrimeTek.
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
package org.primefaces.ism.component.table.feature;

import java.io.IOException;
import javax.faces.context.FacesContext;
import org.primefaces.ism.component.table.Table;
import org.primefaces.ism.component.table.TableRenderer;

public class ResizableColumnsFeature implements DataTableFeature {

    public void decode(FacesContext context, Table table) {
        throw new RuntimeException("ResizableColumnsFeature should not decode.");
    }

    public void encode(FacesContext context, TableRenderer renderer, Table table) throws IOException {
        throw new RuntimeException("ResizableColumnsFeature should not encode.");
    }

    public boolean shouldDecode(FacesContext context, Table table) {
        return false;
    }

    public boolean shouldEncode(FacesContext context, Table table) {
        return false;
    }
}
