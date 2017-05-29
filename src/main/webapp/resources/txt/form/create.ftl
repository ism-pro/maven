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
                <h1><h:outputText value="${r"#{"}ism.${entityName}TitleCreate${r"}"}"/></h1>

                <h:form id="bodyForm">

                    <p:panelGrid id="panelForm" columns="2" columnClasses="columnFieldClass, columnContentClass" styleClass="panelGridClass"
                                 rendered="${r"#{"}${managedBean}.selected != null${r"}"}">


<#list entityDescriptors as entityDescriptor>
                        <p:outputLabel value="${r"#{"}ism.${entityName}Field_${entityDescriptor.id?replace(".","_")}${r"}"}" <#if entityDescriptor.id?contains("Id")>rendered="false"</#if> for="${entityDescriptor.id?replace(".","_")}" />
                        <h:panelGrid columns="2" cellpadding="0px" cellspacing="0px" style="margin:0px; padding:0px;" <#if entityDescriptor.id?contains("Id")>rendered="false"</#if>>                
    <#if entityDescriptor.dateTimeFormat?? && entityDescriptor.dateTimeFormat != "">
                            <p:calendar id="${entityDescriptor.id?replace(".","_")}" pattern="dd/MM/yyyy" rendered="false"
                                        value="${r"#{"}${entityDescriptor.name}${r"}"}" 
                                        title="${r"#{"}ism.${entityName}FieldInfo_${entityDescriptor.id?replace(".","_")}${r"}"}" 
                                        required="true" 
                                        requiredMessage="${r"#{"}ism.${entityName}RequiredMsg_${entityDescriptor.id?replace(".","_")}${r"}"}" 
                                        showOn="button"/>
    <#elseif entityDescriptor.returnType?matches(".*[Bb]+oolean")>
                            <p:selectBooleanCheckbox id="${entityDescriptor.id?replace(".","_")}"
                                                     value="${r"#{"}${entityDescriptor.name}${r"}"}" 
                                                     required="true" 
                                                     requiredMessage="${r"#{"}ism.${entityName}RequiredMsg_${entityDescriptor.id?replace(".","_")}${r"}"}" 
                                                     />
    <#elseif entityDescriptor.blob>
                            <p:inputTextarea rows="4" cols="30" id="${entityDescriptor.id?replace(".","_")}" value="${r"#{"}${entityDescriptor.name}${r"}"}" 
                                             title="${r"#{"}ism.${entityName}FieldInfo_${entityDescriptor.id?replace(".","_")}${r"}"}"  <#if entityDescriptor.required>required="true" requiredMessage="${r"#{"}ism.${entityName}RequiredMsg_${entityDescriptor.id?replace(".","_")}${r"}"}" </#if>/>
    <#elseif entityDescriptor.id?contains("Company")>
                            <p:inputText id="${entityDescriptor.id?replace(".","_")}" widgetVar="${entityDescriptor.id?replace(".","_")}"
                                         value="${r"#{"}${entityDescriptor.name} = staffAuthController.company}${r"}"}"  
                                         style="display: none" 
                                         required="true" disabled="true" 
                                         requiredMessage="${r"#{"}ism.${entityName}RequiredMsg_${entityDescriptor.id?replace(".","_")}${r"}"}" />
                            <p:outputLabel style="margin-left: 5px;"
                                           value="${r"#{"}staffAuthController.company.CCompany${r"}"} - ${r"#{"}staffAuthController.company.CDesignation${r"}"}" />
    <#elseif entityDescriptor.relationshipOne>
                            <p:selectOneMenu id="${entityDescriptor.id?replace(".","_")}"  widgetVar="${entityDescriptor.id?replace(".","_")}"
                                             filter="true" filterMatchMode="contains"
                                             value="${r"#{"}${entityDescriptor.name}${r"}"}"
                                             required="true" 
                                             requiredMessage="${r"#{"}ism.${entityName}RequiredMsg_${entityDescriptor.id?replace(".","_")}${r"}"}" 
                                             >
                                <f:selectItem itemLabel="${r"#{"}ism.SelectList${r"}"}" itemValue=""/>
                                <f:selectItems value="${r"#{"}${entityDescriptor.valuesGetter}${r"}"}"
                                               var="${entityDescriptor.id?replace(".","_")}Item"
                                               itemLabel="${r"#{"}${entityDescriptor.id?replace(".","_")}Item.${entityDescriptor.id?replace(".","_").id?replace(".","_")}${r"}"} - ${r"#{"}${entityDescriptor.id?replace(".","_")}Item.stgdDesignation${r"}"}" 
                                               itemValue="${r"#{"}${entityDescriptor.id?replace(".","_")}Item${r"}"}"/>
                                </p:selectOneMenu>
    <#elseif entityDescriptor.relationshipMany>
                            <p:selectManyMenu id="${entityDescriptor.id?replace(".","_")}" value="${r"#{"}${entityDescriptor.name}${r"}"}" <#if entityDescriptor.required>required="true" requiredMessage="${r"#{"}ism.${entityName}RequiredMsg_${entityDescriptor.id?replace(".","_")}${r"}"}" </#if>/>
                            <f:selectItems value="${r"#{"}${entityDescriptor.valuesGetter}${r"}"}"
                                           var="${entityDescriptor.id?replace(".","_")}Item"
                                           itemValue="${r"#{"}${entityDescriptor.id?replace(".","_")}Item${r"}"}"/>
                            </p:selectManyMenu>
    <#else>
                            <p:inputText id="${entityDescriptor.id?replace(".","_")}" 
                                         value="${r"#{"}${entityDescriptor.name}${r"}"}" 
                                         title="${r"#{"}ism.${entityName}FieldInfo_${entityDescriptor.id?replace(".","_")}${r"}"}" 
                                         required="true" requiredMessage="${r"#{"}ism.${entityName}RequiredMsg_${entityDescriptor.id?replace(".","_")}${r"}"}" 
                                         />
    </#if>
                            <p:message for="${entityDescriptor.id?replace(".","_")}" />
                            </h:panelGrid>


