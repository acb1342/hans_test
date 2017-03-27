<script type="text/javascript">
	$(function() {
		$("#userMenu").load("/home/title.htm");	
	});
	
	function logout() {
		parent.location.href='/home/logout.htm';
	}
</script>
<div class="nav_menu" style="height:60px;">
   <nav>
     <div class="nav toggle">
       <a id="menu_toggle"><i class="fa fa-bars"></i></a>
     </div>
     <div id ="userMenu" style="line-height:60px; text-align:right; margin-right:30px" >
     	<strong class="name">Welcome ${loginUser.name}[${userId}].</strong>
		<a href="javascript:logout()" class="logout"><img src="/images/top/btn_logout.gif" alt="Logout" border="0"/></a>
	  </div>
  </nav>
</div>