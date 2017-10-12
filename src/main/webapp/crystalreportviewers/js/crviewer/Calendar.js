if(typeof bobj=="undefined"){bobj={}}if(typeof bobj.crv=="undefined"){bobj.crv={}}if(typeof bobj.crv.Calendar=="undefined"){bobj.crv.Calendar={}}bobj.crv.Calendar.getInstance=function(){if(!bobj.crv.Calendar.__instance){bobj.crv.Calendar.__instance=bobj.crv.newCalendar()}return bobj.crv.Calendar.__instance};bobj.crv.Calendar.Signals={OK_CLICK:"okClick",CANCEL_CLICK:"cancelClick",ON_HIDE:"onHide"};bobj.crv.newCalendar=function(a){var c=MochiKit.Base.update;a=c({id:bobj.uniqueId()+"_calendar",showTime:false,date:new Date(),timeFormats:["HH:mm:ss","H:mm:ss","H:m:s","HH:mm","H:mm","H:m","h:mm:ss a","h:m:s a","h:mm:ssa","h:m:sa","h:mm a","h:m a","h:mma","h:ma"]},a);var b=newMenuWidget();b.widgetType="Calendar";bobj.fillIn(b,a);b._menuJustInTimeInit=b.justInTimeInit;c(b,bobj.crv.Calendar);b._curTimeFormat=b.timeFormats[0];b._cells=[];b._firstDay=0;b._numDays=0;return b};bobj.crv.Calendar._createHeaderButtons=function(){var a=8;var c=4;var b=46;var f=0;var d=12;var e=MochiKit.Base.bind;this._prevMonthBtn=newIconWidget(this.id+"_pm",_skin+"../lov.gif",e(this._onPrevMonthClick,this),"",_calendarPrevMonthLab,a,c,b,d);this._prevYearBtn=newIconWidget(this.id+"_py",_skin+"../lov.gif",e(this._onPrevYearClick,this),"",_calendarPrevYearLab,a,c,b,d);this._nextMonthBtn=newIconWidget(this.id+"_nm",_skin+"../lov.gif",e(this._onNextMonthClick,this),"",_calendarNextMonthLab,a,c,b,f);this._nextYearBtn=newIconWidget(this.id+"_ny",_skin+"../lov.gif",e(this._onNextYearClick,this),"",_calendarNextYearLab,a,c,b,f);this._prevMonthBtn.allowDblClick=true;this._prevYearBtn.allowDblClick=true;this._nextMonthBtn.allowDblClick=true;this._nextYearBtn.allowDblClick=true};bobj.crv.Calendar._createTimeTextField=function(){var a=MochiKit.Base.bind;this._timeField=newTextFieldWidget(this.id+"_time",a(this._onTimeChange,this),null,null,null,true,null,null,null,null)};bobj.crv.Calendar._createOKCancelButtons=function(){var a=MochiKit.Base.bind;this._okBtn=newButtonWidget(this.id+"_ok",L_bobj_crv_OK,a(this._onOKClick,this));this._cancelBtn=newButtonWidget(this.id+"_cancel",L_bobj_crv_Cancel,a(this._onCancelClick,this))};bobj.crv.Calendar.justInTimeInit=function(){this._menuJustInTimeInit();this._prevMonthBtn.init();this._prevYearBtn.init();this._nextMonthBtn.init();this._nextYearBtn.init();this._okBtn.init();this._cancelBtn.init();this._timeField.init();this._timeField.layer.style.width="100%";this._timeField.setValue(bobj.external.date.formatDate(this.date,this._curTimeFormat));this._timeRow=getLayer(this.id+"_timeRow");this._timeSep=getLayer(this.id+"_timeSep");this._month=getLayer(this.id+"_month");this._year=getLayer(this.id+"_year");var b=6*7;for(var a=0;a<b;a++){this._cells[a]=getLayer(this.id+"_c"+a)}this._update()};bobj.crv.Calendar.getHTML=function(){var k=bobj.html;var n=k.TABLE;var a=k.TBODY;var c=k.TR;var q=k.TD;var m=k.DIV;var p=k.SPAN;var b=k.A;this._createHeaderButtons();this._createTimeTextField();this._createOKCancelButtons();var l="MenuWidget_keyDown('"+this.id+"', event); return true";var d="eventCancelBubble(event)";var o="eventCancelBubble(event)";var f="eventCancelBubble(event)";var e={"class":"calendarTextPart"};var g=n({dir:"ltr",id:this.id,border:"0",cellpadding:"0",cellspacing:"0",onkeydown:l,onmousedown:d,onmouseup:o,onkeypress:f,"class":"menuFrame",style:{cursor:"default",visibility:"hidden","z-index":10000}},a(null,c(null,q(null,this._getMonthYearHTML())),c(null,q({align:"center"},n({border:"0",cellspacing:"0",cellpadding:"0",style:{margin:"2px","margin-top":"6px"}},c({align:"center"},q(e,L_bobj_crv_SundayShort),q(e,L_bobj_crv_MondayShort),q(e,L_bobj_crv_TuesdayShort),q(e,L_bobj_crv_WednesdayShort),q(e,L_bobj_crv_ThursdayShort),q(e,L_bobj_crv_FridayShort),q(e,L_bobj_crv_SaturdayShort)),c(null,q({colspan:"7",style:{padding:"2px"}},this._getSeparatorHTML())),this._getDaysHTML(),c(null,q({colspan:"7",style:{padding:"2px"}},this._getSeparatorHTML())),c({id:this.id+"_timeRow",style:{display:this.showTime?"":"none"}},q({colspan:"7",style:{"padding-top":"3px","padding-bottom":"3px","padding-right":"10px","padding-left":"10px"}},this._timeField.getHTML())),c({id:this.id+"_timeSep",style:{display:this.showTime?"":"none"}},q({colspan:"7",style:{padding:"2px"}},this._getSeparatorHTML())),c(null,q({colspan:"7",align:"right",style:{"padding-bottom":"3px","padding-top":"3px"}},n(null,a(null,c(null,q(null,this._okBtn.getHTML()),q(null,this._cancelBtn.getHTML())))))))))));return this._getLinkHTML("startLink_"+this.id)+g+this._getLinkHTML("endLink_"+this.id)};bobj.crv.Calendar._getMonthYearHTML=function(){var c=bobj.html;var d=c.TABLE;var b=c.TBODY;var f=c.TR;var a=c.TD;var e=c.DIV;var g=c.SPAN;return d({"class":"dialogzone",width:"100%",cellpadding:"0",cellspacing:"0"},b(null,f(null,a({style:{"padding-top":"1px"}},this._nextMonthBtn.getHTML()),a({rowspan:"2",width:"100%",align:"center","class":"dialogzone"},g({id:this.id+"_month",tabIndex:"0"},_month[this.date.getMonth()]),"&nbsp;&nbsp;",g({id:this.id+"_year",tabIndex:"0"},this.date.getFullYear())),a({style:{"pading-top":"1px"}},this._nextYearBtn.getHTML())),f({valign:"top"},a({style:{"padding-bottom":"1px"}},this._prevMonthBtn.getHTML()),a({style:{"padding-bottom":"1px"}},this._prevYearBtn.getHTML()))))};bobj.crv.Calendar._getSeparatorHTML=function(){var c=bobj.html;var d=c.TABLE;var b=c.TBODY;var e=c.TR;var a=c.TD;return d({width:"100%",height:"3",cellpadding:"0",cellspacing:"0",border:"0",style:backImgOffset(_skin+"menus.gif",0,80)},b(null,e(null,a())))};bobj.crv.Calendar._getLinkHTML=function(a){return bobj.html.A({id:a,href:"javascript:void(0)",onfocus:"MenuWidget_keepFocus('"+this.id+"')",style:{visibility:"hidden",position:"absolute"}})};bobj.crv.Calendar._getDaysHTML=function(){var a=bobj.html.TD;var d=bobj.html.DIV;var c="";for(i=0;i<6;++i){c+='<tr align="right">';for(j=0;j<7;++j){var e=j+(i*7);var b="(this,"+e+",event);";c+=a({id:this.id+"_c"+(i*7+j),"class":"calendarTextPart",onmousedown:"bobj.crv.Calendar._onDayMouseDown"+b,onmouseover:"bobj.crv.Calendar._onDayMouseOver"+b,onmouseout:"bobj.crv.Calendar._onDayMouseOut"+b,ondblclick:"bobj.crv.Calendar._onDayDoubleClick"+b,onkeydown:"bobj.crv.Calendar._onDayKeyDown"+b},d({"class":"menuCalendar"}))}c+="</tr>"}return c};bobj.crv.Calendar._update=function(){var h=6*7;var c=this.date.getDate();var b=this._getMonthInfo(this.date.getMonth(),this.date.getFullYear());var a=b.firstDay;this._firstDay=b.firstDay;this._numDays=b.numDays;var f=""+this.date.getFullYear();while(f.length<4){f="0"+f}this._year.innerHTML=f;this._month.innerHTML=_month[this.date.getMonth()];this._selectedDate=null;for(var e=0;e<h;e++){var g=this._cells[e].firstChild;var k="menuCalendar";var d=this._getDateFromCellNum(e);if(d<1||d>b.numDays){g.innerHTML="";g.tabIndex="-1"}else{g.innerHTML=""+d;g.tabIndex="0";if(d==c){k="menuCalendarSel";this._selectedDay=g}}g.className=k}};bobj.crv.Calendar._getMonthInfo=function(f,e){var c=new Date();c.setDate(1);c.setFullYear(e);c.setMonth(f);var a=c.getDay();c.setDate(28);var b=28;for(var d=29;d<32;d++){c.setDate(d);if(c.getMonth()!=f){break}b=d}return{firstDay:a,numDays:b}};bobj.crv.Calendar._setDayOfMonth=function(a){if(a>0&&a<=this._numDays){var c=this.date.getDate();if(a!=c){var b=this._getCellFromDate(c);if(b){b.firstChild.className="menuCalendar"}this._getCellFromDate(a).firstChild.className="menuCalendarSel";this.date.setDate(a)}}};bobj.crv.Calendar._getCellFromDate=function(a){var b=a+this._firstDay-1;return this._cells[b]};bobj.crv.Calendar._getDateFromCellNum=function(a){return a-this._firstDay+1};bobj.crv.Calendar._onDayMouseOver=function(c,e,b){var d=getWidget(c);var f=c.firstChild;var a=e-d._firstDay+1;if(a<1||a>d._numDays){f.className="menuCalendar"}else{f.className="menuCalendarSel"}};bobj.crv.Calendar._onDayMouseOut=function(c,e,b){var d=getWidget(c);var f=c.firstChild;var a=e-d._firstDay+1;if(a!=d.date.getDate()){f.className="menuCalendar"}};bobj.crv.Calendar._onDayMouseDown=function(c,e,b){var d=getWidget(c);var a=e-d._firstDay+1;d._setDayOfMonth(a)};bobj.crv.Calendar._onDayDoubleClick=function(b,d,a){var c=getWidget(b);c._onOKClick()};bobj.crv.Calendar._onDayKeyDown=function(d,f,c){c=new MochiKit.Signal.Event(d,c);var b=c.key().string;if(b==="KEY_ENTER"){var e=getWidget(d);var a=f-e._firstDay+1;e._setDayOfMonth(a)}};bobj.crv.Calendar._onPrevMonthClick=function(){if(this.date.getMonth()===0){this.date.setYear(this.date.getFullYear()-1);this.date.setMonth(11)}else{this.date.setMonth(this.date.getMonth()-1)}this._update()};bobj.crv.Calendar._onPrevYearClick=function(){this.date.setFullYear(this.date.getFullYear()-1);this._update()};bobj.crv.Calendar._onNextMonthClick=function(){this.date.setMonth(this.date.getMonth()+1);this._update()};bobj.crv.Calendar._onNextYearClick=function(){this.date.setFullYear(this.date.getFullYear()+1);this._update()};bobj.crv.Calendar._onOKClick=function(){this.restoreFocus();MochiKit.Signal.signal(this,this.Signals.OK_CLICK,this._copyDate(this.date));this.show(false)};bobj.crv.Calendar._copyDate=function(a){if(a){return new Date(a.getFullYear(),a.getMonth(),a.getDate(),a.getHours(),a.getMinutes(),a.getSeconds(),a.getMilliseconds())}return new Date()};bobj.crv.Calendar._onCancelClick=function(){this.restoreFocus();this.show(false);MochiKit.Signal.signal(this,this.Signals.CANCEL_CLICK)};bobj.crv.Calendar._onTimeChange=function(){var d=this._timeField.getValue();var a=null;var c=null;for(var b=0;b<this.timeFormats.length&&a===null;++b){c=this.timeFormats[b];a=bobj.external.date.getDateFromFormat(d,c)}if(a){this._curTimeFormat=c;this.date.setHours(a.getHours());this.date.setMinutes(a.getMinutes());this.date.setSeconds(a.getSeconds());this.date.setMilliseconds(a.getMilliseconds())}else{this._timeField.setValue(bobj.external.date.formatDate(this.date,this._curTimeFormat))}};bobj.crv.Calendar.setShowTime=function(b){var a=b?"":"none";this.showTime=b;if(this.layer){this._timeRow.style.display=a;this._timeSep.style.display=a}};bobj.crv.Calendar.setDate=function(a){this.date=a;if(this.layer){this._timeField.setValue(bobj.external.date.formatDate(this.date,this._curTimeFormat));this._update()}};bobj.crv.Calendar.show=function(d,a,e,c,b){ScrollMenuWidget_show.call(this,d,a,e);if(d){this.focus()}else{MochiKit.Signal.signal(this,this.Signals.ON_HIDE)}};bobj.crv.Calendar.focus=function(){if(this._selectedDay){this._selectedDay.focus()}};