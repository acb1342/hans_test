
<script type="text/javascript">
	$(function() {
		//
		$.getJSON("/admin/menu/getRootMenu.json", function(data) {
			displayMenu(data);
		});
	});
	
	//
	function displayMenu(data) {
		var step;
		var makeHtml = "";
		var parentTitle;
		var title;
		var ulId;
		var old_type = "";
		var new_type = "";
		var url;

		if (data.length > 0) {
			for (step = 1; step < data.length; step++) {
				title = data[step].text;
				ulId = "ulId" + step;
				new_type = data[step].type;
				url = data[step].url;

				if(step>1 && new_type == "DIRECTORY"){
					makeHtml +="</ul></li>";
				}
				if(new_type == "DIRECTORY"){
					parentTitle = title + " > ";
					makeHtml += "<li><a>" + title + "<span class='fa fa-chevron-down'></span></a>";
					makeHtml += "<ul class='nav child_menu' id='child_menu'>";
				}else{
					makeHtml += "<li id="+data[step].id+" onclick=\'getTitleInPage("   + "\"" + url + "\"" + "\," +  "\"" + parentTitle + title + "\"," + data[step].id+  ")\'><a>" + title + "</a></li>";
				}
				old_type = new_type;
			}
			makeHtml +="</ul></li>";
			
			//console.log(makeHtml);
			$("#sideMenu").append(makeHtml);
		}
       $("body").append("<script src=" + "\'/css/gentelella-master/vendors/jquery/dist/jquery.min.js\'><" + "/script>");
		$("body").append("<script src=" + "\'/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js\'><" + "/script>");
		$("body").append("<script src=" + "\'/css/gentelella-master/build/js/custom.min.js\'><" + "/script>");
	}
	//
	function getTitleInPage(url,title,id) {
		$("#contentTitle").html("<strong style='font-size:15px;'>● "+ title +"</strong>");
		
		$('ul.nav.child_menu').find('li.active').removeClass('active');
		$("#"+id).addClass('active');
				
		if(url.length>0){
			$.ajax({
				type : "GET",
				url : url,
				success : function(response){

					$("#content").html(response);
},
				error : function(x,e){
					console.log("error!!");
					
					if(x.status==0){ $("#content").html("<h1 align='center'>"+x.status+"<p>You are offline!!n Please Check Your Network.</h1>");}
					else if(x.status==404){ $("#content").html("<h1 align='center'>"+x.status+"<p>Requested URL not found.</h1>");}
					else if(x.status==500){ $("#content").html("<h1 align='center'>"+x.status+"<p>Internel Server Error.</h1>");}
					else if(e=='parsererror'){ $("#content").html("<h1 align='center'>Error.nParsing JSON Request failed.</h1>");}
					else if(e=='timeout'){ $("#content").html("<h1 align='center'>Request Time out.</h1>");}
					else { $("#content").html("<h1 align='center'>Unknow Error.n"+x.responseText+"</h1>");}
					
					return false;
				}
			});
		}
	}
</script>


<div class="menu_section" id="sidebar-menu">
	<ul class="nav side-menu" id="sideMenu"></ul>
</div>


