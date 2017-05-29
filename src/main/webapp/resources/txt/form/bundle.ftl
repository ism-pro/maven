<#if comment>

TEMPLATE DESCRIPTION:

This is Bundle.properties template for 'JSF Pages From Entity Beans' action. Templating
is performed using FreeMaker (http://freemarker.org/) - see its documentation
for full syntax. Variables available for templating are:

comment - always (Boolean; always FALSE)
projectName - display name of the project (type: String)
entities - list of beans with following properites:
entityClassName - controller class name (type: String)
entityDescriptors - list of beans describing individual entities. Bean has following properties:
label - part of bundle key name for label (type: String)
title - part of bundle key name for title (type: String)
name - field property name (type: String)
dateTimeFormat - date/time/datetime formatting (type: String)
blob - does field represents a large block of text? (type: boolean)
relationshipOne - does field represent one to one or many to one relationship (type: boolean)
relationshipMany - does field represent one to many relationship (type: boolean)
id - field id name (type: String)
required - is field optional and nullable or it is not? (type: boolean)
valuesGetter - if item is of type 1:1 or 1:many relationship then use this
getter to populate <h:selectOneMenu> or <h:selectManyMenu>

        This template is accessible via top level menu Tools->Templates and can
        be found in category JavaServer Faces->JSF from Entity.

</#if>
        PersistenceErrorOccured=A persistence error occurred.
        Create=Create
        View=View
        Edit=Edit
        Delete=Delete
        Close=Close
        Cancel=Cancel
        Save=Save
        SelectOneMessage=Select One...
        Home=Home
        Maintenance=Maintenance
        AppName=${projectName}

<#list entities as entity>
        ################################################################################
        ### ${entity.entityClassName}     ###
${entity.entityClassName}Title=${entity.entityClassName}
${entity.entityClassName}TitleCreate=${entity.entityClassName} - Création
${entity.entityClassName}TitleEdit=${entity.entityClassName} - Edition
${entity.entityClassName}TitleList=${entity.entityClassName} - Lister
${entity.entityClassName}TitleView=${entity.entityClassName} - Aperçu

${entity.entityClassName}Created=${entity.entityClassName} créé(e) avec succès.
${entity.entityClassName}Updated=${entity.entityClassName} modifié(e) avec succès.
${entity.entityClassName}Deleted=${entity.entityClassName} supprimé(e) avec succès.

${entity.entityClassName}Information=Permet la gestion "${entity.entityClassName}"

<#list entity.entityDescriptors as entityDescriptor>
${entity.entityClassName}Field_${entityDescriptor.id?replace(".","_")}=${entityDescriptor.label}
</#list>

<#list entity.entityDescriptors as entityDescriptor>
${entity.entityClassName}FieldInfo_${entityDescriptor.id?replace(".","_")}=Définit le/la ${entityDescriptor.label}
</#list>

<#list entity.entityDescriptors as entityDescriptor>
${entity.entityClassName}RequiredMsg_${entityDescriptor.id?replace(".","_")}=Requière le/la ${entityDescriptor.label} !
</#list>

<#list entity.entityDescriptors as entityDescriptor>
${entity.entityClassName}MsgSelect_${entityDescriptor.id?replace(".","_")}=Sélectionner le/la ${entityDescriptor.label} !
</#list>

${entity.entityClassName}ReleaseSelectedSummary=${entity.entityClassName} : rafraîchissement
${entity.entityClassName}ReleaseSelectedDetail=Ligne désélectionnée. <br >Rafraîchissement de la page principale
${entity.entityClassName}ToggleMultiCreationSummary=${entity.entityClassName} : Multi-création
${entity.entityClassName}ToggleMultiCreationDetail=Changement de mode de création à ...

${entity.entityClassName}PersistenceCreatedSummary=${entity.entityClassName} : creation
${entity.entityClassName}PersistenceCreatedDetail=Création réussie ... 
${entity.entityClassName}PersistenceUpdatedSummary=${entity.entityClassName} : édition
${entity.entityClassName}PersistenceUpdatedDetail=Edition réussie ... 
${entity.entityClassName}PersistenceDeletedSummary=${entity.entityClassName} : suppression
${entity.entityClassName}PersistenceDeletedDetail=Suppression réussie ... 
${entity.entityClassName}PersistenceError=${entity.entityClassName} : erreur

<#list entity.entityDescriptors as entityDescriptor>
${entity.entityClassName}DuplicationSummary_${entityDescriptor.id?replace(".","_")}==${entity.entityClassName} : duplication ${entityDescriptor.label} !
${entity.entityClassName}DuplicationDetail_${entityDescriptor.id?replace(".","_")}==Erreur de duplication ${entityDescriptor.label} ! !
</#list>

${entity.entityClassName}DlgHeader=${entity.entityClassName} - Suppression
${entity.entityClassName}DlgMessage=Confirmez-vous la suppression ?


</#list>
