/* The fieldMap can point to the page number, if it is not taken, we'll just make it 
point to 'avail' */

var fieldMap  = new Array();
var indexMap = new Array();

var optionMap = new Array();
var currentOption = 0;

function drawFields(retData, noDraw)
{
	$('#id_table_list').html('');

	 for (var fname in retData['fields'])
	 {
	 	rfname = retData['fields'][fname];
		if(retData[rfname] ==null)
			continue;

		targetOb = retData[rfname];

		if(targetOb['type']  == null)
			continue;
		if(targetOb['page'] != null)
		{
			fieldMap[rfname]	 = targetOb['page'];
		}
		else
		{
			fieldMap[rfname]	 = "avail";
		}

		if(!noDraw)
		{

				newDiv = "<tr class='tr1'> <td class='td1'>"
				+ rfname +
					"</td> <td class='td1'>"
				+ targetOb['type'] +
				"</td> </tr> "

				$('#id_table_list').append(newDiv);
		}
	 }
}

function listfields(nodraw)
{
	$('.tf_center').hide();
	jQuery.post("./addfield", {  'action' : 'listfields' }, 
		function(retData){
		if(retData['message'])
			alert(retData['message']);
		if(retData['fields'])
		{
			drawFields(retData, nodraw);	
		}
  },"json");
	if(nodraw)
	{
	}
	else
	{
		$('#tf_field_list').show();
	}

}

function addfield()
{
	$('#field_alltypes').toggle('slow');
}

function addPage()
{
	$('.tf_center').hide();
	$('#tf_addpage').toggle('slow');
}

function showTextFields(inputObj)
{
	$('.tf_center').hide();
	$('#tf_textfield_defn').show();
}

function showDdFields(inputObj)
{
	$('.tf_center').hide();
	$('#tf_ddfield_defn').show();
}




rac = 0;
ddArr = new Array();
function addDdValue()
{
	vala = $('#ft_optval');
	i = 0;

	vala2 = vala.val();
	if(vala2 == '')
		return;
	if(ddArr[vala2])
	{
		return;
	}
	ddArr[vala2] = 1;
	addval = "<div class='dd_value0' id='ddv_" + rac + "' > <div class=\"clear\"> </div> <div class='dd_value1 float_left' id='dd0_" + rac + "'>" + vala2 + 
		"</div> <div class='dd_value2 float_left'> &nbsp; &nbsp; <a href='javascript:void(0)' onclick=\"return removeDdValue('" + rac + "')\" > x </a> </div> </div> ";
		rac++;
		$('#dropdown_indi').append(addval);
}

function removeDdValue(theid)
{
	id1 = "ddv_" + theid;
	id0 = "dd0_" + theid;

	vala = $('#' + id0).html();
	delete( ddArr[vala]);
	$('#' + id1).remove();
}

function boogie()
{
	alert("This is a function to check if the boogie will work ");
}



function displayMessage(msg)
{
	alert(msg);
	/* Ideally another function that overlays a message box */

}



function postAndDisplay($dataString)
{
	alert($dataString['action']);
	jQuery.post("./addfield", eval($dataString),  
		function(retData){
		alert(retData['message']);
		if(retData['action'])
		{
			window[retData['action']]();
		}
  },"json");

}


function addPageSubmit()
{
	title = $('#tf_page_title').val();
	if(title == '')
	{
		alert("Empty title");
		return false;
	}
	jQuery.post("./addfield", {  'title' : title, 
		'action' : 'addpage'  }, 
		function(retData){
		alert(retData['message']);
		if(retData['action'])
		{
			window[retData['action']]();
		}
  },"json");
}
function addTextFieldSubmit()
{
	name = $('#tf_text_name').val();
	if(name == '')
	{
		alert("Empty name");
		return false;
	}
	dataString = new  Array();
	dataString['type'] = 'text';
	dataString['name'] = name;
	dataString['action'] = 'addfield';
	jQuery.post("./addfield", { 'type' : 'text', 'name' : dataString['name'], 'action' : dataString['action'] }, 
		function(retData){
		alert(retData['message']);
		if(retData['action'])
		{
			window[retData['action']]();
		}
  },"json");
}

function addDdFieldSubmit()
{
	name = $('#tf_dd_name').val();
	if(name == '')
	{
		alert("Please enter a name for your field");
		return false;
	}
	type = 'dropdown';

	valArr = new Array();
	$(".dd_value1").each( function()
	{
		valArr.push($(this).text());
	}
	);
	
	options = JSON.stringify(valArr);
	jQuery.post("./addfield", { 'type' : 'dropdown', 'name' : name , 'action' : 'addfield', values : options }, 
		function(retData){
		alert(retData['message']);
		/* Send the action along with the function */
		if(retData['action'])
		{
			window[retData['action']](retData);
		}
  },"json");

}

