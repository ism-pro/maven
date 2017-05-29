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
                <h1><h:outputText value="${r"#{"}ism.${entityName}TitleView${r"}"}"/></h1>

                <h:form id="bodyForm">

                    <p:panelGrid id="panelForm" columns="2" columnClasses="columnFieldClass, columnContentClass" styleClass="panelGridClass"
                                 rendered="${r"#{"}${managedBean}.selected != null${r"}"}">

<#list entityDescriptors as entityDescriptor>
                        <h:outputText value="${r"#{"}ism.${entityName}Field_${entityDescriptor.id?replace(".","_")}${r"}"}"  />
    <#if entityDescriptor.dateTimeFormat?? && entityDescriptor.dateTimeFormat != "">
                        <h:outputText value="${r"#{"}${entityDescriptor.name}${r"}"}" 
                                      title="${r"#{"}ism.${entityName}FieldInfo_${entityDescriptor.id?replace(".","_")}${r"}"}" >
                            <f:convertDateTime pattern="${entityDescriptor.dateTimeFormat}" />
                            </h:outputText>
    <#elseif entityDescriptor.returnType?matches(".*[Bb]+oolean")>
                        <p:selectBooleanCheckbox value="${r"#{"}${entityDescriptor.name}${r"}"}" 
                                                 disabled="true"/>
    <#else>
                        <h:outputText value="${r"#{"}${entityDescriptor.name}${r"}"}" 
                                      title="${r"#{"}ism.${entityName}FieldInfo_${entityDescriptor.id?replace(".","_")}${r"}"}" />
    </#if>


</#list>


                        </p:panelGrid>

                    <h:panelGroup id="btn-crud-zone_id">
                        <p:commandButton icon="ui-btn-Print"
                                         styleClass="ui-btn">
                            <p:printer target="bodyForm"  />
                            </p:commandButton>
                        <p:commandButton icon="ui-btn-Edit"
                                         styleClass="ui-btn"
                                         value="${r"#{"}ism.Edit${r"}"}"
                                         action="Edit.xhtml?faces-redirect=true"
                                         title="Permet d'éditer le processus sélectionné."
                                         disabled="${r"#{"}empty ${managedBean}.selected 
                                         or empty ${managedBean}.selected.<#list entityDescriptors as entityDescriptor><#if entityDescriptor.id?contains("Id")>${entityDescriptor.id?replace(".","_")}</#if></#list>${r"}"}"
                                         />
                        <p:commandButton icon="ui-btn-Delete"
                                         styleClass="ui-btn"
                                         value="${r"#{"}ism.Delete}"
                                         action="./List.xhtml?faces-redirect=true"
                                         title="Permet de supprimer le ${entityName} sélectionné !"
                                         actionListener="${r"#{"}viewTabManager.handleDestroy('${entityName?lower_case}')}"
                                         update=":bodyForm,westGroup,:growl"
                                         onstart=";
                                     ${r"#{"}viewConfirmDialog.dlg.setHeader(ism.${entityName}DlgHeader)${r"}"};
                                     ${r"#{"}viewConfirmDialog.dlg.setSeverity('alert')${r"}"};
                                     ${r"#{"}viewConfirmDialog.dlg.setMessage(ism.${entityName}DlgMessage)${r"}"};
                                         PF('mainConfirmDialog').show()"
                                         disabled="${r"#{"}empty ${managedBean}.selected 
                                         or empty ${managedBean}.selected.<#list entityDescriptors as entityDescriptor><#if entityDescriptor.id?contains("Id")>${entityDescriptor.id?replace(".","_")}</#if></#list>${r"}"}"
                                         >
                            <p:confirm  />
                            </p:commandButton>
                        <p:commandButton icon="ui-btn-Cancel"
                                         styleClass="ui-btn"
                                         value="${r"#{"}ism.Cancel${r"}"}" 
                                         action="./List.xhtml?faces-redirect=true"
                                         update=":bodyForm,:growl"
                                         immediate="true"
                                         />

                        </h:panelGroup>

                    </h:form>

                </ui:define>





            <ui:define name="west" id="west">
                <h:panelGroup id="westGroup">
                    <p:commandButton icon="ui-btn-Print"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Print${r"}"}" type="button">
                        <p:printer target="bodyForm"  />
                        </p:commandButton>
                    <p:commandButton icon="ui-btn-Edit"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Edit${r"}"}"
                                     action="Edit.xhtml?faces-redirect=true"
                                     title="Permet d'éditer le processus sélectionné."
                                     disabled="${r"#{"}empty ${managedBean}.selected 
                                     or empty ${managedBean}.selected.<#list entityDescriptors as entityDescriptor><#if entityDescriptor.id?contains("Id")>${entityDescriptor.id?replace(".","_")}</#if></#list>${r"}"}"
                                     />
                    <p:commandButton icon="ui-btn-Delete"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Delete${r"}"}"
                                     action="./List.xhtml?faces-redirect=true"
                                     title="Permet de supprimer le ${entityName} sélectionné !"
                                     actionListener="${r"#{"}viewTabManager.handleDestroy('${entityName?lower_case}')}"
                                     update=":bodyForm,westGroup,:growl"
                                     onstart=";
                                 ${r"#{"}viewConfirmDialog.dlg.setHeader(ism.${entityName}DlgHeader)${r"}"};
                                 ${r"#{"}viewConfirmDialog.dlg.setSeverity('alert')${r"}"};
                                 ${r"#{"}viewConfirmDialog.dlg.setMessage(ism.${entityName}DlgMessage)${r"}"};
                                     PF('mainConfirmDialog').show()"
                                     disabled="${r"#{"}empty ${managedBean}.selected 
                                     or empty ${managedBean}.selected.<#list entityDescriptors as entityDescriptor><#if entityDescriptor.id?contains("Id")>${entityDescriptor.id?replace(".","_")}</#if></#list>${r"}"}"
                                     >
                        <p:confirm  />
                        </p:commandButton>
                    <p:commandButton icon="ui-btn-Cancel"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Cancel${r"}"}" 
                                     action="./List.xhtml?faces-redirect=true"
                                     update=":bodyForm,:growl"
                                     immediate="true"
                                     />

                    </h:panelGroup>
                </ui:define>
            </ui:composition>
        </html>