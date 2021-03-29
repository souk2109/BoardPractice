<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <script src="/board002/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/board002/resources/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="/board002/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="/board002/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="/board002/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/board002/resources/dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
        
        $(".sidebar-nav")
    		.attr("class","sidebar-nab navbar-collapse collapse")
        	.attr("aria-expanded",'false')
        	.attr("style", "height:1px");
    });
    </script>

</body>

</html>
    