/* The fieldMap can point to the page number, if it is not taken, we'll just make it 
point to 'avail' */

var fieldMap  = new Array();
var indexMap = new Array();

var dropdowns = new Array();

var optionMap = new Array();
var currentOption = 0;

var dd_values =  new Array();


function isNormalInteger(str) {
    var n = ~~Number(str);
		    return String(n) === str && n > 0;
}

function drawFields(retData, noDraw)
{

	$('.fieldrow').remove();

	 for (var fname in retData['fields'])
	 {
	 	rfname = retData['fields'][fname];
		if(retData[rfname] ==null)
			continue;

		targetOb = retData[rfname];

		if(targetOb['type']  == null)
			continue;
		if(targetOb['type']  == 'dropdown')
		{
			dropdowns[rfname] = 1;
			dd_values[rfname] = targetOb['values'];
		}
		if(targetOb['page'] != null)
		{
			fieldMap[rfname]	 = targetOb['page'];
			t_td = true;
		}
		else
		{
			fieldMap[rfname]	 = "avail";
			t_td = false;
		}

		if(!noDraw)
		{
			optValues = eval(targetOb['values']);

			newDiv = "<div class=\"divRow fieldrow \" id='f_" + rfname + "'> <div class=\"divCell\">" + rfname +
			"</div> <div class=\"divCell\">" + targetOb['type'] + "</div> ";

	if(targetOb['type']  == 'dropdown')
	{

					newDiv  = newDiv + "<div class=\"divCell\">" ;
					newDiv = newDiv + "<select class='divSelect' valign='top' >";
	 for (lcindex in optValues)
	 {
//	 	alert(lcindex);
//		alert(optValues[lcindex]);
					newDiv = newDiv + "<option value='" + optValues[lcindex] + "'>";
					newDiv = newDiv + optValues[lcindex];
					newDiv = newDiv + "</option>";
	 }
					newDiv = newDiv + "</select>";
					newDiv = newDiv + "</div>"; 
	}
	else
	{
			newDiv  = newDiv + "<div class=\"divCell\">&nbsp;</div>";
	}
			if(!t_td)
			{
					
				newDiv = newDiv + "<div  class=\"divCell2\">" +
				"<a href='javascript:void(0)' onclick=\"return deletefield('" + rfname + "')\" ><img src=\"./images/delete.png\" /></a></div>";
			}
			else
			{
				newDiv = newDiv + " <div  class=\"divCell2\">&nbsp;</div>";
			}

newDiv = newDiv + "</div>";
//	alert(newDiv);
			$('#id_table_list').append(newDiv);
		}
	 }
}

