// JavaScript Document
var enablepersist="yes" //Enable saving state of content structure using session cookies? (on/off)
var collapseprevious="yes" //Collapse previously open content when opening present? (yes/no)

var contractsymbol='- ' //HTML for contract symbol. For image, use: <img src="whatever.gif">
var expandsymbol='+ ' //HTML for expand symbol.

var allcomments='yes';
var rec_limit='10';
var rating_titles = new Array()
rating_titles[1]='Interesting';
rating_titles[2]='Useful';
rating_titles[3]='Must Read';

if (document.getElementById){
document.write('<style type="text/css">')
document.write('.switchcontent{display:none;}')
document.write('</style>')
}

function getValue(input)
{
	if(!input)	
		return '';
	return input;
}

function getElementbyClass(rootobj, classname){
var temparray=new Array()
var inc=0
var rootlength=rootobj.length
for (i=0; i<rootlength; i++){
if (rootobj[i].className==classname)
temparray[inc++]=rootobj[i]
}
return temparray
}

function sweeptoggle(ec){
var thestate=(ec=="expand")? "block" : "none"
var inc=0
while (ccollect[inc]){
ccollect[inc].style.display=thestate
inc++
}
revivestatus()
}

function getRatingImg(rating)
{
	rating = Math.round(rating);
	return "/images/"+rating+"star.gif";
}

function contractcontent(omit){
var inc=0
while (ccollect[inc]){
if (ccollect[inc].id!=omit)
ccollect[inc].style.display="none";
ccollect[inc].
inc++
}
}

function expandcontent(curobj, cid){
var spantags=curobj.getElementsByTagName("SPAN")
var showstateobj=getElementbyClass(spantags, "showstate")
if (ccollect.length>0){
if (collapseprevious=="yes")
contractcontent(cid);
$('#'+cid).toggle("slow");
//document.getElementById(cid).style.display=(document.getElementById(cid).style.display!="block")? "block" : "none"
if (showstateobj.length>0){ //if "showstate" span exists in header
if (collapseprevious=="no")
showstateobj[0].innerHTML=(document.getElementById(cid).style.display=="block")? contractsymbol : expandsymbol
else
revivestatus()
}
}
}

function hideDiv(divid)
{
	myobj = $("#" + divid);
	setTimeout( "myobj.hide(\'fast\')",5000);
}

function setRead(divid)
{
	myobj = $("#" + divid);
	myobj.removeClass("header_text_link");
	myobj.addClass("header_text_link_read");
}

function comments_display1(){
  if(allcomments=="yes"){
    $('.block_main02a').hide('slow');
    $('#all_comments').html('Show Comments');
    allcomments="no"
  }else{
    $('.block_main02a').show('slow');
    $('#all_comments').html('Hide Comments');
    allcomments="yes"
  }
}

function comments_display(){
  if(allcomments=="yes"){
    $('.block_main03a').hide('slow');
    $('#all_comments').html('Show Comments');
    allcomments="no"
  }else{
    $('.block_main03a').show('slow');
    $('#all_comments').html('Hide Comments');
    allcomments="yes"
  }
}










function clickme(){
var ypos=window.pageYOffset;
if(!ypos)
  ypos=document.documentElement.scrollTop;
var xpos=0;
 window.scrollTo(xpos,ypos);
}
 var fol_id='';
 var comm_id='';
 var book_id='';
var savetext='';
var booktext='';
var sharetext='';
var sh_id='';


function showsavebox(url,id, entity){
  $('#target_id').val(id);
  if(entity){
    $('#entity_id').val(entity);
  }
  if(url){
    $('#entity_url').val(url);
  }

  if(savetext==''){
  savetext = $('#savetext').html();
  $('#savetext').html('');
  }

  fol_id = displaycontent('folder',fol_id,id,savetext)

}
function showsharebox(url,id, entity){
  $('#share_id').val(id);
  if(entity){
    $('#share_entity_id').val(entity);
  }
  if(url){
    $('#share_url').val(url);
  }
  if(sharetext==''){
  sharetext = $('#sharetext').html();
  $('#sharetext').html('');
  }

  sh_id = displaycontent('share',sh_id,id,sharetext)
  if(document.getElementById('share_notes')){
  if (CKEDITOR.instances['share_notes']) {
  CKEDITOR.remove(CKEDITOR.instances['share_notes']);
  }
  CKEDITOR.replace('share_notes');
  }

}

