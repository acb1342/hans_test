
<script type="text/javascript">
	$(function() {
		//
		$.getJSON("/admin1/menu/getRootMenu.json", function(data) {
			displayMenu(data);
		});
	});

	//
	function displayMenu(data) {

		console.log("=========="+JSON.stringify(data));

		var step;
		var makeHtml = "";
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

				if(old_type == "LEAF" && new_type == "DIRECTORY"){
					makeHtml +="</ul></li>";
				}

				if(new_type == "DIRECTORY"){
					makeHtml += "<li><a>" + title + "<span class='fa fa-chevron-down'></span></a>";
					makeHtml += "<ul class='nav child_menu'>";
				}else{
					makeHtml += "<li onclick=\'getTitleInPage("   + "\"" + url + "\"" + "\," +  "\"" + title + "\""+   ")\'><a>" + title + "</a></li>";
				}

				old_type = new_type;

			}
			makeHtml +="</ul></li>";
			console.log(makeHtml);

			$("#sideMenu").append(makeHtml);
		}

        $("body").append("<script src=" + "\'/css/gentelella-master/vendors/jquery/dist/jquery.min.js\'><" + "/script>");
		$("body").append("<script src=" + "\'/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js\'><" + "/script>");
		$("body").append("<script src=" + "\'/css/gentelella-master/build/js/custom.min.js\'><" + "/script>");

	}



	//
	function getTitleInPage(url,title) {
		console.log(url + " / "+title);
		if(url.length>0){
			$.ajax({
				type : "GET",
				url : url,
				success : function(response){
					$("#content").html(response);
					$("#contentTitle").html("<h2>"+ title +"</h2>");
				},
				error : function(){
					console.log("error!!");
					//err_page();
					return false;
				}
			});
		}

	}

</script>


<div class="menu_section" id="sidebar-menu">
	<ul class="nav side-menu" id="sideMenu"></ul>
</div>


