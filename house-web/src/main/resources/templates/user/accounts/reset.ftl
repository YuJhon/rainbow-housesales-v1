<!DOCTYPE html>
<html lang="en">
    <#--  引入头部公共文件  -->
    <@common.header/>
    <body class="page-sub-page page-create-account page-account" id="page-top">
        <div class="wrapper">
            <#--  Navigation  -->
            <@common.nav />
            <#--  Page content  -->
            <div id="page-content">
                <#--  Breadcrumb  -->
                <div class="container">
                    <ol class="breadcrumb">
                        <li><a href="#">Home</a></li>
                        <li class="active">重置密码</li>
                    </ol>
                </div>
                <#--  End BreadCrumb  -->
                <div class="container">
                    <header><h1>为${email!}重置密码</h1><header>
                    <div class="row">
                        <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                            <form role="form" id="form-create-account" method="post" action="/accounts/resetSubmit">
                                <div class="form-group">
                                    <label for="form-create-account-password">密码:</label>
                                    <input type="password" name="passwd" class="form-control" id="form-create-account-password" required />
                                </div>
                                <div class="form-group">
                                    <label for="form-create-account-password">确认密码:</label>
                                    <input type="password" name="confirmPasswd"  class="form-control" id="form-create-account-password" required />
                                </div>
                                <input type="hidden" value="${email!}" name="email" />
                                <input type="hidden" value="${success_key!}" name="key" />
                                <div class="form-group clearfix">
                                    <button type="submit" class="btn pull-right btn-default" id="account-submit">登录</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div><#--  ./container  -->
            </div> 
            <#--  End Page Content  -->   
            <#--  Page Footer  -->
            <@common.footer />
            <#--  End Page Footer  -->
        </div>
        <@common.js/>
        <!--[if gt IE 8]>
        <script type="text/javascript" src="/assets/js/ie.js"></script>
        <![endif]-->
        <script  type="text/javascript" >
            $(document).ready(function() {
                var errorMsg   = "${errorMsg!""}";
                var successMsg = "${successMsg!""}";
                if(errorMsg){ 
                    errormsg("error",errorMsg);
                }
                if(successMsg) {
                    successmsg("success",successMsg);
                }
            });
        </script>
    </body>
</html>