function removeFieldRow(retData)
{
	$('#f_' + retData['id']).remove();

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
		$('.left_block_links').css('font-weight', 'italic');
		$('#tf_field_list').show();
		$('#lf_ctr.a').css('font-weight', 'bold');
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
	$('#tf_dd_name').val('');
	$('#ft_optval').val('');
	$('#dropdown_indi').html('');
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
	addval = "<tr id='ddv_" + rac + "' > <td id='dd0_" + rac + "' class='dd_value1' >" + vala2 + "</td> <td> &nbsp; &nbsp; <a href='javascript:void(0)' onclick=\"return removeDdValue('" + rac + "')\" > <img src=\"./images/delete.png\">  </a> </td> </tr> ";
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

function showmain()
{
	$('.tf_center').hide();
	$('.cond_row').hide();
	$('#sel_input_name').html('');
	$('#tf_notice').show();
}

function reset()
{
	jQuery.post("./addfield", {  'action' : 'reset'  }, 
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

function removefieldRow(retData)
{
	id = retData['id'];
	$('#f_' + id).remove();
}



function deletecond(condname, outputfield)
{
	
	jQuery.post("./addfield", {  'field' : outputfield, 'condname' : condname, 'action' : 'deletecond'}, 
		function(retData){
		alert(retData['message']);
		/* Send the action along with the function */
		if(retData['action'])
		{
			window[retData['action']](retData);
		}
  },"json");

}

function deletefield(field)
{
	
	jQuery.post("./addfield", {  'field' : field , 'action' : 'deletefield'}, 
		function(retData){
		alert(retData['message']);
		/* Send the action along with the function */
		if(retData['action'])
		{
			alert(retData['action']);
			window[retData['action']](retData);
			delete(fieldMap[field]);
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
		$('#b_addcondition').hide();
		
	}
	else
	{
		$('#b_adddisp_tr').show();
		$('#b_addcondition').show();
	}


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
		moreDa = "<div class='field0 left_delr' id='pagemain_" + st + "'> &nbsp;&nbsp;<a href='javascript:void(0)' onclick='getlampu(" + st + ")' > "
			+ retData['pages'][eachData] + "</a> </div>"
			st++;
		$('#lf_pageList').append(moreDa);
	}

}

function addCondRow(condName, inputField,inputArray, outputField, outputArray)
{
	//alert( condName + "=" + inputField + "=" + 
	//	inputArray + "=" + outputField + "=" + outputArray );

	newDiv = "<div class=\"divRow condrow \" >";
	newDiv = newDiv + "<div class=\"divCell3\">" + condName + "</div> ";
	newDiv = newDiv + "<div class=\"divCell3\">" + inputField + "</div> ";
	newDiv = newDiv + "<div class=\"divCell3\"><select>" ;
	for(ind in inputArray)
	{
		newDiv = newDiv + "<option>" + inputArray[ind] + "</option>";
	}
	newDiv = newDiv + "</select></div>" ;
	
	newDiv = newDiv + "<div class=\"divCell3\">" + outputField + "</div> ";
	newDiv = newDiv + "<div class=\"divCell3\"><select>" ;
	for(ind in outputArray)
	{
		newDiv = newDiv + "<option>" + outputArray[ind] + "</option>";
	}
	newDiv = newDiv + "</select></div>" ;

	newDiv = newDiv + "<div  class=\"divCell2\">" +
	"<a href='javascript:void(0)' onclick=\"return deletecond('" + condName + "','" + outputField +"')\" ><img src=\"./images/delete.png\" /></a></div>";
	newDiv = newDiv + "</div>";
	$('#id_tablecond_list').append(newDiv);
}

function drawConditions()
{
	$('.condrow').remove();
	ts = false;

		jQuery.post("./addfield", {  'action' : 'listconditions' }, 
			function(retData){
			{
				for(opfield in retData)
				{
					ts = true;
					//alert(opfield);
					outfield = opfield;
					hashlist = retData[opfield];
					//alert("To publish hashlist");
					//alert("hashlist is " + hashlist);
					for(hashkey in hashlist)
					{
						//alert("hash key is " + hashkey);
						//alert(hashlist[hashkey]);
						innerOb = JSON.parse(hashlist[hashkey]);
						//alert("after");

						//alert("innerOb is like " + innerOb);
						

						infield = innerOb['fieldName'];
						//alert(infield + " is the 2nd parameter");
						//alert("Done with printing inside");
						addCondRow(hashkey,infield, innerOb['input'],outfield,innerOb['output']);
					}
				}
				if(ts)
				{
					$('#id_tablecond_list').show();	
				}

			}
		},"json");
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

function addSpacing()
{
	spacing = $('#fd_spacing').val();
	if(spacing.trim() == '')
	{
		alert("No spacing value, please check");
		return;
	}
	alert(spacing);
	if(!  isNormalInteger(spacing))
	{
		alert("Number of pixels needs to be a positive integer");
		return;
	}
	pageId = $('#page_id').val();

	jQuery.post("./addfield", {  'action' : 'addtopage',
		 'pageId' : pageId, type : 'Spacing', field : spacing }, 
		function(retData){
		 alert(retData['message']);
	 if(retData['html'])
		{
			$('#mockup_top').append(retData['html']);
		}
	},"json");

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

function flipit(inarr)
{
	aka = new Array();
	for(eone in inarr)
	{
		aka[inarr[eone]] = inarr[eone];
	}
	return aka;
}

/* This should fetch the list of conditions and save in the page as well */
// TODO
function addCondition()
{
	listfields(true);
	drawConditions();
	$('.tf_center').hide();
	$('.cond_row').hide();
	$('#sel_input_name').html('');

	lenwa = flipit(dropdowns).length;
	if(lenwa <  2)
	{
		alert("You need at least two drop downs to set a conditional value assignment, in this demo");
		return;
	}
	$('#sel_input_name').append("<option selected value=''></option>");
	for(fieldName in dropdowns)
	{
		$('#sel_input_name').append("<option value='" + fieldName + "'>"+fieldName + "</option>");
	}
	$('#tr_input_row').show();
	$('#newcondition').show(200);
}

function populateInputVals()
{
	selVal = $('#sel_input_name').val();
	$('#sel_input_vals').html('');
	opts = eval(dd_values[selVal]);
	for(val in opts)
	{
		$('#sel_input_vals').append("<option value='" + opts[val] +
			"' >" + opts[val] + "</option>");
	}
	$('#sel_output_name').html('');
	$('#sel_output_name').append("<option selected value=''></option>");
	for(fieldName in dropdowns)
	{
		if(fieldName != selVal)
			$('#sel_output_name').append("<option value='" + fieldName + "'>"+fieldName + "</option>");
	}

	$('#tr_input_vals').show();
	$('#tr_output_name').show();
	
}

/* Send the condition to the server 
Add to the conditions list straightaway */
function addConditionSubmit()
{
	field1 = $('#sel_input_name').val();
	field2 = $('#sel_output_name').val();

	values1 = $('#sel_input_vals').val();
	values2 = $('#sel_output_vals').val();
	name = $('#cond_name').val();

	o_values1 = JSON.stringify(values1);
	o_values2 = JSON.stringify(values2);

	jQuery.post("./addfield", { 'field1' : field1, 'name' : name, 'values1': o_values1,  'action' : 'addcondition' ,
		'values2' : o_values2 , 'field2' : field2 }, 
		function(retData){
		alert(retData['message']);
		if(retData['action'])
		{
			window[retData['action']]();
		}
  },"json");
}

function populateOutputVals()
{
	selVal = $('#sel_output_name').val();
	$('#sel_output_vals').html('');

	opts = eval(dd_values[selVal]);
	for(val in opts)
	{

		$('#sel_output_vals').append("<option value='" + opts[val] +
			"' >" + opts[val] + "</option>");
	}

	$('#tr_output_vals').show();
	$('#tr_cond_name').show();
	$('#tr_cond_submit').show();
	
}

function drawThePage(retData)
{
	innerInd = 0;
	for( email in retData)
	{
		innerObj = retData[email];

		inhtml = "<div class=\"left_blocks\"> <div class=\"left_block_links\">  <a href='javascript:void(0)' onclick=\"$('#" + 
			innerInd + 
			"_div').toggle()\" > " + email + "  </a> </div> </div> <div width='100%' style='display:none' id='" + innerInd + "_div'>";
		inhtml = inhtml + " <table style='width:60%; border:none' >";

		for(innerkey in innerObj)
		{

				inhtml = inhtml + "<tr class='tr1'> <td class='td1'>";
				inhtml = inhtml+ innerkey ;
				inhtml = inhtml + "</td> <td class='td2'>" + innerObj[innerkey] + "</td> </tr>";
		}
		inhtml = inhtml + "</table></div>";

	  $('#disp_user').append(inhtml);
		innerInd = innerInd + 1;
	}			
}

function getthem()
{
	$('#disp_user').html('');
	jQuery.post("./addfield", {  'action' : 'showall' }, 
		function(retData){
		if(retData['message'])
		{
			alert(retData['message']);
		}
		for(i in retData)
		{
			// alert(i);
		}
		drawThePage(retData['all']);
  },"json");
}

