<!DOCTYPE html>
<html lang="en">
  <head>
	<#--<#include "/include/header.jspf">
	<#include "/include/footer.jspf"> -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>MOBILEPARK!</title>
    
    <!-- Bootstrap -->
    <link href="/css/gentelella-master/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="/css/gentelella-master/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <!-- <link href="/css/gentelella-master/vendors/nprogress/nprogress.css" rel="stylesheet"> -->
    <!-- iCheck -->
    <link href="/css/gentelella-master/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
	
    <!-- bootstrap-progressbar -->
    <link href="/css/gentelella-master/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- JQVMap -->
    <link href="/css/gentelella-master/vendors/jqvmap/dist/jqvmap.min.css" rel="stylesheet"/>
    <!-- bootstrap-daterangepicker -->
    <link href="/css/gentelella-master/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="/css/gentelella-master/build/css/custom.min.css" rel="stylesheet">
    
    <style type="text/css">
    /* 메뉴 색상 관련 */
    
    .nav_title {background:none;}
    .nav.side-menu>li.active>a {background:none;}
    .left_col{background:#6B8E23;}   /* 메뉴 색 */
    .nav-sm ul.nav.child_menu{background:#6B8E23;}                                               /* 축소시 메뉴 색  */
    .nav.child_menu>li>a, .nav.side-menu>li>a {}                                                 /* 메뉴 글씨  */
    .nav.side-menu>li>a:hover {#F2F5F7!important;}                                               /* 메뉴 글씨 (마우스오버)   */
    
    .nav-md ul.nav.child_menu li:after{border-left:1px solid #405a01;}                           /* 메뉴 오픈시 좌측 라인 색   */
    .nav-md ul.nav.child_menu li:before{background:#405a01;}                                     /* 메뉴 오픈시 좌측 서클 색   */
    .nav.side-menu>li.active, .nav.side-menu>li.current-page{border-right:5px solid #405a01;}    /* 메뉴 오픈시 우측 라인 색   */
    
    
    /* .left_col{background:#7ac24b;} #6B8E23 거의유사 */
    .nav-md .container.body .right_col{min-height:100%;}
    #title{padding-top:50px; padding-left:230px; background:#F7F7F7;}
    fieldset{min-width:100%;} 
    table{width:100%;}
    body{background:none;}
    td{vertical-align:middle;}

	</style>
	
  </head>


  <body class="nav-md" id="main-body">
    <div class="container body">
      <div class="main_container" style="height:100%;">
      
       <!-- left -->
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">            
            <div class="navbar nav_title" style="height: auto;">
              <a href="/home/home.htm"><img src="/images/icons/hans_logo.png" style="max-width: 100%;"></a>
            </div>
            <div class="clearfix"></div>
            <br />
            <!-- sidebar -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu"> </div>
          </div>
        </div>
        <!-- /left -->
        
		<div>
        <!-- header -->
        <div class="top_nav" id="header"></div>
	
		<div id="title" role="main" ></div>
	
        <!-- content -->
        <div class="right_col" role="main" id="content"></div>
        
        <!-- footer  -->
        
        <div id="footer"></div>
        </div>
      </div>
      
    </div>
    <!-- jQuery -->
    <script src="/css/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <#--<script src="/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>-->
    <!-- FastClick -->
    <script src="/css/gentelella-master/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <!-- <script src="/css/gentelella-master/vendors/nprogress/nprogress.js"></script> -->
    <!-- Chart.js -->
    <script src="/css/gentelella-master/vendors/Chart.js/dist/Chart.min.js"></script>
    <!-- gauge.js -->
    <script src="/css/gentelella-master/vendors/gauge.js/dist/gauge.min.js"></script>
    <!-- bootstrap-progressbar -->
    <script src="/css/gentelella-master/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <!-- iCheck -->
    <script src="/css/gentelella-master/vendors/iCheck/icheck.min.js"></script>
    <!-- Skycons -->
    <script src="/css/gentelella-master/vendors/skycons/skycons.js"></script>
    <!-- Flot -->
    <script src="/css/gentelella-master/vendors/Flot/jquery.flot.js"></script>
    <script src="/css/gentelella-master/vendors/Flot/jquery.flot.pie.js"></script>
    <script src="/css/gentelella-master/vendors/Flot/jquery.flot.time.js"></script>
    <script src="/css/gentelella-master/vendors/Flot/jquery.flot.stack.js"></script>
    <script src="/css/gentelella-master/vendors/Flot/jquery.flot.resize.js"></script>
    <!-- Flot plugins -->
    <script src="/css/gentelella-master/vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
    <script src="/css/gentelella-master/vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
    <script src="/css/gentelella-master/vendors/flot.curvedlines/curvedLines.js"></script>
    <!-- DateJS -->
    <script src="/css/gentelella-master/vendors/DateJS/build/date.js"></script>
    <!-- JQVMap -->
    <script src="/css/gentelella-master/vendors/jqvmap/dist/jquery.vmap.js"></script>
    <script src="/css/gentelella-master/vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
    <script src="/css/gentelella-master/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
    <!-- bootstrap-daterangepicker -->
    <script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>
    <script src="/css/gentelella-master/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>

    <!-- Custom Theme Scripts -->
    <#--<script src="/css/gentelella-master/build/js/custom.min.js"></script>-->
    
    <!-- common.js -->
    <#--<script src="/js/common.js"></script>-->
    <script type="text/javascript">
        $(function() {
            $("#header").load("/home/top.htm");
            $("#sidebar-menu").load("/home/menu.htm");
            $("#title").load("/home/title.htm");
            $("#content").load("/admin/operator/search.htm");
            $("#footer").load("/home/copyright.htm");
            $("#sidebar-menu").css('min-height', $(window).height());
            
        });
        function page_move(url,id){
            //$("#content").load("/admin/operator/detail.htm?id="+id);

            $.ajax({
                type : "GET",
                url : url,
                data : {"id":id},
                success : function(response){
                    $("#content").html(response);
                },
                error : function(){
                    console.log("error!!");
                    //err_page();
                    return false;
                }
            });
        }
    </script>
  </body>
</html>