var commtext = '';
/* Removing entity since we already have it in another field 
*/
function showcommentbox(url,id)
{
  if(commtext==''){
  commtext = $('#commtext').html();
  $('#commtext').html('');

  }


  comm_id = displaycontent('sc',comm_id,id,commtext)
  
  $('#item_id').val(id);
  $('#item_url').val(url);
  
  if(($('#resentity_'+id).val()=='')||($('#resentity_'+id).val()==0))
	{
    $('#entity').val(0);
		entVal = 0;
    $('#dis_groups').show();
  }else
	{

    entVal = $('#resentity_'+id).val();
    $('#entity').val(entVal);
    $('#dis_groups').hide();
  }


  if(document.getElementById('textcontent')){
  if (CKEDITOR.instances['textcontent']) {
  CKEDITOR.remove(CKEDITOR.instances['textcontent']);
  }
  CKEDITOR.replace('textcontent');
  }
}
function showbookbox(url,id ,entity){
  if(booktext==''){
  booktext = $('#booktext').html();
  $('#booktext').html('');
  }
  book_id = displaycontent('book',book_id,id,booktext)
  if(document.getElementById('book_url'))
  {
    document.getElementById('book_url').value = url;
    document.getElementById('book_id').value  = id;
  }
  if((entity)&&(document.getElementById('book_entity')))
  {
    document.getElementById('book_entity').value  = entity;
  }
  
}
function displaycontent(source,fl_id,id,text){
  if(fl_id==''){
    fl_id = id;
    $('#'+source+'_'+fl_id).html(text);
    $('#'+source+'_'+fl_id).show("slow");   
    
  }else if(fl_id != id){
    $('#'+source+'_'+fl_id).html('');
    $('#'+source+'_'+fl_id).hide(1000);
    fl_id = id;
    $('#'+source+'_'+fl_id).html(text);
    $('#'+source+'_'+fl_id).show("slow");   
    
  }else{
  $('#'+source+'_'+fl_id).html('');
  $('#'+source+'_'+fl_id).hide(1000);
   fl_id=''
  }
  return fl_id;
  
}

function saveBookmark(){
 var url = $('#book_url').val();
 var key = $('#book_key').val();

// var notes = CKEDITOR.instances['book_notes'].getData();
// notes = escape(notes);
var entity = $('#book_entity').val();

var bookId = $('#book_id').val();
var des = $('#desc_'+ bookId).val();
var tit = $('#tit_'+ bookId).val();

 if(url==''){
var dataString = 'entity='+entity+'&key='+key;
 }else{
var dataString = 'url='+url+'&key='+key+ '&tit='+ tit + '&des=' + des ;
 }
  $.ajax({
  type: "GET",
  url: "/user/saveBookmarks",
  data: dataString,
  dataType: "html",
  cache: false,
  success: function(){
    var id = $('#book_id').val();
    showbookbox(url,id ,entity);
	$('#book_'+id).html('<div class="admin_err_placeholder" id="comm_pop" style="text-align:center;" >Saved as bookmark</div>');
	$('#book_'+id).show("slow");
	setTimeout("removePopup('comm_pop')",5000);
  }
  });
}
var month=new Array(12);
month[0]="January";
month[1]="February";
month[2]="March";
month[3]="April";
month[4]="May";
month[5]="June";
month[6]="July";
month[7]="August";
month[8]="September";
month[9]="October";
month[10]="Novembe";
month[11]="December";


function getDispTxt(intime)
{
    var d = new Date(intime*1000);
		mm =month[d.getMonth()];
  date_dis = d.getDate() + "  " + mm;
	return date_dis;
}

