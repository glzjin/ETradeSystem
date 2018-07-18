<%@ page contentType="text/html;charset=UTF-8" language="java" %>
</section>
<!-- /.content -->

</div>
<!-- /.content-wrapper -->

<footer class="main-footer">
    界面由 AdminLTE  派生。
</footer>

<!-- Add the sidebar's background. This div must be placed
immediately after the control sidebar -->
<div class="control-sidebar-bg"></div>

</div>
<!-- ./wrapper -->

<!-- jQuery 3 -->
<script src="/assets/third/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="/assets/third/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- FastClick -->
<script src="/assets/third/bower_components/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="/assets/js/adminlte.min.js"></script>
<!-- Sparkline -->
<script src="/assets/third/bower_components/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
<!-- jvectormap  -->
<script src="/assets/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="/assets/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- SlimScroll -->
<script src="/assets/third/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- ChartJS -->
<script src="/assets/third/bower_components/chart.js/Chart.js"></script>
<!-- Blockies -->
<script src="/assets/third/bower_components/blockies/blockies.min.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="/assets/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- Bootstrap WYSIHTML5 Upload -->
<script src="/assets/third/bower_components/blueimp-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/assets/third/bower_components/blueimp-file-upload/js/jquery.iframe-transport.js"></script>
<script src="/assets/third/bower_components/blueimp-file-upload/js/jquery.fileupload.js"></script>
<script src="/assets/plugins/wysihtml5-image-upload/wysihtml5-image-upload.js"></script>
<!-- SimpleStarRating -->
<script src="/assets/plugins/SimpleStarRating/SimpleStarRating.js"></script>

<script>
    $(function () {
        $('.textarea').wysihtml5({
            "font-styles": false, //Font styling, e.g. h1, h2, etc. Default true
            "emphasis": false, //Italics, bold, etc. Default true
            "lists": false, //(Un)ordered lists, e.g. Bullets, Numbers. Default true
            "html": false, //Button which allows you to edit the generated HTML. Default false
            "link": false, //Button to insert a link. Default true
            "image": true, //Button to insert an image. Default true,
            "color": false //Button to change color of font
        });
    });
    $(function () {
        $('#item_img_uri').fileupload({
            dataType: 'json',
            done: function (e, data) {
                $('#item_img_uri_real').val(data.result.files[0].url);
                $('#item_img_upload').html("<p>已上传！</p><image class=\"img-responsive pad\" src=\"" + data.result.files[0].url +"\"/>");
            },
            add: function (e, data) {
                var goUpload = true;
                var uploadFile = data.files[0];
                if (!(/\.(bmp|jpg|png)$/i).test(uploadFile.name)) {
                    alert('您应该上传格式为 bmp，jpg, png 的文件。');
                    goUpload = false;
                }
                if (uploadFile.size > 2000000) { // 2mb
                    alert('请上传文件尺寸小于 2 MB 的文件。');
                    goUpload = false;
                }
                if (goUpload == true) {
                    data.submit();
                }
            },
        });
    });
</script>
<script>
    $(document).ready(function() {
        var url = window.location;
        var element = $('ul.sidebar-menu a').filter(function() {
            return this.href == url || (url.href.indexOf(this.href) == 0 && this.href.slice(this.href.lastIndexOf("/buyer/")) != "/buyer/");
        }).parent().addClass('active');
        if (element.is('li')) {
            element.addClass('active').parent().parent('li').addClass('active')
        }
    });
</script>
<script>
    $(function () {
        var icon = blockies.create({ // All options are optional
            seed: '${user.id}${user.userName}', // seed used to generate icon data, default: random
            color: '#dfe', // to manually specify the icon color, default: random
            bgcolor: '#aaa', // choose a different background color, default: white
            size: 11, // width/height of the icon in blocks, default: 10
            scale: 3 // width/height of each block in pixels, default: 5
        });
        $('#user-header-img-small').html(icon);

        var icon2 = blockies.create({ // All options are optional
            seed: '${user.id}${user.userName}', // seed used to generate icon data, default: random
            color: '#dfe', // to manually specify the icon color, default: random
            bgcolor: '#aaa', // choose a different background color, default: white
            size: 20, // width/height of the icon in blocks, default: 10
            scale: 3 // width/height of each block in pixels, default: 5
        });
        $('#user-header-img-large').html(icon2);
    });
</script>
<script>
    var ratings = document.getElementsByClassName('rating');

    for (var i = 0; i < ratings.length; i++) {
        var r = new SimpleStarRating(ratings[i]);

        ratings[i].addEventListener('rate', function(e) {
            $("#" + e.target.getAttribute("data-bind")).val(e.detail);
        });
    }
</script>
</body>
</html>
