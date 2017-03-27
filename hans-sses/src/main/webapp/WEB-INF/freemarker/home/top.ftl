<script type="text/javascript">
	function logout() {
		parent.location.href='/home/logout.htm';
	}
</script>
<div class="nav_menu" style="height:60px;">
<nav>
	<div class="nav toggle">
		<a id="menu_toggle"><i class="fa fa-bars"></i></a>
	</div>
	<div id="menuTitle" style="margin:20px 0px 0px 30px; text-align:left; float:left;">
		<strong style="font-size:15px;">Home</strong>
	</div>
 	<div id ="userMenu" style="text-align:right; margin:20px 30px; float:right;" >
		<strong class="name">Welcome ${loginUser.name}[${userId}].</strong>
	<a href="javascript:logout()" class="logout"><img src="/images/top/btn_logout.gif" alt="Logout" border="0"/></a>
 	</div>
</nav>
</div>