function feeds1Js(res)
{

var data = eval(res);

var rssentries = data.results;
var records = data.records;
var ratings = data.ratings;
var moreLink = data.moreLink;
var feedSources = data.feedSources;
var myfilter = data.filter;
var myratings = data.myratings;

var key = $('#key').val();
key = key *1;

var rec2 = "";


for ( var index in rssentries)
{
	 key = key + 1;
	value = rssentries[index];
	num_comments = 0;
	num_ratings = 0;
	avg_rating = 0;
	entity_id = 0;
	my_rating = 0;

	var hash = value['hashcode'];

	comments = null;


	if(records[hash])
	{
		num_comments = records[hash]['num_comments'];
		num_ratings = records[hash]['num_ratings'];
		avg_rating = records[hash]['avg_rating'];
		if(avg_rating)
			avg_rating = Math.round(avg_rating);
		entity_id = records[hash]['entity_id'];
		comments = records[hash]['comments'];
		my_rating = myratings[entity_id];
	}

	textbox = 'textcontent_'+key;
	source = 'sc_'+key;
	comment_div = 'comment_'+key;

	rec = "\ <input type='hidden' value='"+value['title']+"' id='tit_"+ key +"' />\ <input type='hidden' value='"+value['des']+"' id='desc_"+ key +"' /> ";


	fsId = value['feed_src_id'];
	feedSource = feedSources[fsId];
	feedImg = feedSource['image'];
	feedSrcId = feedSource['id'];
	feedSrcName = feedSource['name'];
	hasRead = value['read'];
	if (fsId) 
	{
		rec0 = "<input type='hidden' value='" + fsId + "' id='fsId_" + key + "' />";
		rec = rec + rec0;
	}

	rec0 =  "<input type='hidden' name='c_" + key + "' id='c_" + key + "' value='" + num_comments +"' />\
	<input type='hidden' value='" + entity_id +"' id='resentity_" + key +"' name='resentity_" + key +"'  />\
	\n<div class=\"section01\">\
	\n<div class=\"float_left06\"><img src=\"" + feedImg +"\" border=\"0\" class=\"img_border02\" /></div>\
	\n<div class=\"float_right02\">\
	\n<div class=\"block_main01a\">\
	\n<div class=\"contributor_block02\">\
	\n<div> ";
	rec = rec + rec0;


	if(hasRead == 'true'){
	viewclass='header_text_link_read';
	}else{
	viewclass='header_text_link';
	}

	rec0 = "\n<div class=\"float_left1\">\
	<a class=\"" + viewclass+"\" href=\""+ value['proxy'] + "\"   rel='tooltip' target=\"_se\"" ;
	if(myfilter == 'unread')
	{
		rec0 = rec0 + ' onclick=hideDiv(\'s_'  + key + '\') ';
	}
	else
	{
		rec0 = rec0 + ' onclick=setRead(\'a_'  + key + '\') ';
	}

	rec0 = rec0 +" > " 
	+ value['title'] +"</a><br/>";
	rec = rec + rec0;

	if(avg_rating>0)
	{
					 dis_avg ='<a title="'+rating_titles[avg_rating]+'" rel="tooltip" ><img border="0" src="' 
						+ getRatingImg(avg_rating) +'" ></a>&nbsp('+num_ratings+')';
				 }else{
					dis_avg ='<i>Not Rated</i>';
				 }

	rec0 = "\n<div><span class=\"text1\" id=\"avg_" 
		+ key+"\" >" + dis_avg+
		"</span> </div>\
		</div> \n<div class=\"float_right\"><span class=\"text2\" >" +  getDispTxt(value['created_at']) +  "</span></div>\
	\n<div class=\"clear\"></div>\
	</div>";
	rec = rec + rec0;

	rec0 = "\n<div>\
	\n<div class=\"float_left\"><span class=\"text1\">Feed Source: " + feedSrcName +"</span></div>" + 
	 "\n<div class=\"float_right\"><span class=\"text2\"><a href=\"javascript:clickme();\" class=\"a1\" onClick=\"showsavebox('" + value['url']+
		"','" + key+"');\" style=\"cursor:hand; cursor:pointer\">Save</a> |<a href=\"javascript:clickme();\" class=\"a1\" onClick=\"showsharebox('" + value['url']+"','" + 
		key+ "' );\" style=\"cursor:hand; cursor:pointer\">Share</a></span></div>\
	\n<div class=\"clear\"></div>\
	</div>\
	</div>\
	\n<div class=\"contributor_block03\"> "; 

	rec = rec + rec0;

	if(value['des']) { rec = rec +  value['des']; }

	rec0 =  "</div>\
	\n<div class=\"contributor_block04\">\
	\n<div class=\"float_left\" >\
	Your rating: </div> <div class=\"float_left02\" id='y_rate_" + key+"'  > ";

	avg_rating = "'0 px;'";
	if(!my_rating)
	{
	rec0  = rec0 + "	<ul class=\"star-rating \"> ";

	rec0 = rec0 + 	"	<li class=\"current-rating\" style=\"width:\"" + avg_rating +"\%\" id='star_" + key+"' ></li> ";
	rec0 = rec0 + "		<li><a href=\"javascript:clickme();\" title=\"Interesting\" class=\"one-star\" onclick=\"rateResource('" + value['url'] +"','1'," + key +", '' );\" >1</a></li>";

	rec0 = rec0 + "		<li><a href=\"javascript:clickme();\" title=\"Useful\" class=\"two-stars\" onclick=\"rateResource('" +
		value['url']+
			"','2'," + key+ ", '');\" >2</a></li>";
	rec0 = rec0 + "		<li><a href=\"javascript:clickme();\" title=\"Must Read\" class=\"three-stars\" onclick=\"rateResource('" + value['url']+"','3'," + key+", '');\" >3</a></li>";
	rec0 = rec0 + "		</ul>";
	}
	else
	{
		 rec0 = rec0 + '<img border="0" src="'+  
			getRatingImg(my_rating)+'">';
	}

	rec0 = rec0 + "</div> ";
	rec = rec + rec0;

	rec0 = "\n<div class=\"float_left02\">Comments: <span id='com_count_" + key;
	rec0 = rec0 + "' >" + num_comments +"</span> | <a href=\"javascript:clickme();\" class=\"a1\" onClick=\"showcommentbox('" ;
	rec0 = rec0	+	value['url'] + "','" + key+"');\" style=\"cursor:hand; cursor:pointer\">Add</a></div>";

	rec0 = rec0 + "\n<div class=\"float_left02\"><a href=\"javascript:clickme();\" onclick=\"return showbookbox('" + value['url']+"','" + key+"');\" ><img src=\"/images/icon_bookmark.gif\" border=\"0\" /></a></div>\
	\n<div class=\"clear\"></div>\
	</div>";

	rec = rec+ rec0;
	rec0 = "\n<div id=\"" + source + "\" class=\"switchcontent\">\
	</div>\
	\n<div id=\"folder_" + key+"\" class=\"switchcontent\">\
	</div>\
	\n<div id=\"book_" + key+"\" class=\"switchcontent\">\
	</div>\
	\n<div id=\"share_" + key+"\" class=\"switchcontent\">\
	</div>\
	</div>";
	rec = rec + rec0;
	if(comments)
	{ 
	rec0 = '\n<div class="block_main02a" id="comm_';
	rec0 = rec0 +  key +'">';
	rec = rec + rec0;

	rec0 =  "\n<div class=\"show_all_comment_div\" align=\"right\">";

	rec0 = rec0 + "<a  href=\"javascript:clickme();\" class=\"link\"  id=\"all_"
		+ key + "\" onclick=\"ToggleAllComments(\"" + 
			key+ "\",\"" + records[hash]['id']+
				"\");\" >Show All Comments</a></div>";
	rec = rec + rec0;
	rec0 = "\n<div id='"+ comment_div + "' >";
	rec = rec + rec0;

	comments = records[hash]['comments'];
	userImg = records[hash]['com_user'];

	rec0 = " \n<div class=\"commentator_block_main01\">\
	\n<div class=\"commentator_block01\">\
	\n<div class=\"float_left03\"><a href=\""+comments['profile_url']+"\"><img src=\"" + userImg  + "\" border=\"0\" class=\"img_border02\" /></a></div>\
	\n<div class=\"float_left04\">" ;
	rec = rec + rec0;

	/*if(comments['rating'] > 0 ){
					 dis_avg ='Rated: <img border="0" src="'+  
						getRatingImg(comments['rating'])+'">';
	} 
	else
	{
		dis_avg = 'Rated: Not Rated';
	}
	rec0 = "\
	<div class=\"float_right04\">" + dis_avg +" \
	</div>\*/
	rec0 = "\
	<span class=\"header_text\">" + comments['username']+"</span><br />\
	<span class=\"text1\">" + comments['comment']+"</span><br />\
	</div>\
	<div class=\"float_right\"><span class=\"text3\">" + getDispTxt(comments['time']) +"</div>\
	<div class=\"clear\"></div>\
	</div>\
	</div>\
	</div>\
	</div>";
	rec = rec + rec0;
	}
	else 
	{ 
	rec0 = "\
	<div class=\"block_main02a\"  id='comm_" + key+"' style='display:none;'>\
	<div class=\"show_all_comment_div\" align=\"right\"><a href='javascript:clickme();' class=\"link\" id='all_" + key+"' onclick=\"ToggleAllComments('" + key+"','" + "''" +"');\" >Hide All Comments</a></div> \
	<div id='" + comment_div+ "' >\
	</div>\
	</div>";
	rec = rec + rec0;
	}
	rec0 = "</div>\
	<div class=\"clear\"></div>\
	</div> ";
	rec = rec + rec0;
	rec2 = rec2 + rec;
}
$("#results").append(rec2);
$("#key").val(key);


if(moreLink){
	targHtml = "<a href=\"javascript:clickme();\" onclick=\"showMoreSummary('" + moreLink + "',feeds1Js);\" >Show More Results</a>";
	$("#more_results").html(targHtml);
//	alert("The target html is here " + targHtml);
}
else
{
	$("#more_results").html('');
}


}