</#list>
                        </p:panelGrid>








                    <h:panelGroup id="btn-crud-zone_id"  >
                        <p:outputLabel  value="${r"#{"}ism.CreateMultiple${r"}"}" style="margin-right: 10px;"/>
                        <p:selectBooleanButton  value="${r"#{"}${managedBean}.isOnMultiCreation${r"}"}" 
                                                onLabel="${r"#{"}ism.Yes${r"}"}" offLabel="${r"#{"}ism.No${r"}"}" 
                                                onIcon="ui-icon-check" offIcon="ui-icon-close" 
                                                styleClass="ui-btn"
                                                >
                            <p:ajax update=":bodyForm:btn-crud-zone_id,westGroup,:growl" listener="${r"#{"}${managedBean}.toggleMultiCreationFake()${r"}"}"  />
                            </p:selectBooleanButton>

                        <p:commandButton icon="ui-btn-Save"
                                         styleClass="ui-btn"
                                         value="${r"#{"}ism.Save${r"}"}" 
                                         actionListener="${r"#{"}${managedBean}.create${r"}"}" 
                                         onstart="${r"#{"}p:widgetVar('bodyForm:<#list entityDescriptors as entityDescriptor><#if entityDescriptor.id?contains("Company")>${entityDescriptor.id?replace(".","_")}</#if></#list>')${r"}"}.enable();"
                                         update=":bodyForm,:growl" 
                                         rendered="${r"#{"}${managedBean}.isOnMultiCreation${r"}"}"
                                         />
                        <p:commandButton icon="ui-btn-SaveOne"
                                         styleClass="ui-btn"
                                         value="${r"#{"}ism.Save${r"}"}" 
                                         actionListener="${r"#{"}${managedBean}.createUnReleasded()${r"}"}" 
                                         onstart="${r"#{"}p:widgetVar('bodyForm:<#list entityDescriptors as entityDescriptor><#if entityDescriptor.id?contains("Company")>${entityDescriptor.id?replace(".","_")}</#if></#list>')${r"}"}.enable();"
                                         update=":bodyForm,:growl"
                                         action="List.xhtml?faces-redirect=true"
                                         rendered="${r"#{"}!${managedBean}.isOnMultiCreation${r"}"}"
                                         />

                        <p:commandButton icon="ui-btn-Cancel"
                                         styleClass="ui-btn"
                                         value="${r"#{"}ism.Cancel${r"}"}" 
                                         update=":bodyForm,:growl"
                                         actionListener="${r"#{"}${managedBean}.prepareCreate()${r"}"}"
                                         action="./List.xhtml?faces-redirect=true"
                                         immediate="true"
                                         />

                        </h:panelGroup>

                    <p:messages showDetail="true" showSummary="false"
                                escape="false"/>


                    </h:form>

                </ui:define>





            <ui:define name="west" id="west">
                <h:panelGroup id="westGroup">
                    <p:commandButton icon="ui-btn-Save"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Save${r"}"}" 
                                     actionListener="${r"#{"}${managedBean}.create${r"}"}" 
                                     onstart="${r"#{"}p:widgetVar('bodyForm:<#list entityDescriptors as entityDescriptor><#if entityDescriptor.id?contains("Company")>${entityDescriptor.id?replace(".","_")}</#if></#list>')${r"}"}.enable();"
                                     update=":bodyForm,:growl" 
                                     rendered="${r"#{"}${managedBean}.isOnMultiCreation${r"}"}"
                                     form="bodyForm"
                                     />
                    <p:commandButton icon="ui-btn-SaveOne"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Save${r"}"}" 
                                     actionListener="${r"#{"}${managedBean}.createUnReleasded()${r"}"}" 
                                     onstart="${r"#{"}p:widgetVar('bodyForm:<#list entityDescriptors as entityDescriptor><#if entityDescriptor.id?contains("Company")>${entityDescriptor.id?replace(".","_")}</#if></#list>')${r"}"}.enable();"
                                     update=":bodyForm,:growl"
                                     action="List.xhtml?faces-redirect=true"
                                     rendered="${r"#{"}!${managedBean}.isOnMultiCreation${r"}"}"
                                     form="bodyForm"
                                     />

                    <p:commandButton icon="ui-btn-Cancel"
                                     styleClass="options-btn"
                                     value="${r"#{"}ism.Cancel${r"}"}" 
                                     update=":bodyForm,:growl"
                                     actionListener="${r"#{"}${managedBean}.prepareCreate()${r"}"}"
                                     action="./List.xhtml?faces-redirect=true"
                                     immediate="true"
                                     />

                    </h:panelGroup>
                </ui:define>
            </ui:composition>
        </html>