var pageArray = new Array() ;
var pageRevArray = new Array() ;

/* Send the action on the page after all */
function getlampu(pageId)
{
	title = pageArray[pageId];
	$('#page_title').html(title);

	$('#addf_select').html('');
	$('#add_disp_select').html('');

	c1 = 0;
	c2 = 0;

	for(fieldName in fieldMap)
	{
		
		if(fieldMap[fieldName] == "avail")
		{
			c1 = 1;
			$('#addf_select').append("<option value=\"" + fieldName + 
			"\" id='option_" + currentOption + "' >" + fieldName + " </option>");
		}
		else 
		{
			if(fieldMap[fieldName] < pageId)
			{
				c2 = 1;
				$('#add_disp_select').append("<option value=\"" + fieldName + 
			"\" id='option_" + currentOption + "' >" + fieldName + " </option>");
			}
		}
		optionMap[fieldName] = currentOption;
		currentOption = currentOption + 1;

	}
	if(c2  != 1)
	{
		$('#b_adddisp_tr').hide();
		
	}
	else
		$('#b_adddisp_tr').show();


	jQuery.ajaxSetup({async:false});
	/* Now make the request to get the html and display it */
		jQuery.post("./addfield", {  'action' : 'gethtml', 'pageId' : pageId }, 
			function(retData){
			if(retData['message'])
			 alert(retData['message']);
			{
				$('#mockup_top').html(retData['html']);
			}
		},"json");


	$('.tf_center').hide();
	$('#tf_each_page').show();
	$('#actual_page_demo').show();
	$('#page_id').val(pageId);
}

function showPages( retData )
{
	st = 0 ;
	$('#lf_pageList').html('');
	pageArray = retData['pages'];
	for(var eachData in retData['pages'])
	{
		pageRevArray[retData['pages'][eachData]] = eachData;
		moreDa = "<div class='field0 left_delr' id='pagemain_" + st + "'> <a href='javascript:void(0)' onclick='getlampu(" + st + ")' > "
			+ retData['pages'][eachData] + "</a> </div>"
			st++;
		$('#lf_pageList').append(moreDa);
	}

}

function refreshPages()
{
	if(fieldMap && (fieldMap.length > 0))
	{
		jQuery.post("./addfield", {  'action' : 'listpages' }, 
			function(retData){
			// alert(retData['message']);
			{
				showPages(retData);
			}
		},"json");
	}
	else
	{	
		jQuery.post("./addfield", {  'action' : 'listpages2' }, 
			function(retData){
			// alert(retData['message']);
			{
				drawFields(retData, true);	
				showPages(retData);
			}
		},"json");
	}
	$('#lf_pageList').toggle();

}
jQuery.ajaxSetup({async:false});


function showPara()
{
	 $('#para_textarea').val('');
	 $('#newpara').show(1000);
}

function addParaToPage()
{
	paraData = $('#para_textarea').val();
	if(paraData.trim() == '')
	{
		alert("Empty paragraph, please check ");
	}
	pageId = $('#page_id').val();

	jQuery.post("./addfield", {  'action' : 'addtopage',
		 'pageId' : pageId, type : 'Para', field : paraData }, 
		function(retData){
		 alert(retData['message']);
	 if(retData['html'])
		{
			$('#mockup_top').append(retData['html']);
		}
	},"json");

	 $('#newpara').hide(1000);
}

function addDisplayToPage()
{
	fieldName = $('#add_disp_select').val();
	pageId = $('#page_id').val();

		jQuery.post("./addfield", {  'action' : 'addtopage',
			 'pageId' : pageId, type : 'Display', field : fieldName  }, 
			function(retData){
			 alert(retData['message']);
		 if(retData['html'])
			{
				$('#mockup_top').append(retData['html']);
			}
		},"json");

}

function addFieldToPage()
{
	fieldName = $('#addf_select').val();
	pageId = $('#page_id').val();

		jQuery.post("./addfield", {  'action' : 'addtopage',
			 'pageId' : pageId, type : 'FieldData', field : fieldName  }, 
			function(retData){
			 alert(retData['message']);
		 if(retData['html'])
			{
				$('#mockup_top').append(retData['html']);
			}
		},"json");


	opval = optionMap[fieldName];
	fieldMap[fieldName] = $('#page_id').val();
	$('#option_' + opval).remove();
}