function summary1Js(res)
{
     var data = eval(res);
     var rssentries = data.records;
     var moreLink = data.moreLink;
     var myratings = data.myratings;
     
var record = $('#key').val();
record = record *1;

      var rec1='';
      for (var entry in rssentries)
    {
			var r1 = '';
			var r2 = '';
			var r3 = '';
			var c1 = '';


       record++;
       var recMsg = '';
        var img = '';
        var url1='';
      var type =rssentries[entry].type;
		var entity_id = rssentries[entry].entity_id;
		var	my_rating = 0;
	  if(entity_id)
		{
			my_rating = myratings[entity_id];
	  }
			

      if(type == 3)
      {
        var recMsg = "Saved search";
        var img = "/images/search_img.gif";
        var url1 = '/page/singleSearch/'+entity_id;
      }else if(type == 2){
        if(rssentries[entry].num_objects == 1)
        var recMsg = "Saved Folder :"+rssentries[entry].num_objects+" object";
        else
        var recMsg = "Saved Folder :"+rssentries[entry].num_objects+" objects";
        var img = "/images/folder_img.gif";
      }else if(type == 4){
        if(rssentries[entry].url){
        var recMsg = "Message with shared link";
        var img = "/images/message_link_img.gif";
        }else{
        var recMsg = "Message";
        var img = "/images/message_img.gif";
        }
        var url1 = '/page/message/'+entity_id;
      }else if(type == 1){
        var fsId = rssentries[entry].feedSrcId;
        var recMsg = "Feed Source: ";
        var url1 = '/page/feed/'+entity_id;
        var img = "/images/icon_feed.gif";
      }else{
        var recMsg = "Saved search";
        var img = "/images/search_img.gif";
        var url1 = rssentries[entry].url;
			}
	 
    
    var avg = rssentries[entry].avg_rating * 18;
    var avg1='0 px'
    if(isNaN(rssentries[entry].avg_rating)||(rssentries[entry].avg_rating=='0')){
        var avg_rating = '<span id="avg_'+record+'" > <i>Not Rated</i></span>';
    }else{
        var avg_rating = '<span id="avg_'+record+'"><a title="'+rating_titles[rssentries[entry].avg_rating]+'" rel="tooltip" ><img border="0" src="' + getRatingImg(rssentries[entry].avg_rating) +'"></a>&nbsp('+rssentries[entry].num_ratings+')</span>';
    }
rec1+='<div class="section01">';
if(entity_id){
	
 rec1+='<div class="float_left06"> <a href="'+rssentries[entry].profile_url+'"><img border="0" class="img_border02" src="'+rssentries[entry].creator_img+'"></a></div>';
}
rec1+='<input type="hidden" value="'+rssentries[entry].title+'" id="tit_'+record+'" /><input type="hidden" value="'+rssentries[entry].description+'" id="desc_'+record+'" /><input type="hidden" name="c_'+record+'" id="c_'+record+'" value="'+rssentries[entry].num_comments+'" /><div class="float_right02"><div class="block_main01a">';
if(entity_id){
	rec1+='<div class="contributor_block01"><div class="float_left"><span class="header_text">'+getValue(rssentries[entry].username)+'</span> </div><div class="float_right"><span class="text2">'+getDispTxt(rssentries[entry].updated_at)+'</span></div><div class="clear"></div><div class="float_left"><span class="text1">';
		if(rssentries[entry].groups)
		{
			rec1 += 'Posted to '+rssentries[entry].groups;
		}
		
	rec1 +=	'</span></div>';
	if(type!=2)
	rec1+='<div class="float_right"><span class="text2"><a href="javascript:clickme();" class="a1" onClick="showsavebox(\' \','+record+','+entity_id+');" style="cursor:hand; cursor:pointer">Save</a>&nbsp;|&nbsp;<a href="javascript:clickme();" class="a1" onClick="showsharebox(\' \','+record+','+entity_id+');" style="cursor:hand; cursor:pointer">Share</a> </span></div>';
	rec1+='<div class="clear"></div></div>';
}
rec1+='<div class="contributor_block02">';
if(type==2)
rec1+='<div class="float_left"><a class="header_text_link" href="/folderrecord/'+entity_id+'" >'+rssentries[entry].title+'</a> <a href="#"><img border="0" src="'+img+'"></a></div>';
else
rec1+='<div class="float_left"><a class="header_text_link" href="'+url1+'" >'+rssentries[entry].title+'</a> <a href="'+url1+'" target="_se"><img border="0" src="'+img+'"></a></div>';
// alert(entity_id);
if(entity_id){
r1='onclick="rateResource(\'\',1,'+record+','+entity_id+');"';
r2='onclick="rateResource(\'\',2,'+record+','+entity_id+');"';
r3='onclick="rateResource(\'\',3,'+record+','+entity_id+');"';
c1 ='onClick="showcommentbox(\'\',' + record + ','+entity_id+');"';
rec1+='<input type="hidden" value="'+entity_id+'" id="resentity_'+record+'" name="resentity_'+record+'" />';
}else{
r1='onclick="rateResource(\''+rssentries[entry].url+'\',1,'+ record + ',\'\');"';
r2='onclick="rateResource(\''+rssentries[entry].url+'\',2,'+ record + ',\'\');"';
r3='onclick="rateResource(\''+rssentries[entry].url+'\',3,'+ record + ',\'\');"';
c1 ='onClick="showcommentbox(\''+rssentries[entry].url+'\',' + record + ');"';
rec1+='<input type="hidden" value="0" id="resentity_'+record+'" name="resentity_'+record+'" />';
}
rec1+='<div class="clear"><span class="text1">'+avg_rating+'</span>';
if(rssentries[entry].keywords)
rec1+='<br/><span class="text1">Tags:'+rssentries[entry].keywords+'</span>';
rec1+='</div></div><div class="contributor_block03">'+rssentries[entry].description+'</div><div class="contributor_block04">';
if(!entity_id){
	var sh_box ='onClick="showsharebox(\''+rssentries[entry].url+'\','+record+');"';
	var s_box ='onClick="showsavebox(\''+rssentries[entry].url+'\','+record+');"';
rec1+='<div class="new_seach_block01"><div class="float_right"><span class="text2"><a href="javascript:clickme();" class="a1"  style="cursor:hand; cursor:pointer" '+s_box+' >Save</a>&nbsp;|&nbsp;<a href="javascript:clickme();" class="a1" '+sh_box+'  style="cursor:hand; cursor:pointer">Share</a></span></div></div>';
 }
 rec1+='<div class="float_left" >Your rating:</div> <div class="float_left02" id="y_rate_'+record+'"  >';
 
 
if(my_rating)
{
rec1+= '<img border="0" src="' +
	 getRatingImg(my_rating) + '"  >';

// alert("Rating for " + entity_id + " is " + my_rating);
}
else
{
 
 tcas = '<ul class="star-rating "><li class="current-rating" style="width:'+avg1+'.px" id="star_'+record+'" ></li><li><a href="javascript:clickme();" title="Interesting" class="one-star" '+r1+' >1</a></li><li><a href="javascript:clickme();" title="Useful" class="two-stars" '+r2+' >2</a></li><li><a href="javascript:clickme();" title="Must Read" class="three-stars"  '+r3+' >3</a></li></ul>';
rec1 = rec1 + tcas;
// alert(tcas);
}
 
 rec1+= '</div>';
if(type!=2)
rec1+='<div class="float_left02">Comments:<span id="com_count_'+record+'" >'+rssentries[entry].num_comments+'</span> | <a href="javascript:clickme();" class="a1" '+c1+' style="cursor:hand; cursor:pointer">Add</a></div>';
rec1+='<div class="float_left02"></div><div class="float_left02">';

if(!rssentries[entry].bookmark_id)
rec1+='<a href="javascript:clickme();" onclick="return showbookbox(\' \','+record+','+entity_id+');" ><img border="0" src="/images/icon_bookmark.gif"></a>';
rec1+='</div><div class="clear"></div></div><div id="sc_'+record+'" class="switchcontent"></div><div id="folder_'+record+'" class="switchcontent"></div><div id="book_'+record+'" class="switchcontent"></div><div id="share_'+record+'" class="switchcontent"></div></div>';

if(rssentries[entry].num_comments > 0){
rec1+='<div style="display: block;" id="comm_'+record+'" class="block_main03a">';
 if(rssentries[entry].num_comments > 1){
rec1+='<div align="right" class="show_all_comment_div"><a  href="javascript:clickme();" class="link"  id="all_'+record+'" onclick="ToggleAllComments('+record+','+entity_id+');">Show All Comments</a></div>';
  } 
  var newcomment = eval(rssentries[entry].comments);
	var date_dis = getDispTxt( newcomment.time);

rec1+='<div id="comment_'+record+'" ><div class="commentator_block_main01"><div class="commentator_block01"><div class="float_left03"><a href="'+newcomment.profile_url +'" ><img border="0" class="img_border02" src="'+rssentries[entry].com_user+'"></a></div><div class="float_left04">';
if(newcomment.rating>0){
		rec1 +='<div class="float_right04"> <a href="javascript:clickme();" rel="tooltip" title="'+newcomment.rating+'"><img src="/images/'+newcomment.rating+'star.gif" border="0" /></a></div>';
		}else{
		rec1 +='<div class="float_right04"> <i>Not Rated</i></div>';
		}
rec1+='<span class="header_text">'+newcomment.username+'</span> <span class="text2">'+newcomment.comment+'</span></div><div class="float_right"><span class="text3">'+date_dis+'</span></div><div class="clear"></div></div></div></div></div>';
 }else{
rec1+='<div class="block_main03a"  id="comm_'+record+'" style="display:none;"><div class="show_all_comment_div" align="right"><a href="javascript:clickme();" class="link" id="all_'+record+'" onclick="ToggleAllComments('+record+','+entity_id+');" >Hide All Comments</a></div><div id="comment_'+record+'" ></div></div>';
 } 
rec1+='</div><div class="clear"></div></div>';

    }
  $("#results").append(rec1);
	$("#key").val(record);
  // $("#res_count").html(record+' Results');

  if(moreLink){
		targHtml = "<a href=\"javascript:clickme();\" onclick=\"showMoreSummary('" + moreLink + "',summary1Js);\" >Show More Results</a>";
    $("#more_results").html(targHtml);
  }
	else
	{
    $("#more_results").html('');
	}
  
}


