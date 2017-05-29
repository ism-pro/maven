<#if comment>

TEMPLATE DESCRIPTION:

This is XHTML template for 'JSF Pages From Entity Beans' action. Templating
is performed using FreeMaker (http://freemarker.org/) - see its documentation
for full syntax. Variables available for templating are:

entityName - name of entity being modified (type: String)
managedBean - name of managed choosen in UI (type: String)
managedBeanProperty - name of managed bean property choosen in UI (type: String)
item - name of property used for dataTable iteration (type: String)
comment - always set to "false" (type: Boolean)
nsLocation - which namespace location to use (http://xmlns.jcp.org in case of JSF2.2, http://java.sun.com otherwise)
entityDescriptors - list of beans describing individual entities. Bean has following properties:
label - field label (type: String)
name - field property name (type: String)
dateTimeFormat - date/time/datetime formatting (type: String)
blob - does field represents a large block of text? (type: boolean)
relationshipOne - does field represent one to one or many to one relationship (type: boolean)
relationshipMany - does field represent one to many relationship (type: boolean)
returnType - fully qualified data type of the field (type: String)
id - field id name (type: String)
required - is field optional and nullable or it is not? (type: boolean)
valuesGetter - if item is of type 1:1 or 1:many relationship then use this
getter to populate <h:selectOneMenu> or <h:selectManyMenu>
        bundle - name of the variable defined in the JSF config file for the resource bundle (type: String)

        This template is accessible via top level menu Tools->Templates and can
        be found in category JavaServer Faces->JSF from Entity.

</#if>
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml"
          xmlns:ui="${nsLocation}/jsf/facelets"
          xmlns:h="${nsLocation}/jsf/html"
          xmlns:f="${nsLocation}/jsf/core"
          xmlns:p="http://primefaces.org/ui">


        <ui:composition template="/tmpl/MainWindow.xhtml">

            <ui:define name="body">
                <h1><h:outputText value="${r"#{"}ism.${entityName}TitleList${r"}"}"/></h1>

                <h:form id="bodyForm">
                    <p:contextMenu id="contextMenu" for="datalist" >
                        <p:menuitem icon="ui-btn-Create"
                                    styleClass="options-btn"
                                    value="${r"#{"}ism.Create${r"}"}"
                                    actionListener="${r"#{"}${managedBean}${r".prepareCreate"}${r"}"}"
                                    action="Create.xhtml?faces-redirect=true"
                                    title="Permet de créer un nouveau ${entityName}"
                                    />
                        <p:separator />
                        <p:menuitem icon="ui-btn-View" iconPos="left"
                                    styleClass="options-btn"
                                    value="${r"#{"}ism.View${r"}"}"
                                    action="View.xhtml?faces-redirect=true"
                                    title="Permet de visualiser le ${entityName} sélectionné."
                                    disabled="${r"#{"}empty ${managedBean}.selected${r"}"}"
                                    />
                        <p:menuitem icon="ui-btn-Edit"
                                    styleClass="options-btn"
                                    value="${r"#{"}ism.Edit${r"}"}"
                                    action="Edit.xhtml?faces-redirect=true"
                                    title="Permet d'editer le ${entityName} sélectionné."
                                    disabled="${r"#{"}empty ${managedBean}.selected${r"}"}"
                                    />
                        <p:menuitem icon="ui-btn-Delete"
                                    styleClass="options-btn"
                                    value="${r"#{"}ism.Delete${r"}"}"
                                    title="Permet de supprimer le ${entityName} sélectionné !"
                                    actionListener="${r"#{"}viewTabManager.handleDestroy('${entityName?lower_case}')${r"}"}"
                                    update=":bodyForm:datalist, westGroup,:growl"
                                    onstart=";
                                ${r"#{"}viewConfirmDialog.dlg.setHeader(ism.${entityName}DlgHeader)${r"}"};
                                ${r"#{"}viewConfirmDialog.dlg.setSeverity('alert')${r"}"};
                                ${r"#{"}viewConfirmDialog.dlg.setMessage(ism.${entityName}DlgMessage)${r"}"};
                                    PF('mainConfirmDialog').show();"
                                    disabled="${r"#{"}empty ${managedBean}.selected${r"}"}"
                                    >
                            <p:confirm  />
                            </p:menuitem>
                        <p:menuitem icon="ui-btn-Refresh"
                                    styleClass="options-btn"
                                    value="${r"#{"}ism.Refresh${r"}"}"
                                    title="Permet de rafraichir la table des ${entityName} "
                                    actionListener="${r"#{"}${managedBean}.releaseSelected()${r"}"}"
                                    update=":bodyForm,:bodyForm:datalist,westGroup,:growl"
                                    disabled="${r"#{"}empty ${managedBean}.selected ${r"}"}"
                                    />
                        </p:contextMenu>






                    <p:dataTable id="datalist" widgetVar="datalist"
                                 value="${r"#{"}viewTabManager.${entityName?lower_case}${r"}"}" var="item"
                                 filteredValue="${r"#{"}viewTabManager.${entityName?lower_case}Filtered${r"}"}"

                                 selectionMode="single" selection="${r"#{"}${managedBean}.selected${r"}"}"
                                 rowIndexVar="_rowIndex"
                                 rowKey="${r"#{"}${item}.${entityIdField}${r"}"}"
                                 paginator="${r"#{"}tableView.paginator${r"}"}"
                                 rows="${r"#{"}tableView.rows${r"}"}"
                                 resizableColumns="${r"#{"}tableView.resizableColumns${r"}"}"
                                 rowsPerPageTemplate="${r"#{"}tableView.rowsPerPageTemplate${r"}"}"
                                 paginatorTemplate="${r"#{"}tableView.paginatorTemplate${r"}"}"
                                 currentPageReportTemplate="${r"#{"}tableView.currentPageReportTemplate${r"}"}"
                                 liveResize="${r"#{"}tableView.liveResize${r"}"}"
                                 reflow="${r"#{"}tableView.reflow${r"}"}"
                                 draggableColumns="${r"#{"}tableView.draggableColumns${r"}"}"
                                 sortMode="${r"#{"}tableView.sortMode${r"}"}"
                                 emptyMessage="${r"#{"}ism.EmptyList${r"}"}"
                                 >





                        <f:facet name="${r"{"}Exporters${r"}"}">
                            <div style="float:right" >
                                <p:commandLink ajax="false" onclick="PrimeFaces.monitorDownload(showStatus, hideStatus)">
                                    <p:graphicImage library="img" name="ism/std/ExtXLS.png" width="24" height="24"/>
                                    <p:dataExporter type="xls" target="datalist" fileName="${r"#{"}ism.${entityName}TitleList${r"}"}_${r"#{"}viewUtil.maintenant${r"}"}" 
                                                    postProcessor="${r"#{"}tableView.handlePostProcessXLS${r"}"}"/>
                                    </p:commandLink>
                                <p:commandLink ajax="false" onclick="PrimeFaces.monitorDownload(showStatus, hideStatus)">
                                    <p:graphicImage library="img" name="ism/std/ExtPDF.png" width="24" height="24"/>
                                    <p:dataExporter type="pdf" target="datalist" fileName="${r"#{"}ism.${entityName}TitleList${r"}"}_${r"#{"}viewUtil.maintenant${r"}"}"  />
                                    </p:commandLink>
                                </div>
                            </f:facet>


                        <f:facet name="header">
                            <p:commandButton id="toggler" type="button" value="Columns" 
                                             style="float:left;" icon="ui-icon-calculator" 
                                             />
                            <p:commandButton type="button" icon="ui-icon-print" style="float:left;">
                                <p:printer target="datalist"  />
                                </p:commandButton>

                            <p:columnToggler id="tableColToggler" datasource="datalist" trigger="toggler" >
                                <p:ajax event="toggle" update=":growl"
                                        listener="${r"#{"}${managedBean}.handleColumnToggle${r"}"}" />
                                </p:columnToggler>

                            <p:outputPanel style="float:right;">
                                <h:outputText value="${r"#{"}ism.Search${r"}"}"  />
                                <p:inputText id="globalFilter" onkeyup="PF('datalist').filter()" 
                                             style="width:150px;" placeholder="${r"#{"}ism.EnterKeyWord${r"}"}" />
                                </p:outputPanel>

                            </f:facet>






                        <p:ajax event="rowSelect" update="westGroup bodyForm"  />
                        <p:ajax event="rowUnselect" update="westGroup bodyForm" />
                        <p:ajax event="rowDblselect" listener="${r"#{"}tableView.handleRowDoubleClick${r"}"}" />
                        <p:ajax event="colReorder" update="datalist,tableColToggler,:growl" listener="${r"#{"}${managedBean}.handleColumnReorder${r"}"}"  />






                        <p:column headerText="${r"#{"}ism.CtrlShort${r"}"}" style="width:32px;text-align: center"
                                  exportable="false"
                                  visible="${r"#{"}${managedBean}.getIsVisibleColKey(ism.CtrlShort)${r"}"}" >
                            <p:commandButton icon="ui-btn-View" style="width: 22px; height: 16px;"
                                             title="${r"#{"}ism.View${r"}"}" action="View.xhtml?faces-redirect=true">
                                <f:setPropertyActionListener value="${r"#{"}item${r"}"}" target="${r"#{"}${managedBean}.selected${r"}"}" />
                                </p:commandButton>
                            </p:column>


<#list entityDescriptors as entityDescriptor>
                        <p:column headerText="${r"#{"}ism.${entityName}Field_${entityDescriptor.id?replace(".","_")}${r"}"}" <#if entityDescriptor.id?contains("Id")>style="max-width: 24px;" </#if>
                                  visible="${r"#{"}${managedBean}.getIsVisibleColKey(ism.${entityName}Field_${entityDescriptor.id?replace(".","_")})${r"}"}"
                                  sortBy="${r"#{"}item.${entityDescriptor.name}${r"}"}" filterBy="${r"#{"}item.${entityDescriptor.name}${r"}"}" 
    <#if entityDescriptor.dateTimeFormat?? && entityDescriptor.dateTimeFormat != "" >   
                                  filterFunction="${r"#{"}viewTabManager.handleDateRangeFilters${r"}"}" ${r">"}
    <#elseif entityDescriptor.returnType?matches(".*[Bb]+oolean")>
                                  filterMatchMode="equals" ${r">"}
                                  <f:facet name="filter">
                                <p:selectOneButton onchange="PF('datalist').filter()" styleClass="ui-btn-filters" >
                                    <f:converter converterId="javax.faces.Boolean" />
                                    <f:selectItem itemLabel="" itemValue="" />
                                    <f:selectItem itemLabel="" itemValue="true" />
                                    <f:selectItem itemLabel="" itemValue="false" />
                                    </p:selectOneButton>
                                </f:facet>
    <#else>
                            filterMatchMode="contains" ${r">"}
    </#if>  
    <#if entityDescriptor.dateTimeFormat?? && entityDescriptor.dateTimeFormat != "">
                            <h:outputText value="${r"#{"}${entityDescriptor.name}${r"}"}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:outputText>
    <#elseif entityDescriptor.returnType?matches(".*[Bb]+oolean")>
                            <p:selectBooleanCheckbox value="${r"#{"}${entityDescriptor.name}${r"}"}" disabled="true"/>
    <#else>
                            <h:outputText value="${r"#{"}${entityDescriptor.name}${r"}"}"/>
    </#if>
                            </p:column>



</#list>


                        </p:dataTable>
                    </h:form>

                </ui:define>






            <ui:define name="west" id="west">
                <h:panelGroup id="westGroup">
                    <p:commandButton id="createButton" 
                                     icon="ui-btn-Create"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Create${r"}"}" 
                                     actionListener="${r"#{"}${managedBean}.prepareCreate()${r"}"}"
                                     action="Create.xhtml?faces-redirect=true"
                                     title="Permet de créer un nouveau ${entityName}"
                                     />
                    <br />
                    <p:commandButton id="viewButton"
                                     icon="ui-btn-View"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.View${r"}"}"
                                     action="View.xhtml?faces-redirect=true"
                                     title="Permet de visualiser le ${entityName} sélectionné."
                                     disabled="${r"#{"}empty ${managedBean}.selected 
                                 <#list entityDescriptors as entityDescriptor>
                                     or empty ${managedBean}.selected.${entityDescriptor.id?replace(".","_")}${r"}"}"
                                             <#break>
                                 </#list>
                                     />
                    <br />
                    <p:commandButton id="editButton"
                                     icon="ui-btn-Edit"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Edit${r"}"}"
                                     action="Edit.xhtml?faces-redirect=true"
                                     title="Permet d'editer le ${entityName} sélectionné."
                                     disabled="${r"#{"}empty ${managedBean}.selected 
                                 <#list entityDescriptors as entityDescriptor>
                                     or empty ${managedBean}.selected.${entityDescriptor.id?replace(".","_")}${r"}"}"
                                             <#break>
                                 </#list>
                                     />
                    <p:commandButton id="deleteButton"
                                     icon="ui-btn-Delete"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Delete${r"}"}"
                                     title="Permet de supprimer le ${entityName} sélectionné !"
                                     actionListener="${r"#{"}viewTabManager.handleDestroy('${entityName?lower_case}')}"
                                     update=":bodyForm:datalist,westGroup,:growl"
                                     onstart=";
                                 ${r"#{"}viewConfirmDialog.dlg.setHeader(ism.${entityName}DlgHeader)${r"}"};
                                 ${r"#{"}viewConfirmDialog.dlg.setSeverity('alert')${r"}"};
                                 ${r"#{"}viewConfirmDialog.dlg.setMessage(ism.${entityName}DlgMessage)${r"}"};
                                     PF('mainConfirmDialog').show()"
                                     disabled="${r"#{"}empty ${managedBean}.selected 
                                 <#list entityDescriptors as entityDescriptor>
                                     or empty ${managedBean}.selected.${entityDescriptor.id?replace(".","_")}${r"}"}"
                                             <#break>
                                 </#list>
                                     >
                        <p:confirm  />
                        </p:commandButton>
                    <p:commandButton id="refreshButton"
                                     icon="ui-btn-Refresh"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Refresh${r"}"}"
                                     title="Permet de rafraichir la table des ${entityName} "
                                     actionListener="${r"#{"}${managedBean}.releaseSelected()${r"}"}"
                                     update=":bodyForm,:bodyForm:datalist,westGroup,:growl"
                                     disabled="${r"#{"}empty ${managedBean}.selected 
                                 <#list entityDescriptors as entityDescriptor>
                                     or empty ${managedBean}.selected.${entityDescriptor.id}${r"}"}"
                                             <#break>
                                 </#list>
                                     />
                    </h:panelGroup>



                </ui:define>
            <p:blockUI block="body" trigger="createButton, viewButton, editButton, deleteButton" >
                <p:graphicImage width="48px" height="48px" library="img" name="ism/std/Onload.gif"/>
                </p:blockUI>

            </ui:composition>

        </html>
