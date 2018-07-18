<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 引入 jstl 支持 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include flush="true" page="../../includes/seller/header.jsp" />

    <div class="box box-default">
        <div class="box-header with-border">
            <h3 class="box-title">商品编辑</h3>
        </div>
        <form role="form" action="/seller/items/edit?page=${currentPage}&id=<%=request.getParameter("id")%>" method="post">
            <jsp:include flush="true" page="../../includes/form.jsp" />
            <div class="box-body">
                <div class="form-group">
                    <label>商品名</label>
                    <input type="text" class="form-control" name="item_name" value="${Item.itemName}" required maxlength="16">
                </div>
                <div class="form-group">
                    <label>商品价格</label>
                    <input type="number" class="form-control" name="item_price" value="${Item.itemPrice}" required min="0.01" max="9999.99" step="0.01">
                </div>
                <div class="form-group">
                    <label>商品库存</label>
                    <input type="number" class="form-control" name="item_sku" value="${Item.itemSku}" required min="0" max="999999" step="1" onchange="if(/\D/.test(this.value)){alert('只能输入数字');this.value=parseInt(this.value, 10);}"
                </div>
                <div class="form-group">
                    <label>商品头图</label>
                    <div id="item_img_upload"><input type="file" name="files[]" id="item_img_uri" accept="image/png,image/jpg,image/bmp" data-url="https://etrade-image.wetolink.com/index.php"></div>
                    <input type="text" class="form-control" id="item_img_uri_real" name="item_img_uri" style="display:none">

                    <p class="help-block"> 只允许上传 jpg, bmp, png 文件, 如不需要编辑则请不要上传</p>
                </div>
                <div class="form-group">
                    <label>商品描述</label>
                    <textarea name="item_description" class="textarea" style="width: 100%; height: 200px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;">${Item.itemDescription}</textarea>
                </div>
            </div>
            <!-- /.box-body -->

            <div class="box-footer">
                <button type="submit" class="btn btn-primary">保存</button>
            </div>
        </form>
    </div>
    <!-- /.box-default -->

<jsp:include flush="true" page="../../includes/seller/footer.jsp" />