/*  3 templates ,3 links */
function showMoreSummary(theLink, fnPtr)
{
//alert("This is the link we are downloading " + theLink);
  $.ajax({
  type: "GET",
  url: theLink,
  dataType: "json",
  cache: false,
  success: fnPtr
  });
}



//function moreInformation(type,grpid){
//  var start= record + 1;
//  var dataString = 'start='+start;
//  if(type!=''){
//  dataString +='&type='+type;
//  }
//  if(grpid!=''){
//  dataString +='&grp_id='+grpid;
//  }
//  $.ajax({
//  type: "GET",
//  url: "/home/summary1",
//  data: dataString,
//  dataType: "json",
 // cache: false,
//
//	}
//	}

function showUrl(id){
  $('#'+id).toggle();
}
var ck_url = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
function validateMessage(){
  var mesg = $.trim($('#message_title').val());
  if((mesg.length<1)||(mesg=='Enter your message title')){
      alert('Please enter the title of your message');
    return false;
  }

  var mesg1 = $.trim($('#message').val());
  if((mesg1.length<1)||(mesg1 =='Enter your message')){
      alert('Please add a message to be shared');
    return false;
  }

    var checkCount = 0;
    $("input[name='mesg_groups[]']:checked").each(function(i) {
    checkCount++;
    });
  if(checkCount==0){
    alert('Please select one or more groups');
    return false;
  }
  if($('#url').val()!=''){
    if(ck_url.test($("#url").val())){

    }else{
      alert("Please check the Url you have entered");
      return false;    
    }
  }
  
}

function checkandSubmit(){
  var checkCount=0;
  $("input[name='pre_feeds[]']:checked").each(function(i) {
        checkCount++;
  });

  if (checkCount == 0)
  {
    alert("Please select the feeds.");
    return false;
  }else{
    document.gform.submit();
  }

}


function cleardefault(id,default_mesg){
  if($('#'+id).val()==default_mesg){    
  $('#'+id).val(' ');
  }

}

function  validateProfile()
{
	var state=true;
	$('#errors').html('');  
	if($("#first_name").val()==''){
	$('#errors').append("Please enter your first name.");
	state=false;
	}
	if($("#last_name").val()==''){
	$('#errors').append("Please enter your last name.");
	state=false;

	}
	if($("#email").val()==''){
	$('#errors').append("Please enter your email.");
	state=false;
	}
	if($("#work_ph").val()!=''){
		 if(checkphone($("#work_ph").val())==false){
		$('#errors').append("Please enter a valid phone number.");
		state=false;
		}
	}
	if($("#home_ph").val()!=''){
		 if(checkphone($("#home_ph").val())==false){
		$('#errors').append("enter valid phone");
		state=false;
		}
	}
	/*
	if($("#cell_ph").val()!=''){
		 if(checkphone($("#cell_ph").val())==false){
		$('#errors').append("enter valid phone");
		state=false;
		}
	}
	*/
	if($("#user_image").val()!=''){
	if(checkimage($("#user_image").val())){
					
				 }else{
					$('#errors').append("Please upload a jpeg, png or gif image file only");
					state=false;
				 }

		
	}


	if(state!=false){

		return true;
		}else{
		
		$('#errors1').html($('#errors').html())
		$('#error_results').show();
		$('#errors').html('');
		return false;
		}
}
var extArray = new Array(".jpg", ".png", ".gif",".jpeg");
var extArray1 = new Array(".csv");

function checkimage(file){
   allowSubmit = false;
while (file.indexOf("\\") != -1)
    file = file.slice(file.indexOf("\\") + 1);
    ext = file.slice(file.indexOf(".")).toLowerCase();
    for (var i = 0; i < extArray.length; i++) {
    if (extArray[i] == ext) { allowSubmit = true; break; }
    }
    if (allowSubmit){
    return true;
    }else{
        return false;
  }
}

function checkCSV(file){
   allowSubmit = false;
while (file.indexOf("\\") != -1)
    file = file.slice(file.indexOf("\\") + 1);
    ext = file.slice(file.indexOf(".")).toLowerCase();
    for (var i = 0; i < extArray1.length; i++) {
    if (extArray1[i] == ext) { allowSubmit = true; break; }
    }
    if (allowSubmit){
    return true;
    }else{
        return false;
  }
}
var reg = new RegExp("/[0-9]{10}/");
function checkphone(phone) {
       if (phone.length < 1 && reg.test(phone)) {
                 return false;
        }else{ return true };
}
var ck_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i 
var ck_password =  /^[A-Za-z0-9!@#$%^&*()_]{6,15}$/;
function validateLogin(){
var logincheck = true;
if(!ck_email.test($('#email').val())){
    $('#login_error1').html('Please enter a valid email.');
      logincheck = false;
    }else{
    $('#login_error1').html('');
    }
if(!ck_password.test($('#password').val())){
    $('#login_error2').html('Your password must be at least 6 characters in length.No special characters are allowed.');
      logincheck = false;
    }else{
    $('#login_error2').html('');
    }
if(logincheck ==false){
$('#display_error').show();
return false;
}else{
$('#display_error').hide();
return true;
}

}

function validateLogin1(){
var logincheck = true;
if(!ck_email.test($('#email').val())){
    $('#login_error1').html('Please enter a valid email.');
      logincheck = false;
    }else{
    $('#login_error1').html('');
    }
if($('#password').val()==''){
    $('#login_error2').html('Please enter your password.');
      logincheck = false;
    }else{
    $('#login_error2').html('');
    }
if(logincheck ==false){
$('#display_error').show();
return false;
}else{
$('#display_error').hide();
return true;
}

}

function validateform1()
{
var logincheck = true;
var pwdcheck = true;

var fname = $("#first_name");
if(fname)
{
	if($("#first_name").val()=='')
	{
		$('#fname_error').html("Please enter your first name.");
		$('#fname_error').show();
      logincheck = false;
	}
	else
	{
		$('#fname_error').hide();
	}
	lname = $("#last_name");
	if(lname.val()=='')
	{
		$('#lname_error').html("Please enter your last name.");
      logincheck = false;
		$('#lname_error').show();
	}
	else
	{
		$('#lname_error').hide();
	}
}



if(!ck_email.test($('#emailid').val())){
    $('#email_error').html('Please enter a valid email.');
      logincheck = false;
    }else{
    $('#email_error').html('');
    }
if(!ck_password.test($('#new_pwd').val())){
    $('#pwd_error').html('Password --Must be at least 6 characters in length.');
      pwdcheck = false;
      logincheck = false;
    }else{
    $('#pwd_error').html('');
    }
if(!ck_password.test($('#conf_pwd').val())){
    $('#pwd_error').append('<br/>The passwords you entered do not match.');
      pwdcheck = false;
      logincheck = false;
    }
if(pwdcheck){
  if($('#conf_pwd').val()!=$('#new_pwd').val()){
    $('#pwd_error').append('No match b/w passwords');
      logincheck = false;
    }
    }
    
if(!logincheck) 
{
$('#error_results').show();
return false;
}

if(!pwdcheck)
{
$('#error_results').show();
return false;
}

$('#error_results').hide();
return true;
}



function validatenewgroup(){
  if($('#grp_name').val()==''){
  alert('You have not entered a valid group name');
  return false;
  }else{
    if($('#grp_name').val().length>75){
        $('#errors').append('Group name should not be more than 75 characters<br/>');
        state=false;
          
        } 
  }

  var uemails =$('#grp_admins').val();
  var mailarray = new Array();
    if(uemails){
      mailarray = uemails.split(',')
        for(var i=0;i<mailarray.length;i++){
          if(!ck_email.test(mailarray[i])){
          alert('please enter the valid emailaddress');
          return false;
          }
        }

    }

return true;
}
function validatefolder(){
  var state = true;
  var str_length = $('#folder_name').val().length;
if(str_length==0){
  $('#errors').append('Please enter a folder name<br/>');
  state=false;
}else if(str_length>75){
  $('#errors').append('Please enter a folder name that is less than 75 characters<br/>');
  state=false;
}
var count =word_count('#description');
if(count ==0){
  $('#errors').append('Please enter a folder description<br/>');
  state=false;
}else{
  if(count>50){
  $('#errors').append('Please enter a folder description that is less than  50 words<br/>');
  state=false;
  }
}
if(state!=false){
  document.pform.submit();
  }else{
  $('#errors1').html($('#errors').html())
  $('#error_results').show();
  $('#errors').html('');
  }
  return false;
}

function ChangePwd(){
  var state=true;
    if(document.getElementById('new_pwd').value=="")
    {
      $('#errors').append("Enter New password");
      document.getElementById('new_pwd').focus();
       state = false;     
    }
    else if(!ck_password.test(document.getElementById('new_pwd').value))
    {
      $('#errors').append("Password should be greater than 6 characters no special characters");
      document.getElementById('new_pwd').focus();
        state = false;        
    }
    else if(document.getElementById('re_pwd').value=="")
    {
      $('#errors').append("Enter confirm password");
      document.getElementById('re_pwd').focus();
       state = false;         
    }
    else if(!ck_password.test(document.getElementById('re_pwd').value))
    {
      $('#errors').append("Password should be greater than 6 characters no special characters");
      document.getElementById('re_pwd').focus();
       state = false;       
    }
    else if (document.getElementById('new_pwd').value != document.getElementById('re_pwd').value)
    {
      $('#errors').append("The passwords entered do not match. ");
	  $('#re_pwd').val('');
	  $('#new_pwd').val('');
      document.getElementById('new_pwd').focus();
       state = false;   
    }
  if(state!=false){
  document.pform.submit();
  }else{
  $('#errors1').html($('#errors').html())
  $('#error_results').show();
  $('#errors').html('');
  return false;
  }
  
    
}
function validatefeedurl(){
	var feed_tit = $.trim($("#feed_title").val());
	if(feed_tit.length<2){
    alert("Please enter a feed title");
    return false; 
    }
  if(!ck_url.test($("#feed_url").val())){
      alert("Please enter a valid URL in the form of http://www....");
    return false; 
    }
  document.s_form.submit();

  }


/* The fieldMap can point to the page number, if it is not taken, we'll just make it 
point to 'avail' */

var fieldMap  = new Array();
var indexMap = new Array();

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
			$('#addf_select').append("<option value=\"" + fieldName + " \">  " + fieldName + " </option>");
		}
		else 
		{
			if(fieldMap[fieldName] < pageId)
			{
				c2 = 1;
				$('#add_disp_select').append("<option value=\"" + fieldName + " \">  " + fieldName + " </option>");
			}
		}
	}
	if(c2  != 1)
	{
		$('#b_adddisp_tr').hide();
		
	}
	else
		$('#b_adddisp_tr').show();
		
	$('.tf_center').hide();
	$('#tf_each_page').show();
	$('#actual_page_demo').show();
	$('#page_id').val(pageId);
}

function showPages( retData )
{
	st = 0 ;
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
	if(fieldMap)
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
				showPages(retData);
			}
		},"json");
	}

}
jQuery.ajaxSetup({async:false});

function addFieldToPage()
{
	fieldName = $('#addf_select').val();
	pageId = $('#page_id').val();
	index = indexMap[pageId];
	if(!index)
		index = '0'

		jQuery.post("./addfield", {  'action' : 'addtopage',
			'index' : index, 'pageId' : pageId, type : 'FieldData' }, 
			function(retData){
			 alert(retData['message']);
			{
				$('#mockup_top').append(retData['html']);
			}
		},"json");
}